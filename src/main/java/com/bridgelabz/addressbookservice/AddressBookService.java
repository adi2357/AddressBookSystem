package com.bridgelabz.addressbookservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bridgelabz.exception.DBException;
import com.bridgelabz.ioservice.AddressBookDBIOService;
import com.bridgelabz.model.Contacts;


public class AddressBookService {

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	public static final Scanner SC = new Scanner(System.in);
	private List<Contacts> contactDataList;
	private AddressBookDBIOService addressBookDBService;

	public AddressBookService() {
		this.contactDataList = new ArrayList<Contacts>();
		addressBookDBService = AddressBookDBIOService.getInstatnce();
	}

	public AddressBookService(List<Contacts> employeeList) {
		this();
		this.contactDataList = employeeList;
	}

	public int sizeOfContactList() {
		return this.contactDataList.size();
	}

	public void readContactData(IOService ioType) {
		if (ioType.equals(IOService.DB_IO)) {
			try {
				this.contactDataList = addressBookDBService.readData();
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateContactEmail(String firstName, String lastName, String email) {
		int result = addressBookDBService.updateContactEmail(firstName, lastName, email);
		
	}

	public boolean checkContactDataInSyncWithDB(String firstName, String lastName) {
		return false;
	}
}
