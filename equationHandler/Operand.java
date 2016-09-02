package equationHandler;

import java.math.BigDecimal;

import databaseQueries.UnexpectedMissingValueException;


/**
 * Tokens that are also operands.
 * @author hamme
 *
 */
public abstract class Operand extends Token{

	
	/**
	 * Constructor, will a token of type 0.
	 * @param op It's name
	 * 
	 */
	public Operand(String op) {
		
		super(op, 0);

	}
	
	/**
	 * Returns the value of this operand for the asked year.
	 * @param year The year you want the value for.
	 * @return The value of the operand at the given year
	 * @throws UnexpectedMissingValueException if the value is unexpectedly missing.
	 */
	public abstract BigDecimal getValue(int year) throws UnexpectedMissingValueException;

	
	
}
