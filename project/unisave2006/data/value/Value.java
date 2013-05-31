/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data.value;

import java.io.BufferedWriter;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import unisave2006.GlobalSetting;
import unisave2006.XMLSupport;
import unisave2006.data.MeasurementFormatException;
import unisave2006.data.StructuredXMLLoader;
import unisave2006.units.Unit;
import unisave2006.units.UnitConversion;
import unisave2006.units.UnitDescription;
import unisave2006.units.UnitPrefix;

/**
 * @author David Je�ek
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_eulDwBETEdu-TcvEGVchxA"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class Value implements MeasurementEntry {
    
    
	/**
     * Comment for <code>unit</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private Unit unit = Unit.getEmptyUnit();

    public Value() {

    }

    public Value(Value v) {
    	this();
		this.copyFrom(v);
	}

	/**
     * @return Returns the unit.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * @param theUnit The unit to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public void setUnit(Unit theUnit) {
        unit = theUnit;
        valueChanged();
    }

    /**
     * Comment for <code>valueVal</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private double valueVal;

    /**
     * @return Returns the valueVal.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public double getValueVal() {
        return valueVal;
    }

    /**
     * @param theValue The valueVal to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public void setValueVal(double theValue) {
        valueVal = theValue;
        valueChanged();
    }

    /**
     * Comment for <code>indexVal</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private int indexVal;

    /**
     * @return Returns the indexVal.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public int getIndexVal() {
        return indexVal;
    }

    /**
     * @param theIndex The indexVal to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public void setIndexVal(int theIndex) {
        indexVal = theIndex;
    }
    
    public double getTrueValue(){
        return unit.getPrefix().getTrueValue(valueVal);
    }
    
    public void convert(UnitDescription to, UnitPrefix targetPrefix){
        convert(unit.getDescription().GetConverzionTo(to), targetPrefix);
    }
    public void convert(UnitConversion uc, UnitPrefix targetPrefix){
        if(uc == null)
            return;
        if(!(uc.getFrom().equals(getUnit().getDescription())))
            return;
        valueVal = uc.convert(getTrueValue());
        valueVal = targetPrefix.convert(valueVal);

        getUnit().setPrefix(targetPrefix);
        getUnit().setDescription(uc.getTo());
        valueRepresentationChanfed();
    }

    public int getClassType() {
        return MeasurementEntryFactory.VALUE;
    }
    
    public String getValueAsString(){
        if(cachedValueString == null)
            updateCachedString();
        return cachedValueString;
    }
    public String getValueAsStringWthoutUnit(){
        if(cachedOnlyValueString == null)
            updateCachedString();
        return cachedOnlyValueString;
    }
    
    public String toString(){
        return new String(getIndexVal() + ": " + valueVal + unit.getShortcut());
    }

    public MeasurementEntry cloneInstance() {
        Value v = new Value();
        v.indexVal = indexVal;
        v.valueVal = valueVal;
        v.unit = new Unit(unit);
        return v;
    }
    
    protected double cacheRecentTransferValue = 0;
    protected Unit cacheRecentTransferUnit = null;
    public double getValueInUnit(Unit u) throws ConversionException{
        if(cacheRecentTransferUnit != null && cacheRecentTransferUnit.equals(u))
            return cacheRecentTransferValue;
        if(unit.getDescription().isConvertibleTo(u.getDescription())){
            UnitConversion uc = unit.getDescription().GetConverzionTo(u.getDescription());
            double v = uc.convert(getTrueValue());
            v = u.getPrefix().convert(v);
            cacheRecentTransferValue = v;
            cacheRecentTransferUnit = new Unit(u);
            cachedValueInRecentUnit = null;
        }
        else{
            throw new ConversionException(String.format("Nelze převést %s na %s.", unit.toString(), u.toString()));
        }
        return cacheRecentTransferValue;
    }
    
    protected Double cachedValue = null;
    public Number getValueAsNumber(){
        if(cachedValue == null)
            cachedValue = new Double(valueVal);
        return cachedValue;
    }
    
    protected Double cachedValueInRecentUnit = null;
    public Number getValueAsNumberInUnit(Unit u) throws ConversionException{
        if(cachedValueInRecentUnit == null || cacheRecentTransferUnit == null || !cacheRecentTransferUnit.equals(u))
            cachedValueInRecentUnit = new Double(getValueInUnit(u));
        return cachedValueInRecentUnit;
    }
    protected void valueChanged(){
        cachedValue = null;
        cachedValueInRecentUnit = null;
        cacheRecentTransferUnit = null;
        cachedOnlyValueString = null;
        cachedValueString = null;
    }
    protected void valueRepresentationChanfed(){
        cachedValue = null;
        cachedOnlyValueString = null;
        cachedValueString = null;
    }

    protected String cachedOnlyValueString = null;
    protected String cachedValueString = null;
    protected void updateCachedString(){
        cachedOnlyValueString = String.format(GlobalSetting.getUserSetting().getNumberFormat(), valueVal);
        if(!GlobalSetting.getUserSetting().isFillByZerro()){
        	StringBuilder sb = new StringBuilder(cachedOnlyValueString);
        	int i = sb.length()-1;
        	while(i > 0 && sb.charAt(i) == '0')
        		i--;
        	if(i>0 && (sb.charAt(i) == '.' || sb.charAt(i) == ','))
        		i--;
        	sb.delete(i+1, sb.length());
        	cachedOnlyValueString = sb.toString();
        }
        cachedValueString = cachedOnlyValueString + unit.getShortcut();
    }
    public static void main(String []arg){
    	Value v = new Value();
    	v.setValueVal(0.100);
    	v.updateCachedString();
    	System.out.println(v.cachedOnlyValueString);
    	v.setValueVal(0);
    	v.updateCachedString();
    	System.out.println(v.cachedOnlyValueString);
    	v.setValueVal(2.00);
    	v.updateCachedString();
    	System.out.println(v.cachedOnlyValueString);
    	v.setValueVal(2.1588);
    	v.updateCachedString();
    	System.out.println(v.cachedOnlyValueString);
    	
    }
    public void storeToXML(BufferedWriter w, int offset) throws IOException {
        XMLSupport.write("<mesurmentEntry type=\"" + getClassType() + "\">", w, offset);
        offset++;
        XMLSupport.write("<indexVal valueVal=\"" + indexVal + "\"/>", w, offset);
        XMLSupport.write("<valueVal valueVal=\"" + valueVal + "\"/>", w, offset);
        XMLSupport.write("<unit prefix=\"" + unit.getPrefix().getExponent() + "\" description=\"" + unit.getDescription().getId() + "\"/>", w, offset);
        offset--;
        XMLSupport.write("</mesurmentEntry>", w, offset);
    }

    class ValueSAXHandler extends DefaultHandler{

        protected StructuredXMLLoader loader;
        public ValueSAXHandler(StructuredXMLLoader loader) {
            super();
            this.loader = loader;
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equals("mesurmentEntry"))
                loader.popHandler();
            else 
                super.endElement(uri, localName, qName);
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equals("indexVal")){
                try{
                    indexVal = Integer.parseInt(attributes.getValue("valueVal"));
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba p�i na��t�n� m��en�.");
                }
            }else if(qName.equals("valueVal")){
                try{
                    valueVal = Double.parseDouble(attributes.getValue("valueVal"));
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba p�i na��t�n� m��en�.");
                }
            }else if(qName.equals("unit")){
                try{
                    int prefix = Integer.parseInt(attributes.getValue("prefix"));
                    int description = Integer.parseInt(attributes.getValue("description"));
                    unit.setDescription(GlobalSetting.getUnitSet().getUnitDescription(description));
                    unit.setPrefix(GlobalSetting.getPrefixSet().getPrefix(prefix));
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba p�i na��t�n� m��en�.");
                }
            }else
                super.startElement(uri, localName, qName, attributes);
        }
        
    }
    public DefaultHandler getSAXHandler(StructuredXMLLoader loader) {
        return new ValueSAXHandler(loader);
    }

	public String getClipboardStringInerr() {
		getValueAsString();
		return cachedOnlyValueString + "\t" + getUnit().getShortcut();
	}

    public String getClipboardString() {
    	getValueAsString();
		return indexVal + "\t" + cachedOnlyValueString + "\t" + getUnit().getShortcut();
	}

	public void updateCachedValueStrings() {
		valueChanged();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null)
			return false;
		if(obj instanceof Value){
			Value v = (Value)obj;
			try {
				return v.getValueInUnit(getUnit()) == getValueVal();
			} catch (ConversionException e) {
				return false;
			}
		}
		return super.equals(obj);
	}
	public void copyFrom(Value v){
		if(v == null){
			Unit uu = Unit.getEmptyUnit();
			this.setIndexVal(0);
			this.getUnit().setDescription(uu.getDescription());
			this.getUnit().setPrefix(uu.getPrefix());
			this.setValueVal(0);
		}
		else {
			this.setIndexVal(v.indexVal);
			this.getUnit().setDescription(v.getUnit().getDescription());
			this.getUnit().setPrefix(v.getUnit().getPrefix());
			this.setValueVal(v.getValueVal());
		}
		this.valueChanged();
	}
}
