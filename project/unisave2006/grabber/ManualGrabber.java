/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.grabber;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import unisave2006.GlobalSetting;
import unisave2006.data.Measurement;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.gui.ManualGrabberFrame;
import unisave2006.units.UnitDescription;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_YEDm8BDbEdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class ManualGrabber implements GrabberInterface {
	
    protected List<UnitDescription> posibleUnits = new ArrayList<UnitDescription>(20);
    protected ManualGrabberFrame frame = null;
    protected Measurement measurement = null;
    protected boolean stopped = true;
    
    public ManualGrabber(){
        posibleUnits.addAll(GlobalSetting.getUnitSet().getUnitDescriptions().values());
        Collections.sort(posibleUnits);
    }
    public int getType() {
        return 3;
    }

    public Measurement getMeasurement() {
    	return measurement;
    }
    
    public void setMeasurement(Measurement measurement) {
    	this.measurement = measurement;
    }
    
    public List<UnitDescription> getPosibleUnits() {
        return posibleUnits;
    }
    
    public void setPosibleUnits(ArrayList<UnitDescription> posibleUnits) {
    	this.posibleUnits = posibleUnits;
    }

    public boolean isRunning() {
        if(frame != null && frame.isVisible())
            return true;
        return false;
    }

    public MeasurementEntry parseOneEntry() {
        frame.setEnableInserting(true);
        MeasurementEntry e = null;
        while(!stopped){
            e = frame.pollValue();
            if(e!= null){
                frame.setEnableInserting(false);
                return e;
            }
            try {
                Thread.sleep(400);
            } catch (InterruptedException e1) {}
        }
        return null;
    }

    public void setData(String uri, String name, String qName, Attributes atts) {
    }

    public void setInputStream(InputStream is) {
    }

    public void startGrabbing(InputStream in, InputStream err, Measurement m) throws IOException {
        measurement = m;
        initFrame(false);
        stopped = false;
        new Thread(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
        }).start();
    }

    public void stopGrabbing(){
        stopped = true;
        frame.setVisible(false);
        frame.dispose();
    }

    public void startGrabbingInNesstedMode(InputStream in, InputStream err, Measurement m) throws IOException {
        measurement = m;
        initFrame(true);
        stopped = false;
        frame.setEnableInserting(false);
        new Thread(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
        }).start();
    }
    
    protected void initFrame(boolean nessted){
        frame = new ManualGrabberFrame(nessted);
        frame.setAllowedValueTypes(measurement.getAllowedEntityTypes());
        frame.setMeasurement(measurement);
        frame.setAlwaysOnTop(true);
        frame.setGrabber(this);
    }
    public GrabberInterface cloneInstance() {
        ManualGrabber mg = new ManualGrabber();
        mg.measurement = measurement;
        return mg;
    }
	public void setReader(Reader r) {
	}
}
