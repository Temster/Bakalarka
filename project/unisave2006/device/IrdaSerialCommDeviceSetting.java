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

public class IrdaSerialCommDeviceSetting extends SerialCommDeviceSetting {

    @Override
    protected void sendCommSetting() throws IOException {
        super.sendCommSetting();
        writer.write(Integer.toString(0));//velikost autoEchoendSeqvence - nepouziva se
        writer.write(System.getProperty("line.separator"));
        writer.write(Integer.toString(sendEcho));
        writer.write(System.getProperty("line.separator"));
        writer.flush();
        
    }
    protected int autoEchoSequence;
    protected int sendEcho;
    
    public IrdaSerialCommDeviceSetting() {
    	
    }
    
    @Override
    public void setData(String uri, String name, String qName, Attributes atts) {
        if("autoEchoSequence".equals(qName)){
            int v = Integer.parseInt(atts.getValue("value"));
            setAutoEchoSequence(v);
        }
        else if("sendEcho".equals(qName)){
            int v = Integer.parseInt(atts.getValue("value"));
            setSendEcho(v);
        }
        else
            super.setData(uri, name, qName, atts);
    }
    public int getAutoEchoSequence() {
        return autoEchoSequence;
    }
    public void setAutoEchoSequence(int autoEchoSequence) {
        this.autoEchoSequence = autoEchoSequence;
    }
    public int getSendEcho() {
        return sendEcho;
    }
    public void setSendEcho(int sendEcho) {
        this.sendEcho = sendEcho;
    }

    protected void copyTo(IrdaSerialCommDeviceSetting c) {
        super.copyTo(c);
        c.autoEchoSequence = autoEchoSequence;
        c.comName = comName;
    }
    @Override
    public CommDeviceSetting cloneInstance() {
        IrdaSerialCommDeviceSetting c = new IrdaSerialCommDeviceSetting();
        copyTo(c);
        return c;
    }
}
