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
import java.util.List;

import org.xml.sax.Attributes;

import unisave2006.data.Measurement;
import unisave2006.data.value.MeasurementEntry;
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
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_4qQH8BDZEdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public interface GrabberInterface {

    public int getType();
    public void setData(String uri, String name, String qName, Attributes atts);
    public void startGrabbing(InputStream in, InputStream err, Measurement m) throws IOException;
    public void startGrabbingInNesstedMode(InputStream in, InputStream err, Measurement m) throws IOException;
    public void stopGrabbing();
    public boolean isRunning();
    public List<UnitDescription> getPosibleUnits();
    public MeasurementEntry parseOneEntry();
    public void setInputStream(InputStream is);
    public void setReader(Reader r);
    public GrabberInterface cloneInstance();
}
