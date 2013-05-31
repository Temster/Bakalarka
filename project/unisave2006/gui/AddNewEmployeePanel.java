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

import unisave2006.data.Employee;
import unisave2006.dao.data.EmployeeDAO;

public class AddNewEmployeePanel extends JDialog {

	private JPanel jPanelMain = null;  //  @jve:decl-index=0:visual-constraint="48,48"
	private JLabel jLabelFirstName = null;
	private JLabel jLabelLastName = null;
	private JTextField jTextFieldLastName = null;
	private JLabel jLabelPosition = null;
	private JTextField jTextFieldPosition = null;
	private JLabel jLabelDepartment = null;
	private JTextField jTextFieldDepartment = null;
	private JButton jButtonAdd = null;
	private JButton jButtonCancel = null;
	private JTextField jTextFieldFirstName = null;
	private Employee emp;
	private EmployeeDAO eDAO;
	
	public AddNewEmployeePanel(EmployeeDAO eDAO) {
		super();
		initialize();
		this.eDAO = eDAO;
	}
	
	private void initialize() {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Nový zaměstnanec");
		this.setContentPane(getJPanelMain());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				emp = null;
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
			gridBagConstraints14.insets = new Insets(2, 3, 2, 3);
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.gridy = 5;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.anchor = GridBagConstraints.EAST;
			gridBagConstraints13.insets = new Insets(5, 3, 5, 3);
			gridBagConstraints13.gridy = 5;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 3;
			jLabelDepartment = new JLabel();
			jLabelDepartment.setText("Oddělení");
			jLabelDepartment.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelDepartment.setPreferredSize(new Dimension(50, 20));
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 2;
			jLabelPosition = new JLabel();
			jLabelPosition.setText("Zařazení");
			jLabelPosition.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelPosition.setPreferredSize(new Dimension(50, 20));
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
			jLabelLastName = new JLabel();
			jLabelLastName.setText("Příjmení");
			jLabelLastName.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabelLastName.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabelLastName.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelLastName.setPreferredSize(new Dimension(50, 20));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints.gridy = 0;
			jLabelFirstName = new JLabel();
			jLabelFirstName.setText("Jméno");
			jLabelFirstName.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelFirstName.setPreferredSize(new Dimension(50, 20));
			jPanelMain = new JPanel();
			jPanelMain.setLayout(new GridBagLayout());
			jPanelMain.setPreferredSize(new Dimension(350, 140));
			jPanelMain.add(jLabelFirstName, gridBagConstraints);
			jPanelMain.add(jLabelLastName, gridBagConstraints2);
			jPanelMain.add(getJTextFieldLastName(), gridBagConstraints3);
			jPanelMain.add(jLabelPosition, gridBagConstraints4);
			jPanelMain.add(getJTextFieldPosition(), gridBagConstraints5);
			jPanelMain.add(jLabelDepartment, gridBagConstraints6);
			jPanelMain.add(getJTextFieldDepartment(), gridBagConstraints7);
			jPanelMain.add(getJButtonAdd(), gridBagConstraints13);
			jPanelMain.add(getJButtonCancel(), gridBagConstraints14);
			jPanelMain.add(getJTextFieldFirstName(), gridBagConstraints15);
		}
		return jPanelMain;
	}

	/**
	 * This method initializes jTextFieldLastName
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldLastName() {
		if (jTextFieldLastName == null) {
			jTextFieldLastName = new JTextField();
			jTextFieldLastName.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return jTextFieldLastName;
	}

	/**
	 * This method initializes jTextFieldPosition
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldPosition() {
		if (jTextFieldPosition == null) {
			jTextFieldPosition = new JTextField();
		}
		return jTextFieldPosition;
	}

	/**
	 * This method initializes jTextFieldDepartment
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldDepartment() {
		if (jTextFieldDepartment == null) {
			jTextFieldDepartment = new JTextField();
		}
		return jTextFieldDepartment;
	}

	/**
	 * This method initializes jButtonAdd
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton();
			jButtonAdd.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonAdd.setPreferredSize(new Dimension(70, 25));
			jButtonAdd.setText("Přidat");
			jButtonAdd.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							emp = new Employee(getJTextFieldFirstName().getText(),
											   getJTextFieldLastName().getText(),
											   getJTextFieldPosition().getText(),
											   getJTextFieldDepartment().getText());
							eDAO.saveOrUpdate(emp);
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
			jButtonCancel.setMnemonic(KeyEvent.VK_UNDEFINED);
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
	 * This method initializes jTextFieldFirstName
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldFirstName() {
		if (jTextFieldFirstName == null) {
			jTextFieldFirstName = new JTextField();
		}
		return jTextFieldFirstName;
	}
	
}
