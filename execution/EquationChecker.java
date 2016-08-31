package execution;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;

import arithmeticParsing.Parser;
import binaryTree.TreeBuilder;
import equationHandler.Equation;
import reader.Configuration;
import reader.CountriesReader;
import reader.EquationDatas;
import reader.EquationReader;
import reader.IncorrectCountryEntryException;
import reader.IncorrectEntryFormatException;
import writer.FileCreator;

public class EquationChecker {

	public static void main(String[] args) {		
		
		int endYear;
		if (args.length == 0) {
			endYear = Calendar.getInstance().get(Calendar.YEAR);
		}
		else {
			endYear = Integer.parseInt(args[0]);
		}
		
		
		EquationReader reader;

		// The countries to check
		LinkedList<String> countries = null;
		
		Configuration config = null;
		try {
			config = new Configuration("configuration.txt");
		} catch (IOException e2) {
			System.out.println("IOException while reading configuration.txt. Make sure you respect the requirements in this file. ");
			e2.printStackTrace();
		}
		
		
		
		try {
			countries = CountriesReader.readCountries("equationCountries.txt");
		} catch (IncorrectCountryEntryException e1) {
			System.out.println(e1.getErrorMessage());
		} catch (IOException e) {
			
			System.out.println("IO Exception concerning equationsCountries.txt");
			e.printStackTrace();
		}

		
		
		try {
			reader = new EquationReader(config);
			String fileName = "CTREnergie.txt";
			FileCreator writer = new FileCreator(fileName);
			
			Iterator<String> countryItr = countries.iterator();
			
			while (countryItr.hasNext()) {
				
				String country = countryItr.next();
				Iterator<EquationDatas> itr = reader.getEquations().iterator();
				
				
				while (itr.hasNext()) {
					
					EquationDatas current = itr.next();
					
					Equation eq;
					
						try {
							eq = new Equation(current, country, config, endYear);
							Parser tokenParser = new Parser(eq.getTokens());

							TreeBuilder tree = new TreeBuilder(tokenParser.getOutput());
							
//							TreePrinter.print(tree.getTree());
							

							eq.queryBodyValue(tree);

							current.setErrors(eq.compare());
							current.setMissing(eq.getMissingValues());
							
//							eq.printBody();

//							eq.printMissingValues();
							
							eq.closeConnection();
							
							
						} catch (ClassNotFoundException | SQLException e) {
							System.out.println("Unknown problem while trying to connect to the database");
							e.printStackTrace();
						} catch (IncorrectEntryFormatException e) {
							System.out.println(e.getErrorMsg());
							
						} 
						
				}
			
				
				writer.write(reader.getEquations(), country, config);
				
				
			}
			
			
			
		} catch (IOException e) {
			System.out.println("Couldn't read the entry file 'equations'.");
			e.printStackTrace();
		} 
		
	
		
	}
	


}
