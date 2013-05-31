/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;


import org.xml.sax.Attributes;

import unisave2006.data.Measurement;
import unisave2006.data.MeasurementGrabbingListener;
import unisave2006.grabber.GrabberInterface;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_NiKyABDaEdu2xrm-LBKU-Q"
 */
public class MeasurementDevice {
    
	public MeasurementDevice() {
		
	}
	
	protected Long id;
	
    /**
     * Comment for <code>commsetting</code>
     */
    protected CommDeviceSetting commSetting;
    protected Thread errorMonitor = null;
    protected BufferedReader errorReader = null; 
    /**
     * Comment for <code>grabber</code>
     */
    protected GrabberInterface grabber;
    protected Measurement measurement;
    
    protected Vector<MeasurementGrabbingListener> measurementGrabbingListeners = new Vector<MeasurementGrabbingListener>(10);
    protected String name;
    
    
    public MeasurementDevice cloneInstance(){
        MeasurementDevice md = new MeasurementDevice();
        md.commSetting = commSetting.cloneInstance();
        md.grabber = grabber.cloneInstance();;
        md.measurement = measurement;
        md.name = name;
        return md;
    }
    public void addMeasurementGrabbingListener(MeasurementGrabbingListener l){
        measurementGrabbingListeners.add(l);
    }
    protected void fireGrabbingStarted(){
        for(MeasurementGrabbingListener l: measurementGrabbingListeners){
            l.grabbingStarted();
        }
    }
    protected void fireGrabbingStoped(){
        for(MeasurementGrabbingListener l: measurementGrabbingListeners){
            l.grabbingStopped();
        }
    }
    protected void fireGrabbingTerminated(String reason){
        for(MeasurementGrabbingListener l: measurementGrabbingListeners){
            l.grabbingTerminated(reason);
        }
    }
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    /**
     * @return Returns the commSetting.
     */
    public CommDeviceSetting getCommSetting() {
        return commSetting;
    }
    
    /**
     * @return Returns the grabber.
     */
    public GrabberInterface getGrabber() {
        return grabber;
    }
    public Measurement getMeasurement() {
        return measurement;
    }

    public String getName() {
        return name;
    }

    protected void monitorErrors() {
        String line = "";
        StringBuilder exception = new StringBuilder(200);
        try {
            do {
                line = errorReader.readLine();
                if(line == null)
                    return;
            } while (line.compareTo("unisave_exception") != 0);

            line = errorReader.readLine();
            if(line == null)
                return;
            while (line.compareTo("unisave_exception_end") != 0) {
                exception.append(line);
                exception.append("\n");
                line = errorReader.readLine();
                if(line == null)
                    break;
            }
            terminateGrabbing(exception.toString());
        } catch (IOException e) {
        }
    }

    public void removeMeasurementGrabbingListener(MeasurementGrabbingListener l){
        measurementGrabbingListeners.remove(l);
    }

    /**
     * @param theCommSetting The commSetting to set.
     */
    public void setCommSetting(CommDeviceSetting theCommSetting) {
        commSetting = theCommSetting;
    }

    public void setData(String uri, String name, String qName, Attributes atts){
        //nothing to do
    }

    /**
     * @param theGrabber The grabber to set.
     */
    public void setGrabber(GrabberInterface theGrabber) {
        grabber = theGrabber;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void startGrabbing(String portName, Measurement m) throws IOException {
        measurement = m;
        commSetting.setComName(portName);
        commSetting.connect();
        errorReader = new BufferedReader(new InputStreamReader(commSetting.getErrStream()));
        errorMonitor = new Thread(new Runnable() {
            public void run() {
                monitorErrors();
            }
        });
        errorMonitor.start();
        grabber.startGrabbing(commSetting.getInStream(), commSetting
                .getErrStream(), m);
        fireGrabbingStarted();
    }
    public void stopGrabbing(){
        commSetting.disconnect();
        grabber.stopGrabbing();
        fireGrabbingStoped();

    }
    public void terminateGrabbing(String reason){
        commSetting.disconnect();
        grabber.stopGrabbing();
        fireGrabbingTerminated(reason);

    }
    public String toString(){
        return getName();
    }
}
