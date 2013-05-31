package unisave2006.gui.value;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JButton;

import unisave2006.data.value.MeasurementEntry;
import unisave2006.gui.ErrorMessagePanel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class EditDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel jPanelEdit = null;

	private JButton jButtonOK = null;

	private JButton jButtonCancel = null;
	
	protected MeasurementEntityEditor editor = null;  //  @jve:decl-portIndex=0:

	//private MeasurementEntry entry;
	
	protected boolean entryChanged = false;

	/**
	 * @param owner
	 */
	public EditDialog(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setTitle("Editace hodnoty");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.weighty = 1.0D;
			gridBagConstraints.gridwidth = 0;
			gridBagConstraints.insets = new Insets(2, 2, 2, 2);
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getJPanelEdit(), gridBagConstraints);
			jContentPane.add(getJButtonOK(), gridBagConstraints1);
			jContentPane.add(getJButtonCancel(), gridBagConstraints2);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelEdit	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelEdit() {
		if (jPanelEdit == null) {
			jPanelEdit = new JPanel();
			jPanelEdit.setLayout(new BorderLayout());
		}
		return jPanelEdit;
	}

	/**
	 * This method initializes jButtonOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setText("Potvrdit");
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(editor != null){
						try {
							editor.getEntity();
							entryChanged = true;
							dispose();
						} catch (MeasurementEntityConstructionException e1) {
							JOptionPane.showMessageDialog(EditDialog.this, new ErrorMessagePanel("Neplatn� hodnota.", e1.getMessage()),"Unisave 2006 - Chyba" ,JOptionPane.ERROR_MESSAGE);
						}
					}
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
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

	public void setEditedEntity(MeasurementEntry entry) {
		//this.entry = entry;
		editor = MeasurementEntityEditPanelFactory.createEditPanel(entry.getClassType());
		editor.setMeasurementEntity(entry);
		editor.setEnableInserting(true);
        getJPanelEdit().removeAll();
        getJPanelEdit().add(editor.getPanel(), BorderLayout.CENTER);
		
		
	}

	public boolean isEntryChanged() {
		return entryChanged;
	}

}
