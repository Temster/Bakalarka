/*
 * Created on 5.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui.value;

import java.awt.Component;

public class MeasurementEntityConstructionException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -7549859291272182029L;

    protected Component source = null;

    public Component getSource() {
        return source;
    }

    public MeasurementEntityConstructionException(String message, Component source) {
        super(message);
        this.source = source;
    }

}
