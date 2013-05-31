/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.table.TableModel;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;
import org.jfree.report.JFreeReport;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import unisave2006.GlobalSetting;
import unisave2006.UserSettingListener;
import unisave2006.XMLSupport;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.MeasurementEntryFactory;
import unisave2006.units.Unit;

/**
 * @author David Je�ek
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_SSH78BGIEduLIuzFGBOiyA"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public abstract class Measurement implements Observer{
    
    public void update(Observable o, Object arg) {
        setModified(true);
    }

    private Long id;
    protected List<MeasurementEntry> elements = new ArrayList<MeasurementEntry>();
    protected boolean modified = false;
    protected Vector<MeasurementEntryListener> measurementEntryListeners = new Vector<MeasurementEntryListener>(10); 
    protected Vector<MeasurementSettingListener> measurementSettingListeners = new Vector<MeasurementSettingListener>(10); 
    
    protected boolean autoIndex = false;
    protected boolean autoConvert = false;
    protected Unit autoConvertUnit;
    protected File file = null;
    protected Vector<Integer> allowedEntityTypes = null;
    
    protected ProtocolSetting protocolSetting =  new ProtocolSetting();
    protected UserSettingListener userSettingListener = null;
    
    public Measurement(){
    	protocolSetting.addObserver(this);
        protocolSetting.updateFrom(GlobalSetting.getUserSetting().getLastUsedProtocolSetting());
        protocolSetting.dateOfMeasurement = new Date(); 
        allowedEntityTypes = new Vector<Integer>(10);
        allowedEntityTypes.add(MeasurementEntryFactory.VALUE);
        allowedEntityTypes.add(MeasurementEntryFactory.NAMED_VALUE);
        allowedEntityTypes.add(MeasurementEntryFactory.XY_VALUE);
        allowedEntityTypes.add(MeasurementEntryFactory.TEXT);
        allowedEntityTypes.add(MeasurementEntryFactory.BLOCK_SEPARATOR);
        allowedEntityTypes.add(MeasurementEntryFactory.STATISTIC);
        
        autoConvert = GlobalSetting.getUserSetting().getLastUsedAutoConvert();
        autoIndex = GlobalSetting.getUserSetting().getLastUsedAutoIndex();
        autoConvertUnit = new Unit(GlobalSetting.getUserSetting().getLastUsedUnit());
        userSettingListener = new UserSettingListener(){
			public void numberFormatChanged() {
				for(MeasurementEntry e: elements){
					e.updateCachedValueStrings();
				}
				fireEntriesChanged();
			}
			public void portNamesChanged() {
			}
        	
        };
        GlobalSetting.getUserSetting().addUserSettingListener(userSettingListener);
    }
    
    
    public int getType(){
        return MeasurementFactory.MEASUREMENT_NONE;
    }
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public void setElements(List<MeasurementEntry> elements) {
    	this.elements = elements;
    }
    
    public boolean getAutoConvert() {
        return autoConvert;
    }

    public void setAutoConvert(boolean autoConvert) {
        this.autoConvert = autoConvert;
        setModified(true);
        fireAutoConvertChanged();
    }

    public Unit getAutoConvertUnit() {
        return autoConvertUnit;
    }

    public void setAutoConvertUnit(Unit autoConvertUnit) {
        this.autoConvertUnit = autoConvertUnit;
        setModified(true);
        fireAutoConvertUnitChanged();
    }

    public boolean getAutoIndex() {
        return autoIndex;
    }

    public void setAutoIndex(boolean autoIndex) {
        this.autoIndex = autoIndex;
        setModified(true);
        fireAutoindexingChanged();
    }

    public UserSettingListener getUserSettingListener() {
    	return userSettingListener;
    }
    public void setUserSettingListener(UserSettingListener userSettingListener) {
    	this.userSettingListener = userSettingListener;
    }
    
    public void setModified(boolean modified) {
        if(this.modified != modified){
            this.modified = modified;
            fireModifiedChanged();
        }
    }

    public boolean hasAssignedFile(){
        return file != null;
    }
    public void setFile(File f){
        if(f != null)
            file = f;
    }
    
    public int saveToXML(){
    	GlobalSetting.getUserSetting().getLastUsedProtocolSetting().updateFrom(getProtocolSetting());
        if(file == null)
            return -1;
        try {
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
             int ret = saveToXML(w);
             w.close();
             return ret;
        } catch (IOException e) {
            return -1;
        }
    }
    public int saveToXML(BufferedWriter w){
        MeasurementXMLWriter xmlW = getXMLWriter(w);
        try {
            xmlW.startFileTag();
            xmlW.startMeasurementTag();
            xmlW.storeMeasurementBody();
            xmlW.endMeasurementTag();
            xmlW.endFileTag();
        } catch (IOException e) {
            return -1;
        }
        setModified(false);
        return 0;
    }
    protected MeasurementXMLWriter getXMLWriter(BufferedWriter w)
    {
        return new MeasurementXMLWriter(w);
    }
    class MeasurementXMLWriter {
        protected int offset = 0;
        protected BufferedWriter w = null;
        public MeasurementXMLWriter(BufferedWriter w){
            this.w = w;
        }
        public void startFileTag() throws IOException {
            write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            write("<unisave_mesurments file_version=\"3\">");
            offset++;
        }

        public void endFileTag() throws IOException {
            offset--;
            write("</unisave_mesurments>");
        }
        public void startMeasurementTag() throws IOException {
            write("<measurement type=\""+ getType() +"\">");
            offset++;
        }
        public void endMeasurementTag() throws IOException {
            offset--;
            write("</measurement>");
        }
        public void storeMeasurementBody() throws IOException {
            write("<autoConvert value=\""+ autoConvert +"\"/>");
            write("<autoConvertUnit prefix=\"" + autoConvertUnit.getPrefix().getExponent() + "\" description=\"" + autoConvertUnit.getDescription().getId() + "\"/>");
            write("<autoIndex value=\""+ autoIndex +"\"/>");
            protocolSetting.storeToXML(w, offset);
            write("<mesurmentEntries>");
            offset++;
            for(MeasurementEntry e: elements){
                e.storeToXML(w, offset);
            }
            offset--;
            write("</mesurmentEntries>");
            
        }
        protected String escape(String s){
            return XMLSupport.escape(s);
        }
        protected void write(String s) throws IOException{
            XMLSupport.write(s, w, offset);
        }
    }
    class MeasurementSAXHandler extends DefaultHandler{
        
        protected StructuredXMLLoader loader;
        private boolean entries = false;

        public MeasurementSAXHandler(StructuredXMLLoader loader){
            this.loader = loader;
        }
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equals("measurement")){
            	initAfterLoad();
                loader.popHandler();
            }
            else if(qName.equals("mesurmentEntries"))
                entries = false;
            else 
                super.endElement(uri, localName, qName);
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equals("autoConvert")){
                autoConvert = Boolean.parseBoolean(attributes.getValue("value")); 
            }else if(qName.equals("autoConvertUnit")){
                try{
                    int prefix = Integer.parseInt(attributes.getValue("prefix"));
                    int description = Integer.parseInt(attributes.getValue("description"));
                    autoConvertUnit = new Unit(prefix, description);
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba p�i na��t�n� m��en�.");
                }
            }else if(qName.equals("autoIndex")){
                autoIndex = Boolean.parseBoolean(attributes.getValue("value")); 
            }else if(qName.equals("protocolSetting")){
                loader.pushHandler(protocolSetting.getSAXHandler(loader)); 
            }else if(qName.equals("mesurmentEntries")){
                entries  = true; 
            }else if(entries && qName.equals("mesurmentEntry")){
                try{
                    int type = Integer.parseInt(attributes.getValue("type"));
                    MeasurementEntry e = MeasurementEntryFactory.getMeasurementEntry(type);
                    elements.add(e);
                    loader.pushHandler(e.getSAXHandler(loader));
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba p�i na��t�n� m��en�.");
                }
            }else
                super.startElement(uri, localName, qName, attributes);
        }
        
    }
    public DefaultHandler getSAXHandler(StructuredXMLLoader loader){
        return new MeasurementSAXHandler(loader);
    }
    
    protected void initAfterLoad() {
		
	}
	public TableModel getTableModel(){
        return null;
    }
    
    public boolean getModified(){
        return modified;
    }
    
    public void addMeasurementEntryListener(MeasurementEntryListener l){
        measurementEntryListeners.add(l);
    }
    
    public void removeMeasurementEntryListener(MeasurementEntryListener l){
        measurementEntryListeners.remove(l);
    }
    public void addMeasurementSettingListener(MeasurementSettingListener l){
        measurementSettingListeners.add(l);
    }
    
    public void removeMeasurementSettingListener(MeasurementSettingListener l){
        measurementSettingListeners.remove(l);
    }
    
    
    public void addEntry(MeasurementEntry e){
        elements.add(e);
        setModified(true);
        fireEntryAdded(e);
    }
    public void addEntries(Collection<MeasurementEntry> e){
        elements.addAll(e);
        setModified(true);
        fireEntriesAdded(e);
    }
    
    public void deleteAllValues(){
        elements.clear();
        setModified(true);
        fireEntriesDeleted();
    }
    
    public void deleteValue(int i){
       MeasurementEntry e = this.elements.remove(i);
       setModified(true);
       fireEntryDeleted(i, e);
    }
    public void deleteLast(){
        deleteValue(elements.size()-1);
    }
    public void deleteValues(Collection<MeasurementEntry> c){
        elements.removeAll(c);
        setModified(true);
        fireEntriesDeleted();
    }
    public MeasurementEntry getElementAt(int i) {
        return elements.get(i);
    }
    public List<MeasurementEntry> getElements() {
        return elements;
    }
    
    public void insertEntry(MeasurementEntry e, int position){
        elements.add(position, e);
        setModified(true);
        fireEntriesChanged();
    }
    
    protected void fireEntryAdded(MeasurementEntry e){
        for(MeasurementEntryListener l: measurementEntryListeners){
            l.entryAdded(e);
        }
    }
    protected void fireEntryDeleted(int index, MeasurementEntry e){
        for(MeasurementEntryListener l: measurementEntryListeners){
            l.entryDeleted(index, e);
        }
    }
    public void fireEntryChanged(int index, MeasurementEntry e){
        for(MeasurementEntryListener l: measurementEntryListeners){
            l.entryChanged(index, e);
        }
    }
    protected void fireEntriesDeleted(){
        for(MeasurementEntryListener l: measurementEntryListeners){
            l.entriesDeleted();
        }
    }
    public void fireEntriesChanged(){
        for(MeasurementEntryListener l: measurementEntryListeners){
            l.entriesChanged();
        }
    }
    protected void fireEntriesAdded(Collection<MeasurementEntry> v){
        for(MeasurementEntryListener l: measurementEntryListeners){
            l.entriesAdded(v);
        }
    }
    protected void fireModifiedChanged(){
        for(MeasurementSettingListener l: measurementSettingListeners){
            l.modifiedChanged();
        }
    }
    protected void fireAutoindexingChanged(){
        for(MeasurementSettingListener l: measurementSettingListeners){
            l.autoindexingChanged();
        }
    }
    protected void fireAutoConvertChanged(){
        for(MeasurementSettingListener l: measurementSettingListeners){
            l.autoConvertChanged();
        }
    }
    protected void fireAutoConvertUnitChanged(){
        for(MeasurementSettingListener l: measurementSettingListeners){
            l.autoConvertUnitChanged();
        }
    }
    public abstract void reindex();
    
    public abstract JFreeChart getchart();
    public abstract boolean isChartCreated();
    
    public abstract JFreeReport getReport();

    public String getFileName() {
        if(file != null)
            return file.getPath();
        return "";
    }
    public File getFile() {
        return file;
    }
    
    public Collection<Integer> getAllowedEntityTypes(){
        return allowedEntityTypes;
    }
    public void setAllowedEntityTypes(Vector<Integer> allowedEntityType) {
    	this.allowedEntityTypes = allowedEntityType;
    }
    
    protected Dataset dataset = null;
    public Dataset getDataset(){
        return dataset;
    }
    
    public int countEntityByType(int type){
        int count = 0;
        for(MeasurementEntry e : elements){
           if(e.getClassType() == type)
               count++;
        }
        return count;
    }
    public ProtocolSetting getProtocolSetting() {
        return protocolSetting;
    }
    public void setProtocolSetting(ProtocolSetting protocolSetting) {
        if(this.protocolSetting != null)
            this.protocolSetting.deleteObserver(this);
        this.protocolSetting = protocolSetting;
        protocolSetting.addObserver(this);
    }
    
    public void detache(){
    	GlobalSetting.getUserSetting().removeUserSettingListener(userSettingListener);
    }
}
