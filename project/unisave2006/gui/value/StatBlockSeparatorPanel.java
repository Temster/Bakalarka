/*
 * Created on 14.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui.value;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import unisave2006.data.value.MeasurementEntry;

public class StatBlockSeparatorPanel extends JPanel implements
        MeasurementEntityEditor {

    private static final long serialVersionUID = 1L;

    public void eraseValue() {
        // TODO Auto-generated method stub

    }

    public void gainFocus() {
        // TODO Auto-generated method stub

    }

    public MeasurementEntry getEntity()
            throws MeasurementEntityConstructionException {
        // TODO Auto-generated method stub
        return null;
    }

    public JPanel getPanel() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setEnableInserting(boolean b) {
        // TODO Auto-generated method stub

    }

    public void setMeasurementEntity(MeasurementEntry e) {
        // TODO Auto-generated method stub

    }

    /**
     * This is the default constructor
     */
    public StatBlockSeparatorPanel() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
    }

}
