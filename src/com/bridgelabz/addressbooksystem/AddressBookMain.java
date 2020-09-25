package com.bridgelabz.addressbooksystem;

import java.util.Scanner;

public class AddressBookMain {
	
	
	public Contacts createContact() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter First Name:");
		String firstName = sc.next();
		System.out.println("Enter Last Name:");
		String lastName=sc.next();
		System.out.println("Enter address:");
		String address=sc.next();
		System.out.println("Enter city:");
		String city=sc.next();
		System.out.println("Enter state:");
		String state=sc.next();
		System.out.println("Enter zip:");
		int zip=sc.nextInt();
		System.out.println("Enter phone No.:");
		String phoneNumber=sc.next();
		System.out.println("Enter email address:");
		String email=sc.next();
		Contacts newContact=new Contacts(firstName, lastName, address, city, state, zip, phoneNumber, email);
		
		return newContact;		
	}
	
	public static void main(String[] args) {
		
		AddressBookMain addressBookObject=new AddressBookMain();		
		Contacts newContact=addressBookObject.createContact();
		System.out.println("New Contact Created : "+"\n"+newContact.toString());		
	}

}
