package equationHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import testing.Config;

public class Variable extends Operand {

	public Variable(String name) {
		super(name);
		System.out.print("Variable " + name + " starting");
		this.queryValue();
	}

	private final Map<Integer, BigDecimal> entryMap = new HashMap<Integer, BigDecimal>();
	
	public void queryValue() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@srv_oracle_prod:1521/bdenerdata.enerdata", 
					Config.login, 
					Config.password);
			Statement stmt = con.createStatement();
			String query = "SELECT tyear, valeur "
					+ "FROM Valeurs_tab "
					+ "WHERE Ticker = (SELECT numero FROM Series WHERE Code_serie = '"
					+ super.getName()
					+ "' AND code_pays = 'fra' AND unite = 'Mm3') "
					+ "ORDER BY tyear";
			
			System.out.println(query);
			ResultSet 	rs = stmt.executeQuery(query);
			
			
			while (rs.next()) {
				
				entryMap.put(rs.getInt(1), rs.getBigDecimal(2));
				
			}
			
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public BigDecimal getValue(int year) {
		
		return this.entryMap.get(year);
	}
	
}
