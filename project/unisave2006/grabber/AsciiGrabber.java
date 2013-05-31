/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.grabber;

import java.io.IOException;
import java.io.InputStream;
import org.xml.sax.Attributes;

import unisave2006.data.Measurement;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.MeasurementEntryFactory;
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
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_BnMYUBDbEdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class AsciiGrabber extends AbstractGrabber{
    
	public AsciiGrabber() {
		
	}
	
    public void parse() {
        int index = 0;
        String valueEndChar = String.format("%c", this.valueEndChar);
        char buf[] = new char[1];
        StringBuilder readedString = new StringBuilder(100);
        StringBuilder valueString = new StringBuilder(100);
        while (!stopped) {
            int read = 0;
            try {
                read = inputReder.read(buf);
            } catch (IOException e) {
                break;
            }
            if (read > 0) {
                readedString.append(buf, 0, read);
                int valueEndCharIndex = readedString.lastIndexOf(valueEndChar);
                if(valueEndCharIndex >= 0) {
                    valueString.append(readedString.substring(0,
                            valueEndCharIndex));
                    readedString.delete(0, valueEndCharIndex+1);
                    int actualStart = 0;
                    int nextEndChar = -1;
                    StringBuilder b = new StringBuilder(); 
                    do {
                        nextEndChar = valueString.indexOf(valueEndChar, actualStart);
                        if(nextEndChar<0)
                            nextEndChar = valueString.length();
                        b.append(valueString, actualStart, nextEndChar);
                        MeasurementEntry e = parseLine(b);
                        b.delete(0, b.length());
                        if(e.getClassType() == MeasurementEntryFactory.VALUE){
                            index++;
                            if(autoIndexing || regAutoIndexing)
                                ((Value)e).setIndexVal(index);
                        }
                        queue.add(e);
                        actualStart = nextEndChar+1;
                    }while(actualStart < valueString.length());
                    valueString.delete(0, valueString.length());
                }
            }
            if (read < 0)
                break;
        }
    }
    protected MeasurementEntry parseLine(StringBuilder b){
        b.delete(0, valueOffset);
        b.delete(b.length() - valueTruncate, b.length());
        double d = 0;
        try {
            Double.parseDouble(b.toString());
        } catch (NumberFormatException e) {
            Text t = new Text(Text.ERROR, String.format("Pøijatý øetìzec \"%s\" nelze pøevést na èíslo.", b.toString()));
            return t;
        }
        Value v = new Value();
        v.setValueVal(d);
        v.setUnit(Unit.getEmptyUnit());
        return v;
    }
    
    
    protected int valueOffset;
    protected int valueTruncate;
    protected char valueEndChar;
    protected boolean regAutoIndexing;
    protected boolean autoIndexing;
    protected int lastAssignedIndex;
    
    public int getValueOffset() {
    	return valueOffset;
    }
    
    public void setValueOffset(int valueOffset) {
    	this.valueOffset = valueOffset;
    }
    
    public int getValueTruncate() {
    	return valueTruncate;
    }
    
    public void setValueTruncate(int valueTruncate) {
    	this.valueTruncate = valueTruncate;
    }
    
    public char getValueEndChar() {
    	return valueEndChar;
    }
    
    public void setValueEndChar(char valueEndChar) {
    	this.valueEndChar = valueEndChar;
    }
    
    public boolean getRegAutoIndexing() {
    	return regAutoIndexing;
    }
    
    public void setRegAutoIndexing(boolean regAutoIndexing) {
    	this.regAutoIndexing = regAutoIndexing;
    }
    
    public boolean getAutoIndexing() {
    	return autoIndexing;
    }
    
    public void setAutoIndexing(boolean autoIndexing) {
    	this.autoIndexing = autoIndexing;
    }
    
    public int getLastAssignedIndex() {
    	return lastAssignedIndex;
    }
    
    public void setLastAssignedIndex(int lastAssignedIndex) {
    	this.lastAssignedIndex = lastAssignedIndex;
    }
    
    public int getType() {
        return 1;
    }

    public void setData(String uri, String name, String qName, Attributes atts) {
        if("valueOffset".equals(qName)){
            int v = Integer.parseInt(atts.getValue("value"));
            valueOffset = v;
        }
        else if("valueTruncate".equals(qName)){
            int v = Integer.parseInt(atts.getValue("value"));
            valueTruncate = v;
        }
        else if("valueEndChar".equals(qName)){
            int v = Integer.parseInt(atts.getValue("value"));
            valueEndChar = (char)v;
        }
        else if("autoIndexing".equals(qName)){
            int v = Integer.parseInt(atts.getValue("value"));
            regAutoIndexing = (v == 1);
        }
        else
            super.setData(uri, name, qName, atts);

    }



    public MeasurementEntry parseOneEntry() {
        //int portIndex = 0;
        char buf[] = new char[1];
        StringBuilder readedString = new StringBuilder(100);
        MeasurementEntry entry = null;
        while(!stopped){
            int read = 0;
            try {
                read = inputReder.read(buf);
                System.out.print((buf[0]=='\r')?'\n':buf[0]);
            } catch (IOException e) {
                return null;
            }
            if(read<0 && readedString.length() == 0)
                return null;
            if(read > 0 && buf[0] != valueEndChar)
                readedString.append(buf, 0, read);
            if(buf[0] == valueEndChar) {
                entry = parseLine(readedString);
                queue.add(entry);
                break;
            }
        }
        return entry;
    }

    protected void copyTo(AsciiGrabber g){
        super.copyTo(g);
        g.autoIndexing = autoIndexing;
        g.lastAssignedIndex = lastAssignedIndex;
        g.regAutoIndexing = regAutoIndexing;
        g.valueEndChar = valueEndChar;
        g.valueOffset = valueOffset;
        g.valueTruncate = valueTruncate;
    }

    public GrabberInterface cloneInstance() {
        AsciiGrabber g = new AsciiGrabber();
        copyTo(g);
        return g;
    }
    @Override
    public void startGrabbingInNesstedMode(InputStream in, InputStream err, Measurement m) throws IOException {
        createEntityBuffer();
        super.startGrabbingInNesstedMode(in, err, m);
    }
}
