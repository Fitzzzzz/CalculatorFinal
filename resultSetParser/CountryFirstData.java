package resultSetParser;

import java.util.LinkedList;

import equationHandler.YearValueDuo;

/**
 * A class storing the negative exceptions data for a country/serial/Unit trio.
 * @author hamme
 *
 */
public class CountryFirstData implements Comparable<Object> {

	/**
	 * The country-code
	 */
	private String countryCode;
	
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * The name of the country
	 */
	private String countryName;
	public String getCountry() {
		return countryName;
	}


	public String getSerie() {
		return serie;
	}


	public String getUnit() {
		return unit;
	}


	public LinkedList<YearValueDuo> getDuo() {
		return duo;
	}

	/** 
	 * The serial
	 */
	private String serie;
	/**
	 * It's unit
	 */
	private String unit;
	
	/**
	 * The linked list of negative Year-Value couples
	 */
	private final LinkedList<YearValueDuo> duo = new LinkedList<YearValueDuo>();
	
	/**
	 * Constructor
	 * @param countryName
	 * @param countryCode
	 * @param serie
	 * @param unit
	 */
	public CountryFirstData(String countryName, String countryCode, String serie, String unit) {
		
		super();
		this.countryCode= countryCode;
		this.countryName = countryName;
		this.serie = serie;
		this.unit = unit;
		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryFirstData other = (CountryFirstData) obj;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (serie == null) {
			if (other.serie != null)
				return false;
		} else if (!serie.equals(other.serie))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}

	/**
	 * Sorts the {@link #CountryFirstData} alphabetically by country first, then by serial, then by unit to write them off easier later
	 */
	@Override
	public int compareTo(Object arg0) {
		
		int tmp;
		
		CountryFirstData compared = (CountryFirstData) arg0;
		
		if (this.equals(compared)) {
			return 0;
		}
		
		if ((tmp = this.countryCode.compareTo(compared.getCountryCode())) == 0) {
			if ((tmp = this.serie.compareTo(compared.getSerie())) == 0) {
				return this.unit.compareTo(compared.getUnit());
			}
			else {
				return tmp;
			}
		}
		else {
			return tmp;
		}
	}



	
	
	
	
	
	
	
}
