package arithmeticParsing;

import java.util.LinkedList;
import equationHandler.Token;
import equationHandler.PriorityToken;
import equationHandler.Parenthesis;


/**
 * A class handling the arithmetic Parsing of the tokens. It will sort them in prefix order to allow them to later enter a binary tree.
 * @author hamme
 *
 */
public class Parser {

	/**
	 * The final result
	 */
	private LinkedList<Token> output;
	
	public LinkedList<Token> getOutput() {
		return output;
	}

	/**
	 * The inputed tokens
	 */
	private Token[] equation;
	
	public Token[] getEquation() {
		return equation;
	}
	
	/**
	 * Will call {@link #convertInfixToPrefix(Token[])} to sort the Tokens in the prefix order
	 * @param equation The tokens to sort
	 */
	public Parser(Token[] equation) {
		this.equation = equation;
		output = new LinkedList<Token>();
		convertInfixToPrefix(equation);
		
	}
	
	/**
	 * Sorts infix sorted tokens in a prefix order using the Shunting-yard algorithm.
	 * @param equation The tokens to sort
	 */
	public void convertInfixToPrefix(Token[] equation) {
		
		
		LinkedList<Token> stack = new LinkedList<Token>();

		for (int i = 0; i < equation.length; i++) {
			
			Token tmp = equation[i];
			
			if (tmp.getType() == 0) {
				
				output.addLast(tmp);
				
			}
			else if (tmp.getType() == 1)  { 
				
				while (!stack.isEmpty() && (stack.peek().getType() == 1) && 
						(((PriorityToken)(stack.peek())).isOfSuperiorPriority((PriorityToken)tmp))) {
					
					output.addLast(stack.pop());
					
				}
				stack.push(tmp);
			}
			else if (tmp.getType() == 2) {
				
				if (((Parenthesis)(tmp)).isLeftParenthesis()) {
					stack.push(tmp);
				}
				else {
					while (!stack.isEmpty() && 
							((stack.peek().getType() != 2) || 
									!(((Parenthesis)(stack.peek())).isLeftParenthesis()))) {
						
						output.addLast(stack.pop());	
					}

					stack.pop();
				}
			}

		}
		
		while (!stack.isEmpty()) {

			output.addLast(stack.pop());

		}
	}
	
	public String getLList(LinkedList<Token> list) {
		
		return list.toString();
		
	}
	/** 
	 * Prints the given list of tokens
	 * @param list tokens to print
	 */ 
	public void printLList(LinkedList<Token> list) {
		System.out.print(list);
	}
	
}
