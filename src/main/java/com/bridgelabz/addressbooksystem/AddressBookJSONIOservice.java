package com.bridgelabz.addressbooksystem;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class AddressBookJSONIOservice {
	public Path jsonFilePath;

	public AddressBookJSONIOservice(Path jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
	}

	public void writeDataInJSONFile(List<Contacts> addressBook) throws IOException {

		try (Writer writer = Files.newBufferedWriter(jsonFilePath)) {
			Gson gson = new Gson();
			String json = gson.toJson(addressBook);
			writer.write(json);
		}
	}

	public List<Contacts> readDataFromJSONFile() throws IOException {

		try (Reader reader = Files.newBufferedReader(jsonFilePath)) {
			Gson gson = new Gson();
			Contacts[] contactObject = gson.fromJson(reader, Contacts[].class);
			List<Contacts> addressBook = Arrays.asList(contactObject);
			return addressBook;
		}
	}
}
