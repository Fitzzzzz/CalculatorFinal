package arithmeticParsing;

import java.util.LinkedList;
import equationHandler.Token;
import equationHandler.PriorityToken;
import equationHandler.Parenthesis;

public class Parser {

	private LinkedList<Token> output;
	
	public LinkedList<Token> getOutput() {
		return output;
	}


	private Token[] equation;
	
	public Token[] getEquation() {
		return equation;
	}
	
	
	public Parser(Token[] equation) {
		this.equation = equation;
		output = new LinkedList<Token>();
		convertInfixToPrefix(equation);
		
	}
	
	public void convertInfixToPrefix(Token[] equation) {
		
		
		LinkedList<Token> stack = new LinkedList<Token>();
		this.printLList(stack);
		this.printLList(output);
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
					// If no leftParenthesis : BUG 
					stack.pop();
				}
			}
			System.out.println("-----------------------");
			System.out.println("              " + output );
			System.out.println(stack );
		}
		
		while (!stack.isEmpty()) {
			// If Parenthesis : BUG
			output.addLast(stack.pop());
			System.out.println("----------------------");
			System.out.println("              " + output );
			System.out.println(stack );
		}
	}
	
	public String getLList(LinkedList<Token> list) {
		
		return list.toString();
		
	}
	
	public void printLList(LinkedList<Token> list) {
		System.out.print(list);
	}
	
}
