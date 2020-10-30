package com.bridgelabz.ioservice;

import java.sql.Connection;
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
		System.out.println("Establishing connection to database : " + jdbcURL);
		return DriverManager.getConnection(jdbcURL, userName, password);
	}

	private void prepareStatementForContactData() throws DBException {
		String sql = "select * from contact where first_name = ? and last_name = ?;";
		try {
			Connection connection = this.establishConnection();
			System.out.println("Connection is successfull!!! " + connection);
			this.addressBookDataStatement = connection.prepareStatement(sql);
		}catch (SQLException e) {
			throw new DBException("Cannot establish connection", DBException.ExceptionType.CONNECTION_FAIL);
		}
	}

	public List<Contacts> getEmplyoeePayrollDataUsingName(String firstName, String lastName) throws DBException {
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

	public List<Contacts> readData() throws com.bridgelabz.exception.DBException {
		String sql = "select * from contact;";
		return this.getContactDataUsingDB(sql);
	}

	private List<Contacts> getContactDataUsingDB(String sql) throws com.bridgelabz.exception.DBException {
		List<Contacts> contactDataList = null;
		try {
			Connection connection = this.establishConnection();
			System.out.println("Connection is successfull!!! " + connection);
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
			Map<String, String> addressBooks = new HashMap<>();
			
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
					addressBooks.put(resultSetForAddressBookData.getString("address_book_name"), resultSetForAddressBookData.getString("type"));
				}
			}
			contactDataList
					.add(new Contacts(firstName, lastName, address, city, state, zip, email, phoneList, addressBooks));
		}
		return contactDataList;
	}

	public int updateContactEmail(String firstName, String lastName, String email) throws DBException {
		String sql = String.format("update contact set email = '%s' where first_name = '%s' and last_name = '%s' ;", email, firstName, lastName);
		try (Connection connection = this.establishConnection()) {
			System.out.println("Connection is successfull!!! " + connection);
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			throw new DBException("Cannot establish connection", DBException.ExceptionType.CONNECTION_FAIL);
		}
	}

	public List<Contacts> readContactsForDateRange(LocalDate startDate, LocalDate endDate) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}
}
