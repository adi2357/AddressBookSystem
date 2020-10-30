package com.bridgelabz.addressbookservicetest;

import java.time.LocalDate;
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
}
