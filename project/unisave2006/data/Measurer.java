package unisave2006.data;

import java.util.Observable;
import java.util.Date;

public class Measurer extends Observable {

	private Long id;
	private String identification;
	private String title;
	private String type;
	private Date calibrationDate;
	private boolean isDeleted = false;
	
	public Measurer() {
		
	}
	
	public Measurer(String identification, String title, String type, Date calibrationDate) {
		this.identification = identification;
		this.title = title;
		this.type = type;
		this.calibrationDate = calibrationDate; 
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
		setChanged();
        notifyObservers();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
		setChanged();
        notifyObservers();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		setChanged();
        notifyObservers();
	}
	
	public Date getcalibrationDate() {
		return calibrationDate;
	}
	public void setCalibrationDate(Date calibrationDate) {
		this.calibrationDate = calibrationDate;
		setChanged();
        notifyObservers();
	}
	
	public boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public void updateFrom(Measurer m) {
		identification = m.getIdentification();
		title = m.getTitle();
		type = m.getType();
		calibrationDate = m.getcalibrationDate();
	}
	
}
