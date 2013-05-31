/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.units;

import java.util.Map;
import java.util.TreeMap;
import java.util.*;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_sN1kMBDREdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class UnitDescription implements Comparable<UnitDescription> {
    /**
     * Comment for <code>conversions</code>
     */
    private Map<Integer, UnitConversion> conversions = new TreeMap<Integer, UnitConversion>();
    /**
     * Comment for <code>id</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    
    private int id;

    /**
     * Comment for <code>name</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private String name;
    /**
     * Comment for <code>shortcut</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private String shortcut;
    /**
     * Comment for <code>prefixAvailable</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private boolean prefixAvailable;

    public UnitDescription() {
    	
    }
    
    public UnitDescription(int id, String name, String shortcut, boolean prefixAvailable){
        setId(id);
        setName(name);
        setShortcut(shortcut);
        setPrefixAvailable(prefixAvailable);
        new UnitConversion(this, this, 1, 1, 0, 0);
    }

    /**
     * @return Returns the conversions.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public Map<Integer, UnitConversion> getConversions() {
        return conversions;
    }

    public void setConversions(Map<Integer, UnitConversion> conversions) {
    	this.conversions = conversions;
    }

    
    /**
     * @return Returns the id.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public int getId() {
        return id;
    }

    /**
     * @param theId The id to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private void setId(int theId) {
        id = theId;
    }


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
    private void setName(String theName) {
        name = theName;
    }


    /**
     * @return Returns the prefixAvailable.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public boolean getPrefixAvailable() {
        return prefixAvailable;
    }

    /**
     * @param thePrefixAvailable The prefixAvailable to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private void setPrefixAvailable(boolean thePrefixAvailable) {
        prefixAvailable = thePrefixAvailable;
    }


    /**
     * @return Returns the shortcut.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public String getShortcut() {
        return shortcut;
    }

    /**
     * @param theShortcut The shortcut to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private void setShortcut(String theShortcut) {
        shortcut = theShortcut;
    }

    public void addConversion(UnitConversion uc){
        if(uc != null && this == uc.getFrom())
            conversions.put(uc.getTo().getId(), uc);
    }
    
    public boolean isConvertibleTo(UnitDescription ud){
        return (conversions.get(ud.getId()) != null);
    }

    public UnitConversion GetConverzionTo(UnitDescription ud){
        return conversions.get(ud.getId());
    }
    
    public String toString(){
        if(shortcut.length() > 0)
            return name + "(" + shortcut + ")";
        else
            return name;
    }

    public int compareTo(UnitDescription o) {
        return id - o.getId();
    }
}
