package testing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

import arithmeticParsing.Parser;
import binaryTree.TreeBuilder;
import binaryTree.TreePrinter;
import databaseQueries.Connector;
import equationHandler.Equation;
import reader.EquationDatas;
import reader.EquationReader;
import reader.IncorrectEntryFormatException;
import resultSetParser.RSParser;
import writer.FileCreator;

public class TestGnacfinn {

	public static void main(String[] args) {
		
		
		long startTime = System.nanoTime();

		
		
		EquationReader reader;
		String codePays = "fra";
		String exceptFile = "negativeExceptions.txt";
		
		
		try {
			reader = new EquationReader();
			
			Iterator<EquationDatas> itr = reader.getEquations().iterator();
			
			
			while (itr.hasNext()) {
				
				EquationDatas current = itr.next();
				
				Equation eq;
				
					try {
						eq = new Equation(current, codePays);
						Parser tokenParser = new Parser(eq.getTokens());

						TreeBuilder tree = new TreeBuilder(tokenParser.getOutput());
						
						TreePrinter.print(tree.getTree());
						
						
						eq.queryBodyValue(tree);
						
						current.setErrors(eq.compare());
						
						eq.printBody();
						eq.printMissingValues();
						
						eq.closeConnection();
						
						
					} catch (ClassNotFoundException | SQLException e) {
						System.out.println("Unknown problem while trying to connect to the database");
						e.printStackTrace();
					} catch (IncorrectEntryFormatException e) {
						System.out.println(e.getErrorMsg());
					}
			}
		
			String fileName = "file.txt";
			FileCreator writer = new FileCreator(fileName);
			writer.write(reader.getEquations());
			
			
		} catch (IOException e) {
			System.out.println("Couldn't read the entry file 'equations'.");
			e.printStackTrace();
		}
		long controlEnd = System.nanoTime();

		EquationReader exceptionReader = new EquationReader(exceptFile);
		long queryNegativStart = 0;
		long queryNegativEnd = 0;
		long writeStart = 0;
		
		
		try {
			HashSet<String> exceptions = exceptionReader.readAllFile();
			Connector connection = new Connector();
			
			queryNegativStart = System.nanoTime();
			
			RSParser parser = new RSParser(connection.queryAll(exceptions));
			
			queryNegativEnd = System.nanoTime();

			FileCreator exceptionCreated = new FileCreator(exceptFile);
			
			writeStart = System.nanoTime();
			exceptionCreated.writeCountryFirst(parser.sortCountryFirst());
			
			
		} catch (IOException e) {
			System.out.println("Exceptions File couldn't be reached/read.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		long stopTime = System.nanoTime();
		
		System.out.println("Temps controle = " + ((controlEnd - startTime)/1000000000));
		System.out.println("Temps negativ = " + ((stopTime - controlEnd)/1000000000));
		System.out.println("Temps query negativ = " + ((queryNegativEnd - queryNegativStart)/1000000000));
		System.out.println("Temps ecriture negative = " + ((stopTime - writeStart)/1000000000));
		System.out.println("Temps total = " + ((stopTime - startTime)/1000000000));
		
		
	}
	


}
