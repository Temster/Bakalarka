/*
 * Created on 20.8.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

public interface MeasurementGrabbingListener {

    public void grabbingStopped();

    public void grabbingStarted();
    
    public void grabbingTerminated(String reason);

}