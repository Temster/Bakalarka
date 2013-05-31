/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.units;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_0932cBDREdu2xrm-LBKU-Q"
 */
public class PrefixSet implements Iterable<UnitPrefix>{
    /**
     * Comment for <code>unitPrefix</code>
     */
    private Map<Integer, UnitPrefix> unitPrefix = new TreeMap<Integer, UnitPrefix>();
    
    private int size = 0;

    /**
     * @return Returns the unitprefix.
     */
    public Map<Integer, UnitPrefix> getPrefixes() {
        return unitPrefix;
    }

    
    public void addPrefix(UnitPrefix up){
        unitPrefix.put(up.getExponent(),up);
        size = unitPrefix.size();
    }
    
    public UnitPrefix getPrefix(int exp){
        return unitPrefix.get(exp);
    }

    public Iterator<UnitPrefix> iterator() {
        return unitPrefix.values().iterator();
    }

    public int getSize() {
        return size;
    }
}
