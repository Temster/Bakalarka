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
import java.util.Observable;

import unisave2006.GlobalSetting;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.MeasurementEntryFactory;
import unisave2006.data.value.Value;
import unisave2006.units.Unit;
import unisave2006.units.UnitPrefix;

public class MostRecentUnitCalculator extends Observable implements MeasurementEntryListener{

    protected int unitUse[] = null;
    protected int expUse[][] = null;
    protected int minExp = Integer.MAX_VALUE;
    protected int maxExp = Integer.MIN_VALUE;
    protected Unit mostRecent = null;
    protected Measurement measurement = null;
    
    public Unit getMostRecentUnit() {
        return mostRecent;
    }
    public MostRecentUnitCalculator(){
        minExp = Integer.MAX_VALUE;
        maxExp = Integer.MIN_VALUE;
        for(UnitPrefix p: GlobalSetting.getPrefixSet()){
             if(p.getExponent() >= maxExp)
                 maxExp = p.getExponent();
             if(p.getExponent() <= minExp)
                 minExp = p.getExponent();
        }
        int expRange = maxExp-minExp+1;
        unitUse = new int[GlobalSetting.getUnitSet().getSize()];
        
        expUse = new int[GlobalSetting.getUnitSet().getSize()][expRange];
        mostRecent  = Unit.getEmptyUnit();
    }
    
    public void entriesAdded(Collection<MeasurementEntry> v) {
        for(MeasurementEntry e: v){
            if(e.getClassType() == MeasurementEntryFactory.VALUE){
                countUse(((Value)e).getUnit());
            }
        }
        updateMostRecentUnit();
    }
    protected void updateMostRecentUnit(){
        int maxCountDescription = -1;
        int mostRecentDescription = 0;
        int mostRecentExp = 0;
        int maxCountPrefix = -1;
        for(int i=0; i<unitUse.length; i++){
            if(unitUse[i] > maxCountDescription){
                maxCountDescription = unitUse[i];
                mostRecentDescription = i;
            }
        }
        for(int j=0; j<expUse[mostRecentDescription].length; j++){
                if(expUse[mostRecentDescription][j] > maxCountPrefix){
                    maxCountPrefix = expUse[mostRecentDescription][j];
                    mostRecentExp = j;
                }
        }
        mostRecentExp += minExp;
        if(mostRecent.getDescription().getId() != mostRecentDescription){
        	mostRecent.setDescription(GlobalSetting.getUnitSet().getUnitDescription(mostRecentDescription));
        	setChanged();
        }
        if(mostRecent.getPrefix().getExponent() != mostRecentExp){
        	mostRecent.setPrefix(GlobalSetting.getPrefixSet().getPrefix(mostRecentExp));
        	setChanged();
        }
        notifyObservers();
    }
    protected void removeUseAndUpdate(Unit unit) {
        unitUse[unit.getDescription().getId()]--;
        expUse[unit.getDescription().getId()][unit.getPrefix().getExponent()-minExp]--;
        updateMostRecentUnit();
    }
    protected void countUse(Unit unit) {
        try {
        	unitUse[unit.getDescription().getId()]++;
            expUse[unit.getDescription().getId()][unit.getPrefix().getExponent()-minExp]++;
        } catch(ArrayIndexOutOfBoundsException e) {
        }
    }
    protected void countUseAndUpade(Unit unit) {
        countUse(unit);
        if(mostRecent.equals(unit))
            return;
        if(unitUse[unit.getDescription().getId()] < unitUse[mostRecent.getDescription().getId()])
            return;
        if(mostRecent.getDescription() == unit.getDescription()){
            if(expUse[unit.getDescription().getId()][unit.getPrefix().getExponent()-minExp] > expUse[mostRecent.getDescription().getId()][mostRecent.getPrefix().getExponent()]){
                updateMostRecentUnit();
                return;
            }
        }
        if(unitUse[unit.getDescription().getId()] > unitUse[mostRecent.getDescription().getId()])
            updateMostRecentUnit();
    }
    public void recalculateUse() {
        deleteUse();
        entriesAdded(measurement.getElements());
    }
    protected void deleteUse() {
        for (int i = 0; i < unitUse.length; i++) {
            unitUse[i] = 0;
            for (int j = 0; j < expUse[i].length; j++) {
                expUse[i][j] = 0;
            }
        }
    }
    
    public void entriesChanged() {
        recalculateUse();
    }

    public void entriesDeleted() {
        recalculateUse();
    }

    public void entryAdded(MeasurementEntry e) {
        if(e.getClassType() == MeasurementEntryFactory.VALUE)
            countUseAndUpade(((Value)e).getUnit());
    }

    public void entryChanged(int index, MeasurementEntry e) {
        recalculateUse();
    }

    public void entryDeleted(int index, MeasurementEntry e) {
        if(e.getClassType() == MeasurementEntryFactory.VALUE){
            removeUseAndUpdate(((Value)e).getUnit());
        }
    }
    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
        measurement.addMeasurementEntryListener(this);
        recalculateUse();
    }
}
