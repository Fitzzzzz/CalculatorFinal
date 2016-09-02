package equationHandler;

/**
 * A token is one element of an equation : can be a variable, an operand, a constant.
 * @author hamme
 *
 */
public abstract class Token {

	/**
	 * Its representation as a String.
	 */
	private String name;
	
	
	/**
	 * Its type : 0 for an operand, 1 for an operator and 2 for parenthesis.
	 */
	private int type;
	
	public int getType() {
		return type;
	}

	/**
	 * Constructor
	 * @param op its name
	 * @param type Its type : 0 for an operand, 1 for an operator and 2 for parenthesis.
	 */
	public Token(String op, int type) {
		
		this.name = op;
		this.type = type;
		
	}

	@Override
	public String toString() {

		return this.name;
	}

	public boolean equals(String tested) {
		
		return tested.equals(this.toString());
		
	}
	
	public String getName() {
		return name;
	}
	
	
}
