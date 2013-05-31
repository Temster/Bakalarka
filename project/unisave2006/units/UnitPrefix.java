/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.units;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_vdLOABDREdu2xrm-LBKU-Q"
 */
public class UnitPrefix {
    /**
     * Comment for <code>exponent</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private int exponent;
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
    
    // 
    private Long id; 
    
    public UnitPrefix() {
    	
    }

    public UnitPrefix(String name, String shortcut, int exp){
        setName(name);
        setShortcut(shortcut);
        setExponent(exp);
    }
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    /**
     * @return Returns the exponent.
     */
    public int getExponent() {
        return exponent;
    }

    /**
     * @param theExponent The exponent to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private void setExponent(int theExponent) {
        exponent = theExponent;
    }


    /**
     * @return Returns the name.
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
     * @return Returns the shortcut.
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
    
    public double getMultiplier(){
        return Math.pow(10, exponent);
    }
    
    public String toString(){
        if(getShortcut().compareTo("") == 0)
            return getName();
        else
        return getName() + "(" + getShortcut() + ")";
    }
    
    public double getTrueValue(double v){
        return v * getMultiplier();
    }
    
    public double convert(double v){
        return v / getMultiplier();
    }
}
