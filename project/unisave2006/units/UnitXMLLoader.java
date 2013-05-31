/*
 * Created on 13.7.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.units;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 */
public class UnitXMLLoader extends DefaultHandler {

    protected InputSource source = null;
    protected int mode = 0;
    protected static int NONE = 0;
    protected static int PREFIXES = 1;
    protected static int UNITS = 2;
    protected static int CONVERSIONS = 3;
    protected UnitSet unitSet = null;
    protected PrefixSet prefixSet = null;
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        UnitXMLLoader loader = new UnitXMLLoader();
        try {
            loader.setSource(new InputStreamReader(UnitXMLLoader.class.getClassLoader().getResourceAsStream("resource/units.xml"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        loader.load();
        //loader.testOutput();
    }
    
    public void load(){
        if(source == null)
            return;
        XMLReader xr = null;
        try {
            xr = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        xr.setContentHandler(this);
        xr.setErrorHandler(this);
        try {
            xr.parse(source);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void setSource(Reader r){
        source = new InputSource(r);
    }
    public void startDocument() {
        //System.out.println("Start document");
    }

    public void endDocument() {
        //System.out.println("End document");
    }

    public void startElement(String uri, String name, String qName,
            Attributes atts) {
        if ("prefixes".equals(qName)){
            mode = PREFIXES;
            prefixSet = new PrefixSet(); 
        }
        else if ("units".equals(qName)){
            mode = UNITS;
            unitSet = new UnitSet();
        }
        else if ("conversions".equals(qName))
            mode = CONVERSIONS;
        else if (mode == PREFIXES && "pref".equals(qName)){
            UnitPrefix p = new UnitPrefix(
                    atts.getValue("name"),
                    atts.getValue("shortcut"),
                    Integer.parseInt(atts.getValue("exp")));
            prefixSet.addPrefix(p);
        }
        else if (mode == UNITS && "unit".equals(qName)){
            UnitDescription u = new UnitDescription(
                    Integer.parseInt(atts.getValue("index")),
                    atts.getValue("name"),
                    atts.getValue("shortcut"),
                    Boolean.parseBoolean(atts.getValue("prefixAvailable")));
            unitSet.add(u);
        }
        else if (mode == CONVERSIONS && unitSet != null && "conv".equals(qName)){
            @SuppressWarnings("unused")
			UnitConversion uc = new UnitConversion(
                    unitSet.getUnitDescription(Integer.parseInt(atts.getValue("from"))),
                    unitSet.getUnitDescription(Integer.parseInt(atts.getValue("to"))),
                    Double.parseDouble(atts.getValue("multiplier")),
                    Double.parseDouble(atts.getValue("divider")),
                    Double.parseDouble(atts.getValue("offset1")),
                    Double.parseDouble(atts.getValue("offset2"))
                    );
        }
    }

    public void endElement(String uri, String name, String qName) {
        if ("prefixes".equals(qName)){
            mode = NONE;
        }
        else if ("units".equals(qName)){
            mode = NONE;
        }
        else if ("conversions".equals(qName))
            mode = NONE;
    }

    public void characters(char ch[], int start, int length) {
    }

    public PrefixSet getPrefixSet() {
        return prefixSet;
    }

    public UnitSet getUnitSet() {
        return unitSet;
    }
    
    private void testOutput(){
        Iterator<UnitPrefix> i = prefixSet.getPrefixes().values().iterator();
        while(i.hasNext()){
            UnitPrefix p = i.next();
            System.out.println(p.getName() + "\t" + p.getShortcut() + "\t" + p.getExponent());
        }
        Iterator<UnitDescription> ii = unitSet.getUnitDescriptions().values().iterator();
        while(ii.hasNext()){
            UnitDescription ud = ii.next();
            System.out.println(ud.getId() + "\t" + ud.getName() + "\t" + ud.getShortcut() + "\t" + ud.getPrefixAvailable());
            Iterator<UnitConversion> iii = ud.getConversions().values().iterator();
            while(iii.hasNext()){
                UnitConversion uc = iii.next();
                System.out.println("\t\t" + uc.getFrom().getName() + "\t" + uc.getTo().getName() + "\t" + uc.getMultiplier() + "\t" + uc.getDivider() + "\t" + uc.getOffset() + "\t" + uc.getOffset2());
            }
        }
        
    }
}
