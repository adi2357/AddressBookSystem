package com.bridgelabz.addressbooksystem;

import java.util.TreeMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;

public class AddressBookMain {

	private Map<String, Contacts> addressBook = new TreeMap<String, Contacts>();

	public void addContactToAddressBook(Contacts contact) {
		addressBook.put(contact.getFullName(), contact);
	}

	public void displayAddressBook() {
		for(Map.Entry<String, Contacts> contact : addressBook.entrySet()) {
			System.out.println(contact.getValue());			
		}
		
	}

	public Contacts createContact() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter First Name:");
		String firstName = sc.next();
		System.out.println("Enter Last Name:");
		String lastName = sc.next();
		System.out.println("Enter address:");
		String address = sc.next();
		System.out.println("Enter city:");
		String city = sc.next();
		System.out.println("Enter state:");
		String state = sc.next();
		System.out.println("Enter zip:");
		int zip = sc.nextInt();
		System.out.println("Enter phone No.:");
		String phoneNumber = sc.next();
		System.out.println("Enter email address:");
		String email = sc.next();
		Contacts newContact = new Contacts(firstName, lastName, address, city, state, zip, phoneNumber, email);
		sc.close();
		return newContact;
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		AddressBookMain addressBookObject = new AddressBookMain();
		System.out.println("Create and Add Contact : ");
		String choice = sc.next();
		switch (choice) {
		case "Y":
			Contacts newContact = addressBookObject.createContact();
			addressBookObject.addContactToAddressBook(newContact);
			break;
		case "N":
			System.out.println("Aborting Operation");
			break;
		default:
			System.out.println("Invalid Choice");
		}
		addressBookObject.displayAddressBook();

		sc.close();
	}

}
