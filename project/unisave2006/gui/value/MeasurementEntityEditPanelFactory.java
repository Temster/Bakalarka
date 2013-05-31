/*
 * Created on 2.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui.value;

import unisave2006.data.value.MeasurementEntryFactory;

public class MeasurementEntityEditPanelFactory {

    public static MeasurementEntityEditor createEditPanel(int type){
    	return createEditPanel(type, false);
    }
    public static MeasurementEntityEditor createEditPanel(int type, boolean nessted){
        MeasurementEntityEditor editor = null;
        switch(type){
        case MeasurementEntryFactory.VALUE:
            editor = new ValuePanel();
            if(nessted)
                ((ValuePanel)editor).setNoIndex();
            break;
        case MeasurementEntryFactory.NAMED_VALUE:
            editor = new NamedValuePanel(); 
            break;
        case MeasurementEntryFactory.XY_VALUE:
            editor = new XYValuePanel();
            break;
        case MeasurementEntryFactory.TEXT:
            editor = new TextValuePanel(); 
            break;
        case MeasurementEntryFactory.BLOCK_SEPARATOR:
            editor = new BlockSeparatorPanel();
            break;
        case MeasurementEntryFactory.STATISTIC:
            editor = new StatisticValuePanel();
            break;
        case MeasurementEntryFactory.STAT_BLOCK_SEPARATOR:
            editor = new StatBlockSeparatorPanel();
            break;
            default:
                editor = null;
        }
        return editor;
        
    }
}
