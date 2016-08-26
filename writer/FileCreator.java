package writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import equationHandler.YearValueDuo;
import reader.EquationDatas;
import resultSetParser.CountryFirstData;

public class FileCreator {

	private BufferedWriter writer;
	private String fileName;

	public FileCreator(String fileName) throws IOException {

		this.fileName = fileName;
		

	}
	
	public void write(LinkedList<EquationDatas> equations, String country) throws IOException {
		
		OpenOption[] options = {StandardOpenOption.CREATE, StandardOpenOption.WRITE};
		Charset charset = Charset.forName("UTF-8");
	    Path path = FileSystems.getDefault().getPath("output", fileName);
	    BufferedWriter writer = Files.newBufferedWriter(path, charset, options);
	    this.writer = writer;
		Iterator<EquationDatas> itr = equations.iterator();
		
		writeString(country + " (code?)"); // TODO : ask for country code if coutnry given, the opposite otherwise
		writer.newLine();
		writeString("====================================================================================");
		
		int defaultSpace = 14;
		boolean starUsed = false;
		
		
		while (itr.hasNext()) {
			
			
			
			System.out.println("itr Has next!"); // TODO : remove
			EquationDatas current = itr.next();
			LinkedList<YearValueDuo> errors = current.getErrors();
			
			if (!errors.isEmpty()) {
				System.out.println("errors is not empty"); // TODO : remove
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
			
			
		}
		if (starUsed) {
			writer.newLine();
			writer.newLine();
			writeString("Numbers followed by a * star are rounded for display advantages.");
		}
		writer.newLine();
		writer.newLine();
		writeString("====================================================================================");
		writer.close();
		
		
	}
	

	public void writeCountryFirst(List<CountryFirstData> list) throws IOException {
	
		long writeCountryFirstStart = System.nanoTime();

		OpenOption[] options = {StandardOpenOption.CREATE, StandardOpenOption.WRITE};
		Charset charset = Charset.forName("UTF-8");
	    Path path = FileSystems.getDefault().getPath("output", "negativExceptions.txt");
	    BufferedWriter writer = Files.newBufferedWriter(path, charset, options);
	    this.writer = writer;
	    
		String country = "";
		int defaultSpace = 10;
	    
	    Iterator<CountryFirstData> itr = list.iterator();
	    
	    // CHECK IF FIRST NOT MISSING !
	    
	    while(itr.hasNext()) {
	    	
	    	CountryFirstData current = itr.next();
	    	
	    	if (!country.equals(current.getCountry())) {

	    		country = current.getCountry();
	    		writer.newLine();
	    		writeString(country + " (" + current.getCountryCode() + ")");
	    		writer.newLine();
	    		writeString("================================================================================");
	    		
	    	}
	    	
	    	LinkedList<YearValueDuo> duo = current.getDuo();
    		Collections.sort(duo);
    		Iterator<YearValueDuo> duoItr = duo.iterator();
    		
    		// CHECK IF FIRST NOT MISSING
    		while (duoItr.hasNext()) {
    			YearValueDuo currentDuo = duoItr.next();
    			writer.newLine();
    			String currentSerie = current.getSerie();
    			String currentUnit = current.getUnit();
    			int currentYear = currentDuo.getYear();
    			BigDecimal currentValue = currentDuo.getValue();
    			
    			String space = calculateSpace(currentSerie.length(), defaultSpace);
    			
    			String toWrite = currentSerie + calculateSpace(currentSerie.length(), defaultSpace)
    								+ currentUnit + calculateSpace(currentUnit.length(), defaultSpace)
    								+ currentYear + calculateSpace(4, defaultSpace)
    								+ currentValue;
    			
    			
    			writeString(toWrite);
    			
//	    		writeString(current.getSerie()
//	    				+ "                  " 
//	    				+ current.getUnit() 
//	    				+ "                  " 
//	    				+ currentDuo.getYear() 
//	    				+ "                  " 
//	    				+ currentDuo.getValue());
	    		

    		}
    		writer.newLine();
	    	
	    }
	    
	    writer.close();
	    long writeCountryFirstEnd = System.nanoTime();
		System.out.println("Temps writeCountry = " + ((writeCountryFirstEnd - writeCountryFirstStart)/1000000000));
	}
	
	private String calculateSpace(int previousLength, int defaultSpaceLength) {
		
		String space = "";
		for (int i = 0; i < defaultSpaceLength - previousLength; i++)  
			space += " ";
		return space;
	}
	
	public void writeString(String msg) throws IOException {
		
		writer.write(msg);
		
		
	}
	
}
