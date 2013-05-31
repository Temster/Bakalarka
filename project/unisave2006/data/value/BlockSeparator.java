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
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_kHozIBETEdu-TcvEGVchxA"
 */
public class BlockSeparator implements MeasurementEntry {
	/**
     * Comment for <code>indexBS</code>
     */
    private int indexBS;

    /**
     * @return Returns the indexBS.
     */
    public int getIndexBS() {
        return indexBS;
    }

    /**
     * @param theIndex The indexBS to set.
     */
    public void setIndexBS(int theIndex) {
        indexBS = theIndex;
    }

    /**
     * Comment for <code>textBS</code>
     */
    private String textBS;

    /**
     * @return Returns the textBS.
     */
    public String getTextBS() {
        return textBS;
    }

    /**
     * @param theText The textBS to set.
     */
    public void setTextBS(String theText) {
        textBS = theText;
    }

    public BlockSeparator() {
    	
    }
    
    public int getClassType() {
        return MeasurementEntryFactory.BLOCK_SEPARATOR;
    }

    public MeasurementEntry cloneInstance() {
        BlockSeparator b = new BlockSeparator();
        b.indexBS = indexBS;
        b.textBS = new String(textBS);
        return b;
    }

    public void storeToXML(BufferedWriter w, int offset) throws IOException {
        XMLSupport.write("<mesurmentEntry type=\"" + getClassType() + "\">", w, offset);
        offset++;
        XMLSupport.write("<indexBS value=\"" + indexBS + "\"/>", w, offset);
        XMLSupport.write("<textBS value=\"" + XMLSupport.escape(textBS) + "\"/>", w, offset);
        offset--;
        XMLSupport.write("</mesurmentEntry>", w, offset);
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
