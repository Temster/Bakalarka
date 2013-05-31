/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.device;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.xml.sax.Attributes;


import unisave2006.GlobalSetting;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_OmjAIBDaEdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class CommDeviceSetting {
	
	protected Long id;
    protected int comDeviceType = CommDeviceSettingFactory.NULL_COMM_DEVICE;
    protected Process bridge = null;
    protected InputStream inStream = null;
    protected InputStream errStream = null;
    protected OutputStream outStream = null;
    protected Reader reader = null;
    protected Reader errReader = null;
    protected Writer writer = null;
    
    public CommDeviceSetting() {
    	
    }
    
    protected void launchCommBridge() throws IOException{
            bridge = new ProcessBuilder(GlobalSetting.getCommBridgeExeFile().getAbsolutePath()).start();
    }
    
    protected void sendCommSetting() throws IOException{
        String lineSep = System.getProperty("line.separator");
        writer.write(Integer.toString(getComDeviceType()));
        writer.write(lineSep);
        writer.write(getComName());
        writer.write(lineSep);
        writer.flush();
        
    }
    
/*    public Reader getErrReader() {
        return errReader;
    }
*/
    public void setId(Long id) {
    	this.id = id;
    }
    
    public Long getId() {
    	return id;
    }
    
    public InputStream getErrStream() {
        return errStream;
    }

    public InputStream getInStream() {
        return inStream;
    }

/*    public Reader getReader() {
        return reader;
    }
*/
    protected void createConnectionToCommBridge(){
        if(bridge != null){
            inStream = bridge.getInputStream();
            errStream = bridge.getErrorStream();
            outStream = bridge.getOutputStream();
            reader = new InputStreamReader(inStream);
            writer = new OutputStreamWriter(outStream);
            errReader = new InputStreamReader(errStream);
        }
    }
    
    public int getComDeviceType() {
        return comDeviceType;
    }

    public void setComDeviceType(int comDeviceType) {
        this.comDeviceType = comDeviceType;
    }

    public void setData(String uri, String name, String qName, Attributes atts){
        //TODO dodelat
    }
    
    public void setCommTimeouts(CommTimeouts t) {
    	
    	
    }
    public void setCommSetting(CommSettings s) {
    	
    }
    
    public void connect() throws IOException{
        this.launchCommBridge();
        this.createConnectionToCommBridge();
        this.sendCommSetting();
    }

    public void disconnect() {
        try {
        	if(writer != null){
	            writer.write(System.getProperty("line.separator"));
	            writer.write("KONEC");
	            writer.write(System.getProperty("line.separator"));
	            writer.flush();
        	}
        } catch (IOException e2) {}
        try {
            if(inStream != null)
            	inStream.close();
        } catch (IOException e2) {}
        try {
            if(errStream != null)
            	errStream.close();
        } catch (IOException e2) {}
        if (bridge != null) {
            for (int i = 0; i < 50; i++) {
                try {
                    if(bridge != null)
                    	bridge.exitValue();
                    break;
                } catch (IllegalThreadStateException e) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e1) {
                    }
                }
            }
            if(bridge != null)
            	bridge.destroy();
        }
        try {
            if(outStream != null)
            	outStream.close();
        } catch (IOException e) {}
        bridge = null;
    }
    
    public void setComName(String name){
    	
    }
    
    public String getComName(){
        return "";
    }
    
    public static void main(String args[]) throws IOException{
        CommDeviceSetting d = new CommDeviceSetting();
        d.connect();
        d.disconnect();
        
    }
    
    protected void copyTo(CommDeviceSetting c){
    	c.setComDeviceType(comDeviceType);
    	
    }

    public CommDeviceSetting cloneInstance() {
        CommDeviceSetting c = new CommDeviceSetting();
        copyTo(c);
        return c;
    }
    
    

}
