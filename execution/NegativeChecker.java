package execution;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;

import databaseQueries.Connector;
import reader.Configuration;
import reader.CountriesReader;
import reader.ExceptionsReader;
import reader.IncorrectCountryEntryException;
import resultSetParser.RSParser;
import writer.FileCreator;

public class NegativeChecker {

	public static void main(String[] args) {
		

		// The file that contains the negative exceptions
		String exceptFile = "negativeExceptions.txt";
		

		// Read the exceptFile
		ExceptionsReader exceptionReader = new ExceptionsReader(exceptFile);

		// Reading the configuration file
		Configuration config = null;
		try {
			config = new Configuration("configuration.txt");
		} catch (IOException e2) {
			System.out.println("IOException while reading configuration.txt. Make sure you respect the requirements in this file. ");
			e2.printStackTrace();
		}
		
		try {
			
			
			
			// Read the exceptfile
			HashSet<String> exceptions = exceptionReader.readAllFile();
			// Create a connection
			Connector connection = new Connector(config);
			
			RSParser parser;
			
			
			// If all countries should be checked
			if (CountriesReader.isItTP("equationCountries.txt")) {
				parser = new RSParser(connection.queryAllNegativ(exceptions));
			}
			// Else (if just some countries are asked to be checked)
			else {
				parser = new RSParser(connection.queryCountriesNegativ(exceptions, CountriesReader.readCountries("equationCountries.txt")));
			}
			

			

			// Write the result in an output file
			FileCreator exceptionCreated = new FileCreator(exceptFile);
			exceptionCreated.writeCountryFirst(parser.sortCountryFirst());
			
			
			
			
			
		} catch (IOException e) {
			System.out.println("Exceptions File couldn't be reached/read.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException, Stack trace following");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException, Stack trace following");
			e.printStackTrace();
		} catch (IncorrectCountryEntryException e) {
			System.out.println(e.getErrorMessage());
		}
		
		
		
		
		

		

	}
	
	
}
