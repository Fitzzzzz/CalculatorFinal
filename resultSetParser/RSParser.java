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
		String countryCode;
		BigDecimal value;
		int year;
		int index;
		String countryName;

		rs.setFetchSize(5000);
		while (rs.next()) {
			
			serie = rs.getString(1);
			unit = rs.getString(2);
			countryCode = rs.getString(3);
			value = rs.getBigDecimal(4);
			year = rs.getInt(5);
			countryName = rs.getString(6);
			

			CountryFirstData data = new CountryFirstData(countryName, countryCode, serie, unit);


			if ((index = list.indexOf(data)) != -1) {
				
				list.get(index).getDuo().add(new YearValueDuo(year, value));
				
			}
			else {
				
				data.getDuo().add(new YearValueDuo(year, value));
				list.add(data);
				
			}
			
			
		}


		Collections.sort(list);
		



		
		return list;
		
	}
	
	
	
	
	
}
