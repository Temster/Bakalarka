/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.grabber;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.xml.sax.Attributes;

import unisave2006.GlobalSetting;
import unisave2006.data.value.BlockSeparator;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.StatBlockSeparator;
import unisave2006.data.value.Statistic;
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
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_Gtb5YBDbEdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class ElectroPhysicAsciiGrabber extends AsciiGrabber {

	public ElectroPhysicAsciiGrabber() {
		
	}
	
    protected Map<String, Unit> stringToUnitMap = new TreeMap<String, Unit>(); 
    protected Map<String, Integer> stringToStatTypeMap = new TreeMap<String, Integer>(); 
    protected String blockString = "";
    protected String statBlockSeparator = "";
    
    public Map<String, Unit> getStringToUnitMap() {
    	return stringToUnitMap;
    }
    public void setStringToUnitMap(Map<String, Unit> stringToUnitMap) {
    	this.stringToUnitMap = stringToUnitMap;
    }
    
    public Map<String, Integer> getStringToStatTypeMap() {
    	return stringToStatTypeMap;
    }
    public void setStringToStatTypeMap(Map<String, Integer> stringToStatTypeMap) {
    	this.stringToStatTypeMap = stringToStatTypeMap;
    }
    
    public String getBlockString() {
    	return blockString;
    }
    public void setBlockString(String blockString) {
    	this.blockString = blockString;
    }
    
    public String getStatBlockSeparator() {
    	return statBlockSeparator;
    }
    public void setStatBlockSeparator(String statBlockSeparator) {
    	this.statBlockSeparator = statBlockSeparator;
    }
    
    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void setData(String uri, String name, String qName, Attributes atts) {
        if("posibleUnit".equals(qName)){
            int v = Integer.parseInt(atts.getValue("value"));
            posibleUnits.add(GlobalSetting.getUnitSet().getUnit(v));
        }
        else if("posibleUnitsMap".equals(qName)){
            int prefix = Integer.parseInt(atts.getValue("prefix"));
            int unit = Integer.parseInt(atts.getValue("unit"));
            String s = atts.getValue("shortcut");
            stringToUnitMap.put(s, new Unit(prefix, unit));
        }
        else if("blockString".equals(qName)){
            blockString = atts.getValue("value");
        }
        else if("statBlockSeparator".equals(qName)){
            statBlockSeparator = atts.getValue("value");
        }
        else if("statType".equals(qName)){
            int id = Integer.parseInt(atts.getValue("id"));
            String s = atts.getValue("string");
            stringToStatTypeMap.put(s, id);
        }
        else
            super.setData(uri, name, qName, atts);
    }

    @Override
    public GrabberInterface cloneInstance() {
        ElectroPhysicAsciiGrabber g = new ElectroPhysicAsciiGrabber();
        copyTo(g);
        return g;
    }

    protected void copyTo(ElectroPhysicAsciiGrabber g) {
        super.copyTo(g);
        g.stringToUnitMap = stringToUnitMap;
        g.stringToStatTypeMap = stringToStatTypeMap;
        g.blockString = blockString;
        g.statBlockSeparator = statBlockSeparator;
    }

    @Override
    protected MeasurementEntry parseLine(StringBuilder b) {
        int lineStartIndex = b.indexOf(">"); 
        if(lineStartIndex >= 0)
        {
            b.delete(0, lineStartIndex+1);
        }
        b.delete(0, valueOffset);
        b.delete(b.length() - valueTruncate, b.length());
        StringTokenizer st = new StringTokenizer(b.toString(), " ");
        if(!st.hasMoreTokens()){
            return new Text(Text.ERROR, String.format("Øetìzec \"%s\" nelze rozpoznat.", b.toString()));
        }
        String token = st.nextToken();
        Integer statType = stringToStatTypeMap.get(token);
        if(statType != null){
            // je to statistika
            Statistic s = new Statistic();
            s.setStatType(statType);
            s.setValueSta(new Value());
            if(!st.hasMoreTokens())
                return s;
            token = st.nextToken();
            try{
                s.getValueSta().setValueVal(Double.parseDouble(token));
            }catch(NumberFormatException ex){
            }
            if(!st.hasMoreTokens())
                return s;
            token = st.nextToken();
            s.getValueSta().setUnit(new Unit(token));
            return s;
        }
        else if(blockString.equals(token))
        {
            BlockSeparator bs = new BlockSeparator();
            bs.setTextBS(token);
            if(!st.hasMoreElements())
                return bs;
            token = st.nextToken();
            try{
                bs.setIndexBS(Integer.parseInt(token));
            } catch(NumberFormatException ex){
                return new Text(Text.TEXT, b.toString());
            }
            return bs;
        }
        if(statBlockSeparator.equals(token))
        {
            StatBlockSeparator sep = new StatBlockSeparator();
            sep.setTextSBS(token);
            return sep;
        }
        else 
        {
            try{
                int index = Integer.parseInt(token);
                Value v = new Value();
                v.setIndexVal(index);
                if(!st.hasMoreTokens())
                    return new Text(Text.ERROR, String.format("Øetìzec \"%s\" nelze rozpoznat.", b.toString()));
                token = st.nextToken();
                try {
                    v.setValueVal(Double.parseDouble(token));
                } catch (NumberFormatException ex) {
                    return new Text(Text.ERROR, String.format("Øetìzec \"%s\" nelze rozpoznat.", b.toString()));
                }
                if(!st.hasMoreTokens())
                    return v;
                token = st.nextToken();
                stringToUnitMap.get(token);
                Unit u = stringToUnitMap.get(token);
                if(u != null)
                    v.setUnit(new Unit(u)); 
                return v;
                
            } catch(NumberFormatException ex){
                return new Text(Text.TEXT, b.toString());
            }
        }
    }
}
