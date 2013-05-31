/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data.value;

import java.io.BufferedWriter;
import java.io.IOException;

import org.xml.sax.helpers.DefaultHandler;

import unisave2006.data.StructuredXMLLoader;

/**
 * @author David Je�ek
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_vL5yYBGCEduLIuzFGBOiyA"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public interface MeasurementEntry {
    
    public int getClassType();
    public MeasurementEntry cloneInstance();
    public void storeToXML(BufferedWriter w, int offset) throws IOException;
    public DefaultHandler getSAXHandler(StructuredXMLLoader loader);
	public String getClipboardString();
	public void updateCachedValueStrings();
	
}
