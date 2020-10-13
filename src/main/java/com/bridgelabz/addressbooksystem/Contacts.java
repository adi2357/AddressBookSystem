package com.bridgelabz.addressbooksystem;

import com.opencsv.bean.CsvBindByName;

public class Contacts {

	@CsvBindByName(column = "First Name")
	private String firstName;
	
	@CsvBindByName(column = "Last Name")
	private String lastName;
	
	@CsvBindByName(column = "Address")
	private String address;
	
	@CsvBindByName(column = "City")
	private String city;
	
	@CsvBindByName(column = "State")
	private String state;
	
	@CsvBindByName(column = "ZIP")
	private String zip;
	
	@CsvBindByName(column = "Phone Number")
	private String phoneNumber;
	
	@CsvBindByName(column = "Email")
	private String email;
	

	public Contacts() {		
	}

	public Contacts(String firstName, String lastName, String address, String city, String state, String zip,
			String phoneNumber, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return (firstName + " " + lastName);
	}

	@Override
	public String toString() {
		return ("Name : " + firstName + " " + lastName + "\t" + "Address : " + address + "\t" + "City : " + city + "\t"
				+ "State : " + state + "\t" + "Zip : " + zip + "\t" + "Phone Number : " + phoneNumber + "\t"
				+ "Email : " + email);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Contacts))
			return false;
		Contacts person = (Contacts) o;
		return firstName.equals(person.firstName) && lastName.equals(person.lastName);
	}

}
