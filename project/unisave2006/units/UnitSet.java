/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.units;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
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
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_0BOoABDREdu2xrm-LBKU-Q"
 */
public class UnitSet implements Iterable<UnitDescription>{
    /**
     * Comment for <code>unitdescription</code>
     */
    private Map<Integer, UnitDescription> unitDescription = new TreeMap<Integer, UnitDescription>();
    
    private int size = 0;

    public void add(UnitDescription ud){
        unitDescription.put(ud.getId(), ud);
        size  = unitDescription.size();
    }
    
    public UnitDescription getUnitDescription(int id){
        return unitDescription.get(id);
    }
    
    public Map<Integer, UnitDescription> getUnitDescriptions() {
        return unitDescription;
    }
    
    public static List<UnitDescription> getConvertibleUnits(List<UnitDescription> from){
        List<UnitDescription> result = new ArrayList<UnitDescription>();
        
        for(UnitDescription u: from){
        	if(u != null) {
            for(UnitConversion uc: u.getConversions().values()) {
                	if(uc != null)
                		result.add(uc.getTo());
                }
        	}
        }
        //Collections.sort(result);
        return result;
    }

    public int getSize() {
        return size;
    }

    public Iterator<UnitDescription> iterator() {
        return unitDescription.values().iterator();
    }
    
    public UnitDescription getUnit(int id){
        return unitDescription.get(id);
    }
}
