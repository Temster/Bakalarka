package unisave2006.gui;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.GridBagLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.ComponentOrientation;
import java.awt.Insets;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;

import unisave2006.data.Address;
import unisave2006.data.Organization;
import unisave2006.dao.data.OrganizationDAO;

public class AddNewCustomerPanel extends JDialog {

	private JPanel jPanelMain = null;  //  @jve:decl-index=0:visual-constraint="48,48"
	private JLabel jLabelTitle = null;
	private JLabel jLabelStreet = null;
	private JTextField jTextFieldUlice = null;
	private JLabel jLabelPSC = null;
	private JTextField jTextFieldPSC = null;
	private JLabel jLabelCity = null;
	private JTextField jTextFieldCity = null;
	private JLabel jLabelTel = null;
	private JTextField jTextFieldTel = null;
	private JButton jButtonAdd = null;
	private JButton jButtonCancel = null;
	private JTextField jTextFieldTitle = null;
	private Address newAddress;
	private Organization org;
	private OrganizationDAO orgDAO;
	
	
	public AddNewCustomerPanel(OrganizationDAO orgDAO) {
		super();
		initialize();
		this.orgDAO = orgDAO;
	}
	
	private void initialize() {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Nový zadavatel");
		this.setContentPane(getJPanelMain());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				org = null;
			}
		});
	}
	
	/**
	 * This method initializes jPanelMain
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelMain() {
		if (jPanelMain == null) {
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.gridy = 0;
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints15.gridx = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.insets = new Insets(10, 3, 5, 3);
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.gridy = 5;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.anchor = GridBagConstraints.EAST;
			gridBagConstraints13.insets = new Insets(10, 3, 5, 3);
			gridBagConstraints13.gridy = 5;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.gridy = 4;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints9.gridx = 1;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints8.gridy = 4;
			jLabelTel = new JLabel();
			jLabelTel.setText("Tel/Fax");
			jLabelTel.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelTel.setPreferredSize(new Dimension(50, 20));
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 3;
			jLabelCity = new JLabel();
			jLabelCity.setText("Město");
			jLabelCity.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelCity.setPreferredSize(new Dimension(50, 20));
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 2;
			jLabelPSC = new JLabel();
			jLabelPSC.setText("PSČ");
			jLabelPSC.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelPSC.setPreferredSize(new Dimension(50, 20));
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints2.gridy = 1;
			jLabelStreet = new JLabel();
			jLabelStreet.setText("Ulice");
			jLabelStreet.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabelStreet.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabelStreet.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelStreet.setPreferredSize(new Dimension(50, 20));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints.gridy = 0;
			jLabelTitle = new JLabel();
			jLabelTitle.setText("Název");
			jLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelTitle.setPreferredSize(new Dimension(50, 20));
			jPanelMain = new JPanel();
			jPanelMain.setLayout(new GridBagLayout());
			jPanelMain.setPreferredSize(new Dimension(400, 180));
			jPanelMain.add(jLabelTitle, gridBagConstraints);
			jPanelMain.add(jLabelStreet, gridBagConstraints2);
			jPanelMain.add(getJTextFieldStreet(), gridBagConstraints3);
			jPanelMain.add(jLabelPSC, gridBagConstraints4);
			jPanelMain.add(getJTextFieldPSC(), gridBagConstraints5);
			jPanelMain.add(jLabelCity, gridBagConstraints6);
			jPanelMain.add(getJTextFieldCity(), gridBagConstraints7);
			jPanelMain.add(jLabelTel, gridBagConstraints8);
			jPanelMain.add(getJTextFieldTel(), gridBagConstraints9);
			jPanelMain.add(getJButtonAdd(), gridBagConstraints13);
			jPanelMain.add(getJButtonCancel(), gridBagConstraints14);
			jPanelMain.add(getJTextFieldTitle(), gridBagConstraints15);
		}
		return jPanelMain;
	}

	/**
	 * This method initializes jTextFieldUlice
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldStreet() {
		if (jTextFieldUlice == null) {
			jTextFieldUlice = new JTextField();
			jTextFieldUlice.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return jTextFieldUlice;
	}

	/**
	 * This method initializes jTextFieldPSC
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldPSC() {
		if (jTextFieldPSC == null) {
			jTextFieldPSC = new JTextField();
		}
		return jTextFieldPSC;
	}

	/**
	 * This method initializes jTextFieldCity
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldCity() {
		if (jTextFieldCity == null) {
			jTextFieldCity = new JTextField();
		}
		return jTextFieldCity;
	}

	/**
	 * This method initializes jTextFieldTel
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldTel() {
		if (jTextFieldTel == null) {
			jTextFieldTel = new JTextField();
			jTextFieldTel.setPreferredSize(new Dimension(4, 20));
		}
		return jTextFieldTel;
	}

	/**
	 * This method initializes jButtonAdd
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton();
			jButtonAdd.setPreferredSize(new Dimension(70, 25));
			jButtonAdd.setText("Přidat");
			jButtonAdd.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							newAddress = new Address(getJTextFieldStreet().getText(),
													getJTextFieldCity().getText(),
													getJTextFieldPSC().getText());
							org = new Organization(getJTextFieldTitle().getText(),
													newAddress,
													getJTextFieldTel().getText());
							org.setIsCustomer(true);
							orgDAO.saveOrUpdate(org);
							dispose();
						}
					}
			);
		}
		return jButtonAdd;
	}

	/**
	 * This method initializes jButtonCancel
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setText("Zrušit");
			jButtonCancel.setPreferredSize(new Dimension(70, 25));
			jButtonCancel.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							dispose();
						}
					}
			);
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jTextFieldTitle
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldTitle() {
		if (jTextFieldTitle == null) {
			jTextFieldTitle = new JTextField();
		}
		return jTextFieldTitle;
	}
	
}

