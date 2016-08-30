package reader;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import com.opencsv.CSVReader;

public class CountriesReader {

	
	public static final LinkedList<String> readCountries(String fileName) throws IOException, IncorrectCountryEntryException {
		
		
		LinkedList<String> countries = new LinkedList<String>();
		FileReader fr = new FileReader(fileName);
		CSVReader reader = new CSVReader(fr, ';');
		
		String[] nextLine = reader.readNext();
		nextLine = reader.readNext();
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
	
	public static final LinkedList<String> readCountriesTP(String fileName) throws IOException, IncorrectCountryEntryException {
		
		
		LinkedList<String> countries = new LinkedList<String>();
		FileReader fr = new FileReader(fileName);
		CSVReader reader = new CSVReader(fr, ';');
		
		String[] nextLine = reader.readNext();
		nextLine = reader.readNext();
		if (nextLine == null) {
			reader.close();
			throw new IncorrectCountryEntryException("Empty line found at line 1. Should be 'P'.");
		}
		
		switch (nextLine[0]) 
		{
		case "TP":
			countries.add("TP");
			break;
		case "P":
			boolean endFound = false;
			while ((nextLine = reader.readNext()) != null && !endFound) {
				
				if (nextLine[0].equals("END") || nextLine[0].contains("END")) {
					endFound = true;
				}
				else {
					countries.add(nextLine[0]);	
				}
				
				
			}
			break;
			
		default:
			
			reader.close();
			throw new IncorrectCountryEntryException("Weird line found at line 1. Should be 'P' followed by countries or 'TP' if asking for all countries. Try to remove any empty space.");
			
		}
		
		reader.close();
		return countries;
		
	}
	
	
	
	
}
