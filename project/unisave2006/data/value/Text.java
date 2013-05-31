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
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_go6pgBETEdu-TcvEGVchxA"

 */
public class Text implements MeasurementEntry {
    
    
	/**
     * Comment for <code>textTxt</code>
     */
    private String textTxt;
    
    private String infoText = null;

    public Text(int type, String t) {
        this.type = type;
        this.textTxt = t;
    }
    public Text(){
        this(Text.TEXT, "");
    }

    /**
     * @return Returns the textTxt.

     */
    public String getTextTxt() {
        return textTxt;
    }

    /**
     * @param theText The textTxt to set.

     */
    public void setTextTxt(String theText) {
        textTxt = theText;
        updateInfoText();
    }

    public String getTypeText() {
        switch (type) {
        case TEXT:
            return "TEXT";
        case INFO:
            return "INFO";
        case WARNING:
            return "V�STRAHA";
        case ERROR:
            return "CHYBA";
        }
        return "";
    }

    protected void updateInfoText() {
        if(textTxt == null)
            textTxt="";        
        StringBuilder sb = new StringBuilder(textTxt.length() + 10);
        switch (type) {
        case TEXT:
            sb.append("TEXT: ");
            break;
        case INFO:
            sb.append("INFO: ");
            break;
        case WARNING:
            sb.append("V�STRAHA: ");
            break;
        case ERROR:
            sb.append("CHYBA: ");
            break;
        }
        sb.append(textTxt);
        infoText = sb.toString();
    }

    /**
     * Typ m�e b�t jen textTxt nebo informace, nebo v�straha, nebo hl�en� o
     * chyb�.
     */
    private int type;
    public static final int TEXT = 0;
    public static final int INFO = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;

    /**
     * @return Returns the type.
     */
    public int getType() {
        return type;
    }

    /**
     * @param theType The type to set.
     */
    public void setType(int theType) {
        type = theType;
        updateInfoText();
    }

    public int getClassType() {
        return MeasurementEntryFactory.TEXT;
    }
    
    public String toString(){
        return getInfoText();
    }

    public void setInfoText(String infoText) {
    	this.infoText = infoText;
    }
    
    protected String getInfoText() {
        if(infoText == null)
            updateInfoText();
        return infoText;
    }
    public MeasurementEntry cloneInstance() {
        Text t = new Text();
        t.type = type;
        t.textTxt = new String(textTxt);
        return t;
    }

    public void storeToXML(BufferedWriter w, int offset) throws IOException {
        XMLSupport.write("<mesurmentEntry type=\"" + getClassType() + "\">", w, offset);
        offset++;
        XMLSupport.write("<type value=\"" + type + "\"/>", w, offset);

        XMLSupport.write("<textTxt>" + XMLSupport.escape(textTxt) + "</textTxt>", w, offset);

        offset--;
        XMLSupport.write("</mesurmentEntry>", w, offset);

    }
    class TextSAXHandler extends DefaultHandler{

        protected StructuredXMLLoader loader;
        private boolean modeText = false;
        private StringBuilder t = null;
        public TextSAXHandler(StructuredXMLLoader loader) {
            super();
            this.loader = loader;
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equals("mesurmentEntry"))
                loader.popHandler();
            else if(qName.equals("textTxt")){
                modeText = false;
                textTxt = t.toString();
            } else
                super.endElement(uri, localName, qName);
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equals("textTxt")){
                modeText = true;
                t = new StringBuilder(50);
            }else if(qName.equals("type")){
                try{
                    type = Integer.parseInt(attributes.getValue("value"));
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba p�i na��t�n� m��en�.");
                }
            }else
                super.startElement(uri, localName, qName, attributes);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if(modeText){
                t.append(ch, start, length);
            }
        }
        
    }
    public DefaultHandler getSAXHandler(StructuredXMLLoader loader) {
        return new TextSAXHandler(loader);
    }
	public String getClipboardString() {
		return getTypeText() + ": " + getTextTxt();
	}
	public void updateCachedValueStrings() {
	}
    
}
