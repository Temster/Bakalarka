package unisave2006.gui;

import javax.swing.JPanel;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.jfree.report.JFreeReport;

import unisave2006.data.XYMeasurement;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class WaitOnGetReport extends JDialog implements Runnable{

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel = null;

	public WaitOnGetReport() {
		super();
		initialize();
	}

	public WaitOnGetReport(Dialog owner) {
		super(owner);
		initialize();
	}

	public WaitOnGetReport(Frame owner, String title) {
		super(owner, title);
		initialize();
	}

	public WaitOnGetReport(Window owner) {
		super(owner);
		initialize();
	}

	/**
	 * @param owner
	 */
	public WaitOnGetReport(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(329, 200);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle("Inicializace...");
		this.setName("WaitDialog");
		this.setModal(true);
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
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints1.gridwidth = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints1.insets = new Insets(10, 10, 10, 10);
			jLabel = new JLabel();
			jLabel.setText("Prosím čekejte ...");
			jLabel2 = new JLabel();
			jLabel2.setText("Probíhá inicializace modulu pro vytváření protokolů.");
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(jLabel2, gridBagConstraints1);
			jContentPane.add(jLabel, gridBagConstraints);
		}
		return jContentPane;
	}

	public static void main(String[] args) {
		new WaitOnGetReport().setVisible(true);
	}

	public void execute(){
		Thread t = new Thread(this);
		t.start();
		setVisible(true);
	}
	protected XYMeasurement measurement = null;
	protected JFreeReport report = null;
	public void run() {
		if (measurement != null) {
			report = measurement.getReport();
		}
		setVisible(false);
	}
	public XYMeasurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(XYMeasurement measurement) {
		this.measurement = measurement;
	}

	public JFreeReport getReport() {
		return report;
	}

	public void setReport(JFreeReport report) {
		this.report = report;
	}
		
}  //  @jve:decl-portIndex=0:visual-constraint="10,10"
