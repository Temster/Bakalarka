package unisave2006.gui;

import javax.swing.JPanel;
import java.awt.Frame;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;
import java.util.List;

import javax.swing.JButton;

import unisave2006.GlobalSetting;
import unisave2006.data.XYMeasurement;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.MeasurementEntryFactory;
import unisave2006.data.value.XYValue;
import unisave2006.units.Unit;
import unisave2006.units.UnitDescription;
import unisave2006.units.UnitSet;

import javax.swing.WindowConstants;

public class UnitConversionDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JRadioButton jRadioButtonAll = null;

	private JRadioButton jRadioButtonSelected = null;

	private UnitSelectionPanel unitXSelectionPanel = null;

	private UnitSelectionPanel unitYSelectionPanel = null;

	private JLabel jLabelXName = null;

	private JLabel jLabelYName = null;

	private JPanel jPanel = null;

	private JButton jButtonOK = null;

	private JButton jButtonCancel = null;
	
	private ButtonGroup buttonGroup = new ButtonGroup();

	private JPanel jPanel1 = null;
	
	protected XYMeasurement measurement = null;  //  @jve:decl-portIndex=0:

	private Vector<MeasurementEntry> selected; 

	protected List<UnitDescription> unitXAll;  //  @jve:decl-portIndex=0:
	protected List<UnitDescription> unitYAll;  //  @jve:decl-portIndex=0:
	protected List<UnitDescription> unitXSelected;  //  @jve:decl-portIndex=0:
	protected List<UnitDescription> unitYSelected;  //  @jve:decl-portIndex=0:
	protected Collection<UnitDescription> unitXAllConv; 
	protected Collection<UnitDescription> unitYAllConv;
	protected Collection<UnitDescription> unitXSelectedConv;
	protected Collection<UnitDescription> unitYSelectedConv; 

	/**
	 * @param owner
	 */
	public UnitConversionDialog(Frame owner) {
		super(owner);
		initialize();
        GlobalSetting.getHelpBroker().enableHelpKey(this.getRootPane(), "UnitConversion", GlobalSetting.getHelpSet());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setName("unitConversionDialog");
		this.setTitle("Převod jednotek");
		this.setContentPane(getJContentPane());
		this.pack();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(2, 2, 2, 2);
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridwidth = 0;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridwidth = 0;
			gridBagConstraints21.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridwidth = 0;
			gridBagConstraints11.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			jLabelYName = new JLabel();
			jLabelYName.setText("Hodnota Y:");
			jLabelXName = new JLabel();
			jLabelXName.setText("Hodnota X:");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridwidth = 0;
			gridBagConstraints3.gridy = -1;
			gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints3.gridx = -1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridwidth = 0;
			gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getJPanel(), gridBagConstraints31);
			jContentPane.add(getJLabelXName(), gridBagConstraints11);
			jContentPane.add(getUnitXSelectionPanel(), gridBagConstraints2);
			jContentPane.add(getJLabelYName(), gridBagConstraints21);
			jContentPane.add(getUnitYSelectionPanel(), gridBagConstraints3);
			jContentPane.add(getJPanel1(), gridBagConstraints6);
		}
		return jContentPane;
	}

	private JLabel getJLabelXName() {
		if (jLabelXName == null) {
			jLabelXName = new JLabel();
			jLabelXName.setText("Hodnota X:");
		}
		return jLabelXName;
	}
	private JLabel getJLabelYName() {
		if (jLabelYName == null) {
			jLabelYName = new JLabel();
			jLabelYName.setText("Hodnota Y:");
		}
		return jLabelYName;
	}

	/**
	 * This method initializes jRadioButtonAll	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonAll() {
		if (jRadioButtonAll == null) {
			jRadioButtonAll = new JRadioButton();
			buttonGroup.add(jRadioButtonAll);
			jRadioButtonAll.setText("Všechny");
			jRadioButtonAll.setSelected(true);
			jRadioButtonAll.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					fillAll();
				}
			});
		}
		return jRadioButtonAll;
	}

	/**
	 * This method initializes jRadioButtonSelected	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonSelected() {
		if (jRadioButtonSelected == null) {
			jRadioButtonSelected = new JRadioButton();
			buttonGroup.add(jRadioButtonSelected);
			jRadioButtonSelected.setText("Vybrané");
			jRadioButtonSelected.setEnabled(false);
			jRadioButtonSelected.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					fillSelected();
				}
			});
		}
		return jRadioButtonSelected;
	}

	/**
	 * This method initializes unitXSelectionPanel	
	 * 	
	 * @return unisave2006.gui.UnitSelectionPanel	
	 */
	private UnitSelectionPanel getUnitXSelectionPanel() {
		if (unitXSelectionPanel == null) {
			unitXSelectionPanel = new UnitSelectionPanel();
		}
		return unitXSelectionPanel;
	}

	/**
	 * This method initializes unitYSelectionPanel	
	 * 	
	 * @return unisave2006.gui.UnitSelectionPanel	
	 */
	private UnitSelectionPanel getUnitYSelectionPanel() {
		if (unitYSelectionPanel == null) {
			unitYSelectionPanel = new UnitSelectionPanel();
		}
		return unitYSelectionPanel;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridwidth = 0;
			gridBagConstraints1.gridy = -1;
			gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints1.gridx = -1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = -1;
			gridBagConstraints.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints.gridy = -1;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJRadioButtonAll(), gridBagConstraints);
			jPanel.add(getJRadioButtonSelected(), gridBagConstraints1);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButtonOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setText("Převést");
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent event) {
					Unit selXUnit = getUnitXSelectionPanel().getSelectedUnit();
					Unit selYUnit = getUnitYSelectionPanel().getSelectedUnit();
					if(buttonGroup.isSelected(getJRadioButtonAll().getModel())){
						for(MeasurementEntry e: measurement.getElements()){
							if(e.getClassType() == MeasurementEntryFactory.XY_VALUE){
								XYValue xy = (XYValue)e;
								xy.getXValue().convert(selXUnit.getDescription(), selXUnit.getPrefix());
								xy.getYValue().convert(selYUnit.getDescription(), selYUnit.getPrefix());
							}
						}
					}else if(buttonGroup.isSelected(getJRadioButtonSelected().getModel())){
						for(MeasurementEntry e: selected){
							if(e.getClassType() == MeasurementEntryFactory.XY_VALUE){
								XYValue xy = (XYValue)e;
								xy.getXValue().convert(selXUnit.getDescription(), selXUnit.getPrefix());
								xy.getYValue().convert(selYUnit.getDescription(), selYUnit.getPrefix());
							}
						}
					}
					dispose();
					measurement.fireEntriesChanged();
				}
			});
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setText("Storno");
			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = -1;
			gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints5.gridy = -1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = -1;
			gridBagConstraints4.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints4.gridy = -1;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJButtonOK(), gridBagConstraints4);
			jPanel1.add(getJButtonCancel(), gridBagConstraints5);
		}
		return jPanel1;
	}

	public void setMeasurement(XYMeasurement measurement) {
		this.measurement = measurement;
		getJLabelXName().setText(measurement.getXValueName() + ":");
		getJLabelYName().setText(measurement.getYValueName() + ":");
		unitXAll = new ArrayList<UnitDescription>();
		unitYAll = new ArrayList<UnitDescription>();
		for(MeasurementEntry e: measurement.getElements()){
			if(e.getClassType() == MeasurementEntryFactory.XY_VALUE){
				XYValue xy = (XYValue)e;
				unitXAll.add(xy.getXValue().getUnit().getDescription());
				unitYAll.add(xy.getYValue().getUnit().getDescription());
			}
		}
		unitXAllConv = UnitSet.getConvertibleUnits(unitXAll);
		unitYAllConv = UnitSet.getConvertibleUnits(unitYAll);
		if(buttonGroup.isSelected(getJRadioButtonAll().getModel())){
			fillAll();
		}
	}
	protected void fillAll(){
		getUnitXSelectionPanel().setUnits(unitXAllConv);
		getUnitYSelectionPanel().setUnits(unitYAllConv);
	}
	protected void fillSelected(){
		getUnitXSelectionPanel().setUnits(unitXSelectedConv);
		getUnitYSelectionPanel().setUnits(unitYSelectedConv);
	}

	public void setSelected(Vector<MeasurementEntry> selectedMeasurementEntries) {
		selected = selectedMeasurementEntries;
		unitXSelected = new ArrayList<UnitDescription>();
		unitYSelected = new ArrayList<UnitDescription>();
		for(MeasurementEntry e: selectedMeasurementEntries){
			if(e.getClassType() == MeasurementEntryFactory.XY_VALUE){
				XYValue xy = (XYValue)e;
				unitXSelected.add(xy.getXValue().getUnit().getDescription());
				unitYSelected.add(xy.getYValue().getUnit().getDescription());
			}
		}
		unitXSelectedConv = UnitSet.getConvertibleUnits(unitXSelected);
		unitYSelectedConv = UnitSet.getConvertibleUnits(unitYSelected);
		if(selected.size() > 0){
			getJRadioButtonSelected().setEnabled(true);
			getJRadioButtonSelected().doClick();
		}
	}

}
