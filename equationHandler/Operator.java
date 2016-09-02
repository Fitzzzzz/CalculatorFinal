package equationHandler;


/**
 * Tokens that are operators.
 * @author hamme
 *
 */
public class Operator extends PriorityToken {

	/**
	 * Constructor
	 * @param op It's name
	 * @param priority the priority of the token.
 	 */
	public Operator(String op, int priority) {
		
		super(op, 1, priority);
		
		
	}
	
}
