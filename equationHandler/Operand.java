package equationHandler;

import java.math.BigDecimal;

import databaseQueries.UnexpectedMissingValueException;

public abstract class Operand extends Token{

	public Operand(String op) {
		
		super(op, 0);

	}
	
	public abstract BigDecimal getValue(int year) throws UnexpectedMissingValueException;

	
	
}
