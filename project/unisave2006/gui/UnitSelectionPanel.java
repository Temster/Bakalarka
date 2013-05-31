/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import javax.swing.JPanel;

import javax.swing.JComboBox;

import unisave2006.GlobalSetting;
import unisave2006.units.Unit;
import unisave2006.units.UnitDescription;
import unisave2006.units.UnitPrefix;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/**
 * @author David Ježek
 * 
 * Copyright (C) 2006 David Ježek david.jezek@vsb.cz. All rights reserved. This
 * class was created as part of project UniSave2006Implementation.
 */
public class UnitSelectionPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 5464743349212846253L;

    private JComboBox jComboBoxPrefix = null;

    private JComboBox jComboBoxUnit = null;

    private Unit editedUnit;  //  @jve:decl-portIndex=0:

    /**
     * This method initializes
     * 
     */
    public UnitSelectionPanel() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
        GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        gridBagConstraints14.gridx = 0;
        gridBagConstraints14.gridy = 0;
        gridBagConstraints14.weightx = 1.0;
        gridBagConstraints14.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints14.insets = new java.awt.Insets(5, 5, 5, 2);
        gridBagConstraints15.gridx = 1;
        gridBagConstraints15.gridy = 0;
        gridBagConstraints15.weightx = 1.0;
        gridBagConstraints15.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints15.insets = new java.awt.Insets(5, 3, 5, 5);
        this.add(getJComboBoxPrefix(), gridBagConstraints14);
        this.add(getJComboBoxUnit(), gridBagConstraints15);

    }

    /**
     * This method initializes jComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getJComboBoxPrefix() {
        if (jComboBoxPrefix == null) {
            jComboBoxPrefix = new JComboBox();
            for (UnitPrefix p : GlobalSetting.getPrefixSet()) {
                int index = 0;
                for (int i = 0; i < jComboBoxPrefix.getItemCount() - 1; i++) {
                    UnitPrefix a = (UnitPrefix) (jComboBoxPrefix.getItemAt(i));
                    if (a.getExponent() > p.getExponent())
                        index = i + 1;
                    else
                        break;
                }
                jComboBoxPrefix.insertItemAt(p, index);
            }
            jComboBoxPrefix.setSelectedItem(GlobalSetting.getPrefixSet()
                    .getPrefix(0));
            jComboBoxPrefix.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent e) {
                    UnitPrefix p = null;
                    if (getJComboBoxPrefix().getSelectedItem() instanceof UnitPrefix)
                        p = (UnitPrefix) (getJComboBoxPrefix()
                                .getSelectedItem());
                    if (p != null){
                        if(editedUnit != null)
                            editedUnit.setPrefix(p);
                    }
                }
            });
        }
        return jComboBoxPrefix;
    }

    /**
     * This method initializes jComboBox1
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getJComboBoxUnit() {
        if (jComboBoxUnit == null) {
            jComboBoxUnit = new JComboBox();
            jComboBoxUnit.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent e) {
                    UnitDescription ud = null;
                    if (getJComboBoxUnit().getSelectedItem() instanceof UnitDescription)
                        ud = (UnitDescription) (getJComboBoxUnit()
                                .getSelectedItem());
                    if (ud != null){
                        getJComboBoxPrefix().setEnabled(ud.getPrefixAvailable());
                        if(editedUnit != null){
                            editedUnit.setDescription(ud);
                            if(!ud.getPrefixAvailable())
                                editedUnit.setPrefix(GlobalSetting.getPrefixSet().getPrefix(0));
                            else{
                                UnitPrefix p = null;
                                if (getJComboBoxPrefix().getSelectedItem() instanceof UnitPrefix)
                                    p = (UnitPrefix) (getJComboBoxPrefix()
                                            .getSelectedItem());
                                if (p != null){
                                    editedUnit.setPrefix(p);
                                }
                            }
                        }
                    }
                }
            });
            setUnits(GlobalSetting.getUnitSet());
        }
        return jComboBoxUnit;
    }

    public void setUnits(Iterable<UnitDescription> c) {
        getJComboBoxUnit().removeAllItems();
        UnitDescription ud = (UnitDescription)getJComboBoxUnit().getSelectedItem();
        for (UnitDescription u : c) {
            jComboBoxUnit.addItem(u);
        }
        getJComboBoxUnit().setSelectedItem(ud);
    }
    
    public void setSelectedUnit(Unit u){
        editedUnit = u;
        UnitDescription ud = u.getDescription();
        UnitPrefix p = u.getPrefix();
        getJComboBoxUnit().setSelectedItem(ud);
        getJComboBoxPrefix().setSelectedItem(p);
    }
    
    public Unit getSelectedUnit(){
        boolean erase = false;
        if(editedUnit == null){
            editedUnit = Unit.getEmptyUnit();
            erase = true;
        }
        if(getJComboBoxUnit().getSelectedItem() instanceof UnitDescription)
            editedUnit.setDescription((UnitDescription)(getJComboBoxUnit().getSelectedItem()));
        if(editedUnit.getDescription().getPrefixAvailable()){
            if(getJComboBoxPrefix().getSelectedItem() instanceof UnitPrefix)
                editedUnit.setPrefix((UnitPrefix)(getJComboBoxPrefix().getSelectedItem()));
        }
        else
            editedUnit.setPrefix(GlobalSetting.getPrefixSet().getPrefix(0));
        if(erase){
            Unit u = editedUnit;
            editedUnit = null;
            return u;
        }
        return editedUnit;
    }
    
    public void setEnableInserting(boolean b){
        getJComboBoxUnit().setEnabled(b);
    }
    
}
