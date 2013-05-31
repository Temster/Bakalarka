/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Vector;

import org.jfree.chart.plot.PlotOrientation;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import unisave2006.XMLSupport;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_i3BFIBDZEdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class GraphSetting {
	
	protected Long id;
	protected String graphTitle = "Graf namìøených hodnot";
	protected boolean xAxisAutomaticalLabeling = true;
	protected boolean yAxisAutomaticalLabeling = true;
	protected String xAxisManualLabel = ""; 
	protected String yAxisManualLabel = "";
	protected boolean sortValues = true;
	protected boolean verticalOrientation = true;
	protected PlotOrientation graphOrientation = PlotOrientation.VERTICAL;
	
	
	protected Vector<GraphSettingListener> listeners = new Vector<GraphSettingListener>();
	
	public GraphSetting() {
	}
	
	public void addGraphSettingListener(GraphSettingListener l){
		listeners.add(l);
	}
	public void removeGraphSettingListener(GraphSettingListener l){
		listeners.remove(l);
	}

	public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
	public String getGraphTitle() {
		return graphTitle;
	}
	public void setGraphTitle(String graphTitle) {
		if(!this.graphTitle.equals(graphTitle)){
			this.graphTitle = graphTitle;
			fireGraphTitleChanged();
		}
	}
	
	public boolean getXAxisAutomaticalLabeling() {
		return xAxisAutomaticalLabeling;
	}
	
	public void setXAxisAutomaticalLabeling(boolean axisAutomaticalLabeling) {
		if(xAxisAutomaticalLabeling != axisAutomaticalLabeling){
			xAxisAutomaticalLabeling = axisAutomaticalLabeling;
			fireXAxisAutomaticLabelingChanged();
		}
	}
	public String getXAxisManualLabel() {
		return xAxisManualLabel;
	}
	public void setXAxisManualLabel(String axisManualLabel) {
		if(!xAxisManualLabel.equals(axisManualLabel)){
			xAxisManualLabel = axisManualLabel;
			fireXAxisManualLabelChanged();
		}
	}
	public boolean getYAxisAutomaticalLabeling() {
		return yAxisAutomaticalLabeling;
	}
	public void setYAxisAutomaticalLabeling(boolean axisAutomaticalLabeling) {
		if(yAxisAutomaticalLabeling != axisAutomaticalLabeling){
			yAxisAutomaticalLabeling = axisAutomaticalLabeling;
			fireYAxisAutomaticLabelingChanged();
		}
	}
	public String getYAxisManualLabel() {
		return yAxisManualLabel;
	}
	public void setYAxisManualLabel(String axisManualLabel) {
		if(!yAxisManualLabel.equals(axisManualLabel)){
			yAxisManualLabel = axisManualLabel;
			fireYAxisManualLabelChanged();
		}
	}
	public boolean getSortValues() {
		return sortValues;
	}
	public void setSortValues(boolean value) {
		if(sortValues != value){
			sortValues = value;
			fireSortValuesChanged();
		}
	}
	
	public boolean getVerticalOrientation() {
		return verticalOrientation;
	}
	
	public void setVerticalOrientation(boolean orientation) {
		if(this.verticalOrientation != orientation) {
			this.verticalOrientation = orientation;
			if(orientation == true)
				this.graphOrientation = PlotOrientation.VERTICAL;
			else
				this.graphOrientation = PlotOrientation.HORIZONTAL;
		}
	}
	
	public PlotOrientation getGraphOrientation() {
		return graphOrientation;
	}
	public void setGraphOrientation(PlotOrientation orientation) {
		if(!graphOrientation.equals(orientation)){
			graphOrientation = orientation;
			fireGraphOrientationChanged();
			if(orientation == PlotOrientation.VERTICAL) {
				verticalOrientation = true;
			}
			else
				verticalOrientation = false;
		}
	}
	
	public void storeToXML(BufferedWriter w, int offset) throws IOException {
        XMLSupport.write("<graphSetting>", w, offset);
        offset++;
        XMLSupport.write("<graphTitle value=\""+ XMLSupport.escape(graphTitle) +"\"/>", w, offset);
        XMLSupport.write("<xAxisAutomaticalLabeling value=\""+ xAxisAutomaticalLabeling +"\"/>", w, offset);
        XMLSupport.write("<yAxisAutomaticalLabeling value=\""+ yAxisAutomaticalLabeling +"\"/>", w, offset);
        XMLSupport.write("<xAxisManualLabel value=\""+ XMLSupport.escape(xAxisManualLabel) +"\"/>", w, offset);
        XMLSupport.write("<yAxisManualLabel value=\""+ XMLSupport.escape(yAxisManualLabel) +"\"/>", w, offset);
        XMLSupport.write("<sortValues value=\""+ sortValues +"\"/>", w, offset);
        XMLSupport.write("<graphOrientation value=\""+
        		((graphOrientation.equals(PlotOrientation.VERTICAL))?"vertical":"horizontal")
        		+"\"/>", w, offset);
        offset--;
        XMLSupport.write("</graphSetting>", w, offset);
    }
    public DefaultHandler getSAXHandler(StructuredXMLLoader loader){
        return new GraphSettingSAXHandler(loader);
    }
    class GraphSettingSAXHandler extends DefaultHandler{
        protected StructuredXMLLoader loader = null;

        public GraphSettingSAXHandler(StructuredXMLLoader loader) {
            super();
            this.loader = loader;
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equals("graphSetting"))
                loader.popHandler();
            else
                super.endElement(uri, localName, qName);
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        	if (qName.equals("graphTitle")) {
        		graphTitle = attributes.getValue("value");
        	} else if (qName.equals("xAxisAutomaticalLabeling")) {
        		xAxisAutomaticalLabeling = Boolean.parseBoolean(attributes.getValue("value"));
        	} else if (qName.equals("yAxisAutomaticalLabeling")) {
        		yAxisAutomaticalLabeling = Boolean.parseBoolean(attributes.getValue("value"));
        	} else if (qName.equals("xAxisManualLabel")) {
        		xAxisManualLabel = attributes.getValue("value");
        	} else if (qName.equals("yAxisManualLabel")) {
        		yAxisManualLabel = attributes.getValue("value");
        	} else if (qName.equals("sortValues")) {
        		sortValues = Boolean.parseBoolean(attributes.getValue("value"));
        	} else if (qName.equals("graphOrientation")) {
        		graphOrientation = (attributes.getValue("value").equals("vertical")?PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL);
        	}
        }
    }
   
	
	protected void fireGraphTitleChanged() {
		for(GraphSettingListener l : listeners){
			l.graphTitleChanged();
		}
	}
	protected void fireXAxisAutomaticLabelingChanged() {
		for(GraphSettingListener l : listeners){
			l.xAxisAutomaticLabelingChanged();
		}
	}
	protected void fireXAxisManualLabelChanged() {
		for(GraphSettingListener l : listeners){
			l.xAxisManualLabelChanged();
		}
	}
	protected void fireYAxisAutomaticLabelingChanged() {
		for(GraphSettingListener l : listeners){
			l.yAxisAutomaticLabelingChanged();
		}
	}
	protected void fireYAxisManualLabelChanged() {
		for(GraphSettingListener l : listeners){
			l.yAxisManualLabelChanged();
		}
	}
	private void fireGraphOrientationChanged() {
		for(GraphSettingListener l : listeners){
			l.GraphOrientationChanged();
		}
	}
	private void fireSortValuesChanged() {
		for(GraphSettingListener l : listeners){
			l.SortValuesChanged();
		}
	}
	public void updateFrom(GraphSetting gs) {
		graphTitle = gs.getGraphTitle();
		xAxisAutomaticalLabeling = gs.getXAxisAutomaticalLabeling();
		yAxisAutomaticalLabeling = gs.getYAxisAutomaticalLabeling();
		xAxisManualLabel = gs.getXAxisManualLabel();
		yAxisManualLabel = gs.getYAxisManualLabel();
		sortValues = gs.getSortValues();
		graphOrientation = gs.getGraphOrientation();	
	}
	
	
}
