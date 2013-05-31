/*
 * Created on 21.8.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import java.awt.GridBagLayout;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;

import unisave2006.data.Measurement;
import unisave2006.gui.action.AbstractActionCopy;
import unisave2006.gui.action.AbstractActionCreateProtocol;
import unisave2006.gui.action.AbstractActionCut;
import unisave2006.gui.action.AbstractActionDeleteLast;
import unisave2006.gui.action.AbstractActionDeleteSelected;
import unisave2006.gui.action.AbstractActionEdit;
import unisave2006.gui.action.AbstractActionSelectAll;
import unisave2006.gui.action.AbstractActionUnitConversion;
import unisave2006.gui.action.AbstractActionViewGraph;
import unisave2006.gui.action.AbstractActionViewHistogram;

public class NullMeasurementPanel extends JPanel implements MeasurementPanelInterface {

    private static final long serialVersionUID = 1L;
    private JLabel jLabel = null;

    /**
     * This is the default constructor
     */
    public NullMeasurementPanel() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0D;
        gridBagConstraints.weighty = 1.0D;
        gridBagConstraints.gridy = 0;
        jLabel = new JLabel();
        jLabel.setText("");
        jLabel.setIcon(new ImageIcon(getClass().getResource("/resource/pictures/UniSave2006_background_screen.png")));
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
        this.add(jLabel, gridBagConstraints);
    }

    public JComponent getPanel() {
        return this;
    }

    public void setMeasurement(Measurement m) {}

    protected AbstractActionCopy actionCopy = null;
    public Action getActionCopy() {
        if(actionCopy == null)
            actionCopy = new AbstractActionCopy();
        return actionCopy;
    }
    
    protected AbstractActionCreateProtocol actionCreateProtocol = null;  //  @jve:decl-portIndex=0:
    public Action getActionCreateProtocol() {
        if(actionCreateProtocol == null)
            actionCreateProtocol = new AbstractActionCreateProtocol();
        return actionCreateProtocol;
    }
    
    protected AbstractActionCut actionCut = null; 
    public Action getActionCut() {
        if(actionCut == null)
            actionCut = new AbstractActionCut();
        return actionCut;
    }
   
    protected AbstractActionDeleteLast actionDeleteLast = null;  //  @jve:decl-portIndex=0:
    public Action getActionDeleteLast() {
        if(actionDeleteLast == null)
            actionDeleteLast = new AbstractActionDeleteLast();
        return actionDeleteLast;
    }
    
    protected AbstractActionDeleteSelected actionDeleteSelected = null;
    public Action getActionDeleteSelected() {
        if(actionDeleteSelected == null)
            actionDeleteSelected = new AbstractActionDeleteSelected();
        return actionDeleteSelected;
    }
    
    protected AbstractActionSelectAll actionSelectAll = null;
    public Action getActionSelectAll() {
        if(actionSelectAll == null)
            actionSelectAll = new AbstractActionSelectAll();
        return actionSelectAll;
    }

    protected AbstractActionUnitConversion actionUnitConversion = null;
    public Action getActionUnitConversion() {
        if(actionUnitConversion == null)
            actionUnitConversion = new AbstractActionUnitConversion();
        return actionUnitConversion;
    }
    
    protected AbstractActionViewGraph actionViewGraph = null;
    public Action getActionViewGraph() {
        if(actionViewGraph == null)
            actionViewGraph = new AbstractActionViewGraph();
        return actionViewGraph;
    }
    
    protected AbstractActionViewHistogram actionViewHistogram = null;  //  @jve:decl-portIndex=0:
    public Action getActionViewHistogram() {
        if(actionViewHistogram == null)
            actionViewHistogram = new AbstractActionViewHistogram();
        return actionViewHistogram;
    }

    protected AbstractActionEdit actionEdit = null;  //  @jve:decl-portIndex=0:
    public Action getActionEdit() {
        if(actionEdit == null)
            actionEdit = new AbstractActionEdit();
        return actionEdit;
    }

	public void close() {
	}

	public void detache() {
	}
}
