package databaseQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;

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
	
	
	public Connector() throws ClassNotFoundException, SQLException {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@" + Config.serveur + ":1521/" + Config.database, 
				Config.login, 
				Config.password);
		stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
	}
	
	
	public ResultSet query(String serie) throws SQLException {
		
		
		
		
		String query = "SELECT tyear, valeur "
				+ "FROM Valeurs_tab "
				+ "WHERE Ticker = (SELECT numero FROM Series WHERE Code_serie = '"
				+ serie
				+ "' AND code_pays = '" + this.country + "'"
				+ " AND unite = '"
				+ unit
				+ "') "
				+ "ORDER BY tyear";
		
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		

		return rs;

		
		
	}
	
	public ResultSet queryYears(String serie) throws SQLException {
		
		String query = "SELECT TYear "
				+ "FROM Valeurs_tab "
				+ "WHERE Ticker = (SELECT numero FROM Series WHERE Code_serie = '"
				+ serie
				+ "' AND code_pays = '" + this.country + "'"
				+ " AND unite = '"
				+ unit
				+ "') "
				+ "ORDER BY tyear";
		
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
//		while (rs.next()) {
//			System.out.println("lol");
//		}

		return rs;
	}
	
	public ResultSet queryAll(HashSet<String> except) throws SQLException {
		
		String query = 	"SELECT code_serie,unite,code_pays,valeur,tyear,p_titre_fra " +
						"FROM Pays P,Series S,Valeurs_tab V " + 
						"WHERE ROUND(V.valeur,6)<0 ";
		String and = "AND S.code_serie NOT LIKE ";
		
		Iterator<String> itr = except.iterator();
		while (itr.hasNext()) {
			
			query = query + and + "'" + itr.next() + "' " ;
			
		}
		query = query + "AND S.numero= V.ticker " +
						"AND P.p_code= S.code_pays " +
						"AND S.Numero=V.Ticker";
		
		ResultSet rs = stmt.executeQuery(query);
		
		return rs;
		
		
		
	}
	
	public void close() throws SQLException {
		
		con.close();
		
	}
}
