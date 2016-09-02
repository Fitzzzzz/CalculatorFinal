package equationHandler;

/**
 * Class for the parenthesis
 * @author hamme
 *
 */
public class Parenthesis extends PriorityToken {

	/**
	 * Constructor, will create a PriorityToken of type 2.
	 * @param op It's name
	 * @param priority It's priority, should be 4 by default.
	 * @param leftParenthesis if it is a left parenthesis or not.
	 */
	public Parenthesis(String op, int priority, boolean leftParenthesis) {
		
		super(op, 2, priority);
		this.leftParenthesis = leftParenthesis;
	}

	/**
	 * True if the object represents a left parenthesis, false otherwise.
	 */
	private boolean leftParenthesis;

	public boolean isLeftParenthesis() {
		return leftParenthesis;
	}
	
}
