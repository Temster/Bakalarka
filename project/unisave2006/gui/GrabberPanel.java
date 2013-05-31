/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import javax.help.CSH;
import javax.swing.JPanel;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import unisave2006.GlobalSetting;
import unisave2006.PortName;
import unisave2006.UserSettingListener;
import unisave2006.data.Measurement;
import unisave2006.data.MeasurementGrabbingListener;
import unisave2006.device.MeasurementDevice;
import unisave2006.units.Unit;
import unisave2006.units.UnitSet;
/**
 * @author David Je�ek
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 */
public class GrabberPanel extends JPanel implements MeasurementGrabbingListener{

    protected UnitSelectionPanel unitSelectionPanel = null;
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JButton jButtonStart = null;
	private JButton jButtonStop = null;
	protected JLabel jLabelDeviceType = null;
	private JComboBox jComboBoxDeviceType = null;
	private JLabel jLabelPort = null;
	private JComboBox jComboBoxPort = null;
	private JPanel jPanelForButtons = null;
    protected Measurement measurement = null;  //  @jve:decl-portIndex=0:
    protected MeasurementDevice measurementDevice = null;
    
    protected UserSettingListener userSettingListener = null;

    
    public void setMeasurement(Measurement m) {
        measurement = m;
    }

    /**
     * This method initializes
     * 
     */
	public GrabberPanel() {
		super();
		initialize();
		CSH.setHelpIDString(this, "MeasurementStartup");
        getJComboBoxPort().setSelectedItem(GlobalSetting.getUserSetting().getLastUsedPort());
        getJComboBoxDeviceType().setSelectedItem(GlobalSetting.getUserSetting().getLastUsedDevice());
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        jLabelDeviceType = new JLabel();
        jLabelPort = new JLabel();
        GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
        GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
        GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
        GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
        GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        jLabelDeviceType.setText("Typ měřidla:");
        gridBagConstraints7.weightx = 1.0;
        gridBagConstraints7.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jLabelPort.setText("Port:");
        gridBagConstraints8.weightx = 1.0;
        gridBagConstraints8.gridwidth = 0;
        gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints8.insets = new java.awt.Insets(2,2,2,2);
        gridBagConstraints7.gridwidth = 0;
        gridBagConstraints7.insets = new java.awt.Insets(2,2,2,2);
        gridBagConstraints9.gridwidth = 0;
        gridBagConstraints9.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints12.ipady = 0;
        gridBagConstraints12.ipadx = 0;
        gridBagConstraints12.insets = new java.awt.Insets(2,2,2,2);
        gridBagConstraints13.insets = new java.awt.Insets(2,2,2,2);
        this.add(getJPanelForButtons(), gridBagConstraints9);
        this.add(jLabelPort, gridBagConstraints13);
        this.add(getJComboBoxPort(), gridBagConstraints8);
        this.add(jLabelDeviceType, gridBagConstraints12);
        this.add(getJComboBoxDeviceType(), gridBagConstraints7);
			
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonStart() {
		if (jButtonStart == null) {
			jButtonStart = new JButton();
			jButtonStart.setText("Start");
			jButtonStart.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent e) {
			        startGrabbing();
			    }
			});
		}
		return jButtonStart;
	}
	protected void startGrabbing() {
        GlobalSetting.getUserSetting().setLastUsedDevice(measurementDevice);
        GlobalSetting.getUserSetting().setLastUsedPort((PortName)(getJComboBoxPort().getSelectedItem()));
        GlobalSetting.getUserSetting().setLastUsedUnit(new Unit(unitSelectionPanel.getSelectedUnit()));
        try {
            measurementDevice.startGrabbing(((PortName)(getJComboBoxPort().getSelectedItem())).getFileName(), measurement);
        } catch (IOException e) {
            stopGrabbing();
            JOptionPane.showMessageDialog(this.getRootPane(), "Nepodařilo se spustit proces načítání hodnot z měřidla.", "UniSave 2006 - chyba", JOptionPane.ERROR_MESSAGE);
        }
    }
    protected void stopGrabbing() {
            measurementDevice.stopGrabbing();
    }

    /**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonStop() {
		if (jButtonStop == null) {
			jButtonStop = new JButton();
			jButtonStop.setText("Stop");
			jButtonStop.setEnabled(false);
			jButtonStop.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent e) {
			        stopGrabbing();
			    }
			});
		}
		return jButtonStop;
	}

    /**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	protected JComboBox getJComboBoxDeviceType() {
		if (jComboBoxDeviceType == null) {
			jComboBoxDeviceType = new JComboBox();
			jComboBoxDeviceType.addItemListener(new java.awt.event.ItemListener() {
			    public void itemStateChanged(java.awt.event.ItemEvent e) {
			        if(measurementDevice != null)
                        measurementDevice.removeMeasurementGrabbingListener(GrabberPanel.this);
                    measurementDevice = ((MeasurementDevice)(getJComboBoxDeviceType().getSelectedItem()));
                    measurementDevice.addMeasurementGrabbingListener(GrabberPanel.this);
			    }
			});
			jComboBoxDeviceType.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent e) {
                    if(unitSelectionPanel != null){
                        unitSelectionPanel.setUnits(UnitSet.getConvertibleUnits(measurementDevice.getGrabber().getPosibleUnits()));
                    }

			    }
			});
            for(MeasurementDevice dev : GlobalSetting.getMeasurementDeviceSet()){
                jComboBoxDeviceType.addItem(dev);
            }
		}
		return jComboBoxDeviceType;
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	protected JComboBox getJComboBoxPort() {
		if (jComboBoxPort == null) {
			jComboBoxPort = new JComboBox();
            for(PortName p: GlobalSetting.getUserSetting().getPortNames()){
                jComboBoxPort.addItem(p);
            }
            userSettingListener = new UserSettingListener(){
				public void numberFormatChanged() {
				}
				public void portNamesChanged() {
					getJComboBoxPort().removeAllItems();
		            for(PortName p: GlobalSetting.getUserSetting().getPortNames()){
		                getJComboBoxPort().addItem(p);
		            }
		            getJComboBoxPort().setSelectedItem(GlobalSetting.getUserSetting().getLastUsedPort());					
				}
            };
            GlobalSetting.getUserSetting().addUserSettingListener(userSettingListener);
		}
		return jComboBoxPort;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelForButtons() {
		if (jPanelForButtons == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			jPanelForButtons = new JPanel();
			jPanelForButtons.setLayout(new GridBagLayout());
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.insets = new java.awt.Insets(5,5,5,2);
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.insets = new java.awt.Insets(5,3,5,5);
			jPanelForButtons.add(getJButtonStart(), gridBagConstraints10);
			jPanelForButtons.add(getJButtonStop(), gridBagConstraints11);
		}
		return jPanelForButtons;
	}

    public void grabbingStarted() {
        getJButtonStart().setEnabled(false);
        getJButtonStop().setEnabled(true);
        getJComboBoxPort().setEnabled(false);
        getJComboBoxDeviceType().setEnabled(false);
    }

    public void grabbingStopped() {
        getJButtonStart().setEnabled(true);
        getJButtonStop().setEnabled(false);
        getJComboBoxPort().setEnabled(true);
        getJComboBoxDeviceType().setEnabled(true);
    }

    public void grabbingTerminated(String reason) {
        JOptionPane.showMessageDialog(this.getRootPane(), 
                new ErrorMessagePanel("Nastala chyba při komunikaci se zařízením.", reason),
                "Unisave 2006 - Chyba", JOptionPane.ERROR_MESSAGE);
        grabbingStopped();
        
    }

    public void setUnitSelectionPanel(UnitSelectionPanel unitSelectionPanel) {
        this.unitSelectionPanel = unitSelectionPanel;
        //unitSelectionPanel.setSelectedUnit(new Unit(GlobalSetting.getUserSetting().getLastUsedUnit()));
    }
    
    public void detache(){
    	stopGrabbing();
    	GlobalSetting.getUserSetting().removeUserSettingListener(userSettingListener);
    }
    
}
