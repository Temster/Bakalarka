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


public class CommSettings implements Cloneable{
	
	protected Long id;
    protected int baudRate;
    protected int byteSize;
    protected int DCBlength;
    protected int eofChar;
    protected int ErrorChar;
    protected int EvtChar;
    protected int fAbrotOnError;
    protected int fBinary;
    protected int fDsrSensitivity;
    protected int fDtrControl;
    protected int fDummy2;
    protected int fErrorChar;
    protected int fInX;
    protected int fNull;
    protected int fOutX;
    protected int fOutxCtsFlow;
    protected int fOutxDsrFlow;
    protected int fParity;
    protected int fRtsControl;
    protected int fTXContinueOnXoff;
    protected int Parity;
    protected int stopBits;
    protected int XoffChar;
    protected int XoffLim;
    protected int XonChar;
    protected int XonLim;
    
    public CommSettings() {
    	
    }
    
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    
    public int getBaudRate() {
        return baudRate;
    }
    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }
    public int getByteSize() {
        return byteSize;
    }
    public void setByteSize(int byteSize) {
        this.byteSize = byteSize;
    }
    public int getDCBlength() {
        return DCBlength;
    }
    public void setDCBlength(int blength) {
        DCBlength = blength;
    }
    public int getEofChar() {
        return eofChar;
    }
    public void setEofChar(int eofChar) {
        this.eofChar = eofChar;
    }
    public int getErrorChar() {
        return ErrorChar;
    }
    public void setErrorChar(int errorChar) {
        ErrorChar = errorChar;
    }
    public int getEvtChar() {
        return EvtChar;
    }
    public void setEvtChar(int evtChar) {
        EvtChar = evtChar;
    }
    public int getFAbrotOnError() {
        return fAbrotOnError;
    }
    public void setFAbrotOnError(int abrotOnError) {
        fAbrotOnError = abrotOnError;
    }
    public int getFBinary() {
        return fBinary;
    }
    public void setFBinary(int binary) {
        fBinary = binary;
    }
    public int getFDsrSensitivity() {
        return fDsrSensitivity;
    }
    public void setFDsrSensitivity(int dsrSensitivity) {
        fDsrSensitivity = dsrSensitivity;
    }
    public int getFDtrControl() {
        return fDtrControl;
    }
    public void setFDtrControl(int dtrControl) {
        fDtrControl = dtrControl;
    }
    public int getFDummy2() {
        return fDummy2;
    }
    public void setFDummy2(int dummy2) {
        fDummy2 = dummy2;
    }
    public int getFErrorChar() {
        return fErrorChar;
    }
    public void setFErrorChar(int errorChar) {
        fErrorChar = errorChar;
    }
    public int getFNull() {
        return fNull;
    }
    public void setFNull(int null1) {
        fNull = null1;
    }
    public int getFOutX() {
        return fOutX;
    }
    public void setFOutX(int outX) {
        fOutX = outX;
    }
    public int getFOutxCtsFlow() {
        return fOutxCtsFlow;
    }
    public void setFOutxCtsFlow(int outxCtsFlow) {
        fOutxCtsFlow = outxCtsFlow;
    }
    public int getFOutxDsrFlow() {
        return fOutxDsrFlow;
    }
    public void setFOutxDsrFlow(int outxDsrFlow) {
        fOutxDsrFlow = outxDsrFlow;
    }
    public int getFParity() {
        return fParity;
    }
    public void setFParity(int parity) {
        fParity = parity;
    }
    public int getFRtsControl() {
        return fRtsControl;
    }
    public void setFRtsControl(int rtsControl) {
        fRtsControl = rtsControl;
    }
    public int getFTXContinueOnXoff() {
        return fTXContinueOnXoff;
    }
    public void setFTXContinueOnXoff(int continueOnXoff) {
        fTXContinueOnXoff = continueOnXoff;
    }
    public int getParity() {
        return Parity;
    }
    public void setParity(int parity) {
        Parity = parity;
    }
    public int getStopBits() {
        return stopBits;
    }
    public void setStopBits(int stopBits) {
        this.stopBits = stopBits;
    }
    public int getXoffChar() {
        return XoffChar;
    }
    public void setXoffChar(int xoffChar) {
        XoffChar = xoffChar;
    }
    public int getXoffLim() {
        return XoffLim;
    }
    public void setXoffLim(int xoffLim) {
        XoffLim = xoffLim;
    }
    public int getXonChar() {
        return XonChar;
    }
    public void setXonChar(int xonChar) {
        XonChar = xonChar;
    }
    public int getXonLim() {
        return XonLim;
    }
    public void setXonLim(int xonLim) {
        XonLim = xonLim;
    }

    
    public void setData(String uri, String name, String qName, Attributes atts){
        int v = 0;
        if("baudRate".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setBaudRate(v);
        }else if("byteSize".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setByteSize(v);
        }else if("DCBlength".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setDCBlength(v);
        }else if("eofChar".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setEofChar(v);
        }else if("ErrorChar".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setErrorChar(v);
        }else if("EvtChar".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setEvtChar(v);
        }else if("fAbrotOnError".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFAbrotOnError(v);
        }else if("fBinary".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFBinary(v);
        }else if("fDsrSensitivity".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFDsrSensitivity(v);
        }else if("fDtrControl".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFDtrControl(v);
        }else if("fDummy2".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFDummy2(v);
        }else if("fErrorChar".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFErrorChar(v);
        }else if("fInX".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFInX(v);
        }else if("fNull".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFNull(v);
        }else if("fOutX".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFOutX(v);
        }else if("fOutxCtsFlow".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFOutxCtsFlow(v);
        }else if("fOutxDsrFlow".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFOutxDsrFlow(v);
        }else if("fParity".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFParity(v);
        }else if("fRtsControl".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFRtsControl(v);
        }else if("fTXContinueOnXoff".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setFTXContinueOnXoff(v);
        }else if("Parity".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setParity(v);
        }else if("stopBits".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setStopBits(v);
        }else if("XoffChar".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setXoffChar(v);
        }else if("XoffLim".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setXoffLim(v);
        }else if("XonChar".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setXonChar(v);
        }else if("XonLim".equals(qName)){
            v = Integer.parseInt(atts.getValue("value"));
            setXonLim(v);
        }
    }
    public void writeTo(Writer w) throws IOException{
        String lineSep = System.getProperty("line.separator");
        w.write(Integer.toString(baudRate));
        w.write(lineSep);
        w.write(Integer.toString(byteSize));
        w.write(lineSep);
        w.write(Integer.toString(DCBlength));
        w.write(lineSep);
        w.write(Integer.toString(eofChar));
        w.write(lineSep);
        w.write(Integer.toString(ErrorChar));
        w.write(lineSep);
        w.write(Integer.toString(EvtChar));
        w.write(lineSep);
        w.write(Integer.toString(fAbrotOnError));
        w.write(lineSep);
        w.write(Integer.toString(fBinary));
        w.write(lineSep);
        w.write(Integer.toString(fDsrSensitivity));
        w.write(lineSep);
        w.write(Integer.toString(fDtrControl));
        w.write(lineSep);
        w.write(Integer.toString(fDummy2));
        w.write(lineSep);
        w.write(Integer.toString(fErrorChar));
        w.write(lineSep);
        w.write(Integer.toString(fInX));
        w.write(lineSep);
        w.write(Integer.toString(fNull));
        w.write(lineSep);
        w.write(Integer.toString(fOutX));
        w.write(lineSep);
        w.write(Integer.toString(fOutxCtsFlow));
        w.write(lineSep);
        w.write(Integer.toString(fOutxDsrFlow));
        w.write(lineSep);
        w.write(Integer.toString(fParity));
        w.write(lineSep);
        w.write(Integer.toString(fRtsControl));
        w.write(lineSep);
        w.write(Integer.toString(fTXContinueOnXoff));
        w.write(lineSep);
        w.write(Integer.toString(Parity));
        w.write(lineSep);
        w.write(Integer.toString(stopBits));
        w.write(lineSep);
        w.write(Integer.toString(XoffChar));
        w.write(lineSep);
        w.write(Integer.toString(XoffLim));
        w.write(lineSep);
        w.write(Integer.toString(XonChar));
        w.write(lineSep);
        w.write(Integer.toString(XonLim));
        w.write(lineSep);
        w.flush();
    }
    public int getFInX() {
        return fInX;
    }
    public void setFInX(int inX) {
        fInX = inX;
    }
    public CommSettings cloneInstance() {
        try {
            return (CommSettings) this.clone();
        } catch (CloneNotSupportedException e) {
        }
        return null;
    }
}
