package equationHandler;

public class Parenthesis extends PriorityToken {

	public Parenthesis(String op, int priority, boolean leftParenthesis) {
		
		super(op, 2, priority);
		this.leftParenthesis = leftParenthesis;
	}

	private boolean leftParenthesis;

	public boolean isLeftParenthesis() {
		return leftParenthesis;
	}
	
}
