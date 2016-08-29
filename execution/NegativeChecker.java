package execution;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;

import databaseQueries.Connector;
import reader.ExceptionsReader;
import resultSetParser.RSParser;
import writer.FileCreator;

public class NegativeChecker {

	public static void main(String[] args) {
		
		long startTime = System.nanoTime();
		String exceptFile = "negativeExceptions.txt";
		
		long controlEnd = System.nanoTime();

		ExceptionsReader exceptionReader = new ExceptionsReader(exceptFile);
		long queryNegativStart = 0;
		long queryNegativEnd = 0;
		long writeStart = 0;
		long writeEnd = 0;
		
		
		try {
			HashSet<String> exceptions = exceptionReader.readAllFile();
			Connector connection = new Connector();
			
			queryNegativStart = System.nanoTime();
			RSParser parser;
			
			
			parser = new RSParser(connection.queryAllNegativ(exceptions));
			
			
			queryNegativEnd = System.nanoTime();

			FileCreator exceptionCreated = new FileCreator(exceptFile);
			
			writeStart = System.nanoTime();
			exceptionCreated.writeCountryFirst(parser.sortCountryFirst());
			writeEnd = System.nanoTime();
			
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
		System.out.println("Temps ecriture negative et parsing = " + ((writeEnd - writeStart)/1000000000));
		System.out.println("Temps total = " + ((stopTime - startTime)/1000000000));
		
		
	}
	
	
}
