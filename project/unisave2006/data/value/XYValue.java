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

import unisave2006.XMLSupport;
import unisave2006.data.MeasurementFormatException;
import unisave2006.data.MeasurementPOJO;
import unisave2006.data.StructuredXMLLoader;


/**
 * @author David Je�ek
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_RK7coBGDEduLIuzFGBOiyA"

 */
public class XYValue implements MeasurementEntry {
    
    
	/**
     * Comment for <code>xValue</code>

     */
    private Value xValue;

    /**
     * @return Returns the xValue.

     */
    public Value getXValue() {
        return xValue;
    }

    /**
     * @param theXValue The xValue to set.

     */
    public void setXValue(Value theXValue) {
        xValue = theXValue;
    }

    /**
     * Comment for <code>yValue</code>

     */
    private Value yValue;

    /**
     * @return Returns the yValue.

     */
    public Value getYValue() {
        return yValue;
    }

    /**
     * @param theYValue The yValue to set.

     */
    public void setYValue(Value theYValue) {
        yValue = theYValue;
    }

    /**
     * Comment for <code>indexXY</code>

     */
    private int indexXY;

    /**
     * @return Returns the indexXY.

     */
    public int getIndexXY() {
        return indexXY;
    }

    /**
     * @param theIndex The indexXY to set.

     */
    public void setIndexXY(int theIndex) {
        indexXY = theIndex;
    }

    private MeasurementPOJO measurement;
    
    public MeasurementPOJO getMeasurement() {
    	return measurement;
    }
    
    public void setMeasurement(MeasurementPOJO measurement) {
    	this.measurement = measurement;
    }
    
    public XYValue() {
    	
    }
    
    public int getClassType() {
        return MeasurementEntryFactory.XY_VALUE;
    }
    
    public String toString(){
        return new String(getIndexXY() + ": " + getXValue().getValueAsString() + " " + getYValue().getValueAsString());
    }

    public MeasurementEntry cloneInstance() {
        XYValue v = new XYValue();
        v.indexXY = indexXY;
        v.xValue = (Value)xValue.cloneInstance();
        v.yValue = (Value)yValue.cloneInstance();
        return null;
    }

    public void storeToXML(BufferedWriter w, int offset) throws IOException {
        XMLSupport.write("<mesurmentEntry type=\"" + getClassType() + "\">", w, offset);
        offset++;
        XMLSupport.write("<indexXY value=\"" + indexXY + "\"/>", w, offset);

        XMLSupport.write("<xValue>", w, offset);
        offset++;
        xValue.storeToXML(w, offset);
        offset--;
        XMLSupport.write("</xValue>", w, offset);
        
        XMLSupport.write("<yValue>", w, offset);
        offset++;
        yValue.storeToXML(w, offset);
        offset--;
        XMLSupport.write("</yValue>", w, offset);

        offset--;
        XMLSupport.write("</mesurmentEntry>", w, offset);
    }

    class XYValueSAXHandler extends DefaultHandler{
        protected StructuredXMLLoader loader;
        protected int mode = -1; 
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equals("mesurmentEntry"))
                loader.popHandler();
            else
                super.endElement(uri, localName, qName);
        }
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            // TODO Auto-generated method stub
            if(qName.equals("indexXY")){
                try{
                    indexXY = Integer.parseInt(attributes.getValue("value"));
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba p�i na��t�n� m��en�.");
                }
            }else if(qName.equals("xValue")){
                mode = 0;
            }else if(qName.equals("yValue")){
                mode = 1;
            }else if(qName.equals("mesurmentEntry")){
                try{
                    int type = Integer.parseInt(attributes.getValue("type"));
                    if(type != MeasurementEntryFactory.VALUE)
                        throw new MeasurementFormatException("Cyba p�i na��t�n� m��en�.");
                    switch(mode){
                    case 0:
                        xValue = new Value();
                        loader.pushHandler(xValue.getSAXHandler(loader));
                        break;
                    case 1:
                        yValue = new Value();
                        loader.pushHandler(yValue.getSAXHandler(loader));
                        break;
                    default:
                        new MeasurementFormatException("Cyba p�i na��t�n� m��en�.");
                    }
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba p�i na��t�n� m��en�.");
                }
            }else
                super.startElement(uri, localName, qName, attributes);
        }
        public XYValueSAXHandler(StructuredXMLLoader loader) {
            super();
            this.loader = loader;
        }
    }
    public DefaultHandler getSAXHandler(StructuredXMLLoader loader) {
        return new XYValueSAXHandler(loader);
    }

	public String getClipboardString() {
		return indexXY + "\t" + getXValue().getClipboardStringInerr() + "\t" + getYValue().getClipboardStringInerr();
	}

	public void updateCachedValueStrings() {
		xValue.updateCachedValueStrings();
		yValue.updateCachedValueStrings();
	}
	
	public void updateFrom(XYValue xy) {
		xValue = xy.xValue;
		yValue = xy.yValue;
		indexXY = xy.indexXY;
	}
}
