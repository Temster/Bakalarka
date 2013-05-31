/*
 * Created on 6.10.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006;

public class PortName implements Comparable<PortName>{
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PortName){
            PortName p = (PortName)obj;
            return name.equals(p.getName()) && fileName.equals(p.getFileName()); 
        }
        return super.equals(obj);
    }

    protected Long id;
    protected String name;
    protected String fileName;
    protected int portIndex;
    
    public PortName() {
    	
    }
    
    public PortName(String name) {
        this(name, name);
    }    
    public PortName(String name, String fileName) {
        super();
        this.name = name;
        this.fileName = fileName;
        StringBuilder sb = new StringBuilder(name);
        sb.delete(0, 3);
        try {
			portIndex = Integer.parseInt(sb.toString());
		} catch (NumberFormatException e) {
		}
    }
    
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
    	this.fileName = fileName;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
    	this.name = name;
    }
    
    public int getPortIndex() {
    	return portIndex; 
    }
    public void setPortIndex(int portIndex) {
    	this.portIndex = portIndex;
    }
    
    public String toString(){
        return name;
    }
	public int compareTo(PortName p) {
		return portIndex - p.portIndex;
	}
}
