package testing;

import java.io.IOException;
import java.sql.SQLException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.Iterator;

import arithmeticParsing.Parser;
import binaryTree.TreeBuilder;
import binaryTree.TreePrinter;
import equationHandler.Equation;
import reader.EquationDatas;
import reader.EquationReader;
import reader.IncorrectEntryFormatException;

public class TestGnacfinn {

	public static void main(String[] args) {
		
//		
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@" + Config.serveur + ":1521/" + Config.database, 
//					Config.login, 
//					Config.password);
//			Statement stmt = con.createStatement();
//			String query = "SELECT tyear, valeur "
//					+ "FROM Valeurs_tab "
//					+ "WHERE Ticker = (SELECT numero FROM Series WHERE Code_serie = '"
//					+ "ethpd"
//					+ "' AND code_pays = '" + "fra" + "'"
//					+ " AND unite = '"
//					+ "GWh"
//					+ "') "
//					+ "ORDER BY tyear";
//			ResultSet rs = stmt.executeQuery(query);
//			while (rs.next()) {
//				System.out.println("un rs de plus");
//			}
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		
		EquationReader reader;
		try {
			reader = new EquationReader();
			String equation = "ethpd-(ecmpd+eptpd+egzpd+ebipd+evapd)=0";
			String codePays = "fra";
			
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
						eq.compare();
						eq.printBody();
						eq.printMissingValues();
						
					} catch (ClassNotFoundException | SQLException e) {
						System.out.println("Unknown problem while trying to connect to the database");
						e.printStackTrace();
					} catch (IncorrectEntryFormatException e) {
						System.out.println(e.getErrorMsg());
					}
			}
		} catch (IOException e) {
			System.out.println("Couldn't read the entry file 'equations'.");
		}
		
		
		
		
		
		
	}
}
