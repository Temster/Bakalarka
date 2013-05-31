/*
 * Created on 2.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui.value;

import javax.swing.JPanel;

import unisave2006.data.value.MeasurementEntry;

public interface MeasurementEntityEditor {

    public JPanel getPanel();
    public MeasurementEntry getEntity() throws MeasurementEntityConstructionException;
    public void setMeasurementEntity(MeasurementEntry e);
    public void setEnableInserting(boolean b);
    public void gainFocus();
    public void eraseValue();
}
