/*
 * Created on 1.10.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import unisave2006.data.GraphSetting;
import unisave2006.data.MeasurementFormatException;
import unisave2006.data.ProtocolSetting;
import unisave2006.data.StructuredXMLLoader;
import unisave2006.data.value.Value;
import unisave2006.device.MeasurementDevice;
import unisave2006.units.Unit;

public class UserSetting {
	protected Long id;
    protected File lastUsedDir = null;
    protected String lastUsedDirPath;
    protected Set<PortName> portNames = new HashSet<PortName>();
    protected Vector<File> recentFiles = new Vector<File>();
    protected Set<String> recentFilesPath = new HashSet<String>();
    protected PortName lastUsedPort = new PortName("com1");
    protected String lastUsedPortName;
    protected MeasurementDevice lastUsedDevice = null;
    protected MeasurementDevice lastUsedSecondDevice = null;
    protected Unit lastUsedUnit;
    protected Unit lastUsedYUnit;
    protected String numberFormat = "%.2f";
    protected int maxNumberOfFloatDigit = 2;
    protected boolean exponentFormat = false;
    protected boolean fillByZerro = true;
    protected ProtocolSetting lastUsedProtocolSetting = new ProtocolSetting();
    protected GraphSetting lastUsedGraphSetting = new GraphSetting();
    protected String lastUsedXName = "Hodnota X";
    protected String lastUsedYName = "Hodnota Y";
    protected boolean lastUsedAutoConvert;
    protected boolean lastUsedAutoIndex;
    
    protected Value lastUsedXMarker;
    protected Value lastUsedYMarker;
    protected boolean lastUsedXMarkerFixed = true;
    protected boolean lastUsedYMarkerFixed = false;
    
    protected Vector<UserSettingListener> listeners = new Vector<UserSettingListener>();
    
    public static UserSetting initialize() {
    	UserSetting us = new UserSetting();
    	us.setLastUsedUnit(Unit.getEmptyUnit());
    	us.setLastUsedYUnit(Unit.getEmptyUnit());
    	us.setLastUsedDir(new File(System.getProperty("user.home")));
        us.setLastUsedDevice(GlobalSetting.getMeasurementDeviceSet().getMeasurementDevice(0));
        us.setLastUsedSecondDevice(GlobalSetting.getMeasurementDeviceSet().getMeasurementDevice(0));
        us.getPortNames().add(new PortName("com1"));
        us.getPortNames().add(new PortName("com2"));
        us.getPortNames().add(new PortName("com3"));
        us.getPortNames().add(new PortName("com4"));
        us.setLastUsedXMarker(new Value());
        us.setLastUsedYMarker(new Value());
        return us;
    }
    
    public UserSetting(){

    }
    
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    
    public void storeToXML(BufferedWriter out) throws IOException{
        UserSettingXMLWriter w = new UserSettingXMLWriter(out);
        w.store();
    }
    class UserSettingXMLWriter {
        protected int offset = 0;
        protected BufferedWriter w = null;
        public UserSettingXMLWriter(BufferedWriter w){
            this.w = w;
        }
        public void startFileTag() throws IOException {
            write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            write("<unisave_user_setting file_version=\"1\">");
            offset++;
        }

        public void endFileGat() throws IOException {
            offset--;
            write("</unisave_user_setting>");
        }
        public void storeUserSettingBody() throws IOException {
            write("<lastUsedDir value=\""+ XMLSupport.escape(lastUsedDir.getAbsolutePath()) +"\"/>");
            write("<recentFiles>");
            offset++;
            for(File f: recentFiles){
                write("<recentFile value=\""+ escape(f.getAbsolutePath()) +"\"/>");
            }
            offset--;
            write("</recentFiles>");
            write("<portList>");
            offset++;
            for(PortName p: portNames){
                write("<portName name=\""+ escape(p.getName()) + "\" fileName=\"" + escape(p.getFileName()) + "\"/>");
            }
            offset--;
            write("</portList>");
            write("<lastUsedPort value=\""+ lastUsedPort +"\"/>");
            //--PRIDANY KOD
            //if (GlobalSetting.getMeasurementDeviceSet().getIndexOf(lastUsedDevice) == 7)
            write("<lastUsedDevice value=\""+ 0 +"\"/>");
            //else            
            //write("<lastUsedDevice value=\""+ Integer.toString(GlobalSetting.getMeasurementDeviceSet().getIndexOf(lastUsedDevice)) +"\"/>");
            //--KONEC PRIDANEHO KODU
            //if(GlobalSetting.getMeasurementDeviceSet().getIndexOf(lastUsedSecondDevice) == 7)
            write("<lastUsedSecondDevice value=\"" + 0 + "\"/>");
            //else
            //write("<lastUsedSecondDevice value=\"" + Integer.toString(GlobalSetting.getMeasurementDeviceSet().getIndexOf(lastUsedSecondDevice)) + "\"/>");
            //if (lastUsedUnit == null)
            	write("<lastUsedUnit prefix=\""+ 0 +"\" description=\"" + 0 + "\"/>");
            //else
            //write("<lastUsedUnit prefix=\""+ lastUsedUnit.getPrefix().getExponent() +"\" description=\"" + lastUsedUnit.getDescription().getId() + "\"/>");
            //if(lastUsedYUnit == null)
            	write("<lastUsedYUnit prefix=\""+ 0 +"\" description=\"" + 0 + "\"/>");
            //else
            //write("<lastUsedYUnit prefix=\""+ lastUsedYUnit.getPrefix().getExponent() +"\" description=\"" + lastUsedYUnit.getDescription().getId() + "\"/>");
            write("<numberFormat>");
            offset++;
            write("<maxNumberOfFloatDigit value=\""+ Integer.toString(maxNumberOfFloatDigit) +"\"/>");
            write("<exponentFormat value=\""+ Boolean.toString(exponentFormat) +"\"/>");
            write("<fillByZerro value=\""+ Boolean.toString(fillByZerro) +"\"/>");
            offset--;
            write("</numberFormat>");
            write("<lastUsedXName value=\""+ escape(lastUsedXName) + "\"/>");
            write("<lastUsedYName value=\""+ escape(lastUsedYName) + "\"/>");
            write("<lastUsedAutoConvert value=\""+ lastUsedAutoConvert + "\"/>");
            write("<lastUsedAutoIndex value=\""+ lastUsedAutoIndex + "\"/>");
            if(lastUsedXMarker != null){
	            XMLSupport.write("<lastUsedXMarker>", w, offset);
	            offset++;
	            lastUsedXMarker.storeToXML(w, offset);
	            offset--;
	            XMLSupport.write("</lastUsedXMarker>", w, offset);
            }
            else
            	XMLSupport.write("<lastUsedXMarker value=\"null\"/>", w, offset);
            if(lastUsedYMarker != null){
	            XMLSupport.write("<lastUsedYMarker>", w, offset);
	            offset++;
	            lastUsedYMarker.storeToXML(w, offset);
	            offset--;
	            XMLSupport.write("</lastUsedYMarker>", w, offset);
            }
            else
            	XMLSupport.write("<lastUsedYMarker value=\"null\"/>", w, offset);
            XMLSupport.write("<lastUsedXMarkerFixed value=\"" + lastUsedXMarkerFixed + "\"/>", w, offset);
            XMLSupport.write("<lastUsedYMarkerFixed value=\"" + lastUsedYMarkerFixed + "\"/>", w, offset);
            lastUsedProtocolSetting.storeToXML(w, offset);
            lastUsedGraphSetting.storeToXML(w, offset);
        }
        protected String escape(String s){
            return XMLSupport.escape(s);
        }
        protected void write(String s) throws IOException{
            XMLSupport.write(s, w, offset);
        }
        public void store() throws IOException{
            startFileTag();
            storeUserSettingBody();
            endFileGat();
            w.flush();
        }
    }
    class UserSettingSAXHandler extends DefaultHandler{
        
        private StructuredXMLLoader loader;
        private boolean recentFilesTag = false;
        private boolean portList = false;
        private boolean numberFormat = false;

        public UserSettingSAXHandler(StructuredXMLLoader loader){
            this.loader = loader;
        }
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equals("unisave_user_setting")){
                for(PortName p: portNames){
                    if(lastUsedPort.getName().equals(p.getName())){
                        lastUsedPort = p;
                    }
                }
                loader.popHandler();
            }
            else if(qName.equals("recentFiles"))
                recentFilesTag = false;
            else if(qName.equals("portList"))
                portList = false;
            else if(qName.equals("numberFormat")){
            	numberFormat = false;
            	updateNumberFormat();
            }
        }

		@Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equals("lastUsedDir")){
                lastUsedDir = new File(attributes.getValue("value")); 
            }else if(qName.equals("lastUsedPort")){
                lastUsedPort = new PortName(attributes.getValue("value"));
                
            }else if(qName.equals("lastUsedDevice")){
                lastUsedDevice = GlobalSetting.getMeasurementDeviceSet().getMeasurementDevice(Integer.parseInt(attributes.getValue("value"))); 
            }else if(qName.equals("lastUsedSecondDevice")){
                lastUsedSecondDevice = GlobalSetting.getMeasurementDeviceSet().getMeasurementDevice(Integer.parseInt(attributes.getValue("value"))); 
            }else if(qName.equals("lastUsedUnit")){
                try{
                    int prefix = Integer.parseInt(attributes.getValue("prefix"));
                    int description = Integer.parseInt(attributes.getValue("description"));
                    lastUsedUnit = new Unit(prefix, description);
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba pøi naèítání mìøení.");
                }
            }else if(qName.equals("lastUsedYUnit")){
                try{
                    int prefix = Integer.parseInt(attributes.getValue("prefix"));
                    int description = Integer.parseInt(attributes.getValue("description"));
                    lastUsedYUnit = new Unit(prefix, description);
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba pøi naèítání mìøení.");
                }
            }else if(qName.equals("numberFormat")){
                numberFormat = true; 
            }else if(numberFormat && qName.equals("maxNumberOfFloatDigit")){
            	try {
					maxNumberOfFloatDigit = Integer.parseInt(attributes.getValue("value"));
				} catch (NumberFormatException e) {
				} 
            }else if(numberFormat && qName.equals("exponentFormat")){
            	exponentFormat = Boolean.parseBoolean(attributes.getValue("value")); 
            }else if(numberFormat && qName.equals("fillByZerro")){
            	fillByZerro = Boolean.parseBoolean(attributes.getValue("value")); 
            }else if(qName.equals("protocolSetting")){
                loader.pushHandler(lastUsedProtocolSetting.getSAXHandler(loader)); 
            }else if(qName.equals("graphSetting")){
                loader.pushHandler(lastUsedGraphSetting.getSAXHandler(loader)); 
            }else if(qName.equals("recentFiles")){
                recentFilesTag  = true; 
            }else if(recentFilesTag && qName.equals("recentFile")){
                    recentFiles.add(new File(attributes.getValue("value")));
            }else if(qName.equals("portList")){
                portList  = true; 
                portNames.clear();
            }else if(portList && qName.equals("portName")){
                portNames.add(new PortName(attributes.getValue("name"), attributes.getValue("fileName")));
            }else if(qName.equals("lastUsedXName")){
                lastUsedXName = attributes.getValue("value"); 
            }else if(qName.equals("lastUsedYName")){
                lastUsedYName = attributes.getValue("value"); 
            }else if(qName.equals("lastUsedAutoConvert")){
                lastUsedAutoConvert = Boolean.parseBoolean(attributes.getValue("value")); 
            }else if(qName.equals("lastUsedAutoIndex")){
                lastUsedAutoIndex = Boolean.parseBoolean(attributes.getValue("value")); 
            }else if(qName.equals("lastUsedXMarker")){
            	if(attributes.getValue("value") != null && attributes.getValue("value").equals("null")){
            		lastUsedXMarker = null;
            	}
            	else
            		loader.pushHandler(lastUsedXMarker.getSAXHandler(loader)); 
            }else if(qName.equals("lastUsedYMarker")){
            	if(attributes.getValue("value") != null && attributes.getValue("value").equals("null")){
            		lastUsedYMarker = null;
            	}
            	else
            		loader.pushHandler(lastUsedYMarker.getSAXHandler(loader)); 
            }else if(qName.equals("lastUsedXMarkerFixed")){
            	lastUsedXMarkerFixed = Boolean.parseBoolean(attributes.getValue("value"));
            }else if(qName.equals("lastUsedYMarkerFixed")){
            	lastUsedYMarkerFixed = Boolean.parseBoolean(attributes.getValue("value")); 
            }
        }
        
    }
    public DefaultHandler getSAXHandler(StructuredXMLLoader loader){
        return new UserSettingSAXHandler(loader);
    }
    public Vector<File> getRecentFiles(){
        return recentFiles;
    }

    public void addRecentFile(File file) {
        recentFiles.remove(file);
        if(file != null)
            recentFiles.insertElementAt(file, 0);
        if(recentFiles.size() > GlobalSetting.getRecentFileMaxCount()){
            recentFiles.setSize(GlobalSetting.getRecentFileMaxCount());
        }
    }

    public Set<String> getRecentFilesPath() {
    	return recentFilesPath;
    }
    public void setRecentFilesPath(Set<String> recentFilesPath) {
    	this.recentFilesPath = recentFilesPath;
    }

    public String getLastUsedDirPath() {
    	return lastUsedDirPath;
    }
    public void setLastUsedDirPath(String lastUsedDirPath) {
    	this.lastUsedDirPath = lastUsedDirPath;
    }
    
    public File getLastUsedDir() {
        return lastUsedDir;
    }


    public GraphSetting getLastUsedGraphSetting() {
        return lastUsedGraphSetting;
    }


    public PortName getLastUsedPort() {
        return lastUsedPort;
    }

    public String getLastUsedPortName() {
    	return lastUsedPortName;
    }
    public void setLastUsedPortName(String lastUsedPortName) {
    	this.lastUsedPortName = lastUsedPortName;
    }

    public ProtocolSetting getLastUsedProtocolSetting() {
        return lastUsedProtocolSetting;
    }


    public Unit getLastUsedUnit() {
        return lastUsedUnit;
    }


    public Unit getLastUsedYUnit() {
        return lastUsedYUnit;
    }


    public MeasurementDevice getLastUsedDevice() {
        return lastUsedDevice;
    }


    public MeasurementDevice getLastUsedSecondDevice() {
        return lastUsedSecondDevice;
    }


    protected void updateNumberFormat() {
		numberFormat = String.format("%%.%d%c", maxNumberOfFloatDigit, (exponentFormat?'e':'f'));
		fireNumberFormatChanged();
	}

    public String getNumberFormat() {
        return numberFormat;
    }
    public void setNumberFormat(String numberFormat) {
    	this.numberFormat = numberFormat;
    }

    public Set<PortName> getPortNames() {
        return portNames;
    }


    public void setLastUsedDir(File lastUsedDir) {
        this.lastUsedDir = lastUsedDir;
    }


    public void setLastUsedGraphSetting(GraphSetting lastUsedGraphSetting) {
        this.lastUsedGraphSetting = lastUsedGraphSetting;
    }


    public void setLastUsedPort(PortName lastUsedPort) {
        this.lastUsedPort = lastUsedPort;
    }


    public void setLastUsedProtocolSetting(ProtocolSetting lastUsedProtocolSetting) {
        this.lastUsedProtocolSetting = lastUsedProtocolSetting;
    }


    public void setLastUsedUnit(Unit lastUsedUnit) {
        this.lastUsedUnit = lastUsedUnit;
    }


    public void setLastUsedYUnit(Unit lastUsedYUnit) {
        this.lastUsedYUnit = lastUsedYUnit;
    }


    public void setLastUsedDevice(MeasurementDevice lastUsetDevice) {
        this.lastUsedDevice = lastUsetDevice;
    }


    public void setLastUsedSecondDevice(MeasurementDevice lastUsetSecondDevice) {
        this.lastUsedSecondDevice = lastUsetSecondDevice;
    }


    public void setPortNames(Set<PortName> portNames) {
        this.portNames = portNames;
        firePortNamesChanged();
    }


    public void setRecentFiles(Vector<File> recentFiles) {
        this.recentFiles = recentFiles;
    }


    public boolean getLastUsedAutoConvert() {
        return lastUsedAutoConvert;
    }


    public void setLastUsedAutoConvert(boolean lastUsedAutoConvert) {
        this.lastUsedAutoConvert = lastUsedAutoConvert;
    }


    public boolean getLastUsedAutoIndex() {
        return lastUsedAutoIndex;
    }


    public void setLastUsedAutoIndex(boolean lastUsedAutoIndex) {
        this.lastUsedAutoIndex = lastUsedAutoIndex;
    }


    public String getLastUsedXName() {
        return lastUsedXName;
    }


    public void setLastUsedXName(String lastUsedXName) {
        this.lastUsedXName = lastUsedXName;
    }


    public String getLastUsedYName() {
        return lastUsedYName;
    }


    public void setLastUsedYName(String lastUsedYName) {
        this.lastUsedYName = lastUsedYName;
    }


	public boolean isExponentFormat() {
		return exponentFormat;
	}


	public void setExponentFormat(boolean exponentFormat) {
		this.exponentFormat = exponentFormat;
		updateNumberFormat();
	}


	public boolean isFillByZerro() {
		return fillByZerro;
	}


	public void setFillByZerro(boolean fillByZerro) {
		this.fillByZerro = fillByZerro;
		updateNumberFormat();
	}


	public int getMaxNumberOfFloatDigit() {
		return maxNumberOfFloatDigit;
	}
	public void setNumberFormat(int maxFloatDigitCount, boolean exponentFormat, boolean fillByZero){
		this.maxNumberOfFloatDigit = maxFloatDigitCount;
		this.exponentFormat = exponentFormat;
		this.fillByZerro = fillByZero;
		updateNumberFormat();
	}

	public void setMaxNumberOfFloatDigit(int maxNumberOfFloatDigit) {
		this.maxNumberOfFloatDigit = maxNumberOfFloatDigit;
		updateNumberFormat();
	}
	
	
	public void addUserSettingListener(UserSettingListener l){
		listeners.add(l);
	}
	public void removeUserSettingListener(UserSettingListener l){
		listeners.remove(l);
	}
	protected void firePortNamesChanged(){
		for(UserSettingListener l: listeners){
			l.portNamesChanged();
		}
	}
	protected void fireNumberFormatChanged(){
		for(UserSettingListener l: listeners){
			l.numberFormatChanged();
		}
	}


	public Value getLastUsedXMarker() {
		return lastUsedXMarker;
	}


	public void setLastUsedXMarker(Value lastUsedXMarker) {
		this.lastUsedXMarker = lastUsedXMarker;
	}


	public boolean isLastUsedXMarkerFixed() {
		return lastUsedXMarkerFixed;
	}


	public void setLastUsedXMarkerFixed(boolean lastUsedXMarkerFixed) {
		this.lastUsedXMarkerFixed = lastUsedXMarkerFixed;
	}


	public Value getLastUsedYMarker() {
		return lastUsedYMarker;
	}


	public void setLastUsedYMarker(Value lastUsedYMarker) {
		this.lastUsedYMarker = lastUsedYMarker;
	}


	public boolean isLastUsedYMarkerFixed() {
		return lastUsedYMarkerFixed;
	}


	public void setLastUsedYMarkerFixed(boolean lastUsedYMarkerFixed) {
		this.lastUsedYMarkerFixed = lastUsedYMarkerFixed;
	}
}
