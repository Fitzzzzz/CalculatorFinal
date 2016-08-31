package execution;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import databaseQueries.Connector;
import reader.Configuration;
import reader.CountriesReader;
import reader.ExceptionsReader;
import reader.IncorrectCountryEntryException;
import resultSetParser.RSParser;
import writer.FileCreator;

public class NegativeChecker {

	public static void main(String[] args) {
		
		long startTime = System.nanoTime();
		String exceptFile = "negativCountries.txt";
		
		long controlEnd = System.nanoTime();

		ExceptionsReader exceptionReader = new ExceptionsReader(exceptFile);
		long queryNegativStart = 0;
		long queryNegativEnd = 0;
		long writeStart = 0;
		long writeEnd = 0;
		
		
		Configuration config = null;
		try {
			config = new Configuration("configuration.txt");
		} catch (IOException e2) {
			System.out.println("IOException while reading configuration.txt. Make sure you respect the requirements in this file. ");
			e2.printStackTrace();
		}
		
		try {
			
			
			
					
			HashSet<String> exceptions = exceptionReader.readAllFile();
			Connector connection = new Connector(config);
			
			queryNegativStart = System.nanoTime();
			RSParser parser;
			
			LinkedList<String> countries = CountriesReader.readCountriesTP("equationCountries.txt");
			Iterator<String> countryItr = countries.iterator();
			
			String country = countryItr.next();
			
			if (country.equals("TP")) {
				
				parser = new RSParser(connection.queryAllNegativ(exceptions));
				
			}
			
			else {
				
				parser = new RSParser(connection.queryCountriesNegativ(exceptions, countries));
				
			}
			
			queryNegativEnd = System.nanoTime();

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
		
		
		
		
		
		long stopTime = System.nanoTime();
		
		System.out.println("Temps controle = " + ((controlEnd - startTime)/1000000000));
		System.out.println("Temps negativ = " + ((stopTime - controlEnd)/1000000000));
		System.out.println("Temps query negativ = " + ((queryNegativEnd - queryNegativStart)/1000000000));
		System.out.println("Temps ecriture negative et parsing = " + ((writeEnd - writeStart)/1000000000));
		System.out.println("Temps total = " + ((stopTime - startTime)/1000000000));
		
		
	}
	
	
}
