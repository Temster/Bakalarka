/*
 * Created on 9.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.util.Observable;

public class Address extends Observable {

	protected Long id;
    protected String street = null;
    protected String city = null;
    protected String psc = null;
    
    
    public Address() {
        this("Ulice","Město","PSČ");
    }

    public Address(String street, String city, String psc) {
        super();
        this.street = street;
        this.city = city;
        this.psc = psc;
    }
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        if(!this.city.equals(city)){
            this.city = city;
            setChanged();
            notifyObservers();
        }
    }
    public String getPsc() {
        return psc;
    }
    public void setPsc(String psc) {
        if(!this.psc.equals(psc)){
            this.psc = psc;
            setChanged();
            notifyObservers();
        }
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        if(!this.street.equals(street)){
            this.street = street;
            setChanged();
            notifyObservers();
        }
    }

	public void updateFrom(Address a) {
		city = a.getCity();
		psc = a.getPsc();
		street = a.getStreet();
		
	}
}
