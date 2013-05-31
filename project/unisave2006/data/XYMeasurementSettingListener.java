/*
 * Created on 9.8.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import unisave2006.units.Unit;

public interface XYMeasurementSettingListener {

    public void xValueNameChanged();
    public void yValueNameChanged();
    public void yAutoconverUnitChanged();
    public void mostRecentYUnitChanged(Unit u);
	public void ignoreGroweXValueChanged();
	public void xMarkerFixedChanged();
	public void xMarkerChanged();
	public void yMarkerFixedChanged();
	public void yMarkerChanged();
}
