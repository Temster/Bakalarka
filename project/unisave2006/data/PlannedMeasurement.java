/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import org.jfree.chart.JFreeChart;
import org.jfree.report.JFreeReport;

/**
 * @author David Je�ek
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_2bSwsBETEdu-TcvEGVchxA"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class PlannedMeasurement extends Measurement {
	
	public PlannedMeasurement() {
		
	}
	
	public PlannedMeasurement(String type) {
		
	}
	
    public int getType(){
        return MeasurementFactory.MEASUREMENT_PLANED;
    }

   
    
    @Override
    public JFreeChart getchart() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JFreeReport getReport() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void reindex() {
        // TODO Auto-generated method stub
        
    }

	@Override
	public boolean isChartCreated() {
		// TODO Auto-generated method stub
		return false;
	}
}
