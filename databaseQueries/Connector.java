package databaseQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import testing.Config;

public class Connector {

	private String unit;
	private String database;
	private String startYear;
	private String endYear;
	private String temporality;
	private String country;
	private Connection con;
	private Statement stmt;
	
	public Connector(String unit, String country, String database, String startYear, String endYear, String temporality) throws ClassNotFoundException, SQLException {
		
		this.unit = unit;
		this.database = database;
		this.startYear = startYear;
		this.endYear = endYear;
		this.temporality = temporality;
		this.country = country;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@" + Config.serveur + ":1521/" + this.database, 
				Config.login, 
				Config.password);
		stmt = con.createStatement();
	}
	
	public Connector(String unit, String country, String startYear, String endYear, String temporality) throws ClassNotFoundException, SQLException {
	
		this.unit = unit;
		this.country = country;
		this.database = Config.database;
		this.startYear = startYear;
		this.endYear = endYear;
		this.temporality = temporality;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@" + Config.serveur + ":1521/" + this.database, 
				Config.login, 
				Config.password);
		stmt = con.createStatement();
	}
	
	public Connector(String unit, String country) throws ClassNotFoundException, SQLException {
		this.unit = unit;
		this.country = country;
		this.database = Config.database;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@" + Config.serveur + ":1521/" + this.database, 
				Config.login, 
				Config.password);
		stmt = con.createStatement();
	}
	
	public Connector(String unit, String country, String database) throws ClassNotFoundException, SQLException {
		this.unit = unit;
		this.country = country;
		this.database = database;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@" + Config.serveur + ":1521/" + this.database, 
				Config.login, 
				Config.password);
		stmt = con.createStatement();
	}
	
	public ResultSet query(String serie) throws ClassNotFoundException, SQLException {
		
		
		
		
		String query = "SELECT tyear, valeur "
				+ "FROM Valeurs_tab "
				+ "WHERE Ticker = (SELECT numero FROM Series WHERE Code_serie = '"
				+ serie
				+ "' AND code_pays = '" + this.country + "'"
				+ "AND unite = '"
				+ unit
				+ "') "
				+ "ORDER BY tyear";
		
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		

		return rs;

		
		
	}
	
	public void close() throws SQLException {
		con.close();
	}
	
}
