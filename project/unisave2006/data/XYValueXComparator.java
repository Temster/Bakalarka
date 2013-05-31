/*
 * Created on 7.9.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.util.Comparator;

import unisave2006.data.value.ConversionException;
import unisave2006.data.value.XYValue;
import unisave2006.units.Unit;

public class XYValueXComparator implements Comparator<XYValue> {

    protected Unit mostRecentUnit = Unit.getEmptyUnit();
    public int compare(XYValue v1, XYValue v2) {
        double delta = 0;
        try {
            delta = v1.getXValue().getValueInUnit(mostRecentUnit) - v2.getXValue().getValueInUnit(mostRecentUnit);
        } catch (ConversionException e) {}
        if(delta < 0)
            return -1;
        if(delta > 0)
            return 1;
        return 0;
    }
    public void setMostRecentUnit(Unit mostRecentUnit) {
        this.mostRecentUnit = mostRecentUnit;
    }
    

}
