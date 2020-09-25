package com.bridgelabz.addressbooksystem;

import java.util.TreeMap;
import java.util.Dictionary;
import java.util.Map;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.Scanner;

public class AddressBookMain {

	private Map<String, Contacts> addressBook = new TreeMap<String, Contacts>();
	
	public void addContactToAddressBook(Contacts contact) {
		addressBook.put(contact.getFullName(), contact);
	}
	
	public void displayAddressBook() {
		for (Map.Entry<String, Contacts> contact : addressBook.entrySet()) {
			System.out.println(contact.getValue());
		}
	}
	
	public static void addressBookOperations(AddressBookMain addressBookObject) {
		Scanner sc=new Scanner(System.in);
		Contacts defaultContact = new Contacts("Aditya", "Verma", "3/40 LDA Colony", "Lucknow", "UP", 224045,
				"8889036440", "addressbook@capgemini.com");
		addressBookObject.addContactToAddressBook(defaultContact);
		boolean operation = true;
		while (operation) {
			System.out.println("1. Create and Add Contact");
			System.out.println("2. Edit Contact");
			System.out.println("3. Delete Contact");
			System.out.println("4. Exit");
			System.out.println("Enter your choice : ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				Contacts newContact = addressBookObject.createContact();
				addressBookObject.addContactToAddressBook(newContact);
				break;
			case 2:
				System.out.println("Enter the Full Name : ");
				sc.nextLine();
				String fullName = sc.nextLine();
				addressBookObject.editContact(fullName);
				break;
			case 3:
				System.out.println("Enter the Full Name : ");
				sc.nextLine();
				String name = sc.nextLine();
				addressBookObject.deleteContact(name);
				break;
			case 4:
				operation=false;
				break;
			default:
				System.out.println("Invalid Choice");
			}
		}
		System.out.println("ADDRESS BOOK : ");
		addressBookObject.displayAddressBook();
	}

	public void editContact(String fullName) {
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		for (Map.Entry<String, Contacts> contact : addressBook.entrySet()) {
			if (fullName.toUpperCase().equals((contact.getKey()).toUpperCase())) {
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
					contact.getValue().setAddress(sc.next());
					break;
				case 2:
					contact.getValue().setCity(sc.next());
					break;
				case 3:
					contact.getValue().setState(sc.next());
					break;
				case 4:
					contact.getValue().setZip(sc.nextInt());
					break;
				case 5:
					contact.getValue().setPhoneNumber(sc.next());
					break;
				case 6:
					contact.getValue().setEmail(sc.next());
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
		for (Map.Entry<String, Contacts> contact : addressBook.entrySet()) {
			if (name.toUpperCase().equals((contact.getKey()).toUpperCase())) {
				addressBook.remove(contact.getKey());
				flag = false;
			}
			if (flag)
				System.out.println("Contact doesn't Exist");
			else
				System.out.println("Contact Deleted");
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
		
		return newContact;
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		
		Dictionary<String, AddressBookMain> addressBookDictionary=new Hashtable<String, AddressBookMain>();
		boolean operation = true;
		while (operation) {
			System.out.println("1. Create and Add AddressBook");
			System.out.println("2. Delete AddressBook");
			System.out.println("3. Exit");
			System.out.println("Enter your choice : ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				AddressBookMain addressBookObject = new AddressBookMain();
				sc.nextLine();
				System.out.println("Enter name of Address Book: ");
				String addressBookName = sc.nextLine();
				addressBookDictionary.put(addressBookName,addressBookObject);
				addressBookOperations(addressBookObject);
				break;
			case 2:
				sc.nextLine();
				System.out.println("Enter name of Address Book: ");
				addressBookDictionary.remove(sc.nextLine());
				System.out.println("Address Book Deleted");

				break;
			case 3:
				operation=false;
				break;
			default:
				System.out.println("Invalid Choice");
			}
		}
	}

}
