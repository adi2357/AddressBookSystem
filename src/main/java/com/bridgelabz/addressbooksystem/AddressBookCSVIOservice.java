package com.bridgelabz.addressbooksystem;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBookCSVIOservice {
	public Path csvFilePath;

	public AddressBookCSVIOservice(Path csvFilePath) {
		this.csvFilePath = csvFilePath;
	}

	public void writeDataInCSVFile(List<Contacts> addressBook)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

		try (Writer writer = Files.newBufferedWriter(csvFilePath)) {
			StatefulBeanToCsv<Contacts> beanToCsv = new StatefulBeanToCsvBuilder<Contacts>(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			beanToCsv.write(addressBook);
		}
	}

	public List<Contacts> readDataFromCSVFile() throws IOException {
		
		try (Reader reader = Files.newBufferedReader(csvFilePath)) {
			CsvToBeanBuilder<Contacts> builder = new CsvToBeanBuilder<Contacts>(reader);
			CsvToBean<Contacts> csvToBean = builder.withType(Contacts.class).withIgnoreLeadingWhiteSpace(true).build();
			List<Contacts> addressBook = csvToBean.parse();
			return addressBook;
		}
	}

}
