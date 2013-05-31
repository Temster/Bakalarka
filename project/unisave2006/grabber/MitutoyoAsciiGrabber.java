/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.grabber;

import org.xml.sax.Attributes;

import unisave2006.GlobalSetting;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.Text;
import unisave2006.data.value.Value;
import unisave2006.units.Unit;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_cShygBDbEdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class MitutoyoAsciiGrabber extends AsciiGrabber {

	public MitutoyoAsciiGrabber() {
		
	}
	
    protected Unit defaultUnit = Unit.getEmptyUnit();
    
    public Unit getDefaultUnit() {
    	return defaultUnit;
    }
    
    public void setDefaultUnit(Unit defaultUnit) {
    	this.defaultUnit = defaultUnit;
    }
    
    @Override
    public int getType() {
        return 4;
    }

    @Override
    public void setData(String uri, String name, String qName, Attributes atts) {
        if("posibleUnit".equals(qName)){
            int v = Integer.parseInt(atts.getValue("value"));
            posibleUnits.add(GlobalSetting.getUnitSet().getUnit(v));
        }
        else if("defaultUnit".equals(qName)){
            int prefix = Integer.parseInt(atts.getValue("prefix"));
            int unit = Integer.parseInt(atts.getValue("unit"));
            defaultUnit = new Unit(prefix, unit);
        }
        else
            super.setData(uri, name, qName, atts);
    }

    @Override
    public GrabberInterface cloneInstance() {
        MitutoyoAsciiGrabber g = new MitutoyoAsciiGrabber();
        copyTo(g);
        return g;
    }

    protected void copyTo(MitutoyoAsciiGrabber g) {
        super.copyTo(g);
        g.defaultUnit = defaultUnit;
    }

    @Override
    protected MeasurementEntry parseLine(StringBuilder b) {
        b.delete(0, valueOffset);
        b.delete(b.length()-valueTruncate, b.length());
        String line = b.toString();
        try{
            double d = Double.parseDouble(line);
            Value v = new Value();
            v.setValueVal(d);
            v.setUnit(new Unit(defaultUnit));
            return v;
        }catch(NumberFormatException ex){
            return new Text(Text.TEXT, line);
        }
    }
}
