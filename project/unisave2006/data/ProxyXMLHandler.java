/*
 * Created on 30.9.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class ProxyXMLHandler extends DefaultHandler {

    protected DefaultHandler target = null;
    
    public void setTarget(DefaultHandler t){
        target = t;
    }

    @Override
    public void endDocument() throws SAXException {
        target.endDocument();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        target.endElement(uri, localName, qName);
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        target.endPrefixMapping(prefix);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        target.error(e);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        target.fatalError(e);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        target.characters(ch, start, length);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        target.ignorableWhitespace(ch, start, length);
    }

    @Override
    public void notationDecl(String name, String publicId, String systemId) throws SAXException {
        target.notationDecl(name, publicId, systemId);
    }


    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        this.target.processingInstruction(target, data);
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
        return target.resolveEntity(publicId, systemId);
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        target.setDocumentLocator(locator);
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        target.skippedEntity(name);
    }

    @Override
    public void startDocument() throws SAXException {
        target.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        target.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        target.startPrefixMapping(prefix, uri);
    }

    @Override
    public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
        target.unparsedEntityDecl(name, publicId, systemId, notationName);
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        target.warning(e);
    }
}
