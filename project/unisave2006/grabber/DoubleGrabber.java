/*
 * Created on 2.9.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.grabber;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.xml.sax.Attributes;

import unisave2006.data.Measurement;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.Text;
import unisave2006.data.value.Value;
import unisave2006.data.value.XYValue;

public class DoubleGrabber extends AbstractGrabber{

	public DoubleGrabber() {
		
	}
	
    protected GrabberInterface grab1 = null;
    protected GrabberInterface grab2 = null;

    public GrabberInterface getGrab1() {
    	return grab1;
    }
    public void setGrab1(GrabberInterface grab1) {
    	this.grab1 = grab1;
    }
    
    public GrabberInterface getGrab2() {
    	return grab2;
    }
    public void setGrab2(GrabberInterface grab2) {
    	this.grab2 = grab2;
    }
    
    public DoubleGrabber(GrabberInterface grab1, GrabberInterface grab2) {
        super();
        this.grab1 = grab1;
        this.grab2 = grab2;
        posibleUnits.addAll(grab1.getPosibleUnits());
        posibleUnits.addAll(grab2.getPosibleUnits());
    }

    public int getType() {
        return GrabberFactory.DOUBLE_GRABBER;
    }


    @Override
    public void startGrabbing(InputStream in, InputStream err, Measurement m) throws IOException {
        InputStreamReader r = null;
		try {
			r = new InputStreamReader(in, "windows-1250");
		} catch (UnsupportedEncodingException e) {
			r = new InputStreamReader(in);
		}
		setReader(r);
        grab1.setReader(r);
        grab2.setReader(r);
    	grab1.startGrabbingInNesstedMode(in, err, m);
        grab2.startGrabbingInNesstedMode(in, err, m);
        super.startGrabbing(in, err, m);
    }

    public void setData(String uri, String name, String qName, Attributes atts) {
        // No DATA to set
    }
    
    public void parse() {
        int index = 0;
        while (!stopped) {
            MeasurementEntry e = parseOneEntry();
            if(e == null)
                stopGrabbing();
            if(e instanceof XYValue){
                XYValue v = (XYValue)e;
                v.setIndexXY(index++);
            }
            queue.add(e);
        }
        stopGrabbing();
    }



    public MeasurementEntry parseOneEntry() {
        XYValue v = new XYValue();
        MeasurementEntry e1 = grab1.parseOneEntry();
        MeasurementEntry e2 = grab2.parseOneEntry();
        if(e1 instanceof Value && e2 instanceof Value){
            v.setXValue((Value)e1);
            v.setYValue((Value)e2);
        }
        else{
            Text t = new Text();
            t.setType(Text.ERROR);
            StringBuilder sb = new StringBuilder(100);
            sb.append("Nepodaøilo se setavit hodnotu XY z: x=\"");
            if(e1 != null)
                sb.append(e1.toString());
            else
                sb.append("žádná hodnota");
            sb.append("\" a y=\"");
            if(e2 != null)
                sb.append(e2.toString());  
            else
                sb.append("žádná hodnota");
            sb.append("\"");
            t.setTextTxt(sb.toString());
            if(e1 == null || e2 == null)
                stopGrabbing();
            return t;
        }
        return v;
    }

    public GrabberInterface cloneInstance() {
        DoubleGrabber dg = new DoubleGrabber(grab1.cloneInstance(), grab2.cloneInstance());
        copyTo(dg);
        return dg;
    }

    @Override
    public void stopGrabbing() {
        super.stopGrabbing();
        grab1.stopGrabbing();
        grab2.stopGrabbing();
    }
}
