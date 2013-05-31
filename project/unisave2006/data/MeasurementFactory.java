/*
 * Created on 20.8.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MeasurementFactory {

    public static final int MEASUREMENT_NONE = 0;
    public static final int MEASUREMENT_STATISTIC = 1;
    public static final int MEASUREMENT_PLANED = 2;
    public static final int MEASUREMENT_XY = 3;
    
    public static Measurement createMeasurement(int type) {
        Measurement m = null;
        switch(type){
        case MEASUREMENT_STATISTIC:
            m = new StatisticMeasurement();
            break;
        case MEASUREMENT_PLANED:
            m = new PlannedMeasurement();
            break;
        case MEASUREMENT_XY:
            m = new XYMeasurement();
            break;
        }
        return m;
    }

    public static Measurement loadMeasurement(File f) throws IOException, Exception{
        MeasurementXMLLoader loader = new MeasurementXMLLoader();
        loader.setSource(new InputStreamReader(new FileInputStream(f), "UTF-8"));
        loader.load();
        if(loader.getMeasurement() != null)
            loader.getMeasurement().setFile(f);
        return loader.getMeasurement();
    }
}
