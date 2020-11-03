package com.bridgelabz.addressbookservicetest;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.addressbookservice.AddressBookService;
import com.bridgelabz.addressbookservice.AddressBookService.IOService;
import com.bridgelabz.model.Contacts;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookServiceTest {

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	public Contacts[] getContactsList() {
		Response response = RestAssured.get("/address-book");
		System.out.println("CONTACT Entries in JSON Server :\n" + response.asString());
		Contacts[] arrayOfContacts = new Gson().fromJson(response.asString(), Contacts[].class);
		return arrayOfContacts;
	}

	public Response addContactToJsonServer(Contacts ContactData) {
		String contactJson = new Gson().toJson(ContactData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		return request.post("/address-book");
	}

	@Test
	public void givenContactsInDB_WhenRetrieved_ShouldMatchContactsCount() {

		AddressBookService serviceObject = new AddressBookService();
		serviceObject.readContactData(IOService.DB_IO);
		int countOfEntriesRetrieved = serviceObject.sizeOfContactList();
		Assert.assertEquals(11, countOfEntriesRetrieved);
	}

	@Test
	public void givenNewEmailForContact_WhenUpdated_ShouldSyncWithDB() {
		try {
			AddressBookService serviceObject = new AddressBookService();
			serviceObject.readContactData(IOService.DB_IO);
			serviceObject.updateContactEmail("Aditya", "Verma", "addressbook@capgemini.com");
			boolean result = serviceObject.checkContactDataInSyncWithDB("Aditya", "Verma");
			Assert.assertTrue(result);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenDateRange_WhenContactsRetrieved_ShouldMatchContactsCount() {
	
		AddressBookService serviceObject = new AddressBookService();
		serviceObject.readContactData(IOService.DB_IO);
		LocalDate startDate = LocalDate.of(2018, 01, 01);
		LocalDate endDate = LocalDate.now();
		List<Contacts> employeePayrollData = serviceObject.readContactsForDateRange(IOService.DB_IO, startDate, endDate);
		Assert.assertEquals(5, employeePayrollData.size());
	}

	@Test
	public void givenCityAndState_WhenRetrieved_ShouldMatchContactsCountPerCityAndPerState() {
	
		AddressBookService serviceObject = new AddressBookService();
		serviceObject.readContactData(IOService.DB_IO);
		int countPerCity = serviceObject.getCountByCity(IOService.DB_IO, "Lucknow");
		int countPerState = serviceObject.getCountByState(IOService.DB_IO, "Uttar Pradesh");
		boolean result = countPerCity == 4 && countPerState == 6;
		Assert.assertTrue(result);
	}

	@Test
	public void givenNewContact_WhenAdded_ShouldSyncWithDB() {
		try {
			AddressBookService serviceObject = new AddressBookService();
			serviceObject.readContactData(IOService.DB_IO);
			serviceObject.addContactToAddressBook("Shreshtra", "Balaji", "4/14 Airport Road", "Mumbai", "Maharashtra", 245245, "addressbooknew@capgemini.com",
												  "9999999999", "Temp", "TemporaryBook");
			boolean result = serviceObject.checkContactDataInSyncWithDB("Shreshtra", "Balaji");
			Assert.assertTrue(result);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenMultipleContacts_WhenAddedToDB_ShouldMatchContactCount() throws InterruptedException {
		AddressBookService serviceObject = new AddressBookService();
		serviceObject.readContactData(IOService.DB_IO);
		Contacts[] arrayOfContacts = {
				new Contacts(0,"Alisha", "Kori","89/11 Deep Road", "Mumbai", "Maharashtra", 456281, "addressbooknew1@capgemini.com", 
						"7777777777", "Temp", "TemporaryBook"),
				new Contacts(0,"Karina", "Sharma","8/88 Karim Road", "Mumbai", "Maharashtra", 454561, "addressbooknew2@capgemini.com", 
						"8888888888", "Temp", "TemporaryBook"),
				new Contacts(0,"Mori", "Singh","4/18 Kirana Nagar", "Bhopal", "Madhya Pradesh", 264561, "addressbooknew3@capgemini.com", 
						"6666666666", "Temp", "TemporaryBook"),
				new Contacts(0,"Shila", "Dixit","2/120 Sadar Colony", "Jabalpur", "Madhya Pradesh", 482001, "addressbooknew4@capgemini.com", 
						"8989454599", "Temp", "TemporaryBook")
				};
		Instant start = Instant.now();
		serviceObject.addContactListToAddressBook(Arrays.asList(arrayOfContacts));
		Instant end = Instant.now();
		Thread.sleep(20);
		System.out.println("Duration with Threading : " + Duration.between(start, end));
		
		Assert.assertEquals(15, serviceObject.sizeOfContactList());
	}

	@Test
	public void givenContactDataInJsonServer_WhenRetrieved_ShouldMatchContactCount() {
		Contacts[] arrayOfContacts = getContactsList();
		AddressBookService serviceObject = new AddressBookService(Arrays.asList(arrayOfContacts));
		long entries = serviceObject.sizeOfContactList();
		Assert.assertEquals(15, entries);
	}

	@Test
	public void givenNewContact_WhenAdded_ShouldMatchContactCount() {
		Contacts[] arrayOfContacts = getContactsList();
		AddressBookService serviceObject = new AddressBookService(Arrays.asList(arrayOfContacts));
		Contacts newContact = new Contacts(0,"Aman", "Singhal","19/11 Akash Road", "Mumbai", "Maharashtra", 458881, "addressbooknew5@capgemini.com",
				"9966996669","Temp", "TemporaryBook");
		Response response = addContactToJsonServer(newContact);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);

		newContact = new Gson().fromJson(response.asString(), Contacts.class);
		serviceObject.addContactToAddressBook(newContact);
		long entries = serviceObject.sizeOfContactList();
		Assert.assertEquals(16, entries);
	}
}

