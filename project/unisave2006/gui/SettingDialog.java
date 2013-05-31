package unisave2006.gui;

import javax.swing.JPanel;
import java.awt.Frame;
import javax.swing.JDialog;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import unisave2006.GlobalSetting;
import unisave2006.PortName;
import unisave2006.UserSetting;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class SettingDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel jPanelPortSelection = null;

	private JList jListPort = null;

	private JButton jButtonAddPorts = null;

	private JButton jButtonRemovePort = null;

	private JPanel jPanelNumberFormat = null;

	private JLabel jLabelMaxFloatCount = null;

	private JTextField jTextFieldMaxFloatCount = null;

	private JRadioButton jRadioButtonNoExp = null;

	private JRadioButton jRadioButtonWithExp = null;

	private JCheckBox jCheckBoxFillZero = null;

	private JButton jButtonOk = null;

	private JButton jButtonCancel = null;
	protected ButtonGroup buttonGroup = new ButtonGroup();  //  @jve:decl-portIndex=0:

	private JPanel jPanelButtons = null;

	private JScrollPane jScrollPane = null;

	private JButton jButtonDefault = null;
	
	protected DefaultListModel model = new DefaultListModel(); 

	/**
	 * @param owner
	 */
	public SettingDialog(Frame owner) {
		super(owner);
		initialize();
        GlobalSetting.getHelpBroker().enableHelpKey(this.getRootPane(), "SettingDialog", GlobalSetting.getHelpSet());

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(364, 334);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Nastavení");
		this.setContentPane(getJContentPane());
		getJTextFieldMaxFloatCount().setText(Integer.toString(GlobalSetting.getUserSetting().getMaxNumberOfFloatDigit()));
		buttonGroup.add(getJRadioButtonNoExp());
		buttonGroup.add(getJRadioButtonWithExp());
		buttonGroup.setSelected(getJRadioButtonNoExp().getModel(), !GlobalSetting.getUserSetting().isExponentFormat());
		buttonGroup.setSelected(getJRadioButtonWithExp().getModel(), GlobalSetting.getUserSetting().isExponentFormat());
		getJCheckBoxFillZero().setSelected(GlobalSetting.getUserSetting().isFillByZerro());
		Set<PortName> ports = GlobalSetting.getUserSetting().getPortNames();
		for(PortName p: ports){
			model.addElement(p);
		}
		getJListPort().setModel(model);
		this.setModal(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.weighty = 1.0D;
			gridBagConstraints4.gridwidth = 0;
			gridBagConstraints4.insets = new Insets(2, 10, 2, 10);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 1.0D;
			gridBagConstraints1.insets = new Insets(2, 10, 2, 10);
			gridBagConstraints1.gridwidth = 0;
			gridBagConstraints1.weightx = 1.0D;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getJPanelPortSelection(), gridBagConstraints1);
			jContentPane.add(getJPanelNumberFormat(), gridBagConstraints4);
			jContentPane.add(getJPanelButtons(), new GridBagConstraints());
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelPortSelection	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelPortSelection() {
		if (jPanelPortSelection == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(20, 2, 2, 2);
			gridBagConstraints.anchor = GridBagConstraints.SOUTH;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.weighty = 1.0;
			gridBagConstraints12.gridheight = 0;
			gridBagConstraints12.weightx = 1.0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
			jPanelPortSelection = new JPanel();
			jPanelPortSelection.setLayout(new GridBagLayout());
			jPanelPortSelection.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Komunikační porty", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelPortSelection.add(getJScrollPane(), gridBagConstraints12);
			jPanelPortSelection.add(getJButtonAddPorts(), gridBagConstraints2);
			jPanelPortSelection.add(getJButtonRemovePort(), gridBagConstraints3);
			jPanelPortSelection.add(getJButtonDefault(), gridBagConstraints);
		}
		return jPanelPortSelection;
	}

	/**
	 * This method initializes jListPort	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListPort() {
		if (jListPort == null) {
			jListPort = new JList();
		}
		return jListPort;
	}

	/**
	 * This method initializes jButtonAddPorts	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAddPorts() {
		if (jButtonAddPorts == null) {
			jButtonAddPorts = new JButton();
			jButtonAddPorts.setText("Přidat...");
			jButtonAddPorts.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PortNumberSelection d = new PortNumberSelection(null);
					d.pack();
					d.setLocationRelativeTo(getJListPort());
					d.setVisible(true);
					if(d.getPortName() != null){
						if(model.contains(d.getPortName())){
							getJListPort().setSelectedIndex(model.indexOf(d.getPortName()));
						}else{
							model.addElement(d.getPortName());
							Object o[] = model.toArray();
							Arrays.sort(o);
							model.removeAllElements();
							for(Object ob: o){
								model.addElement(ob);
							}
						}
					}
				}
			});
		}
		return jButtonAddPorts;
	}

	/**
	 * This method initializes jButtonRemovePort	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRemovePort() {
		if (jButtonRemovePort == null) {
			jButtonRemovePort = new JButton();
			jButtonRemovePort.setText("Odebrat");
			jButtonRemovePort.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object sel[] = getJListPort().getSelectedValues();
					for(Object o: sel){
						model.removeElement(o);
					}
				}
			});
		}
		return jButtonRemovePort;
	}

	/**
	 * This method initializes jPanelNumberFormat	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelNumberFormat() {
		if (jPanelNumberFormat == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridwidth = 0;
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.gridwidth = 0;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridwidth = 0;
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.insets = new Insets(2, 2, 2, 2);
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridwidth = 0;
			gridBagConstraints6.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.weightx = 0.0D;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = -1;
			gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints5.gridwidth = 1;
			gridBagConstraints5.gridy = -1;
			jLabelMaxFloatCount = new JLabel();
			jLabelMaxFloatCount.setText("Maximální počet " +
					"desetinných míst:");
			TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Form�t \u010d�sel", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51));
			titledBorder.setTitle("Formát desetinních čísel");
			jPanelNumberFormat = new JPanel();
			jPanelNumberFormat.setLayout(new GridBagLayout());
			jPanelNumberFormat.setBorder(titledBorder);
			jPanelNumberFormat.add(jLabelMaxFloatCount, gridBagConstraints5);
			jPanelNumberFormat.add(getJTextFieldMaxFloatCount(), gridBagConstraints6);
			jPanelNumberFormat.add(getJRadioButtonNoExp(), gridBagConstraints7);
			jPanelNumberFormat.add(getJRadioButtonWithExp(), gridBagConstraints8);
			jPanelNumberFormat.add(getJCheckBoxFillZero(), gridBagConstraints9);
		}
		return jPanelNumberFormat;
	}

	/**
	 * This method initializes jTextFieldMaxFloatCount	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldMaxFloatCount() {
		if (jTextFieldMaxFloatCount == null) {
			jTextFieldMaxFloatCount = new JTextField();
			jTextFieldMaxFloatCount.setPreferredSize(new Dimension(50, 20));
		}
		return jTextFieldMaxFloatCount;
	}

	/**
	 * This method initializes jRadioButtonNoExp	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonNoExp() {
		if (jRadioButtonNoExp == null) {
			jRadioButtonNoExp = new JRadioButton();
			jRadioButtonNoExp.setText("Bez exponentu");
		}
		return jRadioButtonNoExp;
	}

	/**
	 * This method initializes jRadioButtonWithExp	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonWithExp() {
		if (jRadioButtonWithExp == null) {
			jRadioButtonWithExp = new JRadioButton();
			jRadioButtonWithExp.setText("Exponenciální tvar");
		}
		return jRadioButtonWithExp;
	}

	/**
	 * This method initializes jCheckBoxFillZero	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxFillZero() {
		if (jCheckBoxFillZero == null) {
			jCheckBoxFillZero = new JCheckBox();
			jCheckBoxFillZero.setText("Doplňovat nuly do maximálního počtu desetinných míst");
		}
		return jCheckBoxFillZero;
	}

	/**
	 * This method initializes jButtonOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOk() {
		if (jButtonOk == null) {
			jButtonOk = new JButton();
			jButtonOk.setText("Potvrdit");
			jButtonOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						GlobalSetting.getUserSetting().setNumberFormat(
								Integer.parseInt(getJTextFieldMaxFloatCount().getText()), 
								buttonGroup.isSelected(getJRadioButtonWithExp().getModel()), 
								getJCheckBoxFillZero().isSelected());
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(getJTextFieldMaxFloatCount(), "Text v poli \"Maximální počet desetinních míst\"\nnelze převést na celé číslo.", "UniSave 2006 - Chyba", JOptionPane.ERROR_MESSAGE);
						getJTextFieldMaxFloatCount().requestFocus();
						return;
					}
					Set<PortName> v = new HashSet<PortName>(model.getSize());
					for(Object o: model.toArray()){
						if(o instanceof PortName)
							v.add((PortName)o);
					}
					GlobalSetting.getUserSetting().setPortNames(v);
					dispose();
				}
			});
		}
		return jButtonOk;
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
	 * This method initializes jPanelButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = -1;
			gridBagConstraints11.insets = new Insets(2, 2, 10, 2);
			gridBagConstraints11.gridy = -1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = -1;
			gridBagConstraints10.insets = new Insets(2, 2, 10, 2);
			gridBagConstraints10.gridy = -1;
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(new GridBagLayout());
			jPanelButtons.add(getJButtonOk(), gridBagConstraints10);
			jPanelButtons.add(getJButtonCancel(), gridBagConstraints11);
		}
		return jPanelButtons;
	}
	
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJListPort());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jButtonDefault	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDefault() {
		if (jButtonDefault == null) {
			jButtonDefault = new JButton();
			jButtonDefault.setText("Výchozí");
			jButtonDefault.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					UserSetting s = UserSetting.initialize();
					model.removeAllElements();
					for(PortName p: s.getPortNames()){
						model.addElement(p);
					}
				}
			});
		}
		return jButtonDefault;
	}

	public static void main(String args[]){
		new SettingDialog(null).setVisible(true);
	}

}  //  @jve:decl-portIndex=0:visual-constraint="10,10"
