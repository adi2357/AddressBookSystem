package com.bridgelabz.addressbookservicetest;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bridgelabz.addressbookservice.AddressBookService;
import com.bridgelabz.addressbookservice.AddressBookService.IOService;
import com.bridgelabz.model.Contacts;

public class AddressBookServiceTest {

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
												  "9999999999", "TemporaryBook", "Temp");
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
				new Contacts("Alisha", "Kori","89/11 Deep Road", "Mumbai", "Maharashtra", 456281, "addressbooknew1@capgemini.com",
						"7777777777","TemporaryBook","Temp"),
				new Contacts("Karina", "Sharma","8/88 Karim Road", "Mumbai", "Maharashtra", 454561, "addressbooknew2@capgemini.com",
						"8888888888","TemporaryBook","Temp"),
				new Contacts("Mori", "Singh","4/18 Kirana Nagar", "Bhopal", "Madhya Pradesh", 264561, "addressbooknew3@capgemini.com",
						"6666666666","TemporaryBook","Temp"),
				new Contacts("Shila", "Dixit","2/120 Sadar Colony", "Jabalpur", "Madhya Pradesh", 482001, "addressbooknew4@capgemini.com",
						"8989454599","TemporaryBook","Temp"),
				};
		Instant start = Instant.now();
		serviceObject.addContactListToAddressBook(Arrays.asList(arrayOfContacts));
		Instant end = Instant.now();
		System.out.println("Duration with Threading : " + Duration.between(start, end));
		
		Assert.assertEquals(15, serviceObject.sizeOfContactList());
	}
}
