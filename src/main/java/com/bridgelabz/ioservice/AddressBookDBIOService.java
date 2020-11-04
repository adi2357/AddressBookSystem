package com.bridgelabz.ioservice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bridgelabz.exception.DBException;
import com.bridgelabz.model.Contacts;

public class AddressBookDBIOService {

	private int connectionCounter = 0;
	private PreparedStatement addressBookDataStatement;
	private static AddressBookDBIOService addressBookDBService;

	private AddressBookDBIOService() {
	}

	public static AddressBookDBIOService getInstatnce() {
		if(addressBookDBService == null)
			addressBookDBService = new AddressBookDBIOService();
		return addressBookDBService;
	}

	private Connection establishConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service";
		String userName = "root";
		String password = "First12@";
		Connection connection;
		connectionCounter++;
		System.out.println("Processing Thread : " + Thread.currentThread().getName() + 
						   " Establishing connection to database with Id : " + connectionCounter);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Processing Thread : " + Thread.currentThread().getName() + 
						   " Id : " + connectionCounter + " Connection is successfull!!! " + connection);
		return connection;
	}

	private void prepareStatementForContactData() throws DBException {
		String sql = "select * from contact where first_name = ? and last_name = ?;";
		try {
			Connection connection = this.establishConnection();
			this.addressBookDataStatement = connection.prepareStatement(sql);
		}catch (SQLException e) {
			throw new DBException("Cannot establish connection", DBException.ExceptionType.CONNECTION_FAIL);
		}
	}

	public List<Contacts> getContactDataUsingName(String firstName, String lastName) throws DBException {
		List<Contacts> contactDataList = null;
		if(this.addressBookDataStatement == null)
			this.prepareStatementForContactData();
		try {
			addressBookDataStatement.setString(1, firstName);
			addressBookDataStatement.setString(2, lastName);
			ResultSet resultSet = addressBookDataStatement.executeQuery();
			contactDataList = this.getContactDataUsingResultSet(resultSet);			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Cannot execute query", DBException.ExceptionType.SQL_ERROR);
		}
		return contactDataList;
	}

	public List<Contacts> readContactsForDateRange(LocalDate startDate, LocalDate endDate) throws DBException {
		String sql = String.format("select distinct contact.first_name as first_name, contact.last_name as last_name, contact.address as address, "
				+ "contact.city as city, contact.state as state, contact.zip as zip, contact.email as email "
				+ "from contact "
				+ "inner join contact_book on contact.first_name = contact_book.first_name and contact.last_name = contact_book.last_name "
				+ "where contact_book.date_added between '%s' and '%s' ;", Date.valueOf(startDate),Date.valueOf(endDate));
		return this.getContactDataUsingDB(sql);
	}

	public Map<String, Integer> getCountByCity() throws DBException {
		String sql = "select city, count(*) as contacts_per_city from contact group by city;";
		Map<String, Integer> cityToContactCountMap = new HashMap<>();
		try (Connection connection = this.establishConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				cityToContactCountMap.put(resultSet.getString("city"), resultSet.getInt("contacts_per_city"));
			}
		}catch (SQLException e) {
			throw new DBException("Cannot establish connection",DBException.ExceptionType.CONNECTION_FAIL);
		}
		return cityToContactCountMap;
	}

	public Map<String, Integer> getCountByState() throws DBException {
		String sql = "select state, count(*) as contacts_per_state from contact group by state;";
		Map<String, Integer> stateToContactCountMap = new HashMap<>();
		try (Connection connection = this.establishConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				stateToContactCountMap.put(resultSet.getString("state"), resultSet.getInt("contacts_per_state"));
			}
		}catch (SQLException e) {
			throw new DBException("Cannot establish connection",DBException.ExceptionType.CONNECTION_FAIL);
		}
		return stateToContactCountMap;
	}

	public List<Contacts> readData() throws com.bridgelabz.exception.DBException {
		String sql = "select * from contact;";
		return this.getContactDataUsingDB(sql);
	}

	private List<Contacts> getContactDataUsingDB(String sql) throws com.bridgelabz.exception.DBException {
		List<Contacts> contactDataList = null;
		try {
			Connection connection = this.establishConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			contactDataList = this.getContactDataUsingResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Cannot establish connection",DBException.ExceptionType.CONNECTION_FAIL);
		}
		return contactDataList;
	}

	private List<Contacts> getContactDataUsingResultSet(ResultSet resultSet) throws SQLException {
		List<Contacts> contactDataList = new ArrayList<>();
		while (resultSet.next()) {
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String address = resultSet.getString("address");
			String city = resultSet.getString("city");
			String state = resultSet.getString("state");
			int zip = resultSet.getInt("zip");
			String email = resultSet.getString("email");

			List<String> phoneList = new ArrayList<>();
			List<String> addressBookTypeList = new ArrayList<>();			
			List<String> addressBookNameList = new ArrayList<>();
			
			try (Connection connection = this.establishConnection()) {
				String sql = String.format("select contact.first_name as first_name, contact.last_name as last_name, contact_number.phone as phone "
										 + "from contact "
										 + "inner join contact_number on contact.first_name = contact_number.first_name and contact.last_name = contact_number.last_name "
										 + "where contact.first_name = '%s' and contact.last_name = '%s';", firstName,lastName);
				Statement statement = connection.createStatement();
				ResultSet resultSetForContactNumber = statement.executeQuery(sql);
				while (resultSetForContactNumber.next()) {
					phoneList.add(resultSetForContactNumber.getString("phone"));
				}
			}
			try (Connection connection = this.establishConnection()) {
				String sql = String.format("select contact.first_name as first_name, contact.last_name as last_name, "
										 + "address_book.name as address_book_name,contact_book.type as type "
										 + "from contact "
										 + "inner join contact_book on contact.first_name = contact_book.first_name and contact.last_name = contact_book.last_name "
										 + "inner join address_book on contact_book.type = address_book.type "
										 + "where contact.first_name = '%s' and contact.last_name = '%s';",firstName,lastName);
				Statement statement = connection.createStatement();
				ResultSet resultSetForAddressBookData = statement.executeQuery(sql);
				while (resultSetForAddressBookData.next()) {
					addressBookTypeList.add(resultSetForAddressBookData.getString("type"));
					addressBookNameList.add(resultSetForAddressBookData.getString("address_book_name"));
				}
			}
			contactDataList
					.add(new Contacts(firstName, lastName, address, city, state, zip, email, phoneList, addressBookTypeList, addressBookNameList));
		}
		return contactDataList;
	}

	public int updateContactEmail(String firstName, String lastName, String email) throws DBException {
		String sql = String.format("update contact set email = '%s' where first_name = '%s' and last_name = '%s' ;", email, firstName, lastName);
		try (Connection connection = this.establishConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			throw new DBException("Cannot establish connection", DBException.ExceptionType.CONNECTION_FAIL);
		}
	}

	public Contacts addContactToAddressBook(String firstName, String lastName, String address, String city,
			String state, int zip, String email, String phone, String type, String addressBookName) throws DBException {
		List<String> phoneList = new ArrayList<>();
		List<String> addressBookTypeList = new ArrayList<>();
		List<String> addressBookNameList = new ArrayList<>();
		Contacts newContact = null;
		Connection connection = null;
		try {
			connection = this.establishConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try (Statement statement = connection.createStatement()) {
			boolean isExists = false;
			String sqlToRetrieveAddressBookNameAndType = String.format("select name, type from address_book "
																	 + "where name = '%s' and type = '%s';",addressBookName, type);
			ResultSet resultSetToRetrieveAddressBookNameAndType = statement.executeQuery(sqlToRetrieveAddressBookNameAndType);
			if(resultSetToRetrieveAddressBookNameAndType.next()) {
				isExists = true;
			}
			if(!isExists) {
				String sqlToInsert = String.format("insert into address_book (name, type) values  ('%s','%s');", addressBookName, type);
				int addressBookRowsUpdated = statement.executeUpdate(sqlToInsert, statement.RETURN_GENERATED_KEYS);
				if(addressBookRowsUpdated == 1) {
					System.out.println("Inserted into Address Book");
				}				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try (Statement statement = connection.createStatement()) {
			boolean isExists = false;
			String sqlToRetrieveContactName = 
					String.format("select first_name, last_name from contact where first_name = '%s' and last_name = '%s';", firstName, lastName);
			ResultSet resultSetToRetrieveContactName = statement.executeQuery(sqlToRetrieveContactName);
			if(resultSetToRetrieveContactName.next()) {
				isExists = true;
			}
			if(!isExists){
				String sqlToInsert = String.format(
						"insert into contact " + "(first_name, last_name, address, city, state, zip, email) values "
					  + "('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
						firstName, lastName, address, city, state, zip, email);
				int contactRowsUpdated = statement.executeUpdate(sqlToInsert);
				System.out.println("Inserted into Contact");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try (Statement statement = connection.createStatement()) {
			String sqlToRetrievePhone = 
					String.format("select first_name, last_name, phone "
								+ "from contact_number "
								+ "where first_name = '%s' and last_name = '%s' ;", firstName, lastName);
			ResultSet resultSetToRetrievePhone = statement.executeQuery(sqlToRetrievePhone);
			while(resultSetToRetrievePhone.next()) {
				phoneList.add(resultSetToRetrievePhone.getString("phone"));
			}
			if(!phoneList.contains(phone)) {
				String sqlToInsert = String.format("insert into contact_number "
												 + "(first_name, last_name, phone) values ('%s','%s','%s');", firstName, lastName, phone);
				int contactNumberRowsUpdated = statement.executeUpdate(sqlToInsert, statement.RETURN_GENERATED_KEYS);
				if(contactNumberRowsUpdated == 1) {
					phoneList.add(phone);
				}
				System.out.println("Inserted into Contact Number");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try (Statement statement = connection.createStatement()) {
			String sqlToRetrieveBookNameAndBookType = 
					String.format("select contact_book.type as type, address_book.name as address_book_name "
							+ "from contact_book "
							+ "inner join address_book on address_book.type = contact_book.type "
							+ "where contact_book.first_name = '%s' and contact_book.last_name = '%s';", firstName, lastName);
			ResultSet resultSetRetrieveBookNameAndBookType = statement.executeQuery(sqlToRetrieveBookNameAndBookType);
			while(resultSetRetrieveBookNameAndBookType.next()) {
				addressBookTypeList.add(resultSetRetrieveBookNameAndBookType.getString("type"));
				addressBookNameList.add(resultSetRetrieveBookNameAndBookType.getString("address_book_name"));
			}
			if(!addressBookTypeList.contains(type)){
				String sqlToInsert = String.format(
						"insert into contact_book "
					   +"(type, first_name, last_name) values ('%s', '%s', '%s')", type, firstName, lastName);
				int contactBookRowsUpdated = statement.executeUpdate(sqlToInsert);
				if(contactBookRowsUpdated == 1) {
					addressBookTypeList.add(type);
					addressBookNameList.add(addressBookName);
					System.out.println("Inserted into Contact Book");
				}
			}
			newContact = new Contacts(firstName, lastName, address, city, state, zip, email, phoneList, addressBookTypeList, addressBookNameList);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return newContact;
	}

	public int deleteContactData(String firstName, String lastName) {
		String sql = String.format("delete contact_number, contact, contact_book from contact "
				+ "inner join contact_book on  contact.first_name = contact_book.first_name and contact.last_name = contact_book.last_name "
				+ "inner join contact_number on contact.first_name = contact_number.first_name and contact.last_name = contact_number.last_name "
				+ "where contact.first_name = '%s' and contact.last_name = '%s'; ", firstName, lastName);
		try(Connection connection = this.establishConnection()){
			Statement statement = connection.createStatement();
			int resultSet = statement.executeUpdate(sql);
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
