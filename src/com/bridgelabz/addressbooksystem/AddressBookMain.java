package com.bridgelabz.addressbooksystem;
import java.util.Scanner;

public class AddressBookMain {
	public static void main(String[] args) {
		
		Contacts contacts=new Contacts("Aditya", "Verma", "3/4 LDA Colony", "Lucknow", "Uttar Pradesh",224045, "8803036140","address@gmail.com");
		System.out.println(contacts.toString());		
	}

}
