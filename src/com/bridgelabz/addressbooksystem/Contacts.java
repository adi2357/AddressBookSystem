package com.bridgelabz.addressbooksystem;

public class Contacts {
	public String firstName;
	public String lastName;
	public String address;
	public String city;
	public String state;
	public int zip;
	public String phoneNumber;
	public String email;
	
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
	
	@Override
    public String toString() {
        return ("Name : "+firstName+" "+lastName+"\t"+"Address : "+ address+"\t"+"City : "+ city+"\t"+"State : "+state+"\t"+"Zip : "+zip+"\t"+"Phone Number : "+phoneNumber+"\t"+
        		"Email : "+ email);
    }
	
	
	

}
