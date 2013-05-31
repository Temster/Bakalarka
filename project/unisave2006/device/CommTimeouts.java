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
import java.io.Writer;

import org.xml.sax.Attributes;

public class CommTimeouts implements Cloneable{
    
	protected Long id;
    public int readIntervalTimeout;
    public int readTotalTimeoutConstant;
    public int readTotalTimeoutMultiplier;
    public int writeTotalTimeoutConstant;
    public int writeTotalTimeoutMultiplier;
    
    public CommTimeouts() {
    	
    }
    
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    public int getReadIntervalTimeout() {
        return readIntervalTimeout;
    }
    public void setReadIntervalTimeout(int readIntervalTimeout) {
        this.readIntervalTimeout = readIntervalTimeout;
    }
    public int getReadTotalTimeoutConstant() {
        return readTotalTimeoutConstant;
    }
    public void setReadTotalTimeoutConstant(int readTotalTimeoutConstant) {
        this.readTotalTimeoutConstant = readTotalTimeoutConstant;
    }
    public int getReadTotalTimeoutMultiplier() {
        return readTotalTimeoutMultiplier;
    }
    public void setReadTotalTimeoutMultiplier(int readTotalTimeoutMultiplier) {
        this.readTotalTimeoutMultiplier = readTotalTimeoutMultiplier;
    }
    public int getWriteTotalTimeoutConstant() {
        return writeTotalTimeoutConstant;
    }
    public void setWriteTotalTimeoutConstant(int writeTotalTimeoutConstant) {
        this.writeTotalTimeoutConstant = writeTotalTimeoutConstant;
    }
    public int getWriteTotalTimeoutMultiplier() {
        return writeTotalTimeoutMultiplier;
    }
    public void setWriteTotalTimeoutMultiplier(int writeTotalTimeoutMultiplier) {
        this.writeTotalTimeoutMultiplier = writeTotalTimeoutMultiplier;
    }
    
    public void setData(String uri, String name, String qName, Attributes atts){
        int v = 0;
        if ("readIntervalTimeout".equals(qName)) {
            v = Integer.parseInt(atts.getValue("value"));
            setReadIntervalTimeout(v);
        } else if ("readTotalTimeoutConstant".equals(qName)) {
            v = Integer.parseInt(atts.getValue("value"));
            setReadTotalTimeoutConstant(v);
        } else if ("readTotalTimeoutMultiplier".equals(qName)) {
            v = Integer.parseInt(atts.getValue("value"));
            setReadTotalTimeoutMultiplier(v);
        } else if ("writeTotalTimeoutConstant".equals(qName)) {
            v = Integer.parseInt(atts.getValue("value"));
            setWriteTotalTimeoutConstant(v);
        } else if ("writeTotalTimeoutMultiplier".equals(qName)) {
            v = Integer.parseInt(atts.getValue("value"));
            setWriteTotalTimeoutMultiplier(v);
        }        
    }

    public void writeTo(Writer w) throws IOException{
        String lineSep = System.getProperty("line.separator");
        w.write(Integer.toString(readIntervalTimeout));
        w.write(lineSep);
        w.write(Integer.toString(readTotalTimeoutConstant));
        w.write(lineSep);
        w.write(Integer.toString(readTotalTimeoutMultiplier));
        w.write(lineSep);
        w.write(Integer.toString(writeTotalTimeoutConstant));
        w.write(lineSep);
        w.write(Integer.toString(writeTotalTimeoutMultiplier));
        w.write(lineSep);
        w.flush();
    }
    public CommTimeouts cloneInstance() {
        try {
            return (CommTimeouts)this.clone();
        } catch (CloneNotSupportedException e) {
        }
        return null;
    }
}
