/*
 * Created on 2.9.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data.value;

public class MeasurementEntryFactory {

    public static final int VALUE = 0;
    public static final int NAMED_VALUE = 1;
    public static final int XY_VALUE = 2;
    public static final int TEXT = 3;
    public static final int BLOCK_SEPARATOR = 4;
    public static final int STATISTIC = 5;
    public static final int STAT_BLOCK_SEPARATOR = 6;

    public static String getName(int type){
        String name = "";
        switch(type){
        case VALUE:
            name = "běžná hodnota";
            break;
        case NAMED_VALUE:
            name = "pojmenovaná hodnota";
            break;
        case XY_VALUE:
            name = "hodnota XY";
            break;
        case TEXT:
            name = "textSBS";
            break;
        case BLOCK_SEPARATOR:
            name = "oddělovač bloků";
            break;
        case STATISTIC:
            name = "statistická hodnota";
            break;
        case STAT_BLOCK_SEPARATOR:
            name = "oddělovač bloku statististiky";
            break;
        }
        return name;
    }
    public static MeasurementEntry getMeasurementEntry(int type){
        MeasurementEntry m = null;
        switch(type){
        case VALUE:
            m = new Value();
            break;
        case NAMED_VALUE:
            m = new NamedValue();
            break;
        case XY_VALUE:
            m = new XYValue();
            break;
        case TEXT:
            m = new Text();
            break;
        case BLOCK_SEPARATOR:
            m = new BlockSeparator();
            break;
        case STATISTIC:
            m = new Statistic();
            break;
        case STAT_BLOCK_SEPARATOR:
            m = new StatBlockSeparator();
            break;
        }
        return m;
    }
}
