package unisave2006.data;

import java.util.Observable;


public class Employee extends Observable {

	private Long id;
	private String firstName;
	private String lastName;
	private String position; 
	private String department;
	private boolean isDeleted = false;
	
	public Employee() {
		this("Jméno", "Příjmení", "Zařazení", "Oddělení");
	}
	
	public Employee(String firstName, String lastName, String position, String department) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.department = department;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		setChanged();
        notifyObservers();
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
		setChanged();
        notifyObservers();
	}
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
		setChanged();
        notifyObservers();
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
		setChanged();
        notifyObservers();
	}
	
	public void updateFrom(Employee e) {
		firstName = e.getFirstName();
		lastName = e.getLastName();
		position = e.getPosition();
		department = e.getDepartment();
	}
	
}
