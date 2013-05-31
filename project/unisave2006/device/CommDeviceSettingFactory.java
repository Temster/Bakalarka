/*
 * Created on 11.8.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.device;

public class CommDeviceSettingFactory {
    public static final int SERIAL_COM_DEVICE = 1;
    public static final int IRDA_SERIAL_COM_DEVICE = 2;
    public static final int NULL_COMM_DEVICE = 3;

    public static CommDeviceSetting createCommDeviceSetting(int id){
        CommDeviceSetting dev = null;
        switch(id){
        case SERIAL_COM_DEVICE:
            dev = new SerialCommDeviceSetting();
            break;
        case IRDA_SERIAL_COM_DEVICE:
            dev = new IrdaSerialCommDeviceSetting();
            break;
        case NULL_COMM_DEVICE:
            dev = new NullCommDevice();
            break;
        }
        if(dev != null)
            dev.setComDeviceType(id);
        return dev;
    }
    
}
