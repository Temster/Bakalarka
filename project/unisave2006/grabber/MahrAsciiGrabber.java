/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.grabber;

import java.util.Map;
import java.util.TreeMap;

import org.xml.sax.Attributes;

import unisave2006.GlobalSetting;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.Text;
import unisave2006.data.value.Value;
import unisave2006.units.Unit;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_RS2Y4BDbEdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class MahrAsciiGrabber extends AsciiGrabber {

	public MahrAsciiGrabber() {
		
	}
	
    protected boolean countOnlyNumbersAfterDecimalPoint = false;
    protected Map<Integer, Unit> digitCountToUnitMap = new TreeMap<Integer, Unit>(); 
    
    public boolean getCountOnlyNumbersAfterDecimalPoint() {
    	return countOnlyNumbersAfterDecimalPoint;
    }
    
    public void setCountOnlyNumbersAfterDecimalPoint(boolean countOnlyNumbersAfterDecimalPoint) {
    	this.countOnlyNumbersAfterDecimalPoint = countOnlyNumbersAfterDecimalPoint;
    }
    
    public Map<Integer, Unit> getDigitCountToUnitMap() {
    	return digitCountToUnitMap;
    }
    
    public void setDigitCountToUnitMap(Map<Integer, Unit> digitCountToUnitMap) {
    	this.digitCountToUnitMap = digitCountToUnitMap;
    }
    
    @Override
    public int getType() {
        return 5;
    }

    @Override
    public void setData(String uri, String name, String qName, Attributes atts) {
        if("posibleUnit".equals(qName)){
            int v = Integer.parseInt(atts.getValue("value"));
            posibleUnits.add(GlobalSetting.getUnitSet().getUnit(v));
        }
        else if("posibleUnitsMap".equals(qName)){
            int prefix = Integer.parseInt(atts.getValue("prefix"));
            int unit = Integer.parseInt(atts.getValue("unit"));
            int digits = Integer.parseInt(atts.getValue("digits"));
            digitCountToUnitMap.put(digits, new Unit(prefix, unit));
        }
        else if("countOnlyNumbersAfterDecimalPoint".equals(qName)){
            countOnlyNumbersAfterDecimalPoint = (Integer.parseInt(atts.getValue("value")) == 1);
        }
        else
            super.setData(uri, name, qName, atts);
    }

    @Override
    public GrabberInterface cloneInstance() {
        MahrAsciiGrabber g = new MahrAsciiGrabber();
        copyTo(g);
        return g;
    }

    protected void copyTo(MahrAsciiGrabber g) {
        super.copyTo(g);
        g.countOnlyNumbersAfterDecimalPoint = countOnlyNumbersAfterDecimalPoint;
        g.digitCountToUnitMap = digitCountToUnitMap;
    }

    @Override
    protected MeasurementEntry parseLine(StringBuilder b) {
        //line.TrimLeft('@');
        int i;
        for(i=0; i<b.length(); i++)
            if(b.charAt(i) != '@')
                break;
        b.delete(0, i);
        b.delete(0, valueOffset);
        b.delete(b.length()-valueTruncate, b.length());
        String line = b.toString();
        try{
            double d = Double.parseDouble(line);
            Value v = new Value();
            v.setValueVal(d);
            int digits = 0;
            if(countOnlyNumbersAfterDecimalPoint)
                digits = getDigitsCountAfterDecimalPoint(line);
            else
                digits = getDigitsCount(line);
            Unit u = digitCountToUnitMap.get(digits);
            if(u != null)
                v.setUnit(new Unit(u));
            return v;
        }catch(NumberFormatException ex){
            return new Text(Text.TEXT, line);
        }
    }

    private int getDigitsCount(String line) {
        int count = 0;
        for(int i=0; i<line.length(); i++){
            if(Character.isDigit(line.charAt(i)))
                count++;
        }
        return count;
    }

    private int getDigitsCountAfterDecimalPoint(String line) {
        int pointPos = line.indexOf('.');
        if(pointPos < 0)
            pointPos = line.indexOf(',');
        if(pointPos < 0)
            return 0;
        int count = 0;
        for(int i=pointPos; i<line.length(); i++){
            if(Character.isDigit(line.charAt(i)))
                count++;
        }
        return count;
    }
}
