package equationHandler;

import java.math.BigDecimal;

public class YearValueDuo {

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
	
}
