/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.jfree.report.JFreeReport;
import org.jfree.report.JFreeReportBoot;

import org.jfree.report.modules.output.support.itext.BaseFontFactory;
import org.jfree.report.modules.parser.base.ReportGenerator;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;
import org.jfree.xml.ElementDefinitionException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import unisave2006.GlobalSetting;
import unisave2006.XMLSupport;
import unisave2006.data.value.ConversionException;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.MeasurementEntryFactory;
import unisave2006.data.value.Text;
import unisave2006.data.value.Value;
import unisave2006.data.value.XYValue;
import unisave2006.units.Unit;

/**
 * @author David Je�ek
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_2HhgsBGCEduLIuzFGBOiyA"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class XYMeasurement extends Measurement {
    
	protected String xValueName = "Hodnota X";
    protected String yValueName = "Hodnota Y";
    protected Unit yAutoconvertUnit = Unit.getEmptyUnit(); 
    protected boolean ignoreGroweXValue = false;
    protected Value xMarker = null;
    protected Value xMarkerTmp = new Value();
    protected boolean xMarkerFixed = true;
    protected Value yMarker = null;
    protected Value yMarkerTmp = new Value();
    protected boolean yMarkerFixed = false;
    
    protected MostRecentUnitXYCalculator mostRecentUnitXCalc = null;
    protected MostRecentUnitXYCalculator mostRecentUnitYCalc = null;
    
    protected XYTableModel tableModel = null;
    
    protected Vector<XYMeasurementSettingListener> xyMeasurementSettingListeners = new Vector<XYMeasurementSettingListener>();
    
    protected GraphSetting graphSetting = new GraphSetting();
    
    public void addXYMeasurementSettingListener(XYMeasurementSettingListener l){
        xyMeasurementSettingListeners.add(l);
    }
    public void removeXYMeasurementSettingListener(XYMeasurementSettingListener l){
        xyMeasurementSettingListeners.remove(l);
    }

    public int getType(){
        return MeasurementFactory.MEASUREMENT_XY;
    }

    public XYMeasurement() {
        super();
        graphSetting.addGraphSettingListener(new GraphSettingListener(){

			public void graphTitleChanged() {
				setModified(true);
			}

			public void xAxisAutomaticLabelingChanged() {
				setModified(true);
			}

			public void xAxisManualLabelChanged() {
				setModified(true);
			}

			public void yAxisAutomaticLabelingChanged() {
				setModified(true);
			}

			public void yAxisManualLabelChanged() {
				setModified(true);
			}

			public void GraphOrientationChanged() {
				
				setModified(true);
			}

			public void SortValuesChanged() {
				setModified(true);
			}
        });
        graphSetting.updateFrom(GlobalSetting.getUserSetting().getLastUsedGraphSetting());
        xValueName = GlobalSetting.getUserSetting().getLastUsedXName();
        yValueName = GlobalSetting.getUserSetting().getLastUsedYName();
        yAutoconvertUnit = GlobalSetting.getUserSetting().getLastUsedYUnit();
        
        xMarker = new Value();
        xMarker.copyFrom(GlobalSetting.getUserSetting().getLastUsedXMarker());
        yMarker = new Value();
        yMarker.copyFrom(GlobalSetting.getUserSetting().getLastUsedYMarker());
        xMarkerFixed = GlobalSetting.getUserSetting().isLastUsedXMarkerFixed();
        yMarkerFixed = GlobalSetting.getUserSetting().isLastUsedYMarkerFixed();

        
        allowedEntityTypes.clear();
        allowedEntityTypes.add(MeasurementEntryFactory.XY_VALUE);
        allowedEntityTypes.add(MeasurementEntryFactory.TEXT);
        mostRecentUnitXCalc = new MostRecentUnitXYCalculator(MostRecentUnitXYCalculator.CALCULATE_X);
        addMeasurementEntryListener(mostRecentUnitXCalc);
        mostRecentUnitXCalc.addObserver(new Observer(){
			public void update(Observable o, Object arg) {
				fireMostRecentUnitChanged(mostRecentUnitXCalc.getMostRecentUnit());
			}
        });
        mostRecentUnitXCalc.setMeasurement(this);
        mostRecentUnitYCalc = new MostRecentUnitXYCalculator(MostRecentUnitXYCalculator.CALCULATE_Y);
        mostRecentUnitYCalc.addObserver(new Observer(){
			public void update(Observable o, Object arg) {
				fireMostRecentYUnitChanged(mostRecentUnitYCalc.getMostRecentUnit());
			}
        });
        addMeasurementEntryListener(mostRecentUnitYCalc);
        mostRecentUnitYCalc.setMeasurement(this);
        addMeasurementEntryListener(new MeasurementEntryListener(){

			public void entriesAdded(Collection<MeasurementEntry> v) {
				findMarkerValue();
			}

			public void entriesChanged() {
				findMarkerValue();
			}

			public void entriesDeleted() {
				findMarkerValue();
			}

			public void entryAdded(MeasurementEntry e) {
				findMarkerValue();
			}

			public void entryChanged(int index, MeasurementEntry e) {
				findMarkerValue();
			}

			public void entryDeleted(int index, MeasurementEntry e) {
				findMarkerValue();
			}
        	
        });
        addGraphSettingListener(new GraphSettingListener(){
			public void GraphOrientationChanged() {}
			public void SortValuesChanged() {
				findMarkerValue();
			}
			public void graphTitleChanged() {}
			public void xAxisAutomaticLabelingChanged() {}
			public void xAxisManualLabelChanged() {}
			public void yAxisAutomaticLabelingChanged() {}
			public void yAxisManualLabelChanged() {}
        });
        addXYMeasurementSettingListener(new XYMeasurementSettingListener(){
			public void ignoreGroweXValueChanged() {}

			public void mostRecentYUnitChanged(Unit u) {}
			public void xMarkerChanged() {
				if(xMarkerFixed)
					findYMarkerValue();
			}
			public void xMarkerFixedChanged() {
				findMarkerValue();
			}
			public void xValueNameChanged() {}
			public void yAutoconverUnitChanged() {}
			public void yMarkerChanged() {
				if(yMarkerFixed)
					findXMarkerValue();
			}
			public void yMarkerFixedChanged() {
				findMarkerValue();
			}
			public void yValueNameChanged() {}
        });
    }
    
    protected void fireMostRecentUnitChanged(Unit mostRecentUnit) {
        for(MeasurementSettingListener l:measurementSettingListeners){
            l.mostRecentUnitChanged(mostRecentUnit);
        }
	}
    protected void fireMostRecentYUnitChanged(Unit mostRecentUnit) {
        for(XYMeasurementSettingListener l:xyMeasurementSettingListeners){
            l.mostRecentYUnitChanged(mostRecentUnit);
        }
	}


    @Override
    public AbstractTableModel getTableModel() {
        if(tableModel == null){
            tableModel = new XYTableModel();
            addMeasurementEntryListener(tableModel);
            addXYMeasurementSettingListener(tableModel);
        }
        
        return tableModel;
    }
    
    class XYTableModel extends AbstractTableModel implements MeasurementEntryListener, XYMeasurementSettingListener{

        
        private static final long serialVersionUID = 2536287770543066658L;

        public int getRowCount() {
            return elements.size();
        }

        public int getColumnCount() {
            return 3;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Object ret = "";
            MeasurementEntry e = elements.get(rowIndex);
            switch(e.getClassType()){
            case MeasurementEntryFactory.XY_VALUE:
                switch(columnIndex){
                case 0:
                    ret = ((XYValue)e).getIndexXY();
                    break;
                case 1:
                    ret = ((XYValue)e).getXValue().getValueAsString();
                    break;
                case 2:
                    ret = ((XYValue)e).getYValue().getValueAsString();
                    break;
                }
                break;
            case MeasurementEntryFactory.TEXT:
                switch(columnIndex){
                case 0:
                    ret = ((Text)e).getTypeText();
                    break;
                case 1:
                    ret = ((Text)e).getTextTxt();
                    break;
                case 2:
                    ret = "";
                    break;
                }
                break;
            case MeasurementEntryFactory.BLOCK_SEPARATOR:
            case MeasurementEntryFactory.NAMED_VALUE:
            case MeasurementEntryFactory.STATISTIC:
            case MeasurementEntryFactory.VALUE:
                switch(columnIndex){
                case 0:
                    ret = "";
                    break;
                case 1:
                    ret = "Nepodporovan� hodnota";
                    break;
                case 2:
                    ret = "";
                    break;
                }
            }
            return ret;
        }

        @Override
        public String getColumnName(int column) {
            switch(column){
            case 0:
                return "Index";
            case 1:
                return xValueName;
            case 2:
                return yValueName;
            default:
                return "";
            }
        }

        public void entriesAdded(Collection<MeasurementEntry> v) {
            fireTableRowsInserted(elements.size(), elements.size()+v.size());   
        }

        public void entriesChanged() {
            fireTableDataChanged();
        }

        public void entriesDeleted() {
            fireTableDataChanged();
        }

        public void entryAdded(MeasurementEntry e) {
            fireTableRowsInserted(elements.size()-1, elements.size()-1);
        }

        public void entryChanged(int index, MeasurementEntry e) {
            fireTableRowsUpdated(index, index);
            
        }

        public void entryDeleted(int index, MeasurementEntry e) {
            fireTableRowsDeleted(index, index);
            
        }

        public void xValueNameChanged() {
            fireTableStructureChanged();
        }

        public void yAutoconverUnitChanged() {}

        public void yValueNameChanged() {
            fireTableStructureChanged();
        }

		public void mostRecentYUnitChanged(Unit u) {
			
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
    }

    public String getXValueName() {
        return xValueName;
    }

    public void setXValueName(String valueName) {
        if(valueName == null)
            valueName = "";
        if(valueName.equals(xValueName))
            return;
        xValueName = valueName;
        setModified(true);
        fireXValueNameChanged();
    }

    protected void fireXValueNameChanged() {
        for(XYMeasurementSettingListener l:xyMeasurementSettingListeners){
            l.xValueNameChanged();
        }
        
    }

    public String getYValueName() {
        return yValueName;
    }

    public void setYValueName(String valueName) {
        if(valueName == null)
            valueName = "";
        if(valueName.equals(yValueName))
            return;
        yValueName = valueName;
        setModified(true);
        fireYValueNameChanged();
        getTableModel().fireTableStructureChanged();
    }

    public boolean getIgnoreGroweXValue() {
		return ignoreGroweXValue;
	}
	public void setIgnoreGroweXValue(boolean ignoreGroweXValue) {
		if(this.ignoreGroweXValue == ignoreGroweXValue)
			return;
		this.ignoreGroweXValue = ignoreGroweXValue;
		setModified(true);
		fireIgnoreGroweXValueChanged();
	}
	protected void fireIgnoreGroweXValueChanged() {
        for(XYMeasurementSettingListener l: xyMeasurementSettingListeners){
            l.ignoreGroweXValueChanged();
        }
	}
	public Value getXMarker() {
		return xMarker;
	}
	public boolean setXMarker(Value marker) {
		if(marker == null){
			if(isChartCreated()){
				getchart().getXYPlot().clearDomainMarkers();
			}
			xMarker = null;
			xMark = null;
		}
		if(marker != null){
			if(!marker.getUnit().getDescription().isConvertibleTo(getXMostRecentUnit().getDescription())){
				return false;
			}
			if(xMarker == null){
				xMarker = new Value();
			}
			if(xMarker.equals(marker) && xMark != null)
				return true;
		}
		if(marker != null){
			xMarker.copyFrom(marker);
		}
		updateXMark();
		setModified(true);
		fireXMarkerChanged();
		return true;
	}
	private void updateXMark() {
		double value = 0;
		if(getXMarker() == null){
			return;
		}
		try {
			value = getXMarker().getValueInUnit(getXMostRecentUnit());
		} catch (ConversionException e1) {
			return;
		}
		if (xMark == null) {
			xMark = new ValueMarker(value);
			xMark.setPaint(Color.RED);
			xMark.setStroke(new BasicStroke(2, 0, 0, 1.0f, new float[] { 10f,
					6f }, 0f));
			xMark.setLabelOffsetType(LengthAdjustmentType.EXPAND);
			xMark.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
			xMark.setLabelTextAnchor(TextAnchor.TOP_LEFT);
			xMark.setLabelPaint(Color.RED);
			if (isChartCreated()) {
				getchart().getXYPlot().addDomainMarker(xMark);
			}
		}
		xMark.setValue(value);
		xMark.setLabel(getXMarker().getValueAsString());
	}
	protected void fireXMarkerChanged() {
        for(XYMeasurementSettingListener l: xyMeasurementSettingListeners){
            l.xMarkerChanged();
        }
	}
	public boolean getXMarkerFixed() {
		return xMarkerFixed;
	}
	public void setXMarkerFixed(boolean markerFixed) {
		if(xMarkerFixed == markerFixed)
			return;
		xMarkerFixed = markerFixed;
		setModified(true);
		fireXMarkerFixedChanged();
	}
	private void fireXMarkerFixedChanged() {
        for(XYMeasurementSettingListener l: xyMeasurementSettingListeners){
            l.xMarkerFixedChanged();
        }
	}
	public Value getYMarker() {
		return yMarker;
	}
	public boolean setYMarker(Value marker) {
		if(marker == null){
			if(isChartCreated()){
				getchart().getXYPlot().clearRangeMarkers();
			}
			yMarker = null;
			yMark = null;
		}
		if(marker != null){
			if(!marker.getUnit().getDescription().isConvertibleTo(getYMostRecentUnit().getDescription())){
				return false;
			}
			if(yMarker == null){
				yMarker = new Value(); 
			}
			if(yMarker.equals(marker) && yMark != null)
				return true;
		}
		if(marker != null){
			yMarker.copyFrom(marker);
		}
		updateYMark();
		setModified(true);
		fireYMarkerChanged();
		return true;
	}
	private void updateYMark() {
		double value = 0;
		if(getYMarker() == null){
			return;
		}
		try {
			value = getYMarker().getValueInUnit(getYMostRecentUnit());
		} catch (ConversionException e1) {
			return;
		}
		if (yMark == null) {
			yMark = new ValueMarker(value);
			yMark.setPaint(Color.RED);
			yMark.setStroke(new BasicStroke(2, 0, 0, 1.0f, new float[] { 10f,
					6f }, 0f));
			yMark.setLabelOffsetType(LengthAdjustmentType.EXPAND);
			yMark.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
			yMark.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
			yMark.setLabelPaint(Color.RED);
			if (isChartCreated()) {
				getchart().getXYPlot().addRangeMarker(yMark);
				yMark.addChangeListener(getchart().getXYPlot());
			}
		}
		yMark.setValue(value);
		yMark.setLabel(getYMarker().getValueAsString());
	}
	private void fireYMarkerChanged() {
        for(XYMeasurementSettingListener l: xyMeasurementSettingListeners){
            l.yMarkerChanged();
        }
	}
	public boolean getYMarkerFixed() {
		return yMarkerFixed;
	}
	public void setYMarkerFixed(boolean markerFixed) {
		if(yMarkerFixed == markerFixed)
			return;
		yMarkerFixed = markerFixed;
		setModified(true);
		fireYMarkerFixedChanged();
	}
	private void fireYMarkerFixedChanged() {
        for(XYMeasurementSettingListener l: xyMeasurementSettingListeners){
            l.yMarkerFixedChanged();
        }
	}
	protected void fireYValueNameChanged() {
        for(XYMeasurementSettingListener l: xyMeasurementSettingListeners){
            l.yValueNameChanged();
        }
        
    }
    public Unit getYAutoconvertUnit() {
        return yAutoconvertUnit;
    }
    public void setYAutoconvertUnit(Unit autoconvertUnit) {
        yAutoconvertUnit = autoconvertUnit;
        setModified(true);
        fireYAutoconvertUnitChanged();
    }
    protected void fireYAutoconvertUnitChanged() {
        for(XYMeasurementSettingListener l: xyMeasurementSettingListeners){
            l.yAutoconverUnitChanged();
        }
        
    }
    @Override
    public Dataset getDataset() {
        if(dataset == null){
            XYMeasurementDataset d = new XYMeasurementDataset();
            this.addMeasurementEntryListener(d);
            dataset = d;
        }
        return dataset;
    }
    class XYMeasurementDataset implements XYDataset, MeasurementEntryListener{

        protected Vector<DatasetChangeListener> datasetChangeListener = new Vector<DatasetChangeListener>();
        private DatasetGroup group = null;
        private Vector<XYValue> data = new Vector<XYValue>(100);
        protected XYValueXComparator xComparator = new XYValueXComparator();
        
        public XYMeasurementDataset() {
            reinsertData();
            getGraphSetting().addGraphSettingListener(new GraphSettingListener(){
				public void GraphOrientationChanged() {}
				public void graphTitleChanged() {}
				public void xAxisAutomaticLabelingChanged() {}
				public void xAxisManualLabelChanged() {}
				public void yAxisAutomaticLabelingChanged() {}
				public void yAxisManualLabelChanged() {}
				public void SortValuesChanged() {
					reinsertData();
					fireDatasetChanged();
				}
            });
        }

        protected void reinsertData() {
            data.removeAllElements();
            add(elements);
        }

        protected void add(Collection<MeasurementEntry> c) {
            for(MeasurementEntry e :c){
                if(e.getClassType() == MeasurementEntryFactory.XY_VALUE)
                    data.add((XYValue)e);
            }
            sort();
        }

        protected void sort() {
			if (getGraphSetting().getSortValues()) {
				xComparator.setMostRecentUnit(mostRecentUnitXCalc
						.getMostRecentUnit());
				Collections.sort(data, xComparator);
			}
		}

        public DomainOrder getDomainOrder() {
            return DomainOrder.ASCENDING;
        }

        public int getItemCount(int series) {
            if(series == 0)
                
                return data.size();
            return 0;
        }

        public Number getX(int series, int item) {
            if(series == 0){
                MeasurementEntry e = data.elementAt(item);
                if(e.getClassType() == MeasurementEntryFactory.XY_VALUE)
                    try {
                        return ((XYValue)e).getXValue().getValueAsNumberInUnit(mostRecentUnitXCalc.getMostRecentUnit());
                    } catch (ConversionException e1) {}
            }
            return new Integer(0);
        }

        public double getXValue(int series, int item) {
            if(series == 0){
                MeasurementEntry e = data.elementAt(item);
                if(e.getClassType() == MeasurementEntryFactory.XY_VALUE)
                    try {
                        return ((XYValue)e).getXValue().getValueInUnit(mostRecentUnitXCalc.getMostRecentUnit());
                    } catch (ConversionException e1) {}
            }
            return 0;
        }

        public Number getY(int series, int item) {
            if(series == 0){
                MeasurementEntry e = data.elementAt(item);
                if(e.getClassType() == MeasurementEntryFactory.XY_VALUE)
                    try {
                        return ((XYValue)e).getYValue().getValueAsNumberInUnit(mostRecentUnitYCalc.getMostRecentUnit());
                    } catch (ConversionException e1) {}
            }
            return null;
        }

        public double getYValue(int series, int item) {
            if(series == 0){
                MeasurementEntry e = data.elementAt(item);
                if(e.getClassType() == MeasurementEntryFactory.XY_VALUE)
                    try {
                        return ((XYValue)e).getYValue().getValueInUnit(mostRecentUnitYCalc.getMostRecentUnit());
                    } catch (ConversionException e1) {}
            }
            return 0;
        }

        public int getSeriesCount() {
            return 1;
        }

        public Comparable getSeriesKey(int series) {
            return new Integer(series);
        }

        public int indexOf(Comparable seriesKey) {
            return 0;
        }

        public void addChangeListener(DatasetChangeListener l) {
            datasetChangeListener.add(l);
            
        }

        public DatasetGroup getGroup() {
            return group ;
        }

        public void removeChangeListener(DatasetChangeListener l) {
            datasetChangeListener.remove(l);
            
        }

        public void setGroup(DatasetGroup group) {
            this.group = group;
        }
        protected void fireDatasetChanged(){
            DatasetChangeEvent e = new DatasetChangeEvent(this, this);
            for (DatasetChangeListener l: datasetChangeListener) {
                l.datasetChanged(e);
            }
        }

        public void entriesAdded(Collection<MeasurementEntry> v) {
            add(v);
            fireDatasetChanged();
        }

        public void entriesChanged() {
            reinsertData();
            fireDatasetChanged();
        }

        public void entriesDeleted() {
            reinsertData();
            fireDatasetChanged();
        }

        public void entryAdded(MeasurementEntry e) {
            if(e.getClassType() == MeasurementEntryFactory.XY_VALUE){
                data.add((XYValue)e);
                sort();
                fireDatasetChanged();
            }
        }

        public void entryChanged(int index, MeasurementEntry e) {
            sort();
            fireDatasetChanged();
        }

        public void entryDeleted(int index, MeasurementEntry e) {
            if(e.getClassType() == MeasurementEntryFactory.XY_VALUE){
                data.remove(e);
            }
            fireDatasetChanged();
        }
    }

    protected JFreeChart chart = null;
    protected ValueMarker xMark = null;
    protected ValueMarker yMark = null;
    @Override
    public JFreeChart getchart() {
        if(chart == null){
            chart = ChartFactory.createXYLineChart("XY Series Demo", "X", "Y", (XYDataset)getDataset(), getGraphSetting().getGraphOrientation(), false, true, false);
            chart.setBackgroundPaint(Color.WHITE);
            XYPlot xyplot = chart.getXYPlot();
            XYLineAndShapeRenderer r = new XYLineAndShapeRenderer();
            Path2D.Double p = new Path2D.Double();
            p.append(new Line2D.Double(-3,0,3,0), false);
            p.append(new Line2D.Double(0,-3,0,3), false);
            r.setSeriesShape(0, p);
            r.setSeriesPaint(0, Color.BLACK);
            xyplot.setRenderer(r);
            if(xMark == null && xMarker != null)
            	updateXMark();
            if(xMark != null)
            	xyplot.addDomainMarker(xMark);
            if(yMark == null && yMarker != null)
            	updateYMark();
            if(yMark != null)
            	xyplot.addRangeMarker(yMark);
            ValueAxis valueaxis = xyplot.getDomainAxis();
            valueaxis.setAutoRange(true);
            if(valueaxis instanceof NumberAxis){
            	NumberAxis na = (NumberAxis)valueaxis;
            	na.setAutoRangeIncludesZero(false);
            }
            valueaxis.setAutoRange(true);

            ValueAxis rangeAxis = xyplot.getRangeAxis();
            rangeAxis.setAutoRange(true);
            if(rangeAxis instanceof NumberAxis){
            	NumberAxis na = (NumberAxis)rangeAxis;
            	na.setAutoRangeIncludesZero(false);
            	//na.setAutoRangeStickyZero(true);
            }
            rangeAxis.setAutoRange(true);
        	if(getGraphSetting().getXAxisAutomaticalLabeling()){
        		chart.getXYPlot().getDomainAxis().setLabel(getXValueName() + " " + getXMostRecentUnit().getShortcut());
        	}
        	else {
        		chart.getXYPlot().getDomainAxis().setLabel(getGraphSetting().getXAxisManualLabel());
        	}
        	if(getGraphSetting().getYAxisAutomaticalLabeling())
        		chart.getXYPlot().getRangeAxis().setLabel(getYValueName() + " " + getYMostRecentUnit().getShortcut());
        	else
        		chart.getXYPlot().getRangeAxis().setLabel(getGraphSetting().getYAxisManualLabel());
        	
        	//GraphicsEnvironment.getLocalGraphicsEnvironment().
        	chart.setTitle(new TextTitle(getGraphSetting().getGraphTitle(), new Font("Arial", Font.BOLD, 18)));
        	chart.getXYPlot().getRangeAxis().setLabelFont(new Font("Arial", Font.PLAIN, 12));
        	chart.getXYPlot().getDomainAxis().setLabelFont(new Font("Arial", Font.PLAIN, 12));
        	chart.getXYPlot().getRangeAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 10));
        	chart.getXYPlot().getDomainAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 10));
        }
        return chart;
    }
    @Override
    public JFreeReport getReport() {
		BaseFontFactory.setDefaultFontEncoding("UTF-8");
		JFreeReportBoot.getInstance().start();

		JFreeReport rep = null;
		InputStream in = GlobalSetting.getReportXYTemplate();
		if (in == null) {
			// TODO error odchytit
			// throw new ReportDefinitionException("URL is invalid");
		}

		final ReportGenerator generator = ReportGenerator.getInstance();
		try {
			rep = generator.parseReport(new org.xml.sax.InputSource(in), null);
		} catch (ElementDefinitionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		rep.setData(new XYMeasurementReportTableModel());
		return rep;
	}
    
    class XYMeasurementReportTableModel extends  AbstractTableModel{

        private static final long serialVersionUID = 2330570891673220683L;

        protected ChartPanel chart = null;
        
        private Class[] COLUMN_TYPES =
        {
                String.class,
                String.class, 
                String.class, String.class,String.class,
                String.class,
                String.class, 
                String.class, String.class,String.class,
                String.class,
                String.class, String.class, String.class, String.class,
                String.class, String.class,ChartPanel.class,
                String.class, String.class, String.class, String.class,
                Integer.class, String.class, String.class, String.class, String.class
        };

        private String[] COLUMN_NAMES =
        {
          "measurementTitle",
          "laboratory.name", 
          "laboratory.address.street", "laboratory.address.city","laboratory.address.psc",
          "laboratory.tel",
          "customer.name", 
          "customer.address.street", "customer.address.city","customer.address.psc",
          "customer.tel",
          "objectOfMeasurement", "dateOfMeasurement", "nameOfResponsiblePerson","nameOfMeasurementDevice",
          "nameOfValueX", "nameOfValueY", "chart",
          "xMarkerValue", "xMarkerUnit", "yMarkerValue", "yMarkerUnit",
          "portIndex", "valueXValue", "valueXUnit", "valueYValue", "valueYUnit"
        };

		private Date cachedDate = new Date();
		private String cachedDateString = DateFormat.getDateInstance().format(protocolSetting.getDateOfMeasurement().getTime());;

        
        public Class<?> getColumnClass(int columnIndex) {
            return COLUMN_TYPES[columnIndex];
        }

        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        public String getColumnName(int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }

        public int getRowCount() {
            return elements.size();
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Object ret = null;
            switch(columnIndex){
            case 0:
                ret = protocolSetting.getMeasurementTitle();
                break;
            case 1:
                ret = protocolSetting.getLaboratory().name;
                break;
            case 2:
                ret = protocolSetting.getLaboratory().address.street;
                break;
            case 3:
                ret = protocolSetting.getLaboratory().address.city;
                break;
            case 4:
                ret = protocolSetting.getLaboratory().address.psc;
                break;
            case 5:
                ret = protocolSetting.getLaboratory().tel;
                break;
            case 6:
                ret = protocolSetting.getCustomer().name;
                break;
            case 7:
                ret = protocolSetting.getCustomer().address.street;
                break;
            case 8:
                ret = protocolSetting.getCustomer().address.city;
                break;
            case 9:
                ret = protocolSetting.getCustomer().address.psc;
                break;
            case 10:
                ret = protocolSetting.getCustomer().tel;
                break;
            case 11:
                ret = protocolSetting.getObjectOfMeasurement();
                break;
            case 12:
            	if(!cachedDate.equals(protocolSetting.getDateOfMeasurement())){
            		cachedDateString = DateFormat.getDateInstance().format(protocolSetting.getDateOfMeasurement().getTime());
            		cachedDate = new Date(protocolSetting.getDateOfMeasurement().getTime());
            	}
                ret = cachedDateString;
                break;
            case 13:
                ret = new String(protocolSetting.getResponsiblePerson().getFirstName()
                		+ " " + protocolSetting.getResponsiblePerson().getLastName());
                break;
            case 14:
                ret = new String(protocolSetting.getMeasurer().getIdentification()
                		+ " " + protocolSetting.getMeasurer().getTitle());
                break;
            case 15:
                ret = xValueName;
                break;
            case 16:
                ret = yValueName;
                break;
            case 17:
                if(chart == null)
                    chart =  new ChartPanel(getchart());
                chart.setSize(200, 100);
                ret = chart;
                break;
            case 18:
            	ret = xMarker.getValueAsStringWthoutUnit();
            	break;
            case 19:
            	ret = xMarker.getUnit().getShortcut();
            	break;
            case 20:
            	ret = yMarker.getValueAsStringWthoutUnit();
            	break;
            case 21:
            	ret = yMarker.getUnit().getShortcut();
            	break;
            case 22:
                if(elements.get(rowIndex).getClassType() == MeasurementEntryFactory.XY_VALUE){
                    ret = new Integer(((XYValue)(elements.get(rowIndex))).getIndexXY());
                }
                else
                    ret = ""; 
                break;
            case 23:
                if(elements.get(rowIndex).getClassType() == MeasurementEntryFactory.XY_VALUE){
                    ret = ((XYValue)(elements.get(rowIndex))).getXValue().getValueAsStringWthoutUnit();
                }
                else
                    ret = elements.get(rowIndex).toString(); 
                break;
            case 24:
                if(elements.get(rowIndex).getClassType() == MeasurementEntryFactory.XY_VALUE){
                    ret = ((XYValue)(elements.get(rowIndex))).getXValue().getUnit().getShortcut();
                }
                else
                    ret = ""; 
                break;
            case 25:
                if(elements.get(rowIndex).getClassType() == MeasurementEntryFactory.XY_VALUE){
                    ret = ((XYValue)(elements.get(rowIndex))).getYValue().getValueAsStringWthoutUnit();
                }
                else
                    ret = ""; 
                break;
            case 26:
                if(elements.get(rowIndex).getClassType() == MeasurementEntryFactory.XY_VALUE){
                    ret = ((XYValue)(elements.get(rowIndex))).getYValue().getUnit().getShortcut();
                }
                else
                    ret = ""; 
                break;
            default:
                ret = "";
                break;
            }
            return ret;
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }


        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }
        
    }

    @Override
    public void reindex() {
        int index = 1;
        for(MeasurementEntry e : elements){
            if(e.getClassType() == MeasurementEntryFactory.XY_VALUE){
                ((XYValue)e).setIndexXY(index++);
            }
        }
        setModified(true);
        fireEntriesChanged();
    }
    @Override
    protected MeasurementXMLWriter getXMLWriter(BufferedWriter w) {
        return new XYMeasurementXMLWriter(w);
    }
    class XYMeasurementXMLWriter extends MeasurementXMLWriter{

        @Override
        public void storeMeasurementBody() throws IOException {
            XMLSupport.write("<xValueName value=\"" + XMLSupport.escape(xValueName) + "\"/>", w, offset);
            XMLSupport.write("<yValueName value=\"" + XMLSupport.escape(yValueName) + "\"/>", w, offset);

            XMLSupport.write("<yAutoconvertUnit prefix=\"" + yAutoconvertUnit.getPrefix().getExponent() + "\" description=\"" + yAutoconvertUnit.getDescription().getId() + "\"/>", w, offset);
            
            if(xMarker != null){
            	XMLSupport.write("<xMarker>", w, offset);
	            offset++;
	            xMarker.storeToXML(w, offset);
	            offset--;
	            XMLSupport.write("</xMarker>", w, offset);
            }
            else
            	XMLSupport.write("<xMarker value=\"null\"/>", w, offset);
            if(yMarker != null){
            	XMLSupport.write("<yMarker>", w, offset);
	            offset++;
	            yMarker.storeToXML(w, offset);
	            offset--;
	            XMLSupport.write("</yMarker>", w, offset);
            }
            else
            	XMLSupport.write("<yMarker value=\"null\"/>", w, offset);

            XMLSupport.write("<xMarkerFixed value=\"" + xMarkerFixed + "\"/>", w, offset);
            XMLSupport.write("<yMarkerFixed value=\"" + yMarkerFixed + "\"/>", w, offset);
            XMLSupport.write("<ignoreGroweXValue value=\"" + ignoreGroweXValue + "\"/>", w, offset);

            graphSetting.storeToXML(w, offset);

            super.storeMeasurementBody();
        }

        public XYMeasurementXMLWriter(BufferedWriter w) {
            super(w);
        }
        
    }

    @Override
    public void addEntries(Collection<MeasurementEntry> e) {
        if(autoConvert){
            for (MeasurementEntry ee : e)
				if (ee.getClassType() == MeasurementEntryFactory.XY_VALUE) {
					XYValue v = (XYValue) ee;
					v.getXValue().convert(autoConvertUnit.getDescription(),
							autoConvertUnit.getPrefix());
					v.getYValue().convert(yAutoconvertUnit.getDescription(),
							yAutoconvertUnit.getPrefix());
				}
        }
//        if(ignoreGroweXValue){
//    		XYValue last = findLastXYValue();
//    		Unit mostRecent = getXMostRecentUnit();
//    		if(elements.size() == 0){
//    			
//    		}
//			double lastX = last.getXValue().getValueInUnit(getXMostRecentUnit());
//            for (MeasurementEntry ee : e)
//				if (ee.getClassType() == MeasurementEntryFactory.XY_VALUE) {
//					XYValue v = (XYValue) ee;
//	        		try {
//						double vx = v.getXValue().getValueInUnit(getXMostRecentUnit());
//						if(vx > lastX)
//							return;
//					} catch (ConversionException e1) {
//					}
//				}
//        }
        super.addEntries(e);
    }
    @Override
    public void addEntry(MeasurementEntry e) {
        if(autoConvert){
            if(e.getClassType() == MeasurementEntryFactory.XY_VALUE){
                XYValue v = (XYValue)e;
                v.getXValue().convert(autoConvertUnit.getDescription(), autoConvertUnit.getPrefix());
                v.getYValue().convert(yAutoconvertUnit.getDescription(), yAutoconvertUnit.getPrefix());
            }
        }
//        if(ignoreGroweXValue){
//        	if(e.getClassType() == MeasurementEntryFactory.XY_VALUE){
//        		XYValue v = (XYValue)e;
//        		XYValue last = findLastXYValue();
//        		try {
//					double vx = v.getXValue().getValueInUnit(getXMostRecentUnit());
//					double lastX = last.getXValue().getValueInUnit(getXMostRecentUnit());
//					if(vx > lastX)
//						return;
//				} catch (ConversionException e1) {
//				}
//        	}
//        }
        super.addEntry(e);
    }
    protected XYValue findLastXYValue(){
    	for(int i= elements.size()-1; i>=0; i--){
    		if(elements.get(i).getClassType() == MeasurementEntryFactory.XY_VALUE){
    			return (XYValue)(elements.get(i));
    		}
    	}
    	return null;
    }
    @Override
    public DefaultHandler getSAXHandler(StructuredXMLLoader loader) {
        return new XYMeasurementSAXHandler(loader);
    }
    class XYMeasurementSAXHandler extends MeasurementSAXHandler{
        
        public XYMeasurementSAXHandler(StructuredXMLLoader loader){
            super(loader);
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equals("xValueName")){
                xValueName = attributes.getValue("value"); 
            }else if(qName.equals("yValueName")){
                yValueName = attributes.getValue("value"); 
            }else if(qName.equals("yAutoconvertUnit")){
                try{
                    int prefix = Integer.parseInt(attributes.getValue("prefix"));
                    int description = Integer.parseInt(attributes.getValue("description"));
                    yAutoconvertUnit = new Unit(prefix, description);
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba p�i na��t�n� m��en�.");
                }
            }else if(qName.equals("graphSetting")){
                loader.pushHandler(graphSetting.getSAXHandler(loader)); 
            }else if(qName.equals("xMarker")){
            	if(attributes.getValue("value") != null && attributes.getValue("value").equals("null")){
            		xMarker = null;
            	}
            	else
            		loader.pushHandler(xMarker.getSAXHandler(loader)); 
            }else if(qName.equals("yMarker")){
            	if(attributes.getValue("value") != null && attributes.getValue("value").equals("null")){
            		yMarker = null;
            	}
            	else
            		loader.pushHandler(yMarker.getSAXHandler(loader)); 
            }else if(qName.equals("xMarkerFixed")){
            	xMarkerFixed = Boolean.parseBoolean(attributes.getValue("value"));
            }else if(qName.equals("yMarkerFixed")){
            	yMarkerFixed = Boolean.parseBoolean(attributes.getValue("value")); 
            }else
                super.startElement(uri, localName, qName, attributes);
        }

    }

    @Override
	protected void initAfterLoad() {
		super.initAfterLoad();
    	mostRecentUnitXCalc.recalculateUse();
    	mostRecentUnitYCalc.recalculateUse();
    	updateXMark();
    	updateYMark();
    	findMarkerValue();
	}

    public GraphSetting getGraphSetting() {
        return graphSetting;
    }
    
    public void setGraphSetting(GraphSetting graphSetting) {
    	this.graphSetting = graphSetting;
    }
    
	@Override
	public boolean isChartCreated() {
		return chart != null;
	}
	
	public void addGraphSettingListener(GraphSettingListener l){
		graphSetting.addGraphSettingListener(l);
	}
	public void removeGraphSettingListener(GraphSettingListener l){
		graphSetting.removeGraphSettingListener(l);
	}
	
	public Unit getXMostRecentUnit(){
		return mostRecentUnitXCalc.getMostRecentUnit();
	}

	public Unit getYMostRecentUnit(){
		return mostRecentUnitYCalc.getMostRecentUnit();
	}
	@Override
	public int saveToXML() {
    	GlobalSetting.getUserSetting().getLastUsedGraphSetting().updateFrom(getGraphSetting());
		return super.saveToXML();
	}

	protected void findMarkerValue() {
		if (xMarkerFixed) {
			if(xMark == null && xMarker != null)
				updateXMark();
			findYMarkerValue();
		} else if (yMarkerFixed){
			if(yMark == null && yMarker != null)
				updateYMark();
			findXMarkerValue();
		}
	}
	protected void findXMarkerValue() {
		if (getGraphSetting().getSortValues()) {
			findXMarkerValueSorted();
		} else {
			findXMarkerValueUnsorted();
		}
	}
	protected void findYMarkerValue() {
		if (getGraphSetting().getSortValues()) {
			findYMarkerValueSorted();
		} else {
			findYMarkerValueUnsorted();
		}
	}
	protected void findXMarkerValueSorted(){
		XYValue vLow = null;
		XYValue vHigh = null;
		double vLowDY = 0;
		double vHighDY = 0;
		double vYMarker = 0;
		int count = 0;
		if(getYMarker() == null){
			setXMarker(null);
			return;
		}
		try {
			vYMarker = getYMarker().getValueInUnit(getYMostRecentUnit());
		} catch (ConversionException e2) {
			setXMarker(null);
			return;
		}
		for (MeasurementEntry e : this.elements) {
			if (e.getClassType() == MeasurementEntryFactory.XY_VALUE) {
				XYValue v = (XYValue) e;
				double vy = 0;
				try {
					vy = v.getYValue().getValueInUnit(getYMostRecentUnit());
					v.getXValue().getValueInUnit(getXMostRecentUnit());
				} catch (ConversionException e1) {
					continue;
				}
				count ++;
				if(vy <= vYMarker && (vLow == null || vy > vLowDY)){
					vLow = v;
					vLowDY = vy;
				}
				else if(vy > vYMarker && (vHigh == null || vy < vHighDY)){
					vHigh = v;
					vHighDY = vy;
				}
			}
		}
		if(vLow != null && vHigh != null){
			double x1 = 0;
			double x2 = 0;
			double y1 = 0;
			double y2 = 0;
			try {
				x1 = vLow.getXValue().getValueInUnit(getXMostRecentUnit());
				x2 = vHigh.getXValue().getValueInUnit(getXMostRecentUnit());
				y1 = vLow.getYValue().getValueInUnit(getYMostRecentUnit());
				y2 = vHigh.getYValue().getValueInUnit(getYMostRecentUnit());
			} catch (ConversionException e1) {
				setXMarker(null);
				return;
			}
			xMarkerTmp.setValueVal((x2-x1)*(vYMarker-y1)/(y2-y1)+x1);
			xMarkerTmp.setUnit(getXMostRecentUnit());
			setXMarker(xMarkerTmp);
		}
		else if(vLow == null || vHigh == null){
			setXMarker(null);
		}
//		else if(vLow == null){
//			XYValue higher = findNext(vHigh, false, false);
//			if(higher == null){
//				try {
//					xMarkerTmp.setValue(vHigh.getXValue().getValueInUnit(getXMostRecentUnit()));
//				} catch (ConversionException e1) {
//					return;
//				}
//				xMarkerTmp.setUnit(getXMostRecentUnit());
//				setXMarker(xMarkerTmp);
//			}
//			double x1 = 0;
//			double y1 = 0;
//			double x2 = 0;
//			double y2 = 0;
//			try {
//				x1 = vHigh.getXValue().getValueInUnit(getXMostRecentUnit());
//				y1 = vHigh.getYValue().getValueInUnit(getYMostRecentUnit());
//				x2 = higher.getXValue().getValueInUnit(getXMostRecentUnit());
//				y2 = higher.getYValue().getValueInUnit(getYMostRecentUnit());
//			} catch (ConversionException e1) {
//				setXMarker(null);
//				return;
//			}
//			xMarkerTmp.setValue(x1 - (x2-x1)*(y1-vYMarker)/(y2-y1));
//			xMarkerTmp.setUnit(getYMostRecentUnit());
//			setYMarker(xMarkerTmp);
//		}
//		else if(vHigh == null){
//			XYValue lower = findNext(vLow, true, false);
//			if(lower == null){
//				try {
//					xMarkerTmp.setValue(vLow.getXValue().getValueInUnit(getXMostRecentUnit()));
//				} catch (ConversionException e1) {
//					return;
//				}
//				xMarkerTmp.setUnit(getXMostRecentUnit());
//				setXMarker(xMarkerTmp);
//			}
//			double x1 = 0;
//			double y1 = 0;
//			double x2 = 0;
//			double y2 = 0;
//			try {
//				x2 = vLow.getXValue().getValueInUnit(getXMostRecentUnit());
//				y2 = vLow.getYValue().getValueInUnit(getYMostRecentUnit());
//				x1 = lower.getXValue().getValueInUnit(getXMostRecentUnit());
//				y1 = lower.getYValue().getValueInUnit(getYMostRecentUnit());
//			} catch (ConversionException e1) {
//				setXMarker(null);
//				return;
//			}
//			xMarkerTmp.setValue((x2-x1)*(vYMarker -y2)/(y2-y1)+ x2);
//			xMarkerTmp.setUnit(getXMostRecentUnit());
//			setXMarker(xMarkerTmp);
//		}
	}
	protected void findYMarkerValueSorted(){
		XYValue vLow = null;
		XYValue vHigh = null;
		double vLowDX = 0;
		double vHighDX = 0;
		double vXMarker = 0;
		int count = 0;
		if(getXMarker() == null){
			setYMarker(null);
			return;
		}
		try {
			vXMarker = getXMarker().getValueInUnit(getXMostRecentUnit());
		} catch (ConversionException e2) {
			setYMarker(null);
			return;
		}
		for (MeasurementEntry e : this.elements) {
			if (e.getClassType() == MeasurementEntryFactory.XY_VALUE) {
				XYValue v = (XYValue) e;
				double vx = 0;
				try {
					vx = v.getXValue().getValueInUnit(getXMostRecentUnit());
					v.getYValue().getValueInUnit(getYMostRecentUnit());
				} catch (ConversionException e1) {
					continue;
				}
				count ++;
				if(vx <= vXMarker && (vLow == null || vx > vLowDX)){
					vLow = v;
					vLowDX = vx;
				}
				else if(vx > vXMarker && (vHigh == null || vx < vHighDX)){
					vHigh = v;
					vHighDX = vx;
				}
			}
		}
		if(vLow != null && vHigh != null){
			double x1 = 0;
			double x2 = 0;
			double y1 = 0;
			double y2 = 0;
			try {
				x1 = vLow.getXValue().getValueInUnit(getXMostRecentUnit());
				x2 = vHigh.getXValue().getValueInUnit(getXMostRecentUnit());
				y1 = vLow.getYValue().getValueInUnit(getYMostRecentUnit());
				y2 = vHigh.getYValue().getValueInUnit(getYMostRecentUnit());
			} catch (ConversionException e1) {
				setYMarker(null);
				return;
			}
			yMarkerTmp.setValueVal((y2-y1)*(vXMarker-x1)/(x2-x1)+y1);
			yMarkerTmp.setUnit(getYMostRecentUnit());
			setYMarker(yMarkerTmp);
		}
		else if(vLow == null || vHigh == null){
			setYMarker(null);
		}
//		else if(vLow == null){
//			XYValue higher = findNext(vHigh, false, true);
//			if(higher == null){
//				try {
//					yMarkerTmp.setValue(vHigh.getYValue().getValueInUnit(getYMostRecentUnit()));
//				} catch (ConversionException e1) {
//				}
//				yMarkerTmp.setUnit(getYMostRecentUnit());
//				setYMarker(yMarkerTmp);
//			}
//			double x1 = 0;
//			double y1 = 0;
//			double x2 = 0;
//			double y2 = 0;
//			try {
//				x1 = vHigh.getXValue().getValueInUnit(getXMostRecentUnit());
//				y1 = vHigh.getYValue().getValueInUnit(getYMostRecentUnit());
//				x2 = higher.getXValue().getValueInUnit(getXMostRecentUnit());
//				y2 = higher.getYValue().getValueInUnit(getYMostRecentUnit());
//			} catch (ConversionException e1) {
//				setYMarker(null);
//				return;
//			}
//			yMarkerTmp.setValue(y1 - (y2-y1)*(x1-vXMarker)/(x2-x1));
//			yMarkerTmp.setUnit(getYMostRecentUnit());
//			setYMarker(yMarkerTmp);
//		}
//		else if(vHigh == null){
//			XYValue lower = findNext(vLow, true, true);
//			if(lower == null){
//				try {
//					yMarkerTmp.setValue(vLow.getYValue().getValueInUnit(getYMostRecentUnit()));
//				} catch (ConversionException e1) {
//					return;
//				}
//				yMarkerTmp.setUnit(getYMostRecentUnit());
//				setYMarker(yMarkerTmp);
//			}
//			double x1 = 0;
//			double y1 = 0;
//			double x2 = 0;
//			double y2 = 0;
//			try {
//				x2 = vLow.getXValue().getValueInUnit(getXMostRecentUnit());
//				y2 = vLow.getYValue().getValueInUnit(getYMostRecentUnit());
//				x1 = lower.getXValue().getValueInUnit(getXMostRecentUnit());
//				y1 = lower.getYValue().getValueInUnit(getYMostRecentUnit());
//			} catch (ConversionException e1) {
//				setYMarker(null);
//				return;
//			}
//			yMarkerTmp.setValue((y2-y1)*(vXMarker -x2)/(x2-x1)+ y2);
//			yMarkerTmp.setUnit(getYMostRecentUnit());
//			setYMarker(yMarkerTmp);
//		}
	}
//	private XYValue findNext(XYValue value, boolean lower, boolean xValue) {
//		XYValue r = null;
//		double d = 0;
//		double rd = 0;
//		try {
//			if(xValue)
//				d = value.getXValue().getValueInUnit(getXMostRecentUnit());
//			else
//				d = value.getYValue().getValueInUnit(getYMostRecentUnit());
//		} catch (ConversionException e2) {
//			return null;
//		}
//		for (MeasurementEntry e : this.elements) {
//			if (e.getClassType() == MeasurementEntryFactory.XY_VALUE) {
//				XYValue v = (XYValue) e;
//				double vx = 0;
//				double vy = 0;
//				try {
//					vx = v.getXValue().getValueInUnit(getXMostRecentUnit());
//					vy = v.getYValue().getValueInUnit(getYMostRecentUnit());
//				} catch (ConversionException e1) {
//					continue;
//				}
//				double vd = xValue?vx:vy;
//				if((r == null && (lower?(vd < d):(d < vd))) ||
//						(r != null && (lower?(rd < vd && vd < d):(d < vd && vd < rd)))){
//					rd = vd;
//					r = v;
//				}
//			}
//		}
//		return r;
//	}

	protected void findYMarkerValueUnsorted(){
		double vx = 0;
		double vy = 0;
		double vx_prev = 0;
		double vy_prev = 0;
//		double vx_prev_prev = 0;
//		double vy_prev_prev = 0;
		XYValue v_prevO = null;
		XYValue v = null;
		int count = 0;
//		boolean markBeforFirst = false;
		double xMarkerD;
		if(getXMarker() == null){
			setYMarker(null);
			return;
		}
		try {
			xMarkerD = getXMarker().getValueInUnit(getXMostRecentUnit());
		} catch (ConversionException e2) {
			setYMarker(null);
			return;
		}
		for (MeasurementEntry e : this.elements) {
			if (e.getClassType() == MeasurementEntryFactory.XY_VALUE) {
				v = (XYValue) e;
				try {
					vx = v.getXValue().getValueInUnit(getXMostRecentUnit());
					vy = v.getYValue().getValueInUnit(getYMostRecentUnit());
				} catch (ConversionException e1) {
					continue;
				}
				count ++;
//				if(markBeforFirst){
//					double dx = vx - vx_prev;
//					if(dx == 0){
//						setYMarker(null);
//						return;
//					}
//					yMarkerTmp.setValue(vy_prev-(vy-vy_prev)*(vx_prev-xMarkerD)/dx);
//					yMarkerTmp.setUnit(getYMostRecentUnit());
//					setYMarker(yMarkerTmp);
//					return;
//				}
//				if(v_prevO == null && xMarkerD < vx){
//					markBeforFirst = true;
//				}
//				else
				if(v_prevO != null){
					if(Math.min(vx_prev, vx) <= xMarkerD && xMarkerD <= Math.max(vx_prev, vx)){
						double dx = vx - vx_prev;
						if(dx == 0){
							setYMarker(null);
							return;
						}
						yMarkerTmp.setValueVal((vy-vy_prev)*(xMarkerD-vx_prev)/dx + vy_prev);
						yMarkerTmp.setUnit(getYMostRecentUnit());
						setYMarker(yMarkerTmp);
						return;
					}
				}
//				vy_prev_prev = vy_prev;
//				vx_prev_prev = vx_prev;
				vy_prev = vy;
				vx_prev = vx;
				v_prevO = v; 
			}
		}
		if(v_prevO == null){
			setYMarker(null);
		}
		else if(count == 1){
			yMarkerTmp.setValueVal(vy_prev);
			yMarkerTmp.setUnit(getYMostRecentUnit());
			setYMarker(yMarkerTmp);
		}
		else {
			setYMarker(null);
//			vy_prev = vy_prev_prev;
//			vx_prev = vx_prev_prev;
//			yMarkerTmp.setValue((vy-vy_prev)*(xMarkerD-vx)/(vx - vx_prev) + vy);
//			yMarkerTmp.setUnit(getYMostRecentUnit());
//			setYMarker(yMarkerTmp);
		}
	}
	protected void findXMarkerValueUnsorted(){
		double vx_prev_prev = 0;
		double vy_prev_prev = 0;
		double vx_prev = 0;
		double vy_prev = 0;
		XYValue v_prevO = null;
		XYValue v = null;
		double vx = 0;
		double vy = 0;
		int count = 0;
		boolean markBeforFirst = false;
		double yMarkerD;
		if(getYMarker() == null){
			setXMarker(null);
			return;
		}
		try {
			yMarkerD = getYMarker().getValueInUnit(getYMostRecentUnit());
		} catch (ConversionException e2) {
			setXMarker(null);
			return;
		}
		for (MeasurementEntry e : this.elements) {
			if (e.getClassType() == MeasurementEntryFactory.XY_VALUE) {
				v = (XYValue) e;
				try {
					vx = v.getXValue().getValueInUnit(getXMostRecentUnit());
					vy = v.getYValue().getValueInUnit(getYMostRecentUnit());
				} catch (ConversionException e1) {
					continue;
				}
				count ++;
				if(markBeforFirst){
					double dy = vy - vy_prev;
					if(dy == 0){
						setXMarker(null);
						return;
					}
					xMarkerTmp.setValueVal(vx_prev-(vx-vx_prev)*(vy_prev-yMarkerD)/dy);
					xMarkerTmp.setUnit(getXMostRecentUnit());
					setXMarker(xMarkerTmp);
					return;
				}
				if(v_prevO == null && yMarkerD < vy){
					markBeforFirst = true;
				}
				else if(v_prevO != null){
					if(Math.min(vy_prev, vy) <= yMarkerD && yMarkerD <= Math.max(vy_prev, vy)){
						double dy = vy - vy_prev;
						if(dy == 0){
							setXMarker(null);
							return;
						}
						xMarkerTmp.setValueVal((vx-vx_prev)*(yMarkerD-vy_prev)/dy + vx_prev);
						xMarkerTmp.setUnit(getXMostRecentUnit());
						setXMarker(xMarkerTmp);
						return;
					}
				}
				vy_prev_prev = vy_prev;
				vx_prev_prev = vx_prev;
				vy_prev = vy;
				vx_prev = vx;
				v_prevO = v; 
			}
		}
		if(v_prevO == null){
			setXMarker(null);
		}
		else if(count == 1){
			xMarkerTmp.setValueVal(vx_prev);
			xMarkerTmp.setUnit(getXMostRecentUnit());
			setXMarker(xMarkerTmp);
		}
		else {
			vy_prev = vy_prev_prev;
			vx_prev = vx_prev_prev;
			xMarkerTmp.setValueVal((vx-vx_prev)*(yMarkerD-vy)/(vy - vy_prev) + vx);
			xMarkerTmp.setUnit(getXMostRecentUnit());
			setXMarker(xMarkerTmp);
		}
	}
	public void deleteGrowValuesInX() {
		Vector<MeasurementEntry> toRemove = new Vector<MeasurementEntry>();
		double prevValue = 0;
		boolean first = true;
		boolean growe = false;
		Unit mostRecent = getXMostRecentUnit();
		for (MeasurementEntry e : elements) {
			if(e.getClassType() == MeasurementEntryFactory.XY_VALUE){
				XYValue v = (XYValue)e;
				double d = 0;
				try {
					d = v.getXValue().getValueInUnit(mostRecent);
				} catch (ConversionException e1) {
					continue;
				}
				if(first){
					prevValue = d;
					first = false;
					continue;
				}
				if(growe){
					toRemove.add(v);
					continue;
				}
				
				if(d > prevValue){
					toRemove.add(v);
					growe = true;
					continue;
				}
				prevValue = d;
			}
		}
		deleteValues(toRemove);
	}
}
