/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.grabber;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_6GgqkBDZEdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class GrabberFactory {
    public static final int ASCII_GRABBER = 1;
    public static final int ELEKTRO_PHYSIC_ASCII_GRABBER = 2;
    public static final int MANUAL_GRABBER = 3;
    public static final int MITUTOYO_ASCII_GRABBER = 4;
    public static final int MAHR_ASCII_GRABBER = 5;
    public static final int DOUBLE_GRABBER = 6;
    
    public static GrabberInterface createGrabber(int id){
        GrabberInterface g = null;
        switch(id){
        case ASCII_GRABBER:
            g = new AsciiGrabber();
            break;
        case ELEKTRO_PHYSIC_ASCII_GRABBER:
            g = new ElectroPhysicAsciiGrabber();
            break;
        case MANUAL_GRABBER:
            g = new ManualGrabber();
            break;
        case MITUTOYO_ASCII_GRABBER:
            g = new MitutoyoAsciiGrabber();
            break;
        case MAHR_ASCII_GRABBER:
            g = new MahrAsciiGrabber();
            break;
        }
        return g;
    }
}
