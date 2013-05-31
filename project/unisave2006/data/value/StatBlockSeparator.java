/*
 * Created on 14.9.2006
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

public class StatBlockSeparator implements MeasurementEntry {

	
    protected String textSBS;
    
    public StatBlockSeparator() {
    	
    }
    
    public MeasurementEntry cloneInstance() {
        return new StatBlockSeparator();
    }

    public int getClassType() {
        return 6;
    }

    public void storeToXML(BufferedWriter w, int offset) throws IOException {
        XMLSupport.write("<mesurmentEntry type=\"" + getClassType() + "\">", w, offset);
        offset++;
        XMLSupport.write("<textSBS value=\"" + XMLSupport.escape(textSBS) + "\"/>", w, offset);
        offset--;
        XMLSupport.write("</mesurmentEntry>", w, offset);
    }

    public String getTextSBS() {
        return textSBS;
    }

    public void setTextSBS(String text) {
        this.textSBS = text;
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
	}

}
