package resultSetParser;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;

import equationHandler.YearValueDuo;



public class RSParser {

	private ResultSet rs;
	
	
	public RSParser(ResultSet rs) {
		this.rs = rs;
	}
	
	public LinkedList<CountryFirstData> sortCountryFirst() throws SQLException {
		
		LinkedList<CountryFirstData> list = new LinkedList<CountryFirstData>();
		
		String serie;
		String unit;
		String country;
		BigDecimal value;
		int year;
		int index;
		
		while (rs.next()) {
			
			serie = rs.getString(1);
			unit = rs.getString(2);
			country = rs.getString(3);
			value = rs.getBigDecimal(4);
			year = rs.getInt(5);
			if ((index = list.indexOf(new CountryFirstData(country, serie, unit))) != -1) {
				
				list.get(index).getDuo().add(new YearValueDuo(year, value));
				
			}
			else {
				
				CountryFirstData data = new CountryFirstData(country, serie, unit);
				data.getDuo().add(new YearValueDuo(year, value));
				list.add(data);
				
			}
			
			
			
		}
		Collections.sort(list);
		return list;
		
	}
	
}
