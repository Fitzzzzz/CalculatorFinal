package databaseQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import reader.Configuration;

/**
 * A class handling the connection between the programm and the database
 * @author hamme
 *
 */
public class Connector {

	/**
	 * The unit we will have to ask for
	 */
	private String unit;
	/**
	 * The database we ask to
	 */
	private String database;
	/**
	 * The country whose values we want.
	 */
	private String country;
	
	private Connection con;
	
	private Statement stmt;
	

	/**
	 * Constructor.  Will get the missing informations from the configuration class.
	 * @param unit The unit we want the values to be in
	 * @param country The country we want the values to be from
	 * @param config the configuration file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connector(String unit, String country, Configuration config) throws ClassNotFoundException, SQLException {
		this.unit = unit;
		this.country = country;
		this.database = config.getDatabase();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@" + config.getServer() + ":1521/" + this.database, 
				config.getLogin(),
				config.getPassword());
		stmt = con.createStatement();
	}
	
	
	
	/**
	 * Constructor.  Will get the missing informations from the configuration class.
	 * @param config the configuration file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connector(Configuration config) throws ClassNotFoundException, SQLException {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@" + config.getServer() + ":1521/" + config.getDatabase(), 
				config.getLogin(), 
				config.getPassword());
		stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
	}
	
	/**
	 * Will perform a query asking for the values for the input serie.
	 * @param serie the serie we want the values from
	 * @return a ResultSet containing the values and the coressponding years
	 * @throws SQLException
	 */
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
		
//		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		

		return rs;

		
		
	}
	
	/**
	 * Will ask for the available years for a given serie
	 * @param serie The serie we want to know the years from
	 * @return a ResultSet containing the available years
	 * @throws SQLException
	 */
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
		
		ResultSet rs = stmt.executeQuery(query);

		return rs;
	}
	/**
	 * Will ask for all the negative series available in the database. Will ignore the one given in parameter.
	 * @param except The series to ignore
	 * @return a ResultSet containing the serie_code, the unit, the country_code, the value, the year and the french name of all the negative series.
	 * @throws SQLException
	 */
	public ResultSet queryAllNegativ(HashSet<String> except) throws SQLException {
		
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
						"AND S.Numero=V.Ticker ";

		ResultSet rs = stmt.executeQuery(query);
		
		return rs;
		
		
		
	}
	/**
	 * Will ask for for the negative series of the given countries. Will ignore the one given in parameter.
	 * @param except The series to ignore
	 * @param countries The countries we are searching the negativ values for
	 * @return a ResultSet containing the serie_code, the unit, the country_code, the value, the year and the french name of all the negative series.
	 * @throws SQLException
	 */
	public ResultSet queryCountriesNegativ(HashSet<String> except, LinkedList<String> countries) throws SQLException {
		
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
						"AND S.Numero=V.Ticker ";
		
		query = query + "AND ( ";
		
		Iterator<String> itrCountries = countries.iterator();
		String country = itrCountries.next();
		query = query + "P.p_code = '" + country + "' ";

		while (itrCountries.hasNext()) {
			
			country = itrCountries.next();
			query = query + "OR P.p_code = '" + country + "' ";
		}
		query = query + " ) ";

		ResultSet rs = stmt.executeQuery(query);
		
		return rs;
		
		
		
	}
	/**
	 * Will give the country name in exchange of the country code.
	 * @param countryCode The countryCode of the countryName that is asked for.
	 * @param config The configuration file for the connection with the database
	 * @return The name of the country
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static String queryCountryFromCode(String countryCode, Configuration config) throws SQLException, ClassNotFoundException {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@" + config.getServer() + ":1521/" + config.getDatabase(), 
				config.getLogin(), 
				config.getPassword());
		Statement state = connect.createStatement();
		String query = "SELECT P_Titre_Eng from pays WHERE p_code = '" + countryCode + "' ";
		ResultSet rs = state.executeQuery(query);
		rs.next();
		String country = rs.getString(1);
		connect.close();
		return country;
		
	}
	
	
	/**
	 * Closes the connection with the database.
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		
		con.close();
		
	}
}
