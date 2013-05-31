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

import org.xml.sax.helpers.DefaultHandler;

import unisave2006.XMLSupport;
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
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_itSVABETEdu-TcvEGVchxA"
 */
public class Statistic implements MeasurementEntry {

    public static final int N = 0;
    public static final int AVERAGE = 1;
    public static final int DEVIATION = 2;
    public static final int MAX = 3;
    public static final int MIN = 4;
    public static final int KVAR = 5;

    
    /**
     * Comment for <code>valueSta</code>
     */
    private Value valueSta;

    /**
     * @return Returns the valueSta.
     */
    public Value getValueSta() {
        return valueSta;
    }

    /**
     * @param theValue The valueSta to set.
     */
    public void setValueSta(Value theValue) {
        valueSta = theValue;
    }

    /**
     * Comment for <code>statType</code>
     */
    private int statType;

    /**
     * @return Returns the statType.
     */
    public int getStatType() {
        return statType;
    }

    /**
     * @param theStatType The statType to set.
     */
    public void setStatType(int theStatType) {
        statType = theStatType;
    }

    public Statistic() {
    	
    }
    
    public int getClassType() {
        return MeasurementEntryFactory.STATISTIC;
    }

    public MeasurementEntry cloneInstance() {
        Statistic s = new Statistic();
        s.statType = statType;
        s.valueSta = (Value)valueSta.cloneInstance();
        return s;
    }

    public void storeToXML(BufferedWriter w, int offset) throws IOException {
        XMLSupport.write("<mesurmentEntry type=\"" + getClassType() + "\">", w, offset);
        offset++;
        XMLSupport.write("<statType valueSta=\"" + statType + "\"/>", w, offset);
        XMLSupport.write("<valueSta>", w, offset);
        offset++;
        valueSta.storeToXML(w, offset);
        offset--;
        XMLSupport.write("</valueSta>", w, offset);
        offset--;
        XMLSupport.write("</mesurmentEntry>", w, offset);
        // TODO Auto-generated method stub
        
    }

    public DefaultHandler getSAXHandler(StructuredXMLLoader loader) {
        // TODO Auto-generated method stub
        return null;
    }

	public String getClipboardString() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateCachedValueStrings() {
		valueSta.updateCachedValueStrings();
	}
}
