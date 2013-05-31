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
import java.util.Observer;

public class Organization extends Observable implements Observer{

	protected Long id;
	protected boolean isCustomer = false;
    protected String name = "";
    protected Address address = new Address();
    protected String tel = "";
    
    public Organization() {
        this("Název",new Address(),"Telefon nebo fax");
    }

    public Organization(String name, Address address, String tel) {
        super();
        this.name = name;
        this.address = address;
        this.tel = tel;
        address.addObserver(this);
    }
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public boolean getIsCustomer() {
    	return isCustomer;
    }
    public void setIsCustomer(boolean isCustomer) {
    	this.isCustomer = isCustomer;
    }
    
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        if(this.address != address){
            if(this.address != null)
                this.address.deleteObserver(this);
            this.address = address;
            address.addObserver(this);
            setChanged();
            notifyObservers();
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if(!this.name.equals(name)){
            this.name = name;
            setChanged();
            notifyObservers();
        }
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        if(!this.tel.equals(tel)){
            this.tel = tel;
            setChanged();
            notifyObservers();
        }
    }

    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }

	public void updateFrom(Organization o) {
		address.updateFrom(o.getAddress());
		name = o.getName();
		tel = o.getTel();
		
	}
}
