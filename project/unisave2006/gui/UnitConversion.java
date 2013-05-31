/*
 * Created on 24.9.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;

public class UnitConversion extends JDialog {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;

    /**
     * @param owner
     */
    public UnitConversion(Frame owner) {
        super(owner);
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(300, 200);
        this.setContentPane(getJContentPane());
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
        }
        return jContentPane;
    }

}
