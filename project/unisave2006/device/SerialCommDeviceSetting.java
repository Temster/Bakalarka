/*
 * Created on 11.8.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.device;

import java.io.IOException;

import org.xml.sax.Attributes;

public class SerialCommDeviceSetting extends CommDeviceSetting {

    protected CommSettings commSettings = new CommSettings();
    protected CommTimeouts commTimeouts;
    protected int clearInputBuffer;
    protected String comName = "";
    
    public SerialCommDeviceSetting() {
    	
    }
    
    @Override
    protected void sendCommSetting() throws IOException {
        super.sendCommSetting();
        commTimeouts.writeTo(writer);
        commSettings.writeTo(writer);
        writer.write(Integer.toString(clearInputBuffer));
        writer.write(System.getProperty("line.separator"));
        writer.flush();

        
    }
    public int getClearInputBuffer() {
        return clearInputBuffer;
    }
    public void setClearInputBuffer(int clearInputBuffer) {
        this.clearInputBuffer = clearInputBuffer;
    }
    @Override
    public void setData(String uri, String name, String qName, Attributes atts) {
        if("clearInputBuffer".equals(qName)){
            int v = Integer.parseInt(atts.getValue("value"));
            setClearInputBuffer(v); 
        }
        else
            super.setData(uri, name, qName, atts);
    }
    public CommSettings getCommSettings() {
        return commSettings;
    }
    public void setCommSettings(CommSettings commSettings) {
        this.commSettings = commSettings;
    }
    public CommTimeouts getCommTimeouts() {
        return commTimeouts;
    }
    public void setCommTimeouts(CommTimeouts commTimeouts) {
        this.commTimeouts = commTimeouts;
    }
    @Override
    public String getComName() {
        return comName;
    }
    @Override
    public void setComName(String name) {
        comName = name;
    }
    
    protected void copyTo(SerialCommDeviceSetting c) {
        super.copyTo(c);
        c.clearInputBuffer = clearInputBuffer;
        c.commSettings = commSettings.cloneInstance();
        c.commTimeouts = commTimeouts.cloneInstance();
        c.comName = comName;
    }
    @Override
    public CommDeviceSetting cloneInstance() {
        SerialCommDeviceSetting c = new SerialCommDeviceSetting();
        copyTo(c);
        return c;
    }

}
