package com.bridgelabz.addressbooksystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddressBookFileIOService {
	
	public Path addressBookPath;

	public AddressBookFileIOService(Path addressBookPath) {
		this.addressBookPath = addressBookPath;		
	}
	
	public void writeData(List<Contacts> addressBook) {

		StringBuffer contactBufferString = new StringBuffer();
		addressBook.forEach(contact -> {
			String contactDataString = contact.toString().concat("\n");
			contactBufferString.append(contactDataString);
		});

		try {
			Files.write(addressBookPath, contactBufferString.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Contacts> readData() {
		List<Contacts> contactReadList = new ArrayList<Contacts>();
		try {
			Files.lines(addressBookPath).map(line -> line.trim()).forEach(line -> {
				String[] data = line.split("[a-zA-Z ]+ : ");
				String fullName=data[1].trim();
				String[] name=fullName.split(" ");
				String address=data[2].trim();
				String city=data[3].trim();
				String state=data[4].trim();
				int zip=Integer.parseInt(data[5].trim());
				String phoneNumber=data[6].trim();
				String email=data[7].trim();
				Contacts newContact = new Contacts(name[0], name[1], address, city, state, zip, phoneNumber, email);
				contactReadList.add(newContact);
			});
		} catch (IOException e) {
		}
		return contactReadList;
	}
}
