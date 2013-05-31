/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.help.CSH;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.report.JFreeReport;
import org.jfree.report.ReportProcessingException;
import org.jfree.report.modules.gui.base.PreviewFrame;
import org.jfree.ui.RefineryUtilities;

import unisave2006.GlobalSetting;
import unisave2006.data.Measurer;
import unisave2006.data.GraphSettingListener;
import unisave2006.data.Measurement;
import unisave2006.data.XYMeasurement;
import unisave2006.data.XYMeasurementSettingListener;
import unisave2006.data.value.Value;
import unisave2006.dao.data.MeasurerDAO;
import unisave2006.gui.action.AbstractActionCopy;
import unisave2006.gui.action.AbstractActionCreateProtocol;
import unisave2006.gui.action.AbstractActionCut;
import unisave2006.gui.action.AbstractActionDeleteLast;
import unisave2006.gui.action.AbstractActionDeleteSelected;
import unisave2006.gui.action.AbstractActionEdit;
import unisave2006.gui.action.AbstractActionSelectAll;
import unisave2006.gui.action.AbstractActionUnitConversion;
import unisave2006.gui.action.AbstractActionViewGraph;
import unisave2006.gui.action.AbstractActionViewHistogram;
import unisave2006.units.Unit;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import unisave2006.gui.value.MeasurementEntityConstructionException;
import unisave2006.gui.value.ValuePanel;
import javax.swing.JButton;

/**
 * @author David Je�ek
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 */
public class XYMeasurementPanel extends JTabbedPane implements MeasurementPanelInterface,
															LoadMeasurerListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1300769173925824557L;
    protected XYMeasurement measurement = null;  //  @jve:decl-portIndex=0:
    protected ButtonGroup graphOrientationButtonGroup = new ButtonGroup();  //  @jve:decl-portIndex=0:
    protected ButtonGroup bgMarkerFix = new ButtonGroup();  //  @jve:decl-portIndex=0:
    
    public void setMeasurement(XYMeasurement measurement) {
    	this.measurement = measurement;
        getXYMeasurementValuePanel().setMeasurement(measurement);
        getDoubleDeviceGrraberPanel().setMeasurement(measurement);
        if(measurement.getElements().size() > 0){
            getActionDeleteLast().setEnabled(true);
            getActionUnitConversion().setEnabled(true);
        }
        getMeasurement().getTableModel().addTableModelListener(new TableModelListener(){
            public void tableChanged(TableModelEvent e) {
            	updateActionDeleteLast();
            	updateActionUnitConversion();
            }
        });
        getOrganizationPanelLaboratory().setOrg(measurement.getProtocolSetting().getLaboratory());
        getCustomerPanel().setOrg(measurement.getProtocolSetting().getCustomer());
        getJTextFieldTitle().setText(measurement.getProtocolSetting().getMeasurementTitle());
        getJTextFieldObjectOfMeasurement().setText(measurement.getProtocolSetting().getObjectOfMeasurement());
        getDateSelector().setSelectedDate(measurement.getProtocolSetting().getDateOfMeasurement());
        getEmployeePanel().setEmployee(measurement.getProtocolSetting().getResponsiblePerson());
        measurer = measurement.getProtocolSetting().getMeasurer();
        getJTextFieldNameOfMeasurementDevice().setText("");
        //getJTextFieldNameOfMeasurementDevice().setText(measurer.getIdentification() + ", "
        											// + measurer.getTitle() + ", "
        											// + measurer.getType() + ", "
        											// + measurer.getcalibrationDate());

        getJTextFieldGraphTitle().setText(measurement.getGraphSetting().getGraphTitle());
        getJTextFieldXAxisManual().setText(measurement.getGraphSetting().getXAxisManualLabel());
        getJTextFieldXAxisManual().setEnabled(!measurement.getGraphSetting().getXAxisAutomaticalLabeling());
        getJTextFieldYAxisManual().setText(measurement.getGraphSetting().getYAxisManualLabel());
        getJTextFieldYAxisManual().setEnabled(!measurement.getGraphSetting().getYAxisAutomaticalLabeling());
        getJCheckBoxXAxisLabelAuto().setSelected(measurement.getGraphSetting().getXAxisAutomaticalLabeling());
        getJCheckBoxYAxisLabelAuto().setSelected(measurement.getGraphSetting().getYAxisAutomaticalLabeling());
        graphOrientationButtonGroup.setSelected(getJRadioButtonVertical().getModel(), 
        		getMeasurement().getGraphSetting().getGraphOrientation().equals(PlotOrientation.VERTICAL));
        graphOrientationButtonGroup.setSelected(getJRadioButtonHorizontal().getModel(), 
        		!getMeasurement().getGraphSetting().getGraphOrientation().equals(PlotOrientation.VERTICAL));
        getJCheckBoxSortXValues().setSelected(measurement.getGraphSetting().getSortValues());
        measurement.addGraphSettingListener(new GraphSettingListener(){
			public void graphTitleChanged() {
		        getJTextFieldGraphTitle().setText(XYMeasurementPanel.this.measurement.getGraphSetting().getGraphTitle());
		        updateGraphTitle();
			}
			public void xAxisAutomaticLabelingChanged() {
		        getJCheckBoxXAxisLabelAuto().setSelected(XYMeasurementPanel.this.measurement.getGraphSetting().getXAxisAutomaticalLabeling());
		        getJTextFieldXAxisManual().setEnabled(!XYMeasurementPanel.this.measurement.getGraphSetting().getXAxisAutomaticalLabeling());
		        updateGraphDomainLabel();
			}
			public void xAxisManualLabelChanged() {
		        getJTextFieldXAxisManual().setText(XYMeasurementPanel.this.measurement.getGraphSetting().getXAxisManualLabel());
		        updateGraphDomainLabel();
			}
			public void yAxisAutomaticLabelingChanged() {
		        getJCheckBoxYAxisLabelAuto().setSelected(XYMeasurementPanel.this.measurement.getGraphSetting().getYAxisAutomaticalLabeling());
		        getJTextFieldYAxisManual().setEnabled(!XYMeasurementPanel.this.measurement.getGraphSetting().getYAxisAutomaticalLabeling());
		        updateGraphRangeLabel();
			}
			public void yAxisManualLabelChanged() {
		        getJTextFieldYAxisManual().setText(XYMeasurementPanel.this.measurement.getGraphSetting().getYAxisManualLabel());
		        updateGraphRangeLabel();
			}
			public void GraphOrientationChanged() {
			       graphOrientationButtonGroup.setSelected(getJRadioButtonVertical().getModel(), 
			        		getMeasurement().getGraphSetting().getGraphOrientation().equals(PlotOrientation.VERTICAL));
			        graphOrientationButtonGroup.setSelected(getJRadioButtonHorizontal().getModel(), 
			        		!getMeasurement().getGraphSetting().getGraphOrientation().equals(PlotOrientation.VERTICAL));
			        updateGraphOrientation();
			}
			public void SortValuesChanged() {
				getJCheckBoxSortXValues().setSelected(XYMeasurementPanel.this.measurement.getGraphSetting().getSortValues());
			}
        });        
        measurement.addXYMeasurementSettingListener(new XYMeasurementSettingListener(){
			public void mostRecentYUnitChanged(Unit u) {}
			public void yAutoconverUnitChanged() {}

			public void xValueNameChanged() {
				updateGraphDomainLabel();
			}
			public void yValueNameChanged() {
				updateGraphRangeLabel();
			}
			public void ignoreGroweXValueChanged() {}
			public void xMarkerChanged() {
				updateGraphXMarker();
			}
			public void xMarkerFixedChanged() {}
			public void yMarkerChanged() {
				updateGraphYMarker();
			}
			public void yMarkerFixedChanged() {}
        });
        measurement.addXYMeasurementSettingListener(new XYMeasurementSettingListener(){

			public void mostRecentYUnitChanged(Unit u) {}
			public void xValueNameChanged() {
				getJRadioButtonValueX().setText(getMeasurement().getXValueName());
				getJButtonIgnoreGrowValues().setText("Odebrat vzrůstající hodnoty pro sloupec " + 
						getMeasurement().getXValueName() + ".");
			}
			public void yAutoconverUnitChanged() {}
			public void yValueNameChanged() {
				getJRadioButtonValueY().setText(getMeasurement().getYValueName());
			}
			public void ignoreGroweXValueChanged() {
				//getJCheckBoxIgnoreReturnRunValue().setSelected(getMeasurement().isIgnoreGroweXValue());
			}
			public void xMarkerChanged() {
				Value v = null;
				try {
					v = getValuePanelX().getValue();
				} catch (MeasurementEntityConstructionException e) {
					v = new Value(); 
				}
				v.copyFrom(getMeasurement().getXMarker());
				getValuePanelX().setValue(v);
			}
			public void xMarkerFixedChanged() {
				updateXMaker();				
			}
			public void yMarkerChanged() {
				Value v = null;
				try {
					v = getValuePanelValueY().getValue();
				} catch (MeasurementEntityConstructionException e) {
					v = new Value(); 
				}
				v.copyFrom(getMeasurement().getYMarker());
				getValuePanelValueY().setValue(v);
			}
			public void yMarkerFixedChanged() {
				updateYMaker();
			}
        	
        });
        bgMarkerFix.setSelected(getJRadioButtonValueX().getModel(), getMeasurement().getXMarkerFixed());
        bgMarkerFix.setSelected(getJRadioButtonValueY().getModel(), getMeasurement().getYMarkerFixed());
        updateXMaker();
        updateYMaker();
		getJRadioButtonValueX().setText(getMeasurement().getXValueName());
		getJRadioButtonValueY().setText(getMeasurement().getYValueName());
		//getJCheckBoxIgnoreReturnRunValue().setSelected(measurement.isIgnoreGroweXValue());
		getJButtonIgnoreGrowValues().setText("Odebrat vzrůstající hodnoty pro sloupec " + 
				getMeasurement().getXValueName() + ".");
		getValuePanelX().setValue(new Value(getMeasurement().getXMarker()));
		getValuePanelValueY().setValue(new Value(getMeasurement().getYMarker()));
		
		getJCheckBoxSortXValues().setSelected(getMeasurement().getGraphSetting().getSortValues());
		graphOrientationButtonGroup.setSelected(getJRadioButtonHorizontal().getModel(),
				getMeasurement().getGraphSetting().getGraphOrientation() == PlotOrientation.HORIZONTAL);
		graphOrientationButtonGroup.setSelected(getJRadioButtonVertical().getModel(),
				getMeasurement().getGraphSetting().getGraphOrientation() == PlotOrientation.VERTICAL);
    }
    
	protected void updateGraphYMarker() {}

	protected void updateGraphXMarker() {}

	protected void updateXMaker() {
		boolean b = bgMarkerFix.isSelected(jRadioButtonValueX.getModel());
		valuePanelX.setEnableInserting(b);
		jButtonSetXMarker.setEnabled(b);
	}
	protected void updateYMaker() {
		boolean b = bgMarkerFix.isSelected(jRadioButtonValueY.getModel());
		valuePanelValueY.setEnableInserting(b);
		jButtonSetYMarker.setEnabled(b);
	}

	private void updateGraphOrientation() {
        if(measurement.isChartCreated()){
        	measurement.getchart().getXYPlot().setOrientation(measurement.getGraphSetting().getGraphOrientation());
        }
	}
    protected void updateGraphTitle(){
        if(measurement.isChartCreated()){
        	measurement.getchart().setTitle(measurement.getGraphSetting().getGraphTitle());
        }
    }
    protected void updateGraphDomainLabel(){
        if(measurement.isChartCreated()){
        	if(measurement.getGraphSetting().getXAxisAutomaticalLabeling()){
        		measurement.getchart().getXYPlot().getDomainAxis().setLabel(measurement.getXValueName() + " " + measurement.getXMostRecentUnit().getShortcut());
        	}
        	else {
        		measurement.getchart().getXYPlot().getDomainAxis().setLabel(measurement.getGraphSetting().getXAxisManualLabel());
        	}
        }
    }
    protected void updateGraphRangeLabel(){
        if(measurement.isChartCreated()){
        	if(measurement.getGraphSetting().getYAxisAutomaticalLabeling())
        		measurement.getchart().getXYPlot().getRangeAxis().setLabel(measurement.getYValueName() + " " + measurement.getYMostRecentUnit().getShortcut());
        	else
        		measurement.getchart().getXYPlot().getRangeAxis().setLabel(measurement.getGraphSetting().getYAxisManualLabel());
        }
    }
    
	private XYMeasurementValuePanel XYMeasurementValuePanel = null;
    private DoubleDeviceGrabberPanel doubleDeviceGrabberPanel = null;
	public XYMeasurement getMeasurement() {
        return measurement;
    }

    /**
	 * This method initializes 
	 * 
	 */
	public XYMeasurementPanel() {
		super();
		initialize();
		mDAO = new MeasurerDAO();
        getDoubleDeviceGrraberPanel().setUnitSelectionPanel(getXYMeasurementValuePanel().getUnitSelectionPanelX());
        getDoubleDeviceGrraberPanel().setUnitSelectionPanel2(getXYMeasurementValuePanel().getUnitSelectionPanelY());
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.addTab("Měření", null, getJPanelMeasurement(), null);
        this.addTab("Graf", null, getJPanelGraph(), null);
        this.addTab("Protokol", null, getJPanelProtocol(), null);
        this.addChangeListener(new javax.swing.event.ChangeListener() {
        	public void stateChanged(javax.swing.event.ChangeEvent e) {
        		updateActionCopy();
        		updateActionCut();
        		updateActionDeleteLast();
        		updateActionDeleteSelected();
        		updateActionEdit();
        		updateActionUnitConversion();
        	}
        });
        getXYMeasurementValuePanel().getJTableValues().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                	updateActionCopy();
                	updateActionCut();
                	updateActionDeleteSelected();
                	updateActionEdit();
                }
                
            }
        });
        graphOrientationButtonGroup.add(getJRadioButtonVertical());
        graphOrientationButtonGroup.add(getJRadioButtonHorizontal());
        bgMarkerFix.add(getJRadioButtonValueX());
        bgMarkerFix.add(getJRadioButtonValueY());
	}
	/**
	 * This method initializes XYMeasurementValuePanel	
	 * 	
	 * @return unisave2006.gui.XYMeasurementValuePanel	
	 */    
	private XYMeasurementValuePanel getXYMeasurementValuePanel() {
		if (XYMeasurementValuePanel == null) {
			XYMeasurementValuePanel = new XYMeasurementValuePanel();
			XYMeasurementValuePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Hodnoty", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		}
		return XYMeasurementValuePanel;
	}
    /**
     * This method initializes doubleDeviceGrabberPanel	
     * 	
     * @return unisave2006.gui.DoubleDeviceGrabberPanel	
     */
    private DoubleDeviceGrabberPanel getDoubleDeviceGrraberPanel() {
        if (doubleDeviceGrabberPanel == null) {
            doubleDeviceGrabberPanel = new DoubleDeviceGrabberPanel();
        }
        return doubleDeviceGrabberPanel;
    }

    public JComponent getPanel() {
        return this;
    }

    public void setMeasurement(Measurement m) {
        if(m instanceof XYMeasurement)
            setMeasurement((XYMeasurement)m);
    }

    
    class ActionCopy extends AbstractActionCopy{
        private static final long serialVersionUID = 4053420263141063164L;
        public ActionCopy(){
            super();
        }
        public void actionPerformed(ActionEvent e) {
            getXYMeasurementValuePanel().copySelectedItems();
        }
    }
    protected ActionCopy actionCopy = null;  //  @jve:decl-portIndex=0:
    public Action getActionCopy() {
        if(actionCopy == null)
            actionCopy = new ActionCopy();
        return actionCopy;
    }
    
    class ActionCreateProtocol extends AbstractActionCreateProtocol {
         private static final long serialVersionUID = -1723377101682351232L;
        public ActionCreateProtocol(){
            super();
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            createProtocol();
        }
        
    }
    protected ActionCreateProtocol actionCreateProtocol = null;  //  @jve:decl-portIndex=0:
    public Action getActionCreateProtocol() {
        if(actionCreateProtocol == null)
            actionCreateProtocol = new ActionCreateProtocol();
        return actionCreateProtocol;
    }
    
    class ActionCut extends AbstractActionCut{
        private static final long serialVersionUID = 5768454331884884741L;
        public ActionCut(){
            super();
        }
        public void actionPerformed(ActionEvent e) {
            getXYMeasurementValuePanel().cutSelectedItems();
        }
        
    }
    protected ActionCut actionCut = null;  //  @jve:decl-portIndex=0:
    public Action getActionCut() {
        if(actionCut == null)
            actionCut = new ActionCut();
        return actionCut;
    }
   
    class ActionDeleteLast extends AbstractActionDeleteLast{
        private static final long serialVersionUID = -4096577291862232557L;
        public ActionDeleteLast(){
            super();
        }
        public void actionPerformed(ActionEvent e) {
            getXYMeasurementValuePanel().deleteLast();
        }
        
    }
    protected ActionDeleteLast actionDeleteLast = null;  //  @jve:decl-portIndex=0:
    public Action getActionDeleteLast() {
        if(actionDeleteLast == null)
            actionDeleteLast = new ActionDeleteLast();
        return actionDeleteLast;
    }
    
    class ActionDeleteSelected extends AbstractActionDeleteSelected{
        private static final long serialVersionUID = -8114231891380444751L;
        public ActionDeleteSelected(){
            super();
        }
        public void actionPerformed(ActionEvent e) {
            getXYMeasurementValuePanel().deleteSelected();
        }
    }
    protected ActionDeleteSelected actionDeleteSelected = null;  //  @jve:decl-portIndex=0:
    public Action getActionDeleteSelected() {
        if(actionDeleteSelected == null)
            actionDeleteSelected = new ActionDeleteSelected();
        return actionDeleteSelected;
    }
    
    class ActionSelectAll extends AbstractActionSelectAll{
        private static final long serialVersionUID = 8263727202437333052L;
        public ActionSelectAll(){
            super();
        }
        public void actionPerformed(ActionEvent e) {
            getXYMeasurementValuePanel().selectAll();
        }
    }
    protected ActionSelectAll actionSelectAll = null;
    class ActionUnitConversion extends AbstractActionUnitConversion{
        private static final long serialVersionUID = 5890558447930388714L;
        public ActionUnitConversion(){
            super();
        }
        public void actionPerformed(ActionEvent e) {
            getXYMeasurementValuePanel().unitConversion();
        }
        
    }
    protected ActionUnitConversion actionUnitConversion = null;  //  @jve:decl-portIndex=0:
	protected JFrame graphFrame;
	protected PreviewFrame protocolFrame;
    public Action getActionSelectAll() {
	    if(actionSelectAll == null)
	        actionSelectAll = new ActionSelectAll();
	    return actionSelectAll;
	}

	public Action getActionUnitConversion() {
        if(actionUnitConversion == null)
            actionUnitConversion = new ActionUnitConversion();
        return actionUnitConversion;
    }
    public void close(){
    	if(graphFrame != null)
    		graphFrame.dispose();
    	if(protocolFrame != null)
    		protocolFrame.dispose();
    }
    public void createProtocol() {
    	getRootPane().getGlassPane().setVisible(true);
    	WaitOnGetReport d = new WaitOnGetReport(SwingUtilities.getWindowAncestor(this));
    	d.setLocationRelativeTo(this);
    	d.setMeasurement(measurement);
    	d.execute();
        JFreeReport job = d.getReport();
        if(job == null){
        	//TODO chyba
        }
        if(protocolFrame != null){
        	protocolFrame.dispose();
        	protocolFrame = null;
        }
        d.dispose();
        getRootPane().getGlassPane().setVisible(false);
    	try {
			protocolFrame = new PreviewFrame(job);
		} catch (ReportProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
    	//protocolFrame.setReportJob(job);
        //protocolFrame.getBase().setToolbarFloatable(true);
        protocolFrame.pack();
        protocolFrame.setIconImage(GlobalSetting.getUniSaveDocIcon());
        RefineryUtilities.positionFrameRandomly(protocolFrame);
        protocolFrame.setVisible(true);
        protocolFrame.requestFocus();
        

    }
    public void viewGraph() {
        if(measurement == null)
            return;
        if(graphFrame != null){
        	graphFrame.setVisible(true);
        	return;
        }
        JFreeChart chart = measurement.getchart();
        if(chart == null){
            JOptionPane.showMessageDialog(this, "Toto měření nepodporuje zobrazení grafu.", "Unisave 2006", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        updateGraphTitle();
        updateGraphDomainLabel();
        updateGraphRangeLabel();

        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setFocusable(true);
        
        chartpanel.setPreferredSize(new Dimension(500, 270));
        graphFrame = new JFrame();
        graphFrame.setTitle("Graf měření");
        graphFrame.setIconImage(GlobalSetting.getGraphIcon());
        graphFrame.getContentPane().add(chartpanel);
        graphFrame.pack();
        graphFrame.setVisible(true);
        GlobalSetting.getHelpBroker().enableHelpKey(graphFrame.getRootPane(), "Graph", GlobalSetting.getHelpSet());
    }
    
    class ActionViewGraph extends AbstractActionViewGraph{
        private static final long serialVersionUID = -7636536952761828690L;
        public ActionViewGraph(){
            super();
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            viewGraph();
        }
    }
    protected ActionViewGraph actionViewGraph = null;  //  @jve:decl-portIndex=0:
    public Action getActionViewGraph() {
        if(actionViewGraph == null)
            actionViewGraph = new ActionViewGraph();
        return actionViewGraph;
    }
    
    class ActionViewHistogram extends AbstractActionViewHistogram{
        private static final long serialVersionUID = -8983400028008744160L;
        public ActionViewHistogram(){
            super();
        }
        public void actionPerformed(ActionEvent e) {
            getXYMeasurementValuePanel().viewHistogram();
        }
    }
    protected ActionViewHistogram actionViewHistogram = null;  //  @jve:decl-portIndex=0:
    public Action getActionViewHistogram() {
        if(actionViewHistogram == null)
            actionViewHistogram = new ActionViewHistogram();
        return actionViewHistogram;
    }

    class ActionEdit extends AbstractActionEdit{
        private static final long serialVersionUID = 5120183994142029757L;
        public ActionEdit(){
            super();
        }
        public void actionPerformed(ActionEvent e) {
            getXYMeasurementValuePanel().editSelectedItem();
        }
    }
    protected ActionEdit actionEdit = null;  //  @jve:decl-portIndex=0:
    private JPanel jPanelMeasurement = null;
    private JPanel jPanelGraph = null;
    private JPanel jPanelProtocol = null;
    private OrganizationPanel organizationPanelLaboratory = null;
    private JLabel jLabelTitle = null;
    private JTextField jTextFieldTitle = null;
    private CustomerPanel customerPanel = null;
    private EmployeePanel employeePanel = null;
    private JLabel jLabelObjectOfMeasurement = null;
    private JTextField jTextFieldObjectOfMeasurement = null;
    private JLabel jLabeldateOfMeasurement = null;
    private JLabel jLabelNameOfMeasurementDevice = null;
    private JTextField jTextFieldNameOfMeasurementDevice = null;
    private DateSelector dateSelector = null;
	private JLabel jLabelGraphTitle = null;
	private JTextField jTextFieldGraphTitle = null;
	private JPanel jPanelXAxis = null;
	private JCheckBox jCheckBoxXAxisLabelAuto = null;
	private JLabel jLabelXAxisLabelManual = null;
	private JTextField jTextFieldXAxisManual = null;
	private JPanel jPanelYAxis = null;
	private JCheckBox jCheckBoxYAxisLabelAuto = null;
	private JLabel jLabelYAxisLabelManual = null;
	private JTextField jTextFieldYAxisManual = null;
	private JCheckBox jCheckBoxSortXValues = null;
	private JPanel jPanelOrientation = null;
	private JRadioButton jRadioButtonVertical = null;
	private JRadioButton jRadioButtonHorizontal = null;
	private JLabel jLabelVertical = null;
	private JLabel jLabelHorizontal = null;
	private JPanel jPanelMeasurementExtendedSetting = null;
	private JLabel jLabelMarker1 = null;
	private JRadioButton jRadioButtonValueX = null;
	private JRadioButton jRadioButtonValueY = null;
	private ValuePanel valuePanelX = null;
	private ValuePanel valuePanelValueY = null;
	private JButton jButtonSetXMarker = null;
	private JButton jButtonSetYMarker = null;
	private JPanel jPanelXMarker = null;
	private JPanel jPanelYMarker = null;
	private JButton jButtonIgnoreGrowValues = null;
	private JButton jButtonChangeMeasurer = null;
	private Measurer measurer = new Measurer();
	private MeasurerDAO mDAO;
	private MeasurerListDialog measurerList;
	
	public Measurer getMeasurer() {
		return measurer;
	}
	
	public void setMeasurer(Measurer measurer) {
		this.measurer = measurer;
		updateMeasurer();
	}
	
	public Action getActionEdit() {
        if(actionEdit == null)
            actionEdit = new ActionEdit();
        return actionEdit;
    }

    /**
     * This method initializes jPanelMeasurement	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelMeasurement() {
        if (jPanelMeasurement == null) {
            GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
            gridBagConstraints110.gridwidth = 0;
            gridBagConstraints110.fill = GridBagConstraints.BOTH;
            gridBagConstraints110.insets = new Insets(10, 10, 10, 10);
            jPanelMeasurement = new JPanel();
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridy = -1;
            gridBagConstraints.insets = new Insets(10, 2, 10, 10);
            gridBagConstraints.gridx = -1;
            GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
            gridBagConstraints25.gridx = -1;
            gridBagConstraints25.weighty = 1.0D;
            gridBagConstraints25.weightx = 1.0D;
            gridBagConstraints25.fill = GridBagConstraints.BOTH;
            gridBagConstraints25.gridy = -1;
            gridBagConstraints25.gridheight = 0;
            gridBagConstraints25.insets = new Insets(10, 10, 10, 2);
            jPanelMeasurement.setLayout(new GridBagLayout());
            jPanelMeasurement.add(getXYMeasurementValuePanel(), gridBagConstraints25);
            jPanelMeasurement.add(getJPanelMeasurementExtendedSetting(), gridBagConstraints110);
            jPanelMeasurement.add(getDoubleDeviceGrraberPanel(), gridBagConstraints);
        }
        return jPanelMeasurement;
    }

    /**
     * This method initializes jPanelGraph	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelGraph() {
        if (jPanelGraph == null) {
            GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
            gridBagConstraints30.gridwidth = 0;
            gridBagConstraints30.anchor = GridBagConstraints.WEST;
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            gridBagConstraints20.gridwidth = 0;
            gridBagConstraints20.anchor = GridBagConstraints.WEST;
            GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
            gridBagConstraints19.weightx = 1.0D;
            gridBagConstraints19.gridwidth = 0;
            gridBagConstraints19.gridheight = 1;
            gridBagConstraints19.weighty = 0.0D;
            gridBagConstraints19.anchor = GridBagConstraints.NORTH;
            gridBagConstraints19.insets = new Insets(2, 10, 2, 10);
            gridBagConstraints19.fill = GridBagConstraints.HORIZONTAL;
            GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
            gridBagConstraints15.insets = new Insets(2, 10, 2, 10);
            gridBagConstraints15.gridwidth = 0;
            gridBagConstraints15.weightx = 1.0D;
            gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
            GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
            gridBagConstraints14.insets = new Insets(10, 10, 2, 2);
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints13.gridwidth = 0;
            gridBagConstraints13.insets = new Insets(10, 2, 2, 10);
            gridBagConstraints13.weightx = 1.0;
            jLabelGraphTitle = new JLabel();
            jLabelGraphTitle.setText("Nadpis grafu:");
            jPanelGraph = new JPanel();
            jPanelGraph.setLayout(new GridBagLayout());
            jPanelGraph.add(jLabelGraphTitle, gridBagConstraints14);
            jPanelGraph.add(getJTextFieldGraphTitle(), gridBagConstraints13);
            jPanelGraph.add(getJPanelXAxis(), gridBagConstraints15);
            jPanelGraph.add(getJPanelYAxis(), gridBagConstraints19);
            jPanelGraph.add(getJCheckBoxSortXValues(), gridBagConstraints20);
            jPanelGraph.add(getJPanelOrientation(), gridBagConstraints30);
    		CSH.setHelpIDString(jPanelGraph, "GraphSetting");
        }
        return jPanelGraph;
    }

    /**
     * This method initializes jPanelProtocol	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelProtocol() {
        if (jPanelProtocol == null) {
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.insets = new Insets(2, 2, 2, 10);
            gridBagConstraints8.anchor = GridBagConstraints.WEST;
            gridBagConstraints8.gridwidth = 0;
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.insets = new Insets(2, 5, 2, 2);
            gridBagConstraints13.anchor = GridBagConstraints.NORTHWEST;
            gridBagConstraints13.gridwidth = 0;
            gridBagConstraints13.weightx = 0.5;
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;            
            gridBagConstraints12.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints12.anchor = GridBagConstraints.NORTH;
            gridBagConstraints12.weightx = 1.0;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.insets = new Insets(2, 10, 2, 2);
            gridBagConstraints11.weighty = 1.0D;
            gridBagConstraints11.anchor = GridBagConstraints.NORTHEAST;
            gridBagConstraints11.fill = GridBagConstraints.NONE;
            jLabelNameOfMeasurementDevice = new JLabel();
            jLabelNameOfMeasurementDevice.setText("Měřící zařízení:");
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints10.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints10.gridwidth = 0;
            gridBagConstraints10.weightx = 1.0D;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.insets = new Insets(2, 10, 2, 2);
            gridBagConstraints7.anchor = GridBagConstraints.EAST;
            jLabeldateOfMeasurement = new JLabel();
            jLabeldateOfMeasurement.setText("Datum měření:");
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints6.insets = new Insets(2, 2, 2, 10);
            gridBagConstraints6.gridwidth = 0;
            gridBagConstraints6.weightx = 1.0;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.insets = new Insets(2, 10, 2, 2);
            gridBagConstraints5.anchor = GridBagConstraints.EAST;
            jLabelObjectOfMeasurement = new JLabel();
            jLabelObjectOfMeasurement.setText("Předmět měření:");
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.insets = new Insets(2, 10, 2, 10);
            gridBagConstraints4.gridwidth = 0;
            gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.weightx = 1.0D;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.insets = new Insets(2, 10, 2, 2);
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.insets = new Insets(5, 10, 2, 10);
            gridBagConstraints2.weightx = 1.0D;
            gridBagConstraints2.gridwidth = 0;
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.insets = new Insets(5, 2, 5, 10);
            gridBagConstraints1.gridwidth = 0;
            gridBagConstraints1.weightx = 1.0;
            jLabelTitle = new JLabel();
            jLabelTitle.setText("Nadpis protokolu:");
            jPanelProtocol = new JPanel();
            jPanelProtocol.setLayout(new GridBagLayout());
            jPanelProtocol.add(getOrganizationPanelLaboratory(), gridBagConstraints2);
            jPanelProtocol.add(jLabelTitle, gridBagConstraints3);
            jPanelProtocol.add(getJTextFieldTitle(), gridBagConstraints1);
            jPanelProtocol.add(getCustomerPanel(), gridBagConstraints4);
            jPanelProtocol.add(jLabelObjectOfMeasurement, gridBagConstraints5);
            jPanelProtocol.add(getJTextFieldObjectOfMeasurement(), gridBagConstraints6);
            jPanelProtocol.add(jLabeldateOfMeasurement, gridBagConstraints7);
            jPanelProtocol.add(getDateSelector(), gridBagConstraints8);
            jPanelProtocol.add(getEmployeePanel(), gridBagConstraints10);
            jPanelProtocol.add(jLabelNameOfMeasurementDevice, gridBagConstraints11);
            jPanelProtocol.add(getJTextFieldNameOfMeasurementDevice(), gridBagConstraints12);
            //jPanelProtocol.add(getJButtonChangeMeasurer(), gridBagConstraints13);
    		CSH.setHelpIDString(jPanelProtocol, "ProtocolSetting");

        }
        return jPanelProtocol;
    }

    /**
     * This method initializes organizationPanelLaboratory	
     * 	
     * @return unisave2006.gui.OrganizationPanel	
     */
    private OrganizationPanel getOrganizationPanelLaboratory() {
        if (organizationPanelLaboratory == null) {
            organizationPanelLaboratory = new OrganizationPanel();
            organizationPanelLaboratory.setTitle("Laboratoř");
        }
        return organizationPanelLaboratory;
    }

    /**
     * This method initializes jTextFieldTitle	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldTitle() {
        if (jTextFieldTitle == null) {
            jTextFieldTitle = new JTextField();
            jTextFieldTitle.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    measurement.getProtocolSetting().setMeasurementTitle(getJTextFieldTitle().getText());
                }
            });
        }
        return jTextFieldTitle;
    }

    /**
     * This method initializes customerPanel	
     * 	
     * @return unisave2006.gui.CustomerPanel	
     */
    //-- UPRAVA KODU !!!!
    private CustomerPanel getCustomerPanel() {
        if (customerPanel == null) {
            customerPanel = new CustomerPanel();
            customerPanel.setTitle("Zadavatel");
            //customerPanel.disableComponents();
        }
        return customerPanel;
    }
    
    private EmployeePanel getEmployeePanel() {
    	if(employeePanel == null) {
    		employeePanel = new EmployeePanel();
    		employeePanel.setTitle("Měřil");
    		//employeePanel.disableComponents();
    	}
    	return employeePanel;
    }

    /**
     * This method initializes jTextFieldObjectOfMeasurement	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldObjectOfMeasurement() {
        if (jTextFieldObjectOfMeasurement == null) {
            jTextFieldObjectOfMeasurement = new JTextField();
            jTextFieldObjectOfMeasurement.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    measurement.getProtocolSetting().setObjectOfMeasurement(getJTextFieldObjectOfMeasurement().getText());
                }
            });
        }
        return jTextFieldObjectOfMeasurement;
    }

    /**
     * This method initializes jTextFieldNameOfMeasurementDevice	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldNameOfMeasurementDevice() {
        if (jTextFieldNameOfMeasurementDevice == null) {
            jTextFieldNameOfMeasurementDevice = new JTextField();
            jTextFieldNameOfMeasurementDevice.setEditable(true);
            
        }
        return jTextFieldNameOfMeasurementDevice;
    }

    /**
     * This method initializes dateSelector	
     * 	
     * @return unisave2006.gui.DateSelector	
     */
    private DateSelector getDateSelector() {
        if (dateSelector == null) {
            dateSelector = new DateSelector();
            dateSelector.addObserver(new Observer(){
                public void update(Observable o, Object arg) {
                    measurement.getProtocolSetting().setDateOfMeasurement(dateSelector.getSelectedDate());
                }
            });
        }
        return dateSelector;
    }

	/**
	 * This method initializes jTextFieldGraphTitle	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldGraphTitle() {
		if (jTextFieldGraphTitle == null) {
			jTextFieldGraphTitle = new JTextField();
			jTextFieldGraphTitle.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					measurement.getGraphSetting().setGraphTitle(getJTextFieldGraphTitle().getText());
				}
			});
		}
		return jTextFieldGraphTitle;
	}

	/**
	 * This method initializes jPanelXAxis	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelXAxis() {
		if (jPanelXAxis == null) {
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints18.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints18.gridwidth = 0;
			gridBagConstraints18.weightx = 1.0;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.gridwidth = 0;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.insets = new Insets(2, 2, 2, 2);
			jLabelXAxisLabelManual = new JLabel();
			jLabelXAxisLabelManual.setText("Vlastní:");
			jPanelXAxis = new JPanel();
			jPanelXAxis.setLayout(new GridBagLayout());
			jPanelXAxis.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Popis osy X", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelXAxis.add(getJCheckBoxXAxisLabelAuto(), gridBagConstraints17);
			jPanelXAxis.add(jLabelXAxisLabelManual, gridBagConstraints16);
			jPanelXAxis.add(getJTextFieldXAxisManual(), gridBagConstraints18);
		}
		return jPanelXAxis;
	}

	/**
	 * This method initializes jCheckBoxXAxisLabelAuto	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxXAxisLabelAuto() {
		if (jCheckBoxXAxisLabelAuto == null) {
			jCheckBoxXAxisLabelAuto = new JCheckBox();
			jCheckBoxXAxisLabelAuto.setText("Automatický");
			jCheckBoxXAxisLabelAuto.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					measurement.getGraphSetting().setXAxisAutomaticalLabeling(getJCheckBoxXAxisLabelAuto().isSelected());
				}
			});
		}
		return jCheckBoxXAxisLabelAuto;
	}

	/**
	 * This method initializes jTextFieldXAxisManual	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldXAxisManual() {
		if (jTextFieldXAxisManual == null) {
			jTextFieldXAxisManual = new JTextField();
			jTextFieldXAxisManual.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					measurement.getGraphSetting().setXAxisManualLabel(getJTextFieldXAxisManual().getText());
				}
			});
		}
		return jTextFieldXAxisManual;
	}

	/**
	 * This method initializes jPanelYAxis	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelYAxis() {
		if (jPanelYAxis == null) {
			TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Popis osy X", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51));
			titledBorder.setTitle("Popis osy Y");
			GridBagConstraints gridBagConstraints181 = new GridBagConstraints();
			gridBagConstraints181.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints181.gridwidth = 0;
			gridBagConstraints181.weightx = 1.0;
			gridBagConstraints181.insets = new Insets(2, 2, 2, 2);
			GridBagConstraints gridBagConstraints161 = new GridBagConstraints();
			gridBagConstraints161.insets = new Insets(2, 2, 2, 2);
			jLabelYAxisLabelManual = new JLabel();
			jLabelYAxisLabelManual.setText("Vlastní:");
			GridBagConstraints gridBagConstraints171 = new GridBagConstraints();
			gridBagConstraints171.anchor = GridBagConstraints.WEST;
			gridBagConstraints171.gridwidth = 0;
			gridBagConstraints171.insets = new Insets(2, 2, 2, 2);
			jPanelYAxis = new JPanel();
			jPanelYAxis.setLayout(new GridBagLayout());
			jPanelYAxis.setBorder(titledBorder);
			jPanelYAxis.add(getJCheckBoxYAxisLabelAuto(), gridBagConstraints171);
			jPanelYAxis.add(jLabelYAxisLabelManual, gridBagConstraints161);
			jPanelYAxis.add(getJTextFieldYAxisManual(), gridBagConstraints181);
		}
		return jPanelYAxis;
	}

	/**
	 * This method initializes jCheckBoxYAxisLabelAuto	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxYAxisLabelAuto() {
		if (jCheckBoxYAxisLabelAuto == null) {
			jCheckBoxYAxisLabelAuto = new JCheckBox();
			jCheckBoxYAxisLabelAuto.setText("Automatický");
			jCheckBoxYAxisLabelAuto.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					measurement.getGraphSetting().setYAxisAutomaticalLabeling(getJCheckBoxYAxisLabelAuto().isSelected());
				}
			});
		}
		return jCheckBoxYAxisLabelAuto;
	}

	/**
	 * This method initializes jTextFieldYAxisManual	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldYAxisManual() {
		if (jTextFieldYAxisManual == null) {
			jTextFieldYAxisManual = new JTextField();
			jTextFieldYAxisManual.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					measurement.getGraphSetting().setYAxisManualLabel(getJTextFieldYAxisManual().getText());
				}
			});
		}
		return jTextFieldYAxisManual;
	}
    protected void updateActionCopy(){
    	if(getSelectedComponent() == getJPanelMeasurement() && getXYMeasurementValuePanel().getJTableValues().getSelectedRowCount() > 0){
    		getActionCopy().setEnabled(true);
    	}
    	else{
    		getActionCopy().setEnabled(false);
    	}
    }
    protected void updateActionCut(){
    	if(getSelectedComponent() == getJPanelMeasurement() && getXYMeasurementValuePanel().getJTableValues().getSelectedRowCount() > 0){
    		getActionCut().setEnabled(true);
    	}
    	else{
    		getActionCut().setEnabled(false);
    	}
    }
    protected void updateActionDeleteLast(){
    	if(getSelectedComponent() == getJPanelMeasurement() && measurement.getElements().size() > 0){
    		getActionDeleteLast().setEnabled(true);
    	}
    	else{
    		getActionDeleteLast().setEnabled(false);
    	}
    }
    protected void updateActionDeleteSelected(){
    	if(getSelectedComponent() == getJPanelMeasurement() && getXYMeasurementValuePanel().getJTableValues().getSelectedRowCount() > 0){
    		getActionDeleteSelected().setEnabled(true);
    	}
    	else{
    		getActionDeleteSelected().setEnabled(false);
    	}
    }
    protected void updateActionEdit(){
    	if(getSelectedComponent() == getJPanelMeasurement() && getXYMeasurementValuePanel().getJTableValues().getSelectedRowCount() == 1){
    		getActionEdit().setEnabled(true);
    	}
    	else{
    		getActionEdit().setEnabled(false);
    	}
    }
    protected void updateActionUnitConversion(){
    	if(getSelectedComponent() == getJPanelMeasurement() && measurement.getElements().size() > 0){
    		getActionUnitConversion().setEnabled(true);
    	}
    	else{
    		getActionUnitConversion().setEnabled(false);
    	}
    }

	public void detache() {
		getDoubleDeviceGrraberPanel().detache();
		
	}

	/**
	 * This method initializes jCheckBoxSortXValues	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxSortXValues() {
		if (jCheckBoxSortXValues == null) {
			jCheckBoxSortXValues = new JCheckBox();
			jCheckBoxSortXValues.setText("Třídit měření hodnoty v grafu podle prvního sloupce");
			jCheckBoxSortXValues.setToolTipText("Naměřené hodnoty budou v grafu vždy setřízeny podle hodnoty 1.");
			jCheckBoxSortXValues.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					measurement.getGraphSetting().setSortValues(jCheckBoxSortXValues.isSelected());
					GlobalSetting.getUserSetting().getLastUsedGraphSetting().setSortValues(jCheckBoxSortXValues.isSelected());
				}
			});
		}
		return jCheckBoxSortXValues;
	}

	/**
	 * This method initializes jPanelOrientation	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelOrientation() {
		if (jPanelOrientation == null) {
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.insets = new Insets(5, 5, 5, 5);
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.insets = new Insets(5, 5, 5, 5);
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridwidth = 0;
			jLabelHorizontal = new JLabel();
			jLabelHorizontal.setText("");
			jLabelHorizontal.setIcon(new ImageIcon(getClass().getResource("/resource/pictures/graf_orientation_icon_horizontal.gif")));
			jLabelVertical = new JLabel();
			jLabelVertical.setText("");
			jLabelVertical.setIcon(new ImageIcon(getClass().getResource("/resource/pictures/graf_orientation_icon_vertical.gif")));
			jPanelOrientation = new JPanel();
			jPanelOrientation.setLayout(new GridBagLayout());
			jPanelOrientation.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Orientace grafu", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelOrientation.add(getJRadioButtonVertical(), new GridBagConstraints());
			jPanelOrientation.add(getJRadioButtonHorizontal(), gridBagConstraints21);
			jPanelOrientation.add(jLabelVertical, gridBagConstraints22);
			jPanelOrientation.add(jLabelHorizontal, gridBagConstraints23);
		}
		return jPanelOrientation;
	}

	/**
	 * This method initializes jRadioButtonVertical	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonVertical() {
		if (jRadioButtonVertical == null) {
			jRadioButtonVertical = new JRadioButton();
			jRadioButtonVertical.setText("Na šířku");
			jRadioButtonVertical.setMnemonic(KeyEvent.VK_UNDEFINED);
			jRadioButtonVertical.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(getJRadioButtonVertical().isSelected()){
						measurement.getGraphSetting().setGraphOrientation(PlotOrientation.VERTICAL);
						GlobalSetting.getUserSetting().getLastUsedGraphSetting().setGraphOrientation(PlotOrientation.VERTICAL);
					}
				}
			});
		}
		return jRadioButtonVertical;
	}

	/**
	 * This method initializes jRadioButtonHorizontal	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonHorizontal() {
		if (jRadioButtonHorizontal == null) {
			jRadioButtonHorizontal = new JRadioButton();
			jRadioButtonHorizontal.setText("Na výšku");
			jRadioButtonHorizontal.setMnemonic(KeyEvent.VK_UNDEFINED);
			jRadioButtonHorizontal.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(getJRadioButtonHorizontal().isSelected()){
						measurement.getGraphSetting().setGraphOrientation(PlotOrientation.HORIZONTAL);
						GlobalSetting.getUserSetting().getLastUsedGraphSetting().setGraphOrientation(PlotOrientation.HORIZONTAL);
					}
				}
			});
		}
		return jRadioButtonHorizontal;
	}

	/**
	 * This method initializes jPanelMeasurementExtendedSetting	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelMeasurementExtendedSetting() {
		if (jPanelMeasurementExtendedSetting == null) {
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.gridwidth = 0;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.gridwidth = 0;
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.gridwidth = 0;
			gridBagConstraints26.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints26.anchor = GridBagConstraints.WEST;
			jLabelMarker1 = new JLabel();
			jLabelMarker1.setText("Zjistit hodnotu pro:");
			jPanelMeasurementExtendedSetting = new JPanel();
			jPanelMeasurementExtendedSetting.setLayout(new GridBagLayout());
			jPanelMeasurementExtendedSetting.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Rozšířené nastavení", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelMeasurementExtendedSetting.add(getJButtonIgnoreGrowValues(), gridBagConstraints35);
			jPanelMeasurementExtendedSetting.add(jLabelMarker1, gridBagConstraints26);
			jPanelMeasurementExtendedSetting.add(getJPanelXMarker(), gridBagConstraints32);
			jPanelMeasurementExtendedSetting.add(getJPanelYMarker(), new GridBagConstraints());
			CSH.setHelpIDString(jPanelMeasurementExtendedSetting, "ExtendedSetting");
		}
		return jPanelMeasurementExtendedSetting;
	}

	/**
	 * This method initializes jRadioButtonValueX	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonValueX() {
		if (jRadioButtonValueX == null) {
			jRadioButtonValueX = new JRadioButton();
			jRadioButtonValueX.setText("Hodnota X");
			jRadioButtonValueX.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					 measurement.setXMarkerFixed(bgMarkerFix.isSelected(getJRadioButtonValueX().getModel()));
					 GlobalSetting.getUserSetting().setLastUsedXMarkerFixed(bgMarkerFix.isSelected(getJRadioButtonValueX().getModel()));

				}
			});
		}
		return jRadioButtonValueX;
	}

	/**
	 * This method initializes jRadioButtonValueY	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonValueY() {
		if (jRadioButtonValueY == null) {
			jRadioButtonValueY = new JRadioButton();
			jRadioButtonValueY.setText("Hodnota Y");
			jRadioButtonValueY.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					 measurement.setYMarkerFixed(bgMarkerFix.isSelected(getJRadioButtonValueY().getModel()));
					 GlobalSetting.getUserSetting().setLastUsedYMarkerFixed(bgMarkerFix.isSelected(getJRadioButtonValueY().getModel()));
				}
			});
		}
		return jRadioButtonValueY;
	}

	/**
	 * This method initializes valuePanelX	
	 * 	
	 * @return unisave2006.gui.value.ValuePanel	
	 */
	private ValuePanel getValuePanelX() {
		if (valuePanelX == null) {
			valuePanelX = new ValuePanel();
			valuePanelX.setNoIndex();
			valuePanelX.setEnableInserting(true);
			valuePanelX.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					System.out.println("focusLost()"); // TODO Auto-generated Event stub focusLost()
				}
			});
		}
		return valuePanelX;
	}

	/**
	 * This method initializes valuePanelValueY	
	 * 	
	 * @return unisave2006.gui.value.ValuePanel	
	 */
	private ValuePanel getValuePanelValueY() {
		if (valuePanelValueY == null) {
			valuePanelValueY = new ValuePanel();
			valuePanelValueY.setNoIndex();
		}
		return valuePanelValueY;
	}

	/**
	 * This method initializes jButtonSetXMarker	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonSetXMarker() {
		if (jButtonSetXMarker == null) {
			jButtonSetXMarker = new JButton();
			jButtonSetXMarker.setText("Nastavit");
			jButtonSetXMarker.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						Value v = valuePanelX.getValue();
						if(!measurement.setXMarker(v)){
					        JOptionPane.showMessageDialog(XYMeasurementPanel.this, 
					        		"Hodnotu nelze převést na nejpoužívanější jednotku pro " + measurement.getXValueName() + ".",
					                "Unisave 2006 - Chyba", JOptionPane.ERROR_MESSAGE);
						}
						else {
							GlobalSetting.getUserSetting().setLastUsedXMarker(v);
						}
					} catch (MeasurementEntityConstructionException e1) {
				        JOptionPane.showMessageDialog(XYMeasurementPanel.this, 
				        		new ErrorMessagePanel("Nepodařilo se vytvořit hodnotu.", e1.getMessage()),
				                "Unisave 2006 - Chyba", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return jButtonSetXMarker;
	}

	/**
	 * This method initializes jButtonSetYMarker	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonSetYMarker() {
		if (jButtonSetYMarker == null) {
			jButtonSetYMarker = new JButton();
			jButtonSetYMarker.setText("Nastavit");
			jButtonSetYMarker.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						Value v = valuePanelValueY.getValue();
						if(!measurement.setYMarker(v)){
					        JOptionPane.showMessageDialog(XYMeasurementPanel.this, 
					        		"Hodnotu nelze převést na nejpoužívanější jednotku pro " + measurement.getYValueName() + ".",
					                "Unisave 2006 - Chyba", JOptionPane.ERROR_MESSAGE);
						}
						else {
							GlobalSetting.getUserSetting().setLastUsedYMarker(v);
						}
					} catch (MeasurementEntityConstructionException e1) {
				        JOptionPane.showMessageDialog(XYMeasurementPanel.this, 
				        		new ErrorMessagePanel("Nepodařilo se vytvořit hodnotu.", e1.getMessage()),
				                "Unisave 2006 - Chyba", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return jButtonSetYMarker;
	}

	/**
	 * This method initializes jPanelXMarker	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelXMarker() {
		if (jPanelXMarker == null) {
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.gridwidth = 0;
			gridBagConstraints28.gridy = -1;
			gridBagConstraints28.gridx = -1;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.gridwidth = 1;
			gridBagConstraints29.gridy = -1;
			gridBagConstraints29.gridx = -1;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = -1;
			gridBagConstraints31.gridwidth = 0;
			gridBagConstraints31.gridy = -1;
			jPanelXMarker = new JPanel();
			jPanelXMarker.setLayout(new GridBagLayout());
			jPanelXMarker.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			jPanelXMarker.add(getJRadioButtonValueX(), gridBagConstraints29);
			jPanelXMarker.add(getJButtonSetXMarker(), gridBagConstraints31);
			jPanelXMarker.add(getValuePanelX(), gridBagConstraints28);
		}
		return jPanelXMarker;
	}

	/**
	 * This method initializes jPanelYMarker	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelYMarker() {
		if (jPanelYMarker == null) {
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.gridx = -1;
			gridBagConstraints34.gridwidth = 0;
			gridBagConstraints34.gridy = -1;
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.gridx = -1;
			gridBagConstraints33.gridwidth = 0;
			gridBagConstraints33.gridy = -1;
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.gridwidth = 1;
			gridBagConstraints27.gridy = -1;
			gridBagConstraints27.gridx = -1;
			jPanelYMarker = new JPanel();
			jPanelYMarker.setLayout(new GridBagLayout());
			jPanelYMarker.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			jPanelYMarker.add(getJRadioButtonValueY(), gridBagConstraints27);
			jPanelYMarker.add(getJButtonSetYMarker(), gridBagConstraints33);
			jPanelYMarker.add(getValuePanelValueY(), gridBagConstraints34);
		}
		return jPanelYMarker;
	}

	/**
	 * This method initializes jButtonIgnoreGrowValues	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonIgnoreGrowValues() {
		if (jButtonIgnoreGrowValues == null) {
			jButtonIgnoreGrowValues = new JButton();
			jButtonIgnoreGrowValues.setText("Odebrat vzrůstající hodnoty pro sloupec Hodnota X");
			jButtonIgnoreGrowValues.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMeasurement().deleteGrowValuesInX();
				}
			});
		}
		return jButtonIgnoreGrowValues;
	}
	
	private JButton getJButtonChangeMeasurer() {
		if(jButtonChangeMeasurer == null) {
			jButtonChangeMeasurer = new JButton();
			jButtonChangeMeasurer.setText("Změnit zařízení");
			jButtonChangeMeasurer.setPreferredSize(new Dimension(120, 23));
			jButtonChangeMeasurer.addActionListener(
					new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							changedMeasurer();
						}
					}
			);
		}
		return jButtonChangeMeasurer;
	}
	
	public void changedMeasurer() {
		measurerList = new MeasurerListDialog();
		measurerList.addLoadMeasurerListener(this);
		measurerList.pack();
		measurerList.setLocationRelativeTo(this);
		measurerList.setVisible(true);
	}
	
	public void loadedMeasurerChanged() {
		if(measurerList.getLoadedMeasurer() != null) {
			Measurer m = mDAO.find(measurerList.getLoadedMeasurer());
			setMeasurer(m);
			measurement.getProtocolSetting().setMeasurer(m);
		}
	}
	
	public void deleteMeasurer() {
		if(measurerList.getLoadedMeasurer() != null) {
			Measurer m = mDAO.find(measurerList.getLoadedMeasurer());
			m.setIsDeleted(true);
			mDAO.saveOrUpdate(m);
		}
	}
	
	public void updateMeasurer() {
		getJTextFieldNameOfMeasurementDevice().setText(measurer.getIdentification() + ", "
				 + measurer.getTitle() + ", "
				 + measurer.getType() + ", "
				 + measurer.getcalibrationDate());
	}
	
  }
