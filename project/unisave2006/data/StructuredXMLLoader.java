/*
 * Created on 6.10.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Deque;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class StructuredXMLLoader extends DefaultHandler {

    protected Deque<DefaultHandler> handlers = new ArrayDeque<DefaultHandler>();
    protected XMLReader xr = null;
    protected InputSource source = null;
    protected ProxyXMLHandler p = new ProxyXMLHandler(); 

    public StructuredXMLLoader() {
        super();
    }

    public void setSource(Reader r) {
        source = new InputSource(r);
    }

    public void load() throws IOException {
        if(source == null)
            return;
        xr = null;
        try {
            xr = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        xr.setContentHandler(p);
        xr.setErrorHandler(p);
        pushHandler(this);
        try {
            xr.parse(source);
        } catch (SAXException e) {
            throw new MeasurementFormatException(String.format("Chyba ve form�tu XML.\nSyst�m ohl�sil:\n%s", e.getMessage()));  
        }
    }

    public void pushHandler(DefaultHandler d) {
        handlers.push(d);
        p.setTarget(d);
    }

    public void popHandler() {
        if(handlers.size() <= 1){
            throw new MeasurementFormatException("Hyba form�tu souboru.");
        }
        DefaultHandler d = handlers.pop();
        d = handlers.peek();
        p.setTarget(d);
    }

}