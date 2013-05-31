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

import unisave2006.XMLSupport;
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
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_rVTTsBETEdu-TcvEGVchxA"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class NamedValue implements MeasurementEntry {
    
	/**
     * Comment for <code>toleranc</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private Value toleranc;

    /**
     * @return Returns the toleranc.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public Value getToleranc() {
        return toleranc;
    }

    /**
     * @param theToleranc The toleranc to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public void setToleranc(Value theToleranc) {
        toleranc = theToleranc;
    }

    /**
     * Comment for <code>nominalValue</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private Value nominalValue;

    /**
     * @return Returns the nominalValue.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public Value getNominalValue() {
        return nominalValue;
    }

    /**
     * @param theNominalValue The nominalValue to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public void setNominalValue(Value theNominalValue) {
        nominalValue = theNominalValue;
    }

    /**
     * Comment for <code>measurementValue</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    protected Value measurementValue;

    /**
     * @return Returns the measurementValue.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public Value getMeasurementValue() {
        return measurementValue;
    }

    /**
     * @param theMesurmentValue The measurementValue to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public void setMeasurementValue(Value theMesurmentValue) {
        measurementValue = theMesurmentValue;
    }

    /**
     * Comment for <code>name</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    protected String name;

    /**
     * @return Returns the name.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public String getName() {
        return name;
    }

    /**
     * @param theName The name to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public void setName(String theName) {
        name = theName;
    }

    public NamedValue() {
    	
    }
    
    public int getClassType() {
        return MeasurementEntryFactory.NAMED_VALUE;
    }

    public MeasurementEntry cloneInstance() {
        NamedValue v = new NamedValue();
        v.measurementValue = (Value)measurementValue.cloneInstance();
        v.nominalValue = (Value)nominalValue.cloneInstance();
        v.toleranc = (Value)toleranc.cloneInstance();
        v.name =  new String(name);
        return v;
    }

    public void storeToXML(BufferedWriter w, int offset) throws IOException {
        XMLSupport.write("<mesurmentEntry type=\"" + getClassType() + "\">", w, offset);
        offset++;
        XMLSupport.write("<name value=\"" + XMLSupport.escape(name) + "\"/>", w, offset);

        XMLSupport.write("<measurementValue>", w, offset);
        offset++;
        measurementValue.storeToXML(w, offset);
        offset--;
        XMLSupport.write("</measurementValue>", w, offset);
        
        XMLSupport.write("<nominalValue>", w, offset);
        offset++;
        nominalValue.storeToXML(w, offset);
        offset--;
        XMLSupport.write("</nominalValue>", w, offset);

        XMLSupport.write("<toleranc>", w, offset);
        offset++;
        toleranc.storeToXML(w, offset);
        offset--;
        XMLSupport.write("</toleranc>", w, offset);
        
        offset--;
        XMLSupport.write("</mesurmentEntry>", w, offset);
        
    }

    public DefaultHandler getSAXHandler(StructuredXMLLoader loader) {
        // TODO Auto-generated method stub
        return null;
    }

	public String getClipboardString() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateCachedValueStrings() {
		measurementValue.updateCachedValueStrings();
		nominalValue.updateCachedValueStrings();
		toleranc.updateCachedValueStrings();
	}
}
