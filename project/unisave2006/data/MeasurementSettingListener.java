/*
 * Created on 20.8.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import unisave2006.units.Unit;

public interface MeasurementSettingListener {

    public abstract void autoindexingChanged();

    public abstract void autoConvertChanged();

    public abstract void autoConvertUnitChanged();

    public void modifiedChanged();
    
    public void mostRecentUnitChanged(Unit u);

}