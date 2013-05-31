/*
 * Created on 1.9.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.device;


import unisave2006.grabber.DoubleGrabber;

public class DoubleMeasurementDevice extends MeasurementDevice {

	public DoubleMeasurementDevice() {
		
	}
	
    protected MeasurementDevice dev1 = null;
    protected MeasurementDevice dev2 = null;
    
    public MeasurementDevice getDev1() {
    	return dev1;
    }
    public void setDev1(MeasurementDevice dev1) {
    	this.dev1 = dev1;
    }
    
    public MeasurementDevice getDev2() {
    	return dev2;
    }
    public void setDev2(MeasurementDevice dev2) {
    	this.dev2 = dev2;
    }
    
    public DoubleMeasurementDevice(MeasurementDevice dev1, MeasurementDevice dev2) {
        super();
        this.dev1 = dev1;
        this.dev2 = dev2;
        setCommSetting(dev1.getCommSetting());//TODO kontrola spolecneho comm settingu
        setGrabber(new DoubleGrabber(dev1.getGrabber(), dev2.getGrabber()));
    }

}
