package testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import arithmeticParsing.Parser;
import binaryTree.TreeBuilder;
import binaryTree.TreePrinter;
import databaseQueries.UnexpectedMissingValueException;
import equationHandler.Equation;

public class TestEquation {

	public static void main(String[] args) {

		String equation = "cueleagr=3+4*2/(1-5)"; 
		Equation eq = new Equation(equation, "fra", "GWh");
		Parser tokenParser = new Parser(eq.getTokens());
//		System.out.println(eq.getReceiver());
//		System.out.println(eq.getBody());
		System.out.println(tokenParser.getLList(tokenParser.getOutput()));
		TreeBuilder tree = new TreeBuilder(tokenParser.getOutput());
		TreePrinter.print(tree.getTree());
		try {
			System.out.println("sol = " + tree.postOrderEvaluation(57));
		} catch (UnexpectedMissingValueException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			
			
			int end = 53;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@srv_oracle_prod:1521/bdenerdata.enerdata", 
					Config.login, 
					Config.password);
			Statement stmt = con.createStatement();
			String query = "SELECT tyear, valeur "
					+ "FROM Valeurs_tab "
					+ "WHERE Ticker = (SELECT numero FROM Series WHERE Code_serie = '"
					+ "gnacfind"
					+ "' AND code_pays = 'fra' AND unite = 'Mm3') "
					+ "ORDER BY tyear";
			
			ResultSet rs = stmt.executeQuery(query);
			for (int i = 0; i < end; i++) 
			rs.next();
			System.out.print(rs.getInt(1) + "   ");
			System.out.println(rs.getBigDecimal(2));
			
			
			query = "SELECT tyear, valeur "
					+ "FROM Valeurs_tab "
					+ "WHERE Ticker = (SELECT numero FROM Series WHERE Code_serie = '"
					+ "gnacfnen"
					+ "' AND code_pays = 'fra' AND unite = 'Mm3') "
					+ "ORDER BY tyear";
		
			rs = stmt.executeQuery(query);
			for (int i = 0; i < end; i++) 
			rs.next();
			System.out.print(rs.getInt(1) + "   ");
			System.out.println(rs.getBigDecimal(2));
			
			
			query = "SELECT tyear, valeur "
					+ "FROM Valeurs_tab "
					+ "WHERE Ticker = (SELECT numero FROM Series WHERE Code_serie = '"
					+ "gnacfinn"
					+ "' AND code_pays = 'fra' AND unite = 'Mm3') "
					+ "ORDER BY tyear";
			
			rs = stmt.executeQuery(query);
			
			for (int i = 0; i < end; i++) 
			rs.next();
				System.out.print(rs.getInt(1) + "   ");
				System.out.println(rs.getBigDecimal(2));
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
 