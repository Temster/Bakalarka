/*
 * Created on 6.10.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import unisave2006.data.StructuredXMLLoader;

public class UserSettingXMLLoader extends StructuredXMLLoader{

    UserSetting u = null;
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // TODO Auto-generated method stub
        super.endElement(uri, localName, qName);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("unisave_user_setting")){
            if(1 == Integer.parseInt(attributes.getValue("file_version"))){
                u = UserSetting.initialize();
                pushHandler(u.getSAXHandler(this));
            }
        }
    }

    public UserSetting getUserSetting() {
        return u;
    }

}
