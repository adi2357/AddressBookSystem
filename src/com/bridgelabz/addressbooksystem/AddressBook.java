package com.bridgelabz.addressbooksystem;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
	private ArrayList<Contacts> addressBook = new ArrayList<Contacts>();

	public void addContactToAddressBook(Contacts contact) {
		for(Contacts person : addressBook) {
			if(contact.equals(person)) {
				System.out.println("Duplicate Contact! Enter a new Contact or Edit exixting Contact");
				return;
			}
		}
		addressBook.add(contact);
	}

	public void displayAddressBook() {
		for (Contacts contact : addressBook) {
			System.out.println(contact);
		}
	}

	public void editContact(String fullName) {
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		for (Contacts contact : addressBook) {
			if (fullName.toUpperCase().equals((contact.getFullName()).toUpperCase())) {
				System.out.println("Choose What to EDIT : ");
				System.out.println("1. Address");
				System.out.println("2. City");
				System.out.println("3. State");
				System.out.println("4. Zip");
				System.out.println("5. Phone Number");
				System.out.println("6. Email");
				int choice = sc.nextInt();
				switch (choice) {
				case 1:
					System.out.println("Enter new address:");
					sc.nextLine();
					contact.setAddress(sc.nextLine());
					break;
				case 2:
					System.out.println("Enter new city:");
					contact.setCity(sc.next());
					break;
				case 3:
					System.out.println("Enter new state:");
					sc.nextLine();
					contact.setState(sc.nextLine());
					break;
				case 4:
					System.out.println("Enter new zip:");
					contact.setZip(sc.nextInt());
					break;
				case 5:
					System.out.println("Enter new phone number:");
					contact.setPhoneNumber(sc.next());
					break;
				case 6:
					System.out.println("Enter new email:");
					contact.setEmail(sc.next());
					break;
				default:
					System.out.println("INVALID choice");
				}
				flag = false;
				break;
			}
		}
		if (flag)
			System.out.println("Contact doesn't Exist");

	}

	public void deleteContact(String name) {
		boolean flag = true;
		for (Contacts contact : addressBook) {
			if (name.toUpperCase().equals((contact.getFullName()).toUpperCase())) {
				addressBook.remove(contact);
				flag = false;
			}
		}
		if (flag)
			System.out.println("Contact doesn't Exist");
		else
			System.out.println("Contact Deleted");
	}

	public Contacts createContact() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter First Name:");
		String firstName = sc.next();
		System.out.println("Enter Last Name:");
		String lastName = sc.next();
		System.out.println("Enter address:");
		sc.nextLine();
		String address = sc.nextLine();
		System.out.println("Enter city:");
		String city = sc.next();
		System.out.println("Enter state:");
		sc.nextLine();
		String state = sc.nextLine();
		System.out.println("Enter zip:");
		int zip = sc.nextInt();
		System.out.println("Enter phone No.:");
		String phoneNumber = sc.next();
		System.out.println("Enter email address:");
		String email = sc.next();
		return (new Contacts(firstName, lastName, address, city, state, zip, phoneNumber, email));		
	}

	public void addressBookOperations() {
		
		
		Scanner sc = new Scanner(System.in);
		Contacts defaultContact = new Contacts("Aditya", "Verma", "3/40 LDA Colony", "Lucknow", "UP", 224045,
				"8889036440", "addressbook@capgemini.com");
		addContactToAddressBook(defaultContact);
		boolean operate = true;
		while (operate) {
			System.out.println("1. Create and Add Contact");
			System.out.println("2. Edit Contact");
			System.out.println("3. Delete Contact");
			System.out.println("4. Display Address Book");
			System.out.println("5. Exit");
			System.out.println("Enter your choice : ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				addContactToAddressBook(createContact());
				break;
			case 2:
				System.out.println("Enter the Full Name : ");
				sc.nextLine();
				String fullName = sc.nextLine();
				editContact(fullName);
				break;
			case 3:
				System.out.println("Enter the Full Name : ");
				sc.nextLine();
				String name = sc.nextLine();
				deleteContact(name);
				break;
			case 4:
				displayAddressBook();
				break;
			case 5:
				operate = false;
				break;
			default:
				System.out.println("Invalid Choice");
			}
		}
	}

}
