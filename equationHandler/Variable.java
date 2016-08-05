package equationHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import databaseQueries.UnexpectedMissingValue;
import testing.Config;

public class Variable extends Operand {

	private String country;
	private String unit;
	private boolean empty;
	
	private final Map<Integer, BigDecimal> entryMap = new HashMap<Integer, BigDecimal>();

	public Variable(String name, String country, String unit) {
		super(name);
		this.country = country;
		this.unit = unit;
		System.out.print("Variable " + name + " starting");
		this.queryValue(country);
	}

	
	public void queryValue(String country) {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@" + Config.serveur + ":1521/" + Config.database, 
					Config.login, 
					Config.password);
			Statement stmt = con.createStatement();
			String query = "SELECT tyear, valeur "
					+ "FROM Valeurs_tab "
					+ "WHERE Ticker = (SELECT numero FROM Series WHERE Code_serie = '"
					+ super.getName()
					+ "' AND code_pays = '" + country + "'"
					+ "AND unite = '"
					+ unit
					+ "') "
					+ "ORDER BY tyear";
			
			System.out.println(query);
			ResultSet 	rs = stmt.executeQuery(query);
			empty = true;
			
			while (rs.next()) {
				
				entryMap.put(rs.getInt(1), rs.getBigDecimal(2));
				if (!empty) {
					empty = false;
				}
			}
			con.close();

			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public BigDecimal getValue(int year) throws UnexpectedMissingValue {
		if (!empty) {
			BigDecimal value = entryMap.get(year);
			if (value == null) {
				throw new UnexpectedMissingValue(super.getName(), year);
			}
			else {
				return value;
			}
		}
		
		return BigDecimal.ZERO;
	
	}
	
}
