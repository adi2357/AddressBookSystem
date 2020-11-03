package com.bridgelabz.model;

import java.util.ArrayList;
import java.util.List;
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
	
	private List<String> addressBookTypeList;
	
	private List<String> addressBookNameList;
	
	private int id;
	
	public Contacts() {
	}

	public Contacts(String firstName, String lastName, String address, String city, String state, int zip,
			String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.email = email;
	}

	public Contacts(String firstName, String lastName, String address, String city, String state, int zip,
			String phoneNumber, String email) {
		this(firstName, lastName, address, city, state, zip, email);
		this.phoneNumber = phoneNumber;
		
	}

	public Contacts(String firstName, String lastName, String address, String city, String state, int zip, String email,
			List<String> phoneList, List<String> addressBookTypeList, List<String> addressBookNameList) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.email = email;
		this.phoneList = phoneList;
		this.addressBookTypeList = addressBookTypeList;
		this.addressBookNameList = addressBookNameList;
	}

	public Contacts(int id, String firstName, String lastName, String address, String city, String state, int zip, String email,
			String phoneNumber, String addressBookType, String addressBookName) {
		
		this(firstName, lastName, address, city, state, zip, email);
		this.id = id;
		if(this.phoneList != null) this.phoneList.add(0,phoneNumber);
		else {
			this.phoneList = new ArrayList<>();
			this.phoneList.add(phoneNumber);
		}
		if(this.addressBookTypeList != null) this.addressBookTypeList.add(0,addressBookType);
		else {
			this.addressBookTypeList = new ArrayList<>();
			this.addressBookTypeList.add(addressBookType);
		}
		if(this.addressBookNameList != null) this.addressBookNameList.add(0,addressBookName);
		else {
			this.addressBookNameList = new ArrayList<>();
			this.addressBookNameList.add(addressBookName);
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

	public List<String> getAddressBookTypeList() {
		return addressBookTypeList;
	}

	public void setAddressBookTypeList(List<String> addressBookTypeList) {
		this.addressBookTypeList = addressBookTypeList;
	}

	public List<String> getAddressBookNameList() {
		return addressBookNameList;
	}

	public void setAddressBookNameList(List<String> addressBookNameList) {
		this.addressBookNameList = addressBookNameList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, address, city, state, zip, email, phoneList, addressBookNameList, addressBookTypeList);		
	}

	@Override
	public String toString() {
		return ("Name : " + firstName + " " + lastName + "\t" + "Address : " + address + "\t" + "City : " + city + "\t"
				+ "State : " + state + "\t" + "Zip : " + zip + "\t" + "Phone Number : " + phoneNumber + "\t"
				+ "Email : " + email);
	}

	public String printString() {
		return "Contacts [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", city=" + city
				+ ", state=" + state + ", zip=" + zip + ", email=" + email + ", phoneList=" + phoneList
				+ ", addressBookTypeList=" + addressBookTypeList + ", addressBookNameList=" + addressBookNameList + ", id=" + id + "]";
	}

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
		if (addressBookNameList == null) {
			if (other.addressBookNameList != null)
				return false;
		} else if (!addressBookNameList.equals(other.addressBookNameList))
			return false;
		if (addressBookTypeList == null) {
			if (other.addressBookTypeList != null)
				return false;
		} else if (!addressBookTypeList.equals(other.addressBookTypeList))
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
