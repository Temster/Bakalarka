/*
 * Created on 15.7.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;

import unisave2006.GlobalSetting;
import unisave2006.PortName;
import unisave2006.data.Measurement;
import unisave2006.data.XYMeasurement;
import unisave2006.device.DoubleMeasurementDevice;
import unisave2006.device.MeasurementDevice;
import unisave2006.units.Unit;
import unisave2006.units.UnitSet;

import java.awt.Insets;
import java.io.IOException;

public class DoubleDeviceGrabberPanel extends GrabberPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JLabel jLabelDeviceType2 = null;
    private JComboBox jComboBoxDeviceType2 = null;
    protected XYMeasurement measurement = null;
    protected UnitSelectionPanel unitSelectionPanel2 = null;
    
    public void setMeasurement(XYMeasurement m) {
        measurement = m;
    }

    public void setMeasurement(Measurement m) {
        super.setMeasurement(m);
        if(m instanceof XYMeasurement)
            setMeasurement((XYMeasurement)m);
    }

    /**
     * This method initializes 
     * 
     */
    public DoubleDeviceGrabberPanel() {
    	super();
        jLabelDeviceType.setText("Typ 1. měřidla:");
    	initialize();
        getJComboBoxDeviceType2().setSelectedItem(GlobalSetting.getUserSetting().getLastUsedSecondDevice());
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints.weightx = 1.0;
        jLabelDeviceType2 = new JLabel();
        jLabelDeviceType2.setText("Typ 2. měřidla:");
        this.add(jLabelDeviceType2, gridBagConstraints1);
        this.add(getJComboBoxDeviceType2(), gridBagConstraints);
    		
    }

    /**
     * This method initializes jComboBoxDeviceType2	
     * 	
     * @return javax.swing.JComboBox	
     */
    private JComboBox getJComboBoxDeviceType2() {
        if (jComboBoxDeviceType2 == null) {
            jComboBoxDeviceType2 = new JComboBox();
            jComboBoxDeviceType2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if(unitSelectionPanel2 != null){
                        if(getJComboBoxDeviceType2().getSelectedItem() instanceof MeasurementDevice){
                            MeasurementDevice md = (MeasurementDevice)(getJComboBoxDeviceType2().getSelectedItem()); 
                            unitSelectionPanel2.setUnits(UnitSet.getConvertibleUnits(md.getGrabber().getPosibleUnits()));
                        }
                    }
                }
            });
            for(MeasurementDevice dev : GlobalSetting.getMeasurementDeviceSet()){
                jComboBoxDeviceType2.addItem(dev);
            }
        }
        return jComboBoxDeviceType2;
    }

    @Override
    public void grabbingStarted() {
        super.grabbingStarted();
        getJComboBoxDeviceType2().setEnabled(false);
    }

    @Override
    public void grabbingStopped() {
        super.grabbingStopped();
        getJComboBoxDeviceType2().setEnabled(true);
    }

    public void setUnitSelectionPanel2(UnitSelectionPanel unitSelectionPanel) {
        this.unitSelectionPanel2 = unitSelectionPanel;
        //unitSelectionPanel2.setSelectedUnit(new Unit(GlobalSetting.getUserSetting().getLastUsedYUnit()));
    }

    @Override
    protected void startGrabbing() {
        measurementDevice.removeMeasurementGrabbingListener(this);
        MeasurementDevice md1 = (MeasurementDevice)(getJComboBoxDeviceType().getSelectedItem());
        MeasurementDevice md2 = (MeasurementDevice)(getJComboBoxDeviceType2().getSelectedItem());
        GlobalSetting.getUserSetting().setLastUsedDevice(md1);
        GlobalSetting.getUserSetting().setLastUsedSecondDevice(md2);
        GlobalSetting.getUserSetting().setLastUsedPort((PortName)(getJComboBoxPort().getSelectedItem()));
        GlobalSetting.getUserSetting().setLastUsedUnit(new Unit(unitSelectionPanel.getSelectedUnit()));
        GlobalSetting.getUserSetting().setLastUsedYUnit(new Unit(unitSelectionPanel2.getSelectedUnit()));
        
        if(md1 == md2){
            md2 = md2.cloneInstance();
        }
        
        measurementDevice = new DoubleMeasurementDevice(md1, md2);
        measurementDevice.addMeasurementGrabbingListener(this);
        try {
            measurementDevice.startGrabbing(((PortName)(getJComboBoxPort().getSelectedItem())).getFileName(), measurement);
        } catch (IOException e) {
            stopGrabbing();
            JOptionPane.showMessageDialog(this.getRootPane(), "Nepodařilo se spustit proces načítání hodnot z měřidla.", "UniSave 2006 - chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

}
