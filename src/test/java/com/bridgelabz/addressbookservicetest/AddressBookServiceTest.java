package com.bridgelabz.addressbookservicetest;

import org.junit.Assert;
import org.junit.Test;

import com.bridgelabz.addressbookservice.AddressBookService;
import com.bridgelabz.addressbookservice.AddressBookService.IOService;

public class AddressBookServiceTest {

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
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
