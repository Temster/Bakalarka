package unisave2006.gui;

import javax.swing.JPanel;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;

import unisave2006.PortName;

public class PortNumberSelection extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel jLabelQestion = null;

	private JTextField jTextFieldNumber = null;

	private JButton jButtonOk = null;

	private JButton jButtonCancel = null;

	protected PortName portName;

	/**
	 * @param owner
	 */
	public PortNumberSelection(Frame owner) {
		super(owner);
		initialize();
		setModal(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Port");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				portName = null;
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(2, 2, 10, 10);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.anchor = GridBagConstraints.EAST;
			gridBagConstraints2.insets = new Insets(2, 10, 10, 2);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.insets = new Insets(2, 10, 10, 10);
			gridBagConstraints1.gridwidth = 0;
			gridBagConstraints1.weightx = 1.0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridwidth = 0;
			gridBagConstraints.insets = new Insets(10, 10, 2, 10);
			gridBagConstraints.gridy = 0;
			jLabelQestion = new JLabel();
			jLabelQestion.setText("Vložte číslo portu (1-99):");
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(jLabelQestion, gridBagConstraints);
			jContentPane.add(getJTextFieldNumber(), gridBagConstraints1);
			jContentPane.add(getJButtonOk(), gridBagConstraints2);
			jContentPane.add(getJButtonCancel(), gridBagConstraints3);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextFieldNumber	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldNumber() {
		if (jTextFieldNumber == null) {
			jTextFieldNumber = new JTextField();
		}
		return jTextFieldNumber;
	}

	/**
	 * This method initializes jButtonOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOk() {
		if (jButtonOk == null) {
			jButtonOk = new JButton();
			jButtonOk.setText("Vložit");
			jButtonOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						int n = Integer.parseInt(getJTextFieldNumber().getText());
						if(n>0 && n<100){
							if(n<10)
								portName = new PortName("com"+n);
							else
								portName = new PortName("com"+n, "\\\\.\\com"+n);
						} else {
							JOptionPane.showMessageDialog(PortNumberSelection.this, "Číslo portu musí být v rozmezu 1 až 99.", "UniSave 2006 - Chyba", JOptionPane.ERROR_MESSAGE);
							getJTextFieldNumber().requestFocus();
							return;
						}
						dispose();
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(PortNumberSelection.this, "Text nelze převést na číslo portu.", "UniSave 2006 - Chyba", JOptionPane.ERROR_MESSAGE);
						getJTextFieldNumber().requestFocus();
					}
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
					portName = null;
					dispose();
				}
			});
		}
		return jButtonCancel;
	}

	public PortName getPortName() {
		return portName;
	}

}
