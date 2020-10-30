package com.bridgelabz.ioservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bridgelabz.exception.DBException;
import com.bridgelabz.model.AddressBookData;
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
		while(resultSet.next()) {
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String address = resultSet.getString("address");
			String city = resultSet.getString("city");
			String state = resultSet.getString("state");
			int zip = resultSet.getInt("zip");
			String email = resultSet.getString("email");
			
			List<String> phoneList = new ArrayList<>();
			List<AddressBookData> addressBooks =new ArrayList<AddressBookData>();
			
			try(Connection connection = this.establishConnection()){
				String sql = String.format("select contact.first_name as first_name, contact.last_name as last_name, contact_number.phone as phone "
										 + "from contact "
										 + "inner join contact_number on contact.first_name = contact_number.first_name and contact.last_name = contact_number.last_name "
										 + "where contact.first_name = '%s' and contact.last_name = '%s';", firstName,lastName);
				Statement statement = connection.createStatement();
				ResultSet resultSetForContactNumber = statement.executeQuery(sql);
				while(resultSetForContactNumber.next()) {
					phoneList.add(resultSetForContactNumber.getString("phone"));
				}
			}
			try(Connection connection = this.establishConnection()){
				String sql = String.format("select contact.first_name as first_name, contact.last_name as last_name, "
										 + "address_book.name as address_book_name,contact_book.type as type "
										 + "from contact "
										 + "inner join contact_book on contact.first_name = contact_book.first_name and contact.last_name = contact_book.last_name "
										 + "inner join address_book on contact_book.type = address_book.type "
										 + "where contact.first_name = '%s' and contact.last_name = '%s';",firstName,lastName);
				Statement statement = connection.createStatement();
				ResultSet resultSetForAddressBookData = statement.executeQuery(sql);
				while(resultSetForAddressBookData.next()) {
					addressBooks.add(new AddressBookData(resultSetForAddressBookData.getString("address_book_name"),resultSetForAddressBookData.getString("type")));
				}
			}
			contactDataList.add(new Contacts(firstName, lastName, address, city, state, zip, email, phoneList, addressBooks));
		}
		return contactDataList;
	}

	public int updateContactEmail(String firstName, String lastName, String email) {
		return 0;
	}
}
