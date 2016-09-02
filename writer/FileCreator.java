package writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import databaseQueries.Connector;
import equationHandler.YearValueDuo;
import reader.Configuration;
import reader.EquationDatas;
import resultSetParser.CountryFirstData;

/**
 * A class dedicated to writing in files.
 * @author hamme
 *
 */
public class FileCreator {

	/**
	 * The writer
	 */
	private BufferedWriter writer;
	/**
	 * The name of the file to write in
	 */
	private String fileName;

	public FileCreator(String fileName) throws IOException {

		this.fileName = fileName;
		

	}
	
	
	/**
	 * Will go thru the equations searching for the errors and the missing values and will write them off for the given country.
	 * Gets called by the EquationChecker.
	 * @param equations The equations to go thru
	 * @param countryCode The country code
	 * @param config The configuration file to connect to the database
	 * @throws IOException
	 */
	public void write(LinkedList<EquationDatas> equations, String countryCode, Configuration config) throws IOException {
		
		OpenOption[] options = {StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND};
		Charset charset = Charset.forName("UTF-8");
	    Path path = FileSystems.getDefault().getPath("output", fileName);
	    BufferedWriter writer = Files.newBufferedWriter(path, charset, options);
	    this.writer = writer;
		Iterator<EquationDatas> itr = equations.iterator();
		String country;
		try {
			country = Connector.queryCountryFromCode(countryCode, config);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't access to country name from country code : '" + countryCode + "'");
			country = "";
		}
		writeString(country + " (" + countryCode + ") "); 
		writer.newLine();
		writeString("====================================================================================");
		
		int defaultSpace = 14;
		boolean starUsed = false;
		
		
		while (itr.hasNext()) {
			
			
			
			
			EquationDatas current = itr.next();
			LinkedList<YearValueDuo> errors = current.getErrors();
			
			if (!errors.isEmpty()) {
				
				writer.newLine();
				writer.newLine();
				writeString("Equation : " + current.getEquation());
				writer.newLine();
				writeString("Unité  :    " + current.getUnit());
				writer.newLine();
				
				writeString("Année  :    ");
				
				Iterator<YearValueDuo> itrDuo = errors.iterator();
				
				while (itrDuo.hasNext()) {
					YearValueDuo duo = itrDuo.next();
					int year = duo.getYear();
					writeString(year + "          ");
				}
				
			
				writer.newLine();
				itrDuo = errors.iterator();
				writeString("Valeur :    ");
				
				while (itrDuo.hasNext()) {
					YearValueDuo duo = itrDuo.next();
					BigDecimal value = duo.getValue();
					
					if (value.toString().length() > 13) {
						writeString(value.setScale(7, RoundingMode.HALF_UP) + "*" + calculateSpace(11, defaultSpace));
						starUsed = true;
					}
					else {
						writeString(value + calculateSpace(value.toString().length(), defaultSpace));
					}
					
					
					
					
				}
			}
			Map<String, Set<Integer>> missing = current.getMissing();
			
			if (!missing.isEmpty()) {
				writer.newLine();
				Iterator<String> seriesItr = missing.keySet().iterator();
				while (seriesItr.hasNext()) {
					String serie = seriesItr.next();
					Set<Integer> missingYears = missing.get(serie);
					if (!missingYears.isEmpty()) {
						writer.newLine();
						writeString("Missing years for " + serie + " : ");
						Iterator<Integer> yearItr = missingYears.iterator();
						
						while (yearItr.hasNext()) {
							Integer year = yearItr.next();
							writeString(year + " ");
							
						}
						
					}
 					
				}
				writer.newLine();
				
			}
			
			
		}
		if (starUsed) {
			writer.newLine();
			writer.newLine();
			writeString("Numbers followed by a * star are rounded for display advantages.");
		}
		writer.newLine();
		writer.newLine();
		writeString("====================================================================================");
		writer.newLine();
		writer.newLine();
		writer.close();
		
		
	}
	

	/**
	 * Will write off the negative exceptions that were found. Gets called by NegativeChecker.
	 * @param list the list of data to go thru
	 * @throws IOException
	 */
	public void writeCountryFirst(List<CountryFirstData> list) throws IOException {


		OpenOption[] options = {StandardOpenOption.CREATE, StandardOpenOption.WRITE};
		Charset charset = Charset.forName("UTF-8");
	    Path path = FileSystems.getDefault().getPath("output", "negativExceptions.txt");
	    BufferedWriter writer = Files.newBufferedWriter(path, charset, options);
	    this.writer = writer;
	    
		String country = "";
		int defaultSpace = 10;
	    
	    Iterator<CountryFirstData> itr = list.iterator();
	    

	    
	    while(itr.hasNext()) {
	    	
	    	CountryFirstData current = itr.next();
	    	
	    	if (!country.equals(current.getCountryCode())) {

	    		country = current.getCountryCode();
	    		writer.newLine();
	    		writeString(current.getCountry() + " (" + country + ")");
	    		writer.newLine();
	    		writeString("================================================================================");
	    		
	    	}
	    	
	    	LinkedList<YearValueDuo> duo = current.getDuo();
    		Collections.sort(duo);
    		Iterator<YearValueDuo> duoItr = duo.iterator();
    		
    		while (duoItr.hasNext()) {
    			YearValueDuo currentDuo = duoItr.next();
    			writer.newLine();
    			String currentSerie = current.getSerie();
    			String currentUnit = current.getUnit();
    			int currentYear = currentDuo.getYear();
    			BigDecimal currentValue = currentDuo.getValue();
    			
    		
    			
    			String toWrite = currentSerie + calculateSpace(currentSerie.length(), defaultSpace)
    								+ currentUnit + calculateSpace(currentUnit.length(), defaultSpace)
    								+ currentYear + calculateSpace(4, defaultSpace)
    								+ currentValue;
    			
    			
    			writeString(toWrite);
    			

    		}
    		writer.newLine();
	    	
	    }
	    
	    writer.close();

	}

	/**
	 * Used to calculate the space to leave between values.
	 * @param previousLength
	 * @param defaultSpaceLength
	 * @return
	 */
	private String calculateSpace(int previousLength, int defaultSpaceLength) {
		
		String space = "";
		for (int i = 0; i < defaultSpaceLength - previousLength; i++)  
			space += " ";
		return space;
	}
	
	/**
	 * Writes the msg.
	 * @param msg
	 * @throws IOException
	 */
	public void writeString(String msg) throws IOException {
		
		writer.write(msg);
		
		
	}
	
}
