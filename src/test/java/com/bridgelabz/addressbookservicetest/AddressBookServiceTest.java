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
import com.bridgelabz.exception.DBException;
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

	public Response addContactToJsonServer(Contacts contactData) {
		String contactJson = new Gson().toJson(contactData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		return request.post("/address-book");
	}

	public Response updateContactEmailInJsonServer(Contacts contactData) {
		String contactJson = new Gson().toJson(contactData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		return request.put("/address-book/" + contactData.getId());
	}

	public Response deleteContactFromJsonServer(Contacts contactData) {
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		return request.delete("/address-book/" + contactData.getId());
	}

	@Test
	public void givenContactsInDB_WhenRetrieved_ShouldMatchContactsCount() {

		AddressBookService serviceObject = new AddressBookService();
		serviceObject.readContactData(IOService.DB_IO);
		int countOfEntriesRetrieved = serviceObject.sizeOfContactList();
		Assert.assertEquals(10, countOfEntriesRetrieved);
	}

	@Test
	public void givenNewEmailForContact_WhenUpdated_ShouldSyncWithDB() {
		try {
			AddressBookService serviceObject = new AddressBookService();
			serviceObject.readContactData(IOService.DB_IO);
			serviceObject.updateContactEmail("Aditya", "Verma", "addressbook@capgemini.com");
			boolean result = serviceObject.checkContactDataInSyncWithDB("Aditya", "Verma");
			Assert.assertTrue(result);
		}catch (DBException e) {
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
		Assert.assertEquals(4, employeePayrollData.size());
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
		}catch (DBException e) {
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
	public void givenNewContact_WhenAddedToJSONServer_ShouldMatch201ResponseContactCount() {
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

	@Test
	public void givenMultipleContacts_WhenAddedToJSONServer_ShouldMatchContactCountAndSyncWithMemory() {
		Contacts[] arrayOfContacts = getContactsList();
		AddressBookService serviceObject = new AddressBookService(Arrays.asList(arrayOfContacts));
		Contacts[] arrayOfNewContacts = {
				new Contacts(0,"Morri", "Singhal","10/12 Moti Nagar", "Kanpur", "Uttar Pradesh", 215881, "addressbooknew6@capgemini.com","9888886669","Temp", "TemporaryBook"),
				new Contacts(0,"Tapas", "Singh","2/1 Hamesh Road", "Lucknow", "Uttar Pradesh", 422581, "addressbooknew7@capgemini.com","9977776669","Temp", "TemporaryBook"),
				new Contacts(0,"Alok", "Balaji","4/6 Airport Road", "Mumbai", "Maharashtra", 458456, "addressbooknew8@capgemini.com","9966955559","Temp", "TemporaryBook"),
				new Contacts(0,"Warren", "Estacaldo","5/120 Dumna Road", "Jabalpur", "Madhya Pradesh", 459851, "addressbooknew9@capgemini.com","9964566669","Temp", "TemporaryBook")		
		};
		try {
			for(Contacts newContact : arrayOfNewContacts) {
				Response response = addContactToJsonServer(newContact);
				int statusCode = response.getStatusCode();
				Assert.assertEquals(201, statusCode);
				
				newContact = new Gson().fromJson(response.asString(), Contacts.class);
				serviceObject.addContactToAddressBook(newContact);
				Assert.assertTrue(serviceObject.checkContactDataInSyncWithDB(newContact.getFirstName(), newContact.getLastName()));
			}
			long entries = serviceObject.sizeOfContactList();
			Assert.assertEquals(20, entries);
		}catch (DBException e) {
		}
	}

	@Test
	public void givenNewEmailForContact_WhenUpdatedInJSONServer_ShouldMatch200ResponseAndSyncWithDB() {
		Contacts[] arrayOfContacts = getContactsList();
		AddressBookService serviceObject = new AddressBookService(Arrays.asList(arrayOfContacts));
		try {
			serviceObject.updateContactEmail("Warren", "Estacaldo", "updatedemail@capgemini.com");
			Contacts contactData = serviceObject.getContactData("Warren", "Estacaldo");
			
			Response response = updateContactEmailInJsonServer(contactData);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(200, statusCode);
			
			Contacts updatedContact = new Gson().fromJson(response.asString(), Contacts.class);	
			boolean result = serviceObject.checkContactDataInSyncWithDB(updatedContact.getFirstName(), updatedContact.getLastName());
			Assert.assertTrue(result);
		}catch (DBException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenContact_WhenDeletedFromJSONServer_ShouldMatch200ResponseAndContactCountAndSyncWithDB() {
		Contacts[] arrayOfContacts = getContactsList();
		AddressBookService serviceObject = new AddressBookService(Arrays.asList(arrayOfContacts));
		try {
			Contacts contactData = serviceObject.getContactData("Warren", "Estacaldo");
			Response response = deleteContactFromJsonServer(contactData);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(200, statusCode);
			
			serviceObject.deleteContactData("Warren", "Estacaldo");
			long entries = serviceObject.sizeOfContactList();
			Assert.assertEquals(19, entries);
			
			boolean result = serviceObject.checkContactDataInSyncWithDB("Warren", "Estacaldo");
			Assert.assertTrue(result);
		}catch (DBException e) {
			e.printStackTrace();
		}
	}
}
