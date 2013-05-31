/*
 * Created on 10.8.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.device;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import unisave2006.grabber.GrabberFactory;
import unisave2006.grabber.GrabberInterface;

public class DeviceXMLLoader extends DefaultHandler {

    protected InputSource source = null;
    protected int mode = 0;
    protected static final int NONE = 0;
    protected static final int MEASUREMENT_DEVICE = 1;
    protected static final int DEVICE = 2;
    protected static final int COMM_TIMEOUTS = 3;
    protected static final int COMM_SETTINGS = 4;
    protected static final int GRABBER = 5;
    
    protected MeasurementDeviceSet measurementDeviceSet = null;
    protected MeasurementDevice measurementDevice = null;
    protected CommDeviceSetting commDeviceSetting = null;
    protected CommSettings commSettings = null;
    protected CommTimeouts commTimeouts = null;
    protected GrabberInterface grabber = null;

    
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        DeviceXMLLoader loader = new DeviceXMLLoader();
        try {
            loader.setSource(new InputStreamReader(DeviceXMLLoader.class.getClassLoader().getResourceAsStream("resource/mesurementDevice.xml"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        loader.load();
        loader.testOutput();
    }
    
    public void load(){
        if(source == null)
            return;
        XMLReader xr = null;
        try {
            xr = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        xr.setContentHandler(this);
        xr.setErrorHandler(this);
        try {
            xr.parse(source);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
    public void setSource(Reader r){
        source = new InputSource(r);
    }
    public void startDocument() {
    }

    public void endDocument() {
    }

    public void startElement(String uri, String name, String qName,
            Attributes atts) {
        switch(mode){
        case NONE:
            if ("mesurmentDevices".equals(qName)){
                measurementDeviceSet = new MeasurementDeviceSet(); 
            }
            else if("mesurmentDevice".equals(qName)){
                mode = MEASUREMENT_DEVICE;
                measurementDevice = new MeasurementDevice();
                measurementDevice.setName(atts.getValue("name"));
                
            }
            break;
        case MEASUREMENT_DEVICE:
            if("device".equals(qName)){
                mode = DEVICE;
                int id = Integer.parseInt(atts.getValue("comDeviceType"));
                commDeviceSetting = CommDeviceSettingFactory.createCommDeviceSetting(id); 
            }
            else if("grabber".equals(qName)){
                mode = GRABBER;
                int id = Integer.parseInt(atts.getValue("type"));
                grabber = GrabberFactory.createGrabber(id);
            }
            else
                measurementDevice.setData(uri, name, qName, atts);
            break;
        case DEVICE:
            if("comTimeouts".equals(qName)){
                mode = COMM_TIMEOUTS;
                commTimeouts = new CommTimeouts();
            }
            else if("commSettings".equals(qName)){
                mode = COMM_SETTINGS;
                commSettings = new CommSettings();
            }
            else
                commDeviceSetting.setData(uri, name, qName, atts);
            break;
        case COMM_TIMEOUTS:
            commTimeouts.setData(uri, name, qName, atts);
            break;
        case COMM_SETTINGS:
            commSettings.setData(uri, name, qName, atts);
            break;
        case GRABBER:
            grabber.setData(uri, name, qName, atts);
            break;
        }
    }

    public void endElement(String uri, String name, String qName) {
        switch(mode){
        case MEASUREMENT_DEVICE:
            if ("mesurmentDevice".equals(qName)){
                mode=NONE; 
                measurementDeviceSet.addMeasurementDevice(measurementDevice);
                measurementDevice = null;
            }
            break;
        case DEVICE:
            if ("device".equals(qName)){
                mode=MEASUREMENT_DEVICE;
                measurementDevice.setCommSetting(commDeviceSetting);
                commDeviceSetting = null;
            }
            break;
        case GRABBER:  
            if ("grabber".equals(qName)){
                mode=MEASUREMENT_DEVICE;
                measurementDevice.setGrabber(grabber);
                grabber = null;
            }
            break;
        case COMM_TIMEOUTS:
            if ("comTimeouts".equals(qName)){
                mode=DEVICE;
                commDeviceSetting.setCommTimeouts(commTimeouts);
                commTimeouts = null;
            }
            break;
        case COMM_SETTINGS:
            if ("commSettings".equals(qName)){
                mode=DEVICE;
                commDeviceSetting.setCommSetting(commSettings);
                commSettings = null;
            }
            break;
        }
    }

    public void characters(char ch[], int start, int length) {
    }

    private void testOutput(){
        for(MeasurementDevice dev: measurementDeviceSet){
            System.out.println(dev.name);
        }
/*        Iterator<UnitPrefix> i = prefixSet.getPrefixes().values().iterator();
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
*/        
    }
    
    public MeasurementDeviceSet getMeasurementDeviceSet(){
        return measurementDeviceSet;
    }

}
