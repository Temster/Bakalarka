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

import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Insets;

import javax.swing.ActionMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import unisave2006.GlobalSetting;
import unisave2006.data.Measurement;
import unisave2006.data.MeasurementEntityTransferer;
import unisave2006.data.MeasurementSettingListener;
import unisave2006.data.XYMeasurement;
import unisave2006.data.XYMeasurementSettingListener;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.gui.value.EditDialog;
import unisave2006.units.Unit;

import java.awt.Font;
import java.awt.Color;
import java.util.Vector;
import java.awt.Dimension;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
/**
 * @author David Je�ek
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 */
public class XYMeasurementValuePanel extends JPanel implements MeasurementSettingListener, XYMeasurementSettingListener {

    private static final long serialVersionUID = 8702529021996842247L;
    protected XYMeasurement measurement = null;  //  @jve:decl-portIndex=0:
	private JCheckBox jCheckBoxAutoIndex = null;
	private JButton jButtonReindex = null;
	private JCheckBox jCheckBoxAutoConvert = null;
	private UnitSelectionPanel unitSelectionPanelX = null;
	private JTable jTableValues = null;
	private JScrollPane jScrollPaneforValues = null;
	private JButton jButtonCopyAll = null;
	private JButton jButtonCopySelected = null;
	private JButton jButtonDeleteLast = null;
	private JButton jButtonDeleteSelected = null;
	private JPanel jPanelForEditButtons = null;
    private JPanel jPanelForConversions = null;
    private UnitSelectionPanel unitSelectionPanelY = null;
    private JLabel jLabelXValueName = null;
    private JLabel jLabelYValueName = null;
    private JTextField jTextFieldXValueName = null;
    private JTextField jTextFieldYValueName = null;
    private JSplitPane jSplitPane = null;
    private JPanel jPanelTop = null;
    private JPanel jPanelBottom = null;
    /**
	 * This is the default constructor
	 */
	public XYMeasurementValuePanel() {
		super();
		initialize();
		CSH.setHelpIDString(this, "ValuePanel");
        ActionMap m = getJTableValues().getActionMap();
        map_browse:while(m != null){
        	Object o[] = m.keys();
        	for(int i=0; o!=null && i<o.length; i++){
        		if(o[i].equals("copy")){
        			m.remove("copy");
        			m = null;
        			break map_browse;
        		}
        	}
        	m = m.getParent();
        }
        m = getJTableValues().getActionMap();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.fill = GridBagConstraints.BOTH;
		gridBagConstraints9.weighty = 1.0;
		gridBagConstraints9.weightx = 1.0;
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Hodnoty", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		this.setSize(new Dimension(506, 543));
		this.add(getJSplitPane(), gridBagConstraints9);
	}
	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getJCheckBoxAutoIndex() {
		if (jCheckBoxAutoIndex == null) {
			jCheckBoxAutoIndex = new JCheckBox();
			jCheckBoxAutoIndex.setText("Automaticky indexovat");
			jCheckBoxAutoIndex.addItemListener(new java.awt.event.ItemListener() {
			    public void itemStateChanged(java.awt.event.ItemEvent e) {
                    GlobalSetting.getUserSetting().setLastUsedAutoIndex(getJCheckBoxAutoIndex().isSelected());
                    if(measurement != null)
                        measurement.setAutoIndex(getJCheckBoxAutoIndex().isSelected());
			    }
			});
		}
		return jCheckBoxAutoIndex;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonReindex() {
		if (jButtonReindex == null) {
			jButtonReindex = new JButton();
			jButtonReindex.setText("Přeindexovat");
			jButtonReindex.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent e) {
			        if(measurement != null)
                        measurement.reindex();
			    }
			});
		}
		return jButtonReindex;
	}
	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getJCheckBoxAutoConvert() {
		if (jCheckBoxAutoConvert == null) {
			jCheckBoxAutoConvert = new JCheckBox();
			jCheckBoxAutoConvert.setText("Automaticky převádět na:");
			jCheckBoxAutoConvert.addItemListener(new java.awt.event.ItemListener() {
			    public void itemStateChanged(java.awt.event.ItemEvent e) {
                    GlobalSetting.getUserSetting().setLastUsedAutoConvert(getJCheckBoxAutoConvert().isSelected());
                    if(measurement != null)
                        measurement.setAutoConvert(getJCheckBoxAutoConvert().isSelected());
			    }
			});
		}
		return jCheckBoxAutoConvert;
	}
	/**
	 * This method initializes unitSelectionPanelX	
	 * 	
	 * @return unisave2006.gui.UnitSelectionPanel	
	 */    
	public UnitSelectionPanel getUnitSelectionPanelX() {
		if (unitSelectionPanelX == null) {
			unitSelectionPanelX = new UnitSelectionPanel();
		}
		return unitSelectionPanelX;
	}
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	public JTable getJTableValues() {
		if (jTableValues == null) {
			jTableValues = new JTable();
			jTableValues.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if(e.getButton() == 1 && e.getClickCount() >= 2)
						editSelectedItem();
				}
			});
		}
		return jTableValues;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPaneforValues() {
		if (jScrollPaneforValues == null) {
			jScrollPaneforValues = new JScrollPane();
			jScrollPaneforValues.setPreferredSize(new Dimension(210, 110));
			jScrollPaneforValues.setViewportView(getJTableValues());
		}
		return jScrollPaneforValues;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonCopyAll() {
		if (jButtonCopyAll == null) {
			jButtonCopyAll = new JButton();
			jButtonCopyAll.setText("Kop�rovat v�e");
			jButtonCopyAll.setVisible(false);
		}
		return jButtonCopyAll;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonCopySelected() {
		if (jButtonCopySelected == null) {
			jButtonCopySelected = new JButton();
			jButtonCopySelected.setText("Kop�rovat vybran�");
			jButtonCopySelected.setVisible(false);
		}
		return jButtonCopySelected;
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonDeleteLast() {
		if (jButtonDeleteLast == null) {
			jButtonDeleteLast = new JButton();
			jButtonDeleteLast.setText("Smazat posledn�");
			jButtonDeleteLast.setVisible(false);
			jButtonDeleteLast.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent e) {
                    deleteLast();
			    }
			});
		}
		return jButtonDeleteLast;
	}
    public void deleteLast(){
        measurement.deleteLast();
    }
	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonDeleteSelected() {
        if (jButtonDeleteSelected == null) {
            jButtonDeleteSelected = new JButton();
            jButtonDeleteSelected.setText("Smazat vybran�");
            jButtonDeleteSelected.setVisible(false);
            jButtonDeleteSelected
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            deleteSelected();
                        }
                    });
        }
        return jButtonDeleteSelected;
    }

    protected Vector<MeasurementEntry> getSelectedMeasurementEntries(){
        int[] selection = getJTableValues().getSelectedRows();
        Vector<MeasurementEntry> s = new Vector<MeasurementEntry>(selection.length);
        for(int i=0; i<selection.length; i++){
            s.add(measurement.getElementAt(selection[i]));
        }
        return s;
    }
    public void deleteSelected() {
        measurement.deleteValues(getSelectedMeasurementEntries());
    }
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelForEditButtons() {
		if (jPanelForEditButtons == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = -1;
			gridBagConstraints.gridy = -1;
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			jPanelForEditButtons = new JPanel();
			jPanelForEditButtons.setLayout(new GridBagLayout());
			gridBagConstraints27.gridx = -1;
			gridBagConstraints27.gridy = -1;
			gridBagConstraints27.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints27.insets = new java.awt.Insets(2,2,2,2);
			gridBagConstraints27.weightx = 1.0D;
			gridBagConstraints28.gridx = -1;
			gridBagConstraints28.gridy = -1;
			gridBagConstraints28.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints28.insets = new java.awt.Insets(2,2,2,2);
			gridBagConstraints28.weightx = 1.0D;
			gridBagConstraints29.gridx = -1;
			gridBagConstraints29.gridy = -1;
			gridBagConstraints29.gridwidth = 0;
			gridBagConstraints29.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints29.insets = new java.awt.Insets(2,2,2,2);
			gridBagConstraints29.weightx = 1.0D;
			gridBagConstraints30.gridx = -1;
			gridBagConstraints30.gridy = -1;
			gridBagConstraints30.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints30.insets = new java.awt.Insets(2,2,2,2);
			gridBagConstraints30.weightx = 1.0D;
			jPanelForEditButtons.add(getJButtonCopyAll(), gridBagConstraints30);
			jPanelForEditButtons.add(getJButtonCopySelected(), gridBagConstraints29);
			jPanelForEditButtons.add(getJButtonDeleteLast(), gridBagConstraints28);
			jPanelForEditButtons.add(getJButtonDeleteSelected(), gridBagConstraints27);
		}
		return jPanelForEditButtons;
	}
    /**
     * This method initializes jPanelForConversions	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelForConversions() {
        if (jPanelForConversions == null) {
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.insets = new Insets(2, 2, 2, 2);
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridwidth = 0;
            gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridwidth = 0;
            gridBagConstraints6.insets = new Insets(2, 2, 2, 2);
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.gridwidth = 0;
            gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints5.weightx = 1.0;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints4.weightx = 1.0;
            jLabelYValueName = new JLabel();
            jLabelYValueName.setText("Název 2. hodnoty:");
            jLabelXValueName = new JLabel();
            jLabelXValueName.setText("Název 1. hodnoty:");
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints3.gridx = -1;
            gridBagConstraints3.gridy = -1;
            gridBagConstraints3.gridwidth = 0;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints2.gridx = -1;
            gridBagConstraints2.gridy = -1;
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridwidth = 1;
            jPanelForConversions = new JPanel();
            jPanelForConversions.setLayout(new GridBagLayout());
            jPanelForConversions.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            jPanelForConversions.add(jLabelXValueName, gridBagConstraints8);
            jPanelForConversions.add(jLabelYValueName, gridBagConstraints6);
            jPanelForConversions.add(getJTextFieldXValueName(), gridBagConstraints4);
            jPanelForConversions.add(getJTextFieldYValueName(), gridBagConstraints5);
            jPanelForConversions.add(getJCheckBoxAutoConvert(), gridBagConstraints3);
            jPanelForConversions.add(getUnitSelectionPanelX(), gridBagConstraints2);
            jPanelForConversions.add(getUnitSelectionPanelY(), gridBagConstraints7);
        }
        return jPanelForConversions;
    }
    /**
     * This method initializes unitSelectionPanelY	
     * 	
     * @return unisave2006.gui.UnitSelectionPanel	
     */
    public UnitSelectionPanel getUnitSelectionPanelY() {
        if (unitSelectionPanelY == null) {
            unitSelectionPanelY = new UnitSelectionPanel();
        }
        return unitSelectionPanelY;
    }
    /**
     * This method initializes jTextFieldXValueName	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldXValueName() {
        if (jTextFieldXValueName == null) {
            jTextFieldXValueName = new JTextField();
            jTextFieldXValueName.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    GlobalSetting.getUserSetting().setLastUsedXName(getJTextFieldXValueName().getText());
                    if(measurement != null)
                        measurement.setXValueName(getJTextFieldXValueName().getText());
                }
            });
        }
        return jTextFieldXValueName;
    }
    /**
     * This method initializes jTextFieldYValueName	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldYValueName() {
        if (jTextFieldYValueName == null) {
            jTextFieldYValueName = new JTextField();
            jTextFieldYValueName.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    GlobalSetting.getUserSetting().setLastUsedYName(getJTextFieldYValueName().getText());
                    if(measurement != null)
                        measurement.setYValueName(getJTextFieldYValueName().getText());
                }
            });
        }
        return jTextFieldYValueName;
    }
    /**
     * This method initializes jSplitPane	
     * 	
     * @return javax.swing.JSplitPane	
     */
    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            jSplitPane.setOneTouchExpandable(true);
            jSplitPane.setPreferredSize(new Dimension(510, 431));
            jSplitPane.setBottomComponent(getJPanelBottom());
            jSplitPane.setTopComponent(getJPanelTop());
        }
        return jSplitPane;
    }
    /**
     * This method initializes jPanelTop	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelTop() {
        if (jPanelTop == null) {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridwidth = 0;
            gridBagConstraints1.weightx = 1.0D;
            gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.anchor = GridBagConstraints.EAST;
            gridBagConstraints13.gridwidth = 0;
            gridBagConstraints13.gridx = -1;
            gridBagConstraints13.gridy = -1;
            gridBagConstraints13.insets = new Insets(2, 2, 2, 2);
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints12.gridy = -1;
            gridBagConstraints12.gridx = -1;
            jPanelTop = new JPanel();
            jPanelTop.setLayout(new GridBagLayout());
            jPanelTop.add(getJCheckBoxAutoIndex(), gridBagConstraints12);
            jPanelTop.add(getJButtonReindex(), gridBagConstraints13);
            jPanelTop.add(getJPanelForConversions(), gridBagConstraints1);
        }
        return jPanelTop;
    }
    /**
     * This method initializes jPanelBottom	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelBottom() {
        if (jPanelBottom == null) {
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.gridx = -1;
            gridBagConstraints11.gridy = -1;
            gridBagConstraints11.weightx = 0.0D;
            gridBagConstraints11.gridwidth = 0;
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.fill = GridBagConstraints.BOTH;
            gridBagConstraints10.gridwidth = 0;
            gridBagConstraints10.gridx = -1;
            gridBagConstraints10.gridy = -1;
            gridBagConstraints10.weightx = 1.0;
            gridBagConstraints10.weighty = 1.0;
            gridBagConstraints10.insets = new Insets(2, 2, 2, 2);
            jPanelBottom = new JPanel();
            jPanelBottom.setLayout(new GridBagLayout());
            jPanelBottom.add(getJScrollPaneforValues(), gridBagConstraints10);
            jPanelBottom.add(getJPanelForEditButtons(), gridBagConstraints11);
        }
        return jPanelBottom;
    }

    public XYMeasurement getMeasurement() {
        return measurement;
    }
    public void setMeasurement(XYMeasurement measurement) {
        if(this.measurement != null){
            this.measurement.removeMeasurementSettingListener(this);
            this.measurement.removeXYMeasurementSettingListener(this);
        }
        this.measurement = measurement;
        if(measurement != null){
            measurement.addMeasurementSettingListener(this);
            measurement.addXYMeasurementSettingListener(this);
            getUnitSelectionPanelX().setSelectedUnit(measurement.getAutoConvertUnit());
            getUnitSelectionPanelY().setSelectedUnit(measurement.getYAutoconvertUnit());
            getJTextFieldXValueName().setText(measurement.getXValueName());
            getJTextFieldYValueName().setText(measurement.getYValueName());
            getJCheckBoxAutoConvert().setSelected(measurement.getAutoConvert());
            getJCheckBoxAutoIndex().setSelected(measurement.getAutoIndex());
        }
        getJTableValues().setModel(measurement.getTableModel());
    }
    public void xValueNameChanged() {
        if(measurement != null)
            getJTextFieldXValueName().setText(measurement.getXValueName());
        
    }
    public void yAutoconverUnitChanged() {
    }
    public void yValueNameChanged() {
        if(measurement != null)
            getJTextFieldYValueName().setText(measurement.getYValueName());
        
    }
    public void autoConvertChanged() {
        if(measurement != null)
            getJCheckBoxAutoConvert().setSelected(measurement.getAutoConvert());
        
    }
    public void autoConvertUnitChanged() {
    }
    public void autoindexingChanged() {
        if(measurement != null)
            getJCheckBoxAutoIndex().setSelected(measurement.getAutoIndex());
        
    }
    public void modifiedChanged() {
    }
    
    public void selectAll() {
        getJTableValues().selectAll();
    }


    public void copySelectedItems() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new MeasurementEntityTransferer(getSelectedMeasurementEntries()),
        		new ClipboardOwner(){

			public void lostOwnership(Clipboard clipboard, Transferable contents) {
				// TODO Auto-generated method stub
			}
        	
        });
        
    }
    public void cutSelectedItems() {
        copySelectedItems();
        deleteSelected();
    }
    public void editSelectedItem() {
    	int row = getJTableValues().getSelectedRow();
        if(row >= 0){
        	MeasurementEntry e = measurement.getElementAt(getJTableValues().getSelectedRow());
        	EditDialog ed = new EditDialog(JOptionPane.getFrameForComponent(this));
        	ed.setEditedEntity(e);
        	ed.pack();
        	ed.setLocationRelativeTo(this);
        	ed.setModal(true);
        	ed.setVisible(true);
        	if(ed.isEntryChanged()){
        		measurement.fireEntryChanged(row, e);
        	}
        }
        
    }

    public void unitConversion() {
    	Component f = this;
    	while(f != null && !(f instanceof Frame)){
    		f = f.getParent();
    	}
        UnitConversionDialog ucd = new UnitConversionDialog((Frame)f);
        ucd.setLocationRelativeTo(f);
        ucd.setMeasurement(measurement);
        ucd.setSelected(getSelectedMeasurementEntries());
        ucd.setVisible(true);
    }

    public void viewHistogram() {
        // TODO Auto-generated method stub
        
    }

    public void setMeasurement(Measurement m) {
        if(m instanceof XYMeasurement)
            setMeasurement((XYMeasurement)m);
    }
	public void mostRecentUnitChanged(Unit u) {
		if(measurement.isChartCreated()){
			measurement.getchart().getXYPlot().getDomainAxis().setLabel(measurement.getXValueName() + " [" + u.getShortcut() + "]");
		}
	}
	public void mostRecentYUnitChanged(Unit u) {
		if(measurement.isChartCreated()){
			measurement.getchart().getXYPlot().getRangeAxis().setLabel(measurement.getYValueName() + " [" + u.getShortcut() + "]");
		}
	}
	public void ignoreGroweXValueChanged() {
	}
	public void xMarkerChanged() {
	}
	public void xMarkerFixedChanged() {
	}
	public void yMarkerChanged() {
	}
	public void yMarkerFixedChanged() {
	}

    
}  //  @jve:decl-portIndex=0:visual-constraint="10,10"
