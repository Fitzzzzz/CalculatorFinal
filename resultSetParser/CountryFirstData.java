package resultSetParser;

import java.util.LinkedList;

import equationHandler.YearValueDuo;

public class CountryFirstData implements Comparable<Object> {

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


	private String serie;
	private String unit;
	private final LinkedList<YearValueDuo> duo = new LinkedList<YearValueDuo>();
	
	
	public CountryFirstData(String countryName, String countryCode, String serie, String unit) {
		
		super();
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


	@Override
	public int compareTo(Object arg0) {
		
		int tmp;
		
		CountryFirstData compared = (CountryFirstData) arg0;
		
		if (this.equals(compared)) {
			return 0;
		}
		
		if ((tmp = this.countryName.compareTo(compared.getCountry())) == 0) {
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
