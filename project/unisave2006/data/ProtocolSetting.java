/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import unisave2006.XMLSupport;

/**
 * @author David Je�ek
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_dR7r8BDZEdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class ProtocolSetting extends Observable implements Observer{

	protected Long id;
    protected Organization laboratory = new Organization();
    protected String measurementTitle = "";
    protected Organization customer = new Organization();
    protected String objectOfMeasurement = "";
    protected Date dateOfMeasurement = new Date();
    protected Employee responsiblePerson = new Employee();
    protected Measurer measurer = new Measurer();

    public ProtocolSetting(){
        laboratory.addObserver(this);
        customer.addObserver(this);
        responsiblePerson.addObserver(this);
        measurer.addObserver(this);
    }
    public void storeToXML(BufferedWriter w, int offset) throws IOException {
        XMLSupport.write("<protocolSetting>", w, offset);
        offset++;
        XMLSupport.write("<customer>", w, offset);
        offset++;
        XMLSupport.write("<name value=\""+ XMLSupport.escape(customer.getName()) +"\"/>", w, offset);
        XMLSupport.write("<address>", w, offset);
        offset++;
        XMLSupport.write("<city value=\""+ XMLSupport.escape(customer.getAddress().getCity()) +"\"/>", w, offset);
        XMLSupport.write("<street value=\""+ XMLSupport.escape(customer.getAddress().getStreet()) +"\"/>", w, offset);
        XMLSupport.write("<psc value=\""+ XMLSupport.escape(customer.getAddress().getPsc()) +"\"/>", w, offset);
        offset--;
        XMLSupport.write("</address>", w, offset);
        XMLSupport.write("<tel value=\""+ XMLSupport.escape(customer.getTel()) +"\"/>", w, offset);
        offset--;
        XMLSupport.write("</customer>", w, offset);
        XMLSupport.write("<dateOfMeasurement value=\""+ DateFormat.getInstance().format(dateOfMeasurement) +"\"/>", w, offset);
        XMLSupport.write("<laboratory>", w, offset);
        offset++;
        XMLSupport.write("<name value=\""+ XMLSupport.escape(laboratory.getName()) +"\"/>", w, offset);
        XMLSupport.write("<address>", w, offset);
        offset++;
        XMLSupport.write("<city value=\""+ XMLSupport.escape(laboratory.getAddress().getCity()) +"\"/>", w, offset);
        XMLSupport.write("<street value=\""+ XMLSupport.escape(laboratory.getAddress().getStreet()) +"\"/>", w, offset);
        XMLSupport.write("<psc value=\""+ XMLSupport.escape(laboratory.getAddress().getPsc()) +"\"/>", w, offset);
        offset--;
        XMLSupport.write("</address>", w, offset);
        XMLSupport.write("<tel value=\""+ XMLSupport.escape(laboratory.getTel()) +"\"/>", w, offset);
        offset--;
        XMLSupport.write("</laboratory>", w, offset);
        XMLSupport.write("<measurementTitle value=\""+ XMLSupport.escape(measurementTitle) +"\"/>", w, offset);
        //XMLSupport.write("<nameOfMesurmentDevice value=\""+ measurer.getTitle() +"\"/>", w, offset);
        //XMLSupport.write("<nameOfMesurmentDevice value=\""+ XMLSupport.escape(nameOfMesurmentDevice) +"\"/>", w, offset);
        //XMLSupport.write("<nameOfResponsiblePerson value=\""+ XMLSupport.escape(nameOfResponsiblePerson) +"\"/>", w, offset);
        XMLSupport.write("<objectOfMeasurement value=\""+ XMLSupport.escape(objectOfMeasurement) +"\"/>", w, offset);
        offset--;
        XMLSupport.write("</protocolSetting>", w, offset);
    }
    public DefaultHandler getSAXHandler(StructuredXMLLoader loader){
        return new ProtocolSettingSAXHandler(loader);
    }
    class ProtocolSettingSAXHandler extends DefaultHandler{
        protected StructuredXMLLoader loader = null;
        protected boolean customer = false;
        protected boolean laboratory = false;
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equals("protocolSetting"))
                loader.popHandler();
            else if(qName.equals("customer")){
                customer = false;
            }
            else if(qName.equals("laboratory")){
                laboratory = false;
            }
            else
                super.endElement(uri, localName, qName);
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("customer")) {
                customer = true;
            } else if (qName.equals("laboratory")) {
                laboratory = true;
            } else if (customer && qName.equals("name")) {
                ProtocolSetting.this.customer.name = attributes
                        .getValue("value");
            } else if (customer && qName.equals("city")) {
                ProtocolSetting.this.customer.address.city = attributes
                        .getValue("value");
            } else if (customer && qName.equals("street")) {
                ProtocolSetting.this.customer.address.street = attributes
                        .getValue("value");
            } else if (customer && qName.equals("psc")) {
                ProtocolSetting.this.customer.address.psc = attributes
                        .getValue("value");
            } else if (customer && qName.equals("tel")) {
                ProtocolSetting.this.customer.tel = attributes
                        .getValue("value");
            } else if (laboratory && qName.equals("name")) {
                ProtocolSetting.this.laboratory.name = attributes
                        .getValue("value");
            } else if (laboratory && qName.equals("city")) {
                ProtocolSetting.this.laboratory.address.city = attributes
                        .getValue("value");
            } else if (laboratory && qName.equals("street")) {
                ProtocolSetting.this.laboratory.address.street = attributes
                        .getValue("value");
            } else if (laboratory && qName.equals("psc")) {
                ProtocolSetting.this.laboratory.address.psc = attributes
                        .getValue("value");
            } else if (laboratory && qName.equals("tel")) {
                ProtocolSetting.this.laboratory.tel = attributes
                        .getValue("value");
            } else if (qName.equals("dateOfMeasurement")) {
                try {
                    dateOfMeasurement = DateFormat.getInstance().parse(
                            attributes.getValue("value"));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (qName.equals("measurementTitle")) {
                measurementTitle = attributes.getValue("value");
            } else if (qName.equals("nameOfMesurmentDevice")) {
                //nameOfMesurmentDevice = attributes.getValue("value");
            } else if (qName.equals("nameOfResponsiblePerson")) {
                //nameOfResponsiblePerson = attributes.getValue("value");
            } else if (qName.equals("objectOfMeasurement")) {
                objectOfMeasurement = attributes.getValue("value");
            }
        }

        public ProtocolSettingSAXHandler(StructuredXMLLoader loader){
            this.loader = loader;
        }
    }
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public Organization getCustomer() {
        return customer;
    }
    public void setCustomer(Organization customer) {
        if(this.customer != customer){
            if(this.customer != null)
                this.customer.deleteObserver(this);
            this.customer = customer;
            customer.addObserver(this);
            setChanged();
            notifyObservers();
        }
    }
    
    public Employee getResponsiblePerson() {
    	return responsiblePerson;
    }
    public void setResponsiblePerson(Employee responsiblePerson) {
    	if(this.responsiblePerson != responsiblePerson) {
    		this.responsiblePerson = responsiblePerson;
    		responsiblePerson.addObserver(this);
    		setChanged();
            notifyObservers();
    	}
    }
    
    public Date getDateOfMeasurement() {
        return dateOfMeasurement;
    }
    public void setDateOfMeasurement(Date dateOfMeasurement) {
        if(this.dateOfMeasurement != dateOfMeasurement){
            if(this.dateOfMeasurement != null && this.dateOfMeasurement.equals(dateOfMeasurement))
                return;
            this.dateOfMeasurement = dateOfMeasurement;
            setChanged();
            notifyObservers();
        }
    }
    public Organization getLaboratory() {
        return laboratory;
    }
    public void setLaboratory(Organization laboratory) {
        if(this.laboratory != laboratory){
            if(this.laboratory != null)
                this.laboratory.deleteObserver(this);
            this.laboratory = laboratory;
            laboratory.addObserver(this);
            
            setChanged();
            notifyObservers();
        }
    }
    public String getMeasurementTitle() {
        return measurementTitle;
    }
    public void setMeasurementTitle(String measurementTitle) {
        if(!this.measurementTitle.equals(measurementTitle)){
            this.measurementTitle = measurementTitle;
            setChanged();
            notifyObservers();
        }
    }
    public Measurer getMeasurer() {
        return measurer;
    }
    public void setMeasurer(Measurer measurer) {
        if(this.measurer != measurer){
            this.measurer = measurer;
            measurer.addObserver(this);
            setChanged();
            notifyObservers();
        }
    }
    
    public String getObjectOfMeasurement() {
        return objectOfMeasurement;
    }
    public void setObjectOfMeasurement(String objectOfMeasurement) {
        if(!this.objectOfMeasurement.equals(objectOfMeasurement)){
            this.objectOfMeasurement = objectOfMeasurement;
            setChanged();
            notifyObservers();
        }
    }
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }
	public void updateFrom(ProtocolSetting protocolSetting) {
		customer.updateFrom(protocolSetting.getCustomer());
		laboratory.updateFrom(protocolSetting.getLaboratory());
		dateOfMeasurement = new Date(protocolSetting.getDateOfMeasurement().getTime());
		measurementTitle = protocolSetting.getMeasurementTitle();
		measurer = protocolSetting.getMeasurer();
		responsiblePerson = protocolSetting.getResponsiblePerson();
		objectOfMeasurement = protocolSetting.getObjectOfMeasurement();
	}
}
