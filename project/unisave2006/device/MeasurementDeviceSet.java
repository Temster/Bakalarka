/*
 * Created on 10.8.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.device;

import java.util.Iterator;
import java.util.Vector;

public class MeasurementDeviceSet implements Iterable<MeasurementDevice>{
    /**
     * Comment for <code>measurementDevice</code>
     */
    private Vector<MeasurementDevice> measurementDevice = new Vector<MeasurementDevice>();
    
    
    public void addMeasurementDevice(MeasurementDevice md){
        measurementDevice.add(md);
    }
    
    public MeasurementDevice getMeasurementDevice(int index){
        return measurementDevice.elementAt(index);
    }

    public Iterator<MeasurementDevice> iterator() {
        return measurementDevice.iterator();
    }

    public int getSize() {
        return measurementDevice.size();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public int getIndexOf(MeasurementDevice device) {
        return measurementDevice.indexOf(device);
    }

}
