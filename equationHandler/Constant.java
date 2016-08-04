package equationHandler;

import java.math.BigDecimal;

public class Constant extends Operand {

	public Constant(String name, int value) {
		super(name);
		this.value = value;
		
	}
	
	private int value;

	public int getValue() {
		return value;
	}

	@Override
	public BigDecimal getValue(int year) {
		// TODO Auto-generated method stub
		return new BigDecimal(this.getValue());
	} 
	
	
}
