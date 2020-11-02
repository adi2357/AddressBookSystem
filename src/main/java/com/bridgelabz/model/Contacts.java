package com.bridgelabz.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
	private int zip;
	
	@CsvBindByName(column = "Phone Number")
	private String phoneNumber;
	
	@CsvBindByName(column = "Email")
	private String email;
	
	private List<String> phoneList;
	
	private String addressBookType;
	
	private String addressBookName;
	
	private Map<String,String> addressBooks;
	
	public Contacts() {
	}

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

	

	public Contacts(String firstName, String lastName, String address, String city, String state, int zip, String email,
			List<String> phoneList, Map<String, String> addressBooks) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.email = email;
		this.phoneList = phoneList;
		this.addressBooks = addressBooks;
	}

	public Contacts(String firstName, String lastName, String address, String city, String state, int zip, String email,
			String phoneNumber, String addressBookType, String addressBookName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.email = email;
		if(this.phoneList != null) this.phoneList.add(0,phoneNumber);
		else {
			this.phoneList = new ArrayList<>();
			this.phoneList.add(phoneNumber);
		}
		this.addressBookType = addressBookType;
		this.addressBookName = addressBookName;
		if(this.addressBooks != null) this.addressBooks.put(this.addressBookType, this.addressBookName);
		else {
			this.addressBooks = new HashMap<>();
			this.addressBooks.put(this.addressBookType, this.addressBookName);
		}
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

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
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

	public List<String> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<String> phoneList) {
		this.phoneList = phoneList;
	}

	public Map<String, String> getAddressBooks() {
		return addressBooks;
	}

	public void setAddressBooks(Map<String, String> addressBooks) {
		this.addressBooks = addressBooks;
	}
	

	public String getAddressBookType() {
		return addressBookType;
	}

	public void setAddressBookType(String addressBookType) {
		this.addressBookType = addressBookType;
	}

	public String getAddressBookName() {
		return addressBookName;
	}

	public void setAddressBookName(String addressBookName) {
		this.addressBookName = addressBookName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, address, city, state, zip, email, phoneList, addressBookName, addressBookType);		
	}

	@Override
	public String toString() {
		return ("Name : " + firstName + " " + lastName + "\t" + "Address : " + address + "\t" + "City : " + city + "\t"
				+ "State : " + state + "\t" + "Zip : " + zip + "\t" + "Phone Number : " + phoneNumber + "\t"
				+ "Email : " + email);
	}

	public String printString() {
		return "Contacts [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", city=" + city
				+ ", state=" + state + ", zip=" + zip + ", email=" + email
				+ ", phoneList=" + phoneList + ", addressBooks=" + addressBooks + "]";
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((address == null) ? 0 : address.hashCode());
//		result = prime * result + ((addressBookName == null) ? 0 : addressBookName.hashCode());
//		result = prime * result + ((city == null) ? 0 : city.hashCode());
//		result = prime * result + ((email == null) ? 0 : email.hashCode());
//		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
//		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
//		result = prime * result + ((phoneList == null) ? 0 : phoneList.hashCode());
//		result = prime * result + ((state == null) ? 0 : state.hashCode());
//		result = prime * result + zip;
//		return result;
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacts other = (Contacts) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (addressBookName == null) {
			if (other.addressBookName != null)
				return false;
		} else if (!addressBookName.equals(other.addressBookName))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phoneList == null) {
			if (other.phoneList != null)
				return false;
		} else if (!phoneList.equals(other.phoneList))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zip != other.zip)
			return false;
		return true;
	}
}
