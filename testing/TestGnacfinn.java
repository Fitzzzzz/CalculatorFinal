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
import writer.FileCreator;

public class TestGnacfinn {

	public static void main(String[] args) {
		
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
		
		EquationReader exceptionReader = new EquationReader(exceptFile);

		try {
			HashSet<String> exceptions = exceptionReader.readAllFile();
			Connector connection = new Connector();
			FileCreator exceptionCreated = new FileCreator(exceptFile);
			exceptionCreated.write(connection.queryAll(exceptions));
			
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
		
		
		
		
		
		
		
	}
}
