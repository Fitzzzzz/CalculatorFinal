package reader;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import com.opencsv.CSVReader;

/**
 * A reader that will read the file that contains all the countries to be checked.
 * Has no constructor, works only in a static way. 
 * First, the file should be tested by isItTP() method and if the method returns false, you can call readCountries() to get the concerned countries.
 * @author hamme
 *
 */
public class CountriesReader {

	/**
	 * To use only if {@link #isItTP(String)} returned false.
	 * Reads the file which has to be written in a very specific way.
	 * First line consists only of a comment.
	 * Second line needs to contain 'P'. 
	 * The following lines each contain a country-code. 
	 * Last line contains 'END'
	 * Note that here, the first line is counted as line '0'.
	 * @param fileName the name of the file to read.
	 * @return the list of the countries
	 * @throws IOException in case the file hasn't been found or similar IO Exception stuff.
	 * @throws IncorrectCountryEntryException if the file isn't written as it should.
	 */
	public static final LinkedList<String> readCountries(String fileName) throws IOException, IncorrectCountryEntryException {
		
		
		LinkedList<String> countries = new LinkedList<String>();
		FileReader fr = new FileReader(fileName);
		CSVReader reader = new CSVReader(fr, ';');
		
		String[] nextLine = reader.readNext();
		nextLine = reader.readNext();
		// Testing line 2
		if (nextLine == null) {
			reader.close();
			throw new IncorrectCountryEntryException("Empty line found at line 1. Should be 'P'.");
		}
		else if (!nextLine[0].equals("P")) {
			reader.close();
			throw new IncorrectCountryEntryException("Weird line found at line 1. Should be 'P'. Try to remove any empty space.");
		}
		boolean endFound = false;
		while ((nextLine = reader.readNext()) != null && !endFound) {
			
			// Using contain in case there is an unexpected space
			if (nextLine[0].equals("END") || nextLine[0].contains("END")) {
				endFound = true;
			}
			else {
				countries.add(nextLine[0]);	
			}
			
			
		}
		
		reader.close();
		return countries;
		
	}
	

	/**
	 * Will test if the file contains either 'TP' or 'P' on the second line.
	 * @param fileName The name of the file to read from
	 * @return true if it contains 'TP', false if it contains 'P'
	 * @throws IOException in case the file hasn't been found or similar IO Exception stuff.
	 * @throws IncorrectCountryEntryExceptionif the file isn't written as it should.
	 */
	public static final boolean isItTP(String fileName) throws IOException, IncorrectCountryEntryException {
		
		FileReader fr = new FileReader(fileName);
		CSVReader reader = new CSVReader(fr, ';');
		
		String[] nextLine = reader.readNext();
		nextLine = reader.readNext();
		// Testing line 2
		if (nextLine == null) {
			reader.close();
			throw new IncorrectCountryEntryException("Empty line found at line 1. Should be 'P' or 'TP'.");
		}
		else if (nextLine[0].equals("TP")) {
			reader.close();
			return true;
		}
		else if (nextLine[0].equals("P")){
			reader.close();
			return false;
		}
		else {
			reader.close();
			throw new IncorrectCountryEntryException("Weird line found at line 1. Should be 'P' followed by countries or 'TP' if asking for all countries. Try to remove any empty space.");
		}
		
		
		
	}
	
	
	
	
}
