package writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
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
	
	public void write(LinkedList<EquationDatas> equations) throws IOException {
		
		OpenOption[] options = {StandardOpenOption.CREATE, StandardOpenOption.WRITE};
		Charset charset = Charset.forName("UTF-8");
	    Path path = FileSystems.getDefault().getPath("output", fileName);
	    BufferedWriter writer = Files.newBufferedWriter(path, charset, options);
	    this.writer = writer;
		Iterator<EquationDatas> itr = equations.iterator();
		writeString("FRA");
		while (itr.hasNext()) {
			
			
			System.out.println("itr Has next!");
			EquationDatas current = itr.next();
			LinkedList<YearValueDuo> errors = current.getErrors();
			
			if (!errors.isEmpty()) {
				writer.newLine();
				writer.newLine();
				writeString("Equation : " + current.getEquation());
				writer.newLine();
				writeString("Unité : " + current.getUnit());
				writer.newLine();
				
				writeString("Année  :    ");
				
				Iterator<YearValueDuo> itrDuo = errors.iterator();
				
				while (itrDuo.hasNext()) {
					YearValueDuo duo = itrDuo.next();
					int year = duo.getYear();
					writeString(year + "    ");
				}
				
			
				writer.newLine();
				itrDuo = errors.iterator();
				writeString("Valeur :    ");
				
				while (itrDuo.hasNext()) {
					YearValueDuo duo = itrDuo.next();
					BigDecimal value = duo.getValue();
					int spaces = 8 - value.toString().length();
					String valueToPrint = "";
					if (spaces > 0) {
						for (int i = 0; i < spaces; i++)
						valueToPrint = valueToPrint + " ";
					}
					writeString(value + valueToPrint);
				}
			}
			
			
		}
		writer.close();
		
		
	}
	
	public void write(ResultSet rs) throws IOException, SQLException {
		
		OpenOption[] options = {StandardOpenOption.CREATE, StandardOpenOption.WRITE};
		Charset charset = Charset.forName("UTF-8");
	    Path path = FileSystems.getDefault().getPath("output", "negativExceptions.txt");
	    BufferedWriter writer = Files.newBufferedWriter(path, charset, options);
	    this.writer = writer;
	    
	    String serie = "";
	    rs.next();
	    while (rs.next()) {
	    	
	    	rs.previous();
	    	writer.newLine();
	    	
	    	serie = rs.getString(1);
	    	
	    	writeString(serie);
	    	
	    	writer.newLine();
	    	writeString(rs.getString(2) + "   " + rs.getString(3) + "    " + rs.getBigDecimal(4) + "     " + rs.getInt(5));
    		rs.next();
    		
    		while (rs != null && rs.getString(1).equals(serie)) {
	    		writeString(rs.getString(2) + "   " + rs.getString(3) + "    " + rs.getBigDecimal(4) + "     " + rs.getInt(5));
	    		writer.newLine();
	    		rs.next();
	    		
			}
    		
    		writer.newLine();
    			
	    
	    	
	    }
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
	    
	    Iterator<CountryFirstData> itr = list.iterator();
	    
	    // CHECK IF FIRST NOT MISSING !
	    
	    while(itr.hasNext()) {
	    	
	    	CountryFirstData current = itr.next();
	    	
	    	if (!country.equals(current.getCountry())) {

	    		country = current.getCountry();
	    		writer.newLine();
	    		writeString(country);
	    		writer.newLine();
	    		writeString("=========================================================================");
	    		
	    	}
	    	
	    	LinkedList<YearValueDuo> duo = current.getDuo();
    		Collections.sort(duo);
    		Iterator<YearValueDuo> duoItr = duo.iterator();
    		
    		// CHECK IF FIRST NOT MISSING
    		while (duoItr.hasNext()) {
    			YearValueDuo currentDuo = duoItr.next();
    			writer.newLine();
	    		writeString(current.getSerie() 
	    				+ "            " 
	    				+ current.getUnit() 
	    				+ "           " 
	    				+ currentDuo.getYear() 
	    				+ "         " 
	    				+ currentDuo.getValue());
	    		

    		}
    		writer.newLine();
	    	
	    }
	    writer.close();
	    long writeCountryFirstEnd = System.nanoTime();
		System.out.println("Temps writeCountry = " + ((writeCountryFirstEnd - writeCountryFirstStart)/1000000000));
	}
	
	public void writeString(String msg) throws IOException {
		
		writer.write(msg);
		
		
	}
	
}
