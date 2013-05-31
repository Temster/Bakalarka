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
import java.util.Date;
import java.text.*;

import javax.swing.SwingConstants;

import unisave2006.data.Measurer;
import unisave2006.dao.data.MeasurerDAO;

public class AddNewMeasurerPanel extends JDialog {

	private JPanel jPanelMain = null;  //  @jve:decl-index=0:visual-constraint="31,48"
	private JLabel jLabelId = null;
	private JLabel jLabelTitle = null;
	private JTextField jTextFieldTitle = null;
	private JLabel jLabelType = null;
	private JTextField jTextFieldType = null;
	private JLabel jLabelCalibrationDate = null;
	private JTextField jTextFieldCalibrationDate = null;
	private JButton jButtonAdd = null;
	private JButton jButtonCancel = null;
	private JTextField jTextFieldId = null;
	private DateSelector dateSelector = null;
	private Measurer measurer;
	private MeasurerDAO mDAO;
	
	public AddNewMeasurerPanel(MeasurerDAO mDAO) {
		super();
		initialize();
		setModal(true);
		this.mDAO = mDAO;
	}
	
	private void initialize() {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Nové zařízení");
		this.setContentPane(getJPanelMain());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				measurer = null;
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
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			//gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 3;
			jLabelCalibrationDate = new JLabel();
			jLabelCalibrationDate.setText("Datum kalibrace");
			jLabelCalibrationDate.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelCalibrationDate.setPreferredSize(new Dimension(100, 20));
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 2;
			jLabelType = new JLabel();
			jLabelType.setText("Typ");
			jLabelType.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelType.setPreferredSize(new Dimension(100, 20));
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
			jLabelTitle = new JLabel();
			jLabelTitle.setText("Název");
			jLabelTitle.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabelTitle.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelTitle.setPreferredSize(new Dimension(100, 20));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints.gridy = 0;
			jLabelId = new JLabel();
			jLabelId.setText("Identifikace");
			jLabelId.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelId.setPreferredSize(new Dimension(100, 20));
			jPanelMain = new JPanel();
			jPanelMain.setLayout(new GridBagLayout());
			jPanelMain.setSize(new Dimension(381, 140));
			jPanelMain.setPreferredSize(new Dimension(350, 140));
			jPanelMain.add(jLabelId, gridBagConstraints);
			jPanelMain.add(jLabelTitle, gridBagConstraints2);
			jPanelMain.add(getJTextFieldTitle(), gridBagConstraints3);
			jPanelMain.add(jLabelType, gridBagConstraints4);
			jPanelMain.add(getJTextFieldType(), gridBagConstraints5);
			jPanelMain.add(jLabelCalibrationDate, gridBagConstraints6);
			jPanelMain.add(getDateSelector(), gridBagConstraints7);
			jPanelMain.add(getJButtonAdd(), gridBagConstraints13);
			jPanelMain.add(getJButtonCancel(), gridBagConstraints14);
			jPanelMain.add(getJTextFieldId(), gridBagConstraints15);
		}
		return jPanelMain;
	}

	/**
	 * This method initializes jTextFieldTitle
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldTitle() {
		if (jTextFieldTitle == null) {
			jTextFieldTitle = new JTextField();
			jTextFieldTitle.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return jTextFieldTitle;
	}

	/**
	 * This method initializes jTextFieldType
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldType() {
		if (jTextFieldType == null) {
			jTextFieldType = new JTextField();
		}
		return jTextFieldType;
	}

	/**
	 * This method initializes jTextFieldCalibrationDate
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldCalibrationDate() {
		if (jTextFieldCalibrationDate == null) {
			jTextFieldCalibrationDate = new JTextField();
		}
		return jTextFieldCalibrationDate;
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
							//DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
							Date calibDate = dateSelector.getSelectedDate();
							measurer = new Measurer(getJTextFieldId().getText(),
									getJTextFieldTitle().getText(),
									getJTextFieldType().getText(),
									calibDate);
							mDAO.saveOrUpdate(measurer);
							dispose();
							/*
							try {
								calibDate = df.parse(getJTextFieldCalibrationDate().getText());
								measurer = new Measurer(getJTextFieldId().getText(),
														getJTextFieldTitle().getText(),
														getJTextFieldType().getText(),
														calibDate);
								mDAO.saveOrUpdate(measurer);
								dispose();
							} catch(ParseException e) {
								JOptionPane.showMessageDialog(null, "Datum je špatné. Žadejte datum ve tvaru dd-MM-yyyy",
										"Unisave 2006", JOptionPane.ERROR_MESSAGE);
							}
							*/
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
	 * This method initializes jTextFieldId
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldId() {
		if (jTextFieldId == null) {
			jTextFieldId = new JTextField();
		}
		return jTextFieldId;
	}
	
	private DateSelector getDateSelector() {
		if(dateSelector == null) {
			dateSelector = new DateSelector();
		}
		return dateSelector;
	}
	
}
