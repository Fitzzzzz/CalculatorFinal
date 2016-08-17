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
		
		long parsingStart = System.nanoTime();

		
		LinkedList<CountryFirstData> list = new LinkedList<CountryFirstData>();
		
		String serie;
		String unit;
		String country;
		BigDecimal value;
		int year;
		int index;
//		long indexOf = 0;
//		int counter = 0;
		rs.setFetchSize(5000);
		while (rs.next()) {
			
			serie = rs.getString(1);
			unit = rs.getString(2);
			country = rs.getString(3);
			value = rs.getBigDecimal(4);
			year = rs.getInt(5);
//			indexOf = System.nanoTime();
			index = list.indexOf(new CountryFirstData(country, serie, unit));
//			if (counter > 3950) {
//				System.out.println((System.nanoTime() - indexOf));
//			}
			if (index != -1) {
				
				list.get(index).getDuo().add(new YearValueDuo(year, value));
				
			}
			else {
				
				CountryFirstData data = new CountryFirstData(country, serie, unit);
				data.getDuo().add(new YearValueDuo(year, value));
				list.add(data);
				
			}
			
//			counter++;
			
		}
		long parsingEnd = System.nanoTime();

		Collections.sort(list);
		
		long sortEnd = System.nanoTime();

//		System.out.println("index time = " + (indexOf/1000000000));
		System.out.println("Parsing Time = " + ((parsingEnd - parsingStart)/1000000000));
		System.out.println("Sorting Time = " + ((sortEnd - parsingEnd)/1000000000));

		
		return list;
		
	}
	
}
