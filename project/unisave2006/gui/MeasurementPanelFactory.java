/*
 * Created on 23.8.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import unisave2006.data.MeasurementFactory;


public class MeasurementPanelFactory {

    public static MeasurementPanelInterface createMeasurementPanel(int type){
        MeasurementPanelInterface p = null;
        switch(type){
        case MeasurementFactory.MEASUREMENT_NONE:
            p = new NullMeasurementPanel();
            break;
        case MeasurementFactory.MEASUREMENT_STATISTIC:
            p = new NullMeasurementPanel();
            break;
        case MeasurementFactory.MEASUREMENT_PLANED:
            p = new NullMeasurementPanel();
            break;
        case MeasurementFactory.MEASUREMENT_XY:
            p = new XYMeasurementPanel();
            break;
        }
        return p;
    }
}
