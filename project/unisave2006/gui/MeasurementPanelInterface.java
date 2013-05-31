/*
 * Created on 23.8.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import javax.swing.Action;
import javax.swing.JComponent;
import unisave2006.data.Measurement;

public interface MeasurementPanelInterface {

    public void setMeasurement(Measurement m);
    public JComponent getPanel();
    public Action getActionCreateProtocol();
    public Action getActionCut();
    public Action getActionCopy();
    public Action getActionDeleteLast();
    public Action getActionDeleteSelected();
    public Action getActionSelectAll();
    public Action getActionUnitConversion();
    public Action getActionViewGraph();
    public Action getActionViewHistogram();
    public Action getActionEdit();
    public void close();
	public void detache();
}
