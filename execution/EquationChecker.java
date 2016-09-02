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
		
		// Setting up the endYear
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
		
		// Reading configuration.txt to acquire informations concerning the database
		Configuration config = null;
		try {
			config = new Configuration("configuration.txt");
		} catch (IOException e2) {
			System.out.println("IOException while reading configuration.txt. Make sure you respect the requirements in this file. ");
			e2.printStackTrace();
		}
		
		
		// Reading the countries from equationCountries.txt
		try {
			countries = CountriesReader.readCountries("equationCountries.txt");
		} catch (IncorrectCountryEntryException e1) {
			System.out.println(e1.getErrorMessage());
		} catch (IOException e) {
			
			System.out.println("IO Exception concerning equationsCountries.txt");
			e.printStackTrace();
		}

		
		
		try {
			// Reading the "equations.txt" file to retrieve the equations and their parameters.
			reader = new EquationReader(config);
			String fileName = "CTREnergie.txt";
			FileCreator writer = new FileCreator(fileName);
			
			// Iterating on the countries.
			Iterator<String> countryItr = countries.iterator();
			
			// For each country
			while (countryItr.hasNext()) {
				
				String country = countryItr.next();
				// Iterating on the equations
				Iterator<EquationDatas> itr = reader.getEquations().iterator();
				
				// For each equation
				while (itr.hasNext()) {
					
					EquationDatas current = itr.next();
					
					Equation eq;
					
						try {
							// Create a new Equation to handle it.
							eq = new Equation(current, country, config, endYear);
							
							// Parse the precedent created tokens. Will sort them in the prefix order.
							Parser tokenParser = new Parser(eq.getTokens());
							// Now that the tokens are in prefix order, we can build a tree containing them :
							TreeBuilder tree = new TreeBuilder(tokenParser.getOutput());
							
							
							// Will go thru the tree to evaluate the equation
							eq.queryBodyValue(tree);

							// Getting the errors
							current.setErrors(eq.compare());
							// Getting the missing values
							current.setMissing(eq.getMissingValues());
							
							// Closing the connection.
							eq.closeConnection();
							
							
						} catch (ClassNotFoundException | SQLException e) {
							System.out.println("Unknown problem while trying to connect to the database");
							e.printStackTrace();
						} catch (IncorrectEntryFormatException e) {
							System.out.println(e.getErrorMsg());
							
						} 
						
				}
			
				// Writing in the output file the results
				writer.write(reader.getEquations(), country, config);
				
				
			}
			
			
			
		} catch (IOException e) {
			System.out.println("Couldn't read the entry file 'equations'.");
			e.printStackTrace();
		} 
		
	
		
	}
	


}
