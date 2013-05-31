/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.units;

import java.util.Vector;
import unisave2006.GlobalSetting;
import unisave2006.dao.units.*;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_udwYYBDREdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class Unit {
    
    private Long id;
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    /**
     * Comment for <code>description</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private UnitDescription description;

    /**
     * Comment for <code>prefix</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    private UnitPrefix prefix;

     /**
     * @return Returns the description.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public UnitDescription getDescription() {
        return description;
    }

    /**
     * @param theDescription The description to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public void setDescription(UnitDescription theDescription) {
        description = theDescription;
        eraseChachedTexts();
    }


    /**
     * @return Returns the prefix.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public UnitPrefix getPrefix() {
        return prefix;
    }

    /**
     * @param thePrefix The prefix to set.
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
    public void setPrefix(UnitPrefix thePrefix) {
        prefix = thePrefix;
        eraseChachedTexts();
    }
    
    public static Unit getEmptyUnit(){
    	Unit u = new Unit();
    	u.setPrefix(GlobalSetting.getPrefixSet().getPrefix(0));
    	u.setDescription(GlobalSetting.getUnitSet().getUnit(0));
    	return u;
    }
    
    public Unit(){

    }    
    
    public Unit(Unit u){
        this(u.getPrefix(), u.getDescription());
    }
    
    public Unit(UnitPrefix p, UnitDescription ud){
        setPrefix(p);
        setDescription(ud);
    }
    /**
     * Construct unit based on shurtcut obtained from device
     * @param shortcut
     */
    public Unit(String shortcut){
        UnitSet unitSet = GlobalSetting.getUnitSet();
        PrefixSet prefixSet = GlobalSetting.getPrefixSet();
        Vector<UnitDescription> posibleUD = new Vector<UnitDescription>(unitSet.getSize());
        for(UnitDescription ud : unitSet)
        {
            String s = ud.getShortcut();
            if(shortcut.endsWith(s))
                posibleUD.add(ud);
        }
        Vector<UnitPrefix> posiblePrefix = new Vector<UnitPrefix>(prefixSet.getSize());
        Vector<UnitDescription> unitToRemove = new Vector<UnitDescription>(posibleUD.size());
        for(UnitDescription ud : posibleUD)
        {
            boolean finded = false;
            String p = "";
            if(shortcut.length() - ud.getShortcut().length() >= 0){
                p = shortcut.substring(0, shortcut.length() - ud.getShortcut().length());
                for(UnitPrefix pref: prefixSet)
                {
                    if(pref == null)
                        continue;
                    if(pref.getShortcut().equals(p))
                    {
                        posiblePrefix.add(pref);
                        finded = true;
                    }
                }
            }
            if(!finded)
            {
                if(p.compareTo("u") == 0)
                    posiblePrefix.add(prefixSet.getPrefix(-6));
                else
                    unitToRemove.add(ud);
            }
        }
        posibleUD.removeAll(unitToRemove);
        if(posibleUD.size() > 0 && posiblePrefix.size() > 0)
        {
            prefix = posiblePrefix.firstElement();
            description = posibleUD.firstElement();
        }
        else
        {
            if(shortcut.equals("mils"))//je to zkratka pro milipalec
            {
                prefix = prefixSet.getPrefix(0);
                description = unitSet.getUnit(4); //milipalec
            }
            else
            {
                prefix = prefixSet.getPrefix(0);
                description = unitSet.getUnit(0); //nema jednotku
            }
        }
    }
    
    public Unit(int exp, int unit) {
        this(GlobalSetting.getPrefixSet().getPrefix(exp), 
                GlobalSetting.getUnitSet().getUnit(unit));
    }
    public String getShortcut(){
        if(cacheShortcut == null)
            updateChachedtexts();
        return cacheShortcut;
    }
    public String toString(){
        if(cacheText == null)
            updateChachedtexts();
        return cacheText;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Unit){
            Unit u = (Unit)obj;
            return u.description == description && u.prefix == prefix; 
        }
        return super.equals(obj);
    }
    
    protected String cacheText = null;
    protected String cacheShortcut = null;
    protected void eraseChachedTexts(){
        cacheShortcut = null;
        cacheText = null;
    }
    protected void updateChachedtexts(){
        StringBuilder sb = new StringBuilder(20);
        sb.append(prefix.getShortcut());
        if(description != null)
        	sb.append(description.getShortcut());
        cacheShortcut = sb.toString();
        sb.delete(0, sb.length());
        sb.append(prefix.getName());
        sb.append(" ");
        if(description != null)
        	sb.append(description.getName());
    }
}
