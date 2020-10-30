package com.bridgelabz.model;

public class AddressBookData {

	private String addressBookName;
	private String type;

	public AddressBookData() {
	}

	public AddressBookData(String addressBookName, String type) {
		this.addressBookName = addressBookName;
		this.type = type;
	}

	public String getAddressBookName() {
		return addressBookName;
	}

	public void setAddressBookName(String addressBookName) {
		this.addressBookName = addressBookName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
