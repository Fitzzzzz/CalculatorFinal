package equationHandler;

/**
 * Tokens which got a priority : everything but operands.
 * @author hamme
 *
 */
public abstract class PriorityToken extends Token {

	/**
	 * Constructor
	 * @param op the String representation of the token
	 * @param type its type, 1 for classic operators, 2 for parenthesis.
	 * @param priority the mathematical priority : 1 for PLUS/MINUS, 2 for MULTIPLY/DIVIDE and 4 for PARANTHESIS
	 */
	public PriorityToken(String op, int type, int priority) {
		
		super(op, type);
		this.priority = priority;
		
	}

	/**
	 * The mathematical priority :  1 for PLUS/MINUS, 2 for MULTIPLY/DIVIDE and 4 for PARANTHESIS
	 */
	private int priority;

	
	/**
	 * Tests if this token is of superior priority compared to the argument.
	 * @param opponent The token that this one will be compared to.
	 * @return True if this one is of superior priority than the other one, false otherwise.
	 */
	public boolean isOfSuperiorPriority(PriorityToken opponent) {
		
		return (this.priority >= opponent.getPriority());
		
	}
	
	
	public int getPriority() {
		return priority;
	}
}
