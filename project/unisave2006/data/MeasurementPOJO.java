package unisave2006.data;

import java.util.ArrayList;
import java.util.List;

import unisave2006.data.value.MeasurementEntry;
import unisave2006.units.Unit;

public class MeasurementPOJO {

	public MeasurementPOJO() {
		
	}
	
	private Long id;
	protected List<MeasurementEntry> elements = new ArrayList<MeasurementEntry>();
	protected boolean modified = false;
	protected boolean autoIndex = false;
    protected boolean autoConvert = false;
    protected Unit autoConvertUnit;
    protected ProtocolSetting protocolSetting;
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public List<MeasurementEntry> getElements() {
    	return elements;
    }
    
    public void setElements(List<MeasurementEntry> elements) {
    	this.elements = elements;
    }
    
    public boolean getModified() {
    	return modified;
    }
    
    public void setModified(boolean modified) {
    	this.modified = modified;
    }
    
    public boolean getAutoIndex() {
    	return autoIndex;
    }
    
    public void setAutoIndex(boolean autoIndex) {
    	this.autoIndex = autoIndex;
    }
    
    public boolean getAutoConvert() {
    	return autoConvert;
    }
    
    public void setAutoConvert(boolean autoConvert) {
    	this.autoConvert = autoConvert;
    }
    
    public Unit getAutoConvertUnit() {
    	return autoConvertUnit;
    }
    
    public void setAutoConvertUnit(Unit autoConvertUnit) {
    	this.autoConvertUnit = autoConvertUnit;
    }
    
    public ProtocolSetting getProtocolSetting() {
    	return protocolSetting;
    }
    
    public void setProtocolSetting(ProtocolSetting protocolSetting) {
    	this.protocolSetting = protocolSetting;
    }
}
