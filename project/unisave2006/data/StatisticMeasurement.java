/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.report.JFreeReport;

/**
 * @author David Je�ek
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_OoX7MBDREdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class StatisticMeasurement extends Measurement {
    
	public StatisticMeasurement() {
		
	}
	
	public StatisticMeasurement(String type) {
		
	}
	
	public int getType(){
        return MeasurementFactory.MEASUREMENT_STATISTIC;
    }
    
    protected JFreeChart chart = null;

    @Override
    public JFreeChart getchart() {
        if(chart == null){
            chart = ChartFactory.createLineChart("Graf měření", "M�en�", "Hodnota", (CategoryDataset)getDataset(), PlotOrientation.VERTICAL, false, true, false);
            chart.setBackgroundPaint(Color.WHITE);
            CategoryPlot plot = chart.getCategoryPlot();
            LineAndShapeRenderer r = new LineAndShapeRenderer();
            Path2D.Double p = new Path2D.Double();
            p.append(new Line2D.Double(-3,0,3,0), false);
            p.append(new Line2D.Double(0,-3,0,3), false);
            r.setSeriesShape(0, p);
            r.setSeriesPaint(0, Color.BLACK);
            plot.setRenderer(r);
//            CategoryAxis valueaxis = plot.getDomainAxis();
//            valueaxis.setAutoRange(true);
//            if(valueaxis instanceof NumberAxis){
//            	NumberAxis na = (NumberAxis)valueaxis;
//            	na.setAutoRangeIncludesZero(false);
//            }
//            valueaxis.setAutoRange(true);

            ValueAxis rangeAxis = plot.getRangeAxis();
            rangeAxis.setAutoRange(true);
            if(rangeAxis instanceof NumberAxis){
            	NumberAxis na = (NumberAxis)rangeAxis;
            	na.setAutoRangeIncludesZero(false);
            	//na.setAutoRangeStickyZero(true);
            }
            rangeAxis.setAutoRange(true);
        }
        return chart;
    }

    @Override
    public JFreeReport getReport() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void reindex() {
        // TODO Auto-generated method stub
        
    }

	@Override
	public boolean isChartCreated() {
		// TODO Auto-generated method stub
		return false;
	}
}
