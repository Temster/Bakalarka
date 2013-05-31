/*
 * Created on 7.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.util.Collection;

import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.MeasurementEntryFactory;
import unisave2006.data.value.XYValue;

public class MostRecentUnitXYCalculator extends MostRecentUnitCalculator {

    protected int whatCount = CALCULATE_X;
    public static final int CALCULATE_X = 0;
    public static final int CALCULATE_Y = 1;
    
    public MostRecentUnitXYCalculator(int what){
        whatCount = what;
    }
    @Override
    public void entriesAdded(Collection<MeasurementEntry> v) {
        if (whatCount == CALCULATE_X) {
            for (MeasurementEntry e : v) {
                if (e.getClassType() == MeasurementEntryFactory.XY_VALUE) {
                    countUse(((XYValue) e).getXValue().getUnit());
                }
            }
        } else {
            for (MeasurementEntry e : v) {
                if (e.getClassType() == MeasurementEntryFactory.XY_VALUE) {
                    countUse(((XYValue) e).getYValue().getUnit());
                }
            }
        }
        updateMostRecentUnit();
    }
    @Override
    public void entryAdded(MeasurementEntry e) {
        if (e.getClassType() == MeasurementEntryFactory.XY_VALUE) {
            if (whatCount == CALCULATE_X)
                countUseAndUpade(((XYValue) e).getXValue().getUnit());
            else
                countUseAndUpade(((XYValue) e).getYValue().getUnit());
        }
    }
    @Override
    public void entryDeleted(int index, MeasurementEntry e) {
        if (e.getClassType() == MeasurementEntryFactory.XY_VALUE) {
            if (whatCount == CALCULATE_X)
                removeUseAndUpdate(((XYValue) e).getXValue().getUnit());
            else
                removeUseAndUpdate(((XYValue) e).getYValue().getUnit());
        }
    }
}
