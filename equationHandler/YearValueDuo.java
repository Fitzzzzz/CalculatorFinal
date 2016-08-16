package equationHandler;

import java.math.BigDecimal;

public class YearValueDuo implements Comparable<Object> {

	private BigDecimal value;
	public BigDecimal getValue() {
		return value;
	}

	private int year;
	public int getYear() {
		return year;
	}

	public YearValueDuo(int year, BigDecimal value) {
		
		this.year = year;
		this.value = value;
		
	}

	@Override
	public int compareTo(Object o) {
		
		YearValueDuo compared = (YearValueDuo) o;
		if (this.year == compared.getYear()) {
			return 0;
		}
		else if (this.year > compared.getYear()) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
}
