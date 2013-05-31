/*
 * Created on 11.8.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.device;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.xml.sax.Attributes;

public class NullCommDevice extends CommDeviceSetting {

	public NullCommDevice() {
		
	}
	
    @Override
    public CommDeviceSetting cloneInstance() {
        return new NullCommDevice();
    }

    @Override
    public void connect() throws IOException {
    }

    @Override
    public void disconnect() {
    }

    @Override
    public int getComDeviceType() {
        return CommDeviceSettingFactory.NULL_COMM_DEVICE;
    }

    @Override
    public String getComName() {
        return super.getComName();
    }

    @Override
    public InputStream getErrStream() {
        return new ByteArrayInputStream(new byte[0]);
    }

    @Override
    public InputStream getInStream() {
        return new ByteArrayInputStream(new byte[0]);
    }

    @Override
    public void setComDeviceType(int comDeviceType) {
    }

    @Override
    public void setCommSetting(CommSettings s) {
    }

    @Override
    public void setCommTimeouts(CommTimeouts t) {
    }

    @Override
    public void setComName(String name) {
        super.setComName(name);
    }

    @Override
    public void setData(String uri, String name, String qName, Attributes atts) {
    }

}
