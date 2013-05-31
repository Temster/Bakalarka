/*
 * Created on 28.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MeasurementXMLLoader  extends StructuredXMLLoader {

    protected Measurement m = null;
    

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // TODO Auto-generated method stub
        super.endElement(uri, localName, qName);
    }


    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
         if(qName.equals("unisave_mesurments")){
            try{
                int version = Integer.parseInt(attributes.getValue("file_version"));
                if(version != 3){
                    throw new MeasurementFormatException("Soubor obsahuje m��en� ulo�en� v nepodporovan�m form�tu.");
                }
            }
            catch(NumberFormatException ex){
                throw new MeasurementFormatException("Verze form�tu ulo�en�ch dat nebyla rozpozn�na.");
            }
        }
        else if(qName.equals("measurement")){
            try{
                int type = Integer.parseInt(attributes.getValue("type"));
                m = MeasurementFactory.createMeasurement(type);
                if(m == null){
                    throw new MeasurementFormatException("Soubor obsahuje m��en� nepodporovan�ho typu.");
                }
                pushHandler(m.getSAXHandler(this));
            }
            catch(NumberFormatException ex){
                throw new MeasurementFormatException("Typ tohoto m��en� nebyl rozpozn�n.");
            }
        }
        else{
            //m.setData(uri, localName, qName, attributes);
        }
    }

    public Measurement getMeasurement() {
        return m;
    }
}

