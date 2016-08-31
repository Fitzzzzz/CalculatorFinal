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
	
	
//	public SortedSet<CountryFirstData> sortCountryFirst() throws SQLException {
//
//		
////		rs.setFetchSize(5000);
//	    long parsingStart = System.nanoTime();
//
//	    SortedMap<CountryFirstData, CountryFirstData> identityMap = new TreeMap<>();
//	    //      Comparator.comparing(CountryFirstData::getCountry)
//	    //          .thenComparing(CountryFirstData::getSerie))
//	    //          .thenComparing(CountryFirstData::getUnit));
//
//	    while (rs.next()) {
//
//	        String serie = rs.getString(1); // rs is the previously built resultSet
//	        String unit = rs.getString(2);
//	        String country = rs.getString(3);
//	        BigDecimal value = rs.getBigDecimal(4);
//	        int year = rs.getInt(5);
//	        CountryFirstData data = new CountryFirstData(country, serie, unit);
//	        CountryFirstData oldData = identityMap.putIfAbsent(data, data);
//	        if (oldData != null) {
//	            data = oldData;
//	        }
//	        data.getDuo().add(new YearValueDuo(year, value));
//	    }
//	    long parsingEnd = System.nanoTime();
//	    
//	    System.out.println("Parsing Time = " + ((parsingEnd - parsingStart)/1000000000));
//
//	    return (SortedSet<CountryFirstData>) identityMap.keySet();
//	}
	
	public LinkedList<CountryFirstData> sortCountryFirst() throws SQLException {
		
		long parsingStart = System.nanoTime();

		
		LinkedList<CountryFirstData> list = new LinkedList<CountryFirstData>();
		
		String serie;
		String unit;
		String countryCode;
		BigDecimal value;
		int year;
		int index;
		String countryName;
//		long indexOf = 0;
//		int counter = 0;
		rs.setFetchSize(5000);
		while (rs.next()) {
			
			serie = rs.getString(1);
			unit = rs.getString(2);
			countryCode = rs.getString(3);
			value = rs.getBigDecimal(4);
			year = rs.getInt(5);
			countryName = rs.getString(6);
			
//			indexOf = System.nanoTime();
			CountryFirstData data = new CountryFirstData(countryName, countryCode, serie, unit);
//			if (counter > 3950) {
//				System.out.println((System.nanoTime() - indexOf));
//			}
			if ((index = list.indexOf(data)) != -1) {
				
				list.get(index).getDuo().add(new YearValueDuo(year, value));
				
			}
			else {
				
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
	
	
	
	
	
//	public LinkedList<CountryFirstData> sortCountryFirst() throws SQLException {
//		
//		long parsingStart = System.nanoTime();
//
//		
//		HashSet<CountryFirstData> list = new HashSet<CountryFirstData>();
//		
//		String serie;
//		String unit;
//		String country;
//		BigDecimal value;
//		int year;
//		int index;
////		long indexOf = 0;
////		int counter = 0;
//		rs.setFetchSize(5000);
//		
//		while (rs.next()) {
//			
//			serie = rs.getString(1);
//			unit = rs.getString(2);
//			country = rs.getString(3);
//			value = rs.getBigDecimal(4);
//			year = rs.getInt(5);
////			indexOf = System.nanoTime();
////			if (counter > 3950) {
////				System.out.println((System.nanoTime() - indexOf));
////			}
//			CountryFirstData data = new CountryFirstData(country, serie, unit);
//			if (!list.add(data)) {
//				list.remove(data);
//				data.getDuo().add(new YearValueDuo(year, value));
//				list.add(data);			
//			}
//			else {
//				
//				
//				
//			}
//			
////			counter++;
//			
//		}
//		long parsingEnd = System.nanoTime();
//
//		Collections.sort(list);
//		
//		long sortEnd = System.nanoTime();
//
////		System.out.println("index time = " + (indexOf/1000000000));
//		System.out.println("Parsing Time = " + ((parsingEnd - parsingStart)/1000000000));
//		System.out.println("Sorting Time = " + ((sortEnd - parsingEnd)/1000000000));
//
//		
//		return list;
//		
//	}
	
//	public List<CountryFirstData> sortCountryFirst() throws SQLException {
//
//		
//		
//	    long parsingStart = System.nanoTime();
//
//	    List<CountryFirstData> list = new ArrayList<>();
//	    CountryFirstData oldData = null;
//	    while (rs.next()) {
//	        String serie = rs.getString(1); // rs is the previously built resultSet
//	        String unit = rs.getString(2);
//	        String country = rs.getString(3);
//	        BigDecimal value = rs.getBigDecimal(4);
//	        int year = rs.getInt(5);
//	        CountryFirstData data = new CountryFirstData(country, serie, unit);
//	        if (oldData == null || data.compareTo(oldData) != 0) {
//	            oldData = data;
//	            list.add(data);
//	        }
//	        oldData.getDuo().add(new YearValueDuo(year, value));
//	    }
//	    long parsingEnd = System.nanoTime();
//
//	    System.out.println("Parsing Time = " + ((parsingEnd - parsingStart)/1_000_000_000L));
//
//	    return list;
//	}
	
}
