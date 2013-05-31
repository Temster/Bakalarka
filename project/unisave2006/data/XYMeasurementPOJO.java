package unisave2006.data;

import unisave2006.data.value.Value;
import unisave2006.units.Unit;

public class XYMeasurementPOJO extends MeasurementPOJO{

	public XYMeasurementPOJO() {
		
	}
	
	protected String xValueName = "Hodnota X";
    protected String yValueName = "Hodnota Y";
    protected Unit yAutoconvertUnit; 
    protected boolean ignoreGroweXValue = false;
    protected Value xMarker = null;
    protected boolean xMarkerFixed = true;
    protected Value yMarker = null;
    protected boolean yMarkerFixed = false;
    protected GraphSetting graphSetting;
    
    public String getXValueName() {
    	return xValueName; 
    }
    
    public void setXValueName(String xValueName) {
    	this.xValueName = xValueName;
    }
    
    public String getYValueName() {
    	return yValueName;
    }
    
    public void setYValueName(String yValueName) {
    	this.yValueName = yValueName;
    }
    
    public Unit getYAutoconvertUnit() {
    	return yAutoconvertUnit; 
    }
    
    public void setYAutoconvertUnit(Unit yAutoconvertUnit) {
    	this.yAutoconvertUnit = yAutoconvertUnit;
    }
    
    public boolean getIgnoreGroweXValue() {
    	return ignoreGroweXValue; 
    }
    
    public void setIgnoreGroweXValue(boolean ignoreGroweXValue) {
    	this.ignoreGroweXValue = ignoreGroweXValue;
    }
    
    public Value getXMarker() {
    	return xMarker;
    }
    
    public void setXMarker(Value xMarker) {
    	this.xMarker = xMarker;
    }
    
    public Value getYMarker() {
    	return yMarker;
    }
    
    public void setYMarker(Value yMarker) {
    	this.yMarker = yMarker;
    }
    
    public boolean getXMarkerFixed() {
    	return xMarkerFixed;
    }
    
    public void setXMarkerFixed(boolean xMarkerFixed) {
    	this.xMarkerFixed = xMarkerFixed;
    }
    
    public boolean getYMarkerFixed() {
    	return yMarkerFixed;
    }
    
    public void setYMarkerFixed(boolean yMarkerFixed) {
    	this.yMarkerFixed = yMarkerFixed;
    }
    
    public GraphSetting getGraphSetting() {
    	return graphSetting;
    }
    
    public void setGraphSetting(GraphSetting graphSetting) {
    	this.graphSetting = graphSetting;
    }
}
