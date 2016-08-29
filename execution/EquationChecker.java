package execution;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import arithmeticParsing.Parser;
import binaryTree.TreeBuilder;
import binaryTree.TreePrinter;
import equationHandler.Equation;
import reader.CountriesReader;
import reader.EquationDatas;
import reader.EquationReader;
import reader.IncorrectCountryEntryException;
import reader.IncorrectEntryFormatException;
import writer.FileCreator;

public class EquationChecker {

	public static void main(String[] args) {		
		
		EquationReader reader;
		LinkedList<String> countries = null;

		try {
			countries = CountriesReader.readFile("equationCountries.txt");
		} catch (IncorrectCountryEntryException e1) {
			System.out.println(e1.getErrorMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO Exception concerning equationsCountries.txt");
			e.printStackTrace();
		}

		
		
		try {
			reader = new EquationReader();
			String fileName = "file.txt";
			FileCreator writer = new FileCreator(fileName);
			
			Iterator<String> countryItr = countries.iterator();
			
			while (countryItr.hasNext()) {
				
				String country = countryItr.next();
				Iterator<EquationDatas> itr = reader.getEquations().iterator();
				
				
				while (itr.hasNext()) {
					
					EquationDatas current = itr.next();
					
					Equation eq;
					
						try {
							eq = new Equation(current, country);
							Parser tokenParser = new Parser(eq.getTokens());

							TreeBuilder tree = new TreeBuilder(tokenParser.getOutput());
							
							TreePrinter.print(tree.getTree());
							
//							System.out.println(eq.getEquation() + " starting querybody");
							eq.queryBodyValue(tree);
//							System.out.println(eq.getEquation() + " starting compare");
							current.setErrors(eq.compare());
//							System.out.println(eq.getEquation() + " starting printbody");
							eq.printBody();
//							System.out.println(eq.getEquation() + " starting printmissing");
							eq.printMissingValues();
							
							eq.closeConnection();
							
							
						} catch (ClassNotFoundException | SQLException e) {
							System.out.println("Unknown problem while trying to connect to the database");
							e.printStackTrace();
						} catch (IncorrectEntryFormatException e) {
							System.out.println(e.getErrorMsg());
							
						} 
						
				}
			
				
				writer.write(reader.getEquations(), country);
				
				
			}
			
			
			
		} catch (IOException e) {
			System.out.println("Couldn't read the entry file 'equations'.");
			e.printStackTrace();
		} 
		
	
		
	}
	


}
