package com.bridgelabz.addressbooksystem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBook {
	public static final String ADRESS_BOOK_FILES = "C:\\Users\\aaada\\Dev\\eclipse-workspace\\AddressBookSystem\\AddressBookFiles";
	public static final String CSV_FILES = "C:\\Users\\aaada\\Dev\\eclipse-workspace\\AddressBookSystem\\CSVFiles";
	public static final String JSON_FILES = "C:\\Users\\aaada\\Dev\\eclipse-workspace\\AddressBookSystem\\JSONFiles";
	public static final Scanner SCANNER = new Scanner(System.in);
	private List<Contacts> addressBook;
	private HashSet<String> contactsByCity;
	private HashSet<String> contactsByState;
	private ArrayList<String> city;
	private ArrayList<String> state;

	public enum IOservice {
		CONSOLE_IO, FILE_IO, CSV_IO, JSON_IO
	}

	public AddressBook() {
		this.addressBook = new ArrayList<Contacts>();
		this.contactsByCity = new HashSet<String>();
		this.contactsByState = new HashSet<String>();
		this.city = new ArrayList<String>();
		this.state = new ArrayList<String>();
	}

	public AddressBook(List<Contacts> addressBook) {
		this.addressBook = addressBook;
	}

	public void addContactToAddressBook(Contacts contact) {
		Predicate<Contacts> isPresent = n -> {
			return n.equals(contact);
		};
		if (!addressBook.stream().anyMatch(isPresent))
			addressBook.add(contact);
		else
			System.out.println("Duplicate Contact! Enter a new Contact or Edit exixting Contact");
	}

	public void displayAddressBook() {
		addressBook.stream().forEach(n -> System.out.println(n.toString()));

	}

	public void editContact(String fullName) {
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
				int choice = SCANNER.nextInt();
				switch (choice) {
				case 1:
					System.out.println("Enter new address:");
					SCANNER.nextLine();
					contact.setAddress(SCANNER.nextLine());
					break;
				case 2:
					System.out.println("Enter new city:");
					contact.setCity(SCANNER.next());
					break;
				case 3:
					System.out.println("Enter new state:");
					SCANNER.nextLine();
					contact.setState(SCANNER.nextLine());
					break;
				case 4:
					System.out.println("Enter new zip:");
					contact.setZip(SCANNER.next());
					break;
				case 5:
					System.out.println("Enter new phone number:");
					contact.setPhoneNumber(SCANNER.next());
					break;
				case 6:
					System.out.println("Enter new email:");
					contact.setEmail(SCANNER.next());
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
		System.out.println("Enter First Name:");
		String firstName = SCANNER.next();
		System.out.println("Enter Last Name:");
		String lastName = SCANNER.next();
		System.out.println("Enter address:");
		SCANNER.nextLine();
		String address = SCANNER.nextLine();
		System.out.println("Enter city:");
		String city = SCANNER.next();
		System.out.println("Enter state:");
		SCANNER.nextLine();
		String state = SCANNER.nextLine();
		System.out.println("Enter zip:");
		String zip = SCANNER.next();
		System.out.println("Enter phone No.:");
		String phoneNumber = SCANNER.next();
		System.out.println("Enter email address:");
		String email = SCANNER.next();
		return (new Contacts(firstName, lastName, address, city, state, zip, phoneNumber, email));
	}

	public HashSet<String> searchContactByCity(String city) {
		contactsByCity.clear();
		addressBook.stream().filter(contact -> city.equalsIgnoreCase(contact.getCity()))
				.forEach(contact -> contactsByCity.add(contact.getFullName()));
		return contactsByCity;
	}

	public HashSet<String> searchContactByState(String state) {
		contactsByState.clear();
		addressBook.stream().filter(contact -> state.equalsIgnoreCase(contact.getState()))
				.forEach(contact -> contactsByState.add(contact.getFullName()));
		return contactsByState;
	}

	public void addressBookOperations(String addressBookNameToOperate)
			throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		Path addressBookFilePath = Paths.get(ADRESS_BOOK_FILES + "/" + addressBookNameToOperate + ".txt");
		Path csvFilePath = Paths.get(CSV_FILES + "/" + addressBookNameToOperate + ".csv");
		Path jsonFilePath = Paths.get(JSON_FILES + "/" + addressBookNameToOperate + ".json");
		System.out.println("Entered Address Book -> " + addressBookNameToOperate);

		boolean operate = true;
		while (operate) {
			System.out.println("1. Read Contact");
			System.out.println("2. Edit Contact");
			System.out.println("3. Delete Contact");
			System.out.println("4. Sort Address Book");
			System.out.println("5. Write Address Book");
			System.out.println("6. Exit");
			System.out.println("Enter your choice : ");
			int choice = SCANNER.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter IO Service : \n1.CONSOLE_IO\n2.FILE_IO\n3.CSV_IO\n4.JSON_IO");
				int ioChoice = SCANNER.nextInt();
				if (ioChoice == 1)
					addContactToAddressBook(createContact());
				else if (ioChoice == 2) {
					AddressBookFileIOService fileReadObject = new AddressBookFileIOService(addressBookFilePath);
					addressBook = fileReadObject.readData();
				} else if (ioChoice == 3) {
					AddressBookCSVIOservice csvReadObject = new AddressBookCSVIOservice(csvFilePath);
					addressBook = csvReadObject.readDataFromCSVFile();
				} else if (ioChoice == 4) {
					AddressBookJSONIOservice jsonReadObject = new AddressBookJSONIOservice(jsonFilePath);
					addressBook = jsonReadObject.readDataFromJSONFile();
				} else
					System.out.println("Invalid IO choice selected");
				break;
			case 2:
				System.out.println("Enter the Full Name : ");
				SCANNER.nextLine();
				String fullName = SCANNER.nextLine();
				editContact(fullName);
				break;
			case 3:
				System.out.println("Enter the Full Name : ");
				SCANNER.nextLine();
				String name = SCANNER.nextLine();
				deleteContact(name);
				break;
			case 4:
				System.out.println("Sort by :");
				System.out.println("1.Person");
				System.out.println("2.City ");
				System.out.println("3.State");
				System.out.println("4.Zip");
				System.out.println("Enter your choice : ");
				int sortChoice = SCANNER.nextInt();
				if (sortChoice == 1)
					sortByPerson();
				else if (sortChoice == 2)
					sortByCity();
				else if (sortChoice == 3)
					sortByState();
				else if (sortChoice == 4)
					sortByZip();
				else
					System.out.println("Invalid parameter for sorting selected!");
				break;
			case 5:
				System.out.println("Enter IO Service : \n1.CONSOLE_IO\n2.FILE_IO\n3.CSV_IO\n4.JSON_IO");
				int choiceOfIO = SCANNER.nextInt();
				if (choiceOfIO == 1)
					displayAddressBook();
				else if (choiceOfIO == 2) {
					AddressBookFileIOService fileWriteObject = new AddressBookFileIOService(addressBookFilePath);
					fileWriteObject.writeData(addressBook);
				} else if (choiceOfIO == 3) {
					AddressBookCSVIOservice csvWriteObject = new AddressBookCSVIOservice(csvFilePath);
					csvWriteObject.writeDataInCSVFile(addressBook);
				} else if (choiceOfIO == 4) {
					AddressBookJSONIOservice jsonWriteObject = new AddressBookJSONIOservice(jsonFilePath);
					jsonWriteObject.writeDataInJSONFile(addressBook);
				} else
					System.out.println("Invalid IO choice");
				break;
			case 6:
				operate = false;
				break;
			default:
				System.out.println("Invalid Choice");
			}
		}
	}

//	SORTING
	public void sortByPerson() {
		addressBook = addressBook.stream().sorted(Comparator.comparing(Contacts::getFullName))
				.collect(Collectors.toList());
		System.out.println("Address Book sorted by Person Name");
	}

	public void sortByCity() {
		addressBook = addressBook.stream().sorted(Comparator.comparing(Contacts::getCity)).collect(Collectors.toList());
		System.out.println("Address Book sorted by City");
	}

	public void sortByState() {
		addressBook = addressBook.stream().sorted(Comparator.comparing(Contacts::getState))
				.collect(Collectors.toList());
		System.out.println("Address Book sorted by State");
	}

	public void sortByZip() {
		addressBook = addressBook.stream().sorted(Comparator.comparing(Contacts::getZip)).collect(Collectors.toList());
		System.out.println("Address Book sorted by Zip");
	}

	public ArrayList<String> getCities() {
		addressBook.stream().forEach(contact -> {
			city.add(contact.getCity());
		});
		return city;
	}

	public ArrayList<String> getStates() {
		addressBook.stream().forEach(contact -> {
			state.add(contact.getState());
		});
		return state;
	}

}
