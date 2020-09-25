package com.bridgelabz.addressbooksystem;

public class Contacts {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private int zip;
	private String phoneNumber;
	private String email;
	
	public Contacts(String firstName, String lastName, String address, String city, String state, int zip,
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
	
	public String getFullName() {
		return (firstName+" "+lastName);
	}

	
	@Override
    public String toString() {
        return ("Name : "+firstName+" "+lastName+"\t"+"Address : "+ address+"\t"+"City : "+ city+"\t"+"State : "+state+"\t"+"Zip : "+zip+"\t"+"Phone Number : "+phoneNumber+"\t"+
        		"Email : "+ email);
    }
	
	
	

}
