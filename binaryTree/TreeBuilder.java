package binaryTree;

import java.math.BigDecimal;
import java.util.LinkedList;

import databaseQueries.UnexpectedMissingValueException;
import equationHandler.Operand;
import equationHandler.Token;

/**
 * A class designed to create an Abstract Syntax Tree (AST) out of prefix ordered Tokens that will be then be evaluated thru a reverse post-order evaluation.
 * @author hamme
 *
 */
public class TreeBuilder {

	/**
	 * Will build the tree by calling {@link #constructTree(LinkedList, Node)}
	 * @param list The list of Tokens to create a tree of.
	 */
	public TreeBuilder(LinkedList<Token> list) {
		

		this.tree = new Node();
		constructTree(list, tree);

	}

	/**
	 * The top node of the tree
	 */
	private Node tree;
	public Node getTree() {
		return tree;
	}
	public void setTree(Node tree) {
		this.tree = tree;
	}

	/**
	 * Creates the AST using the standard algorithm.
	 * @param list The prefix ordered tokens
	 * @param top The top node to start the tree with
	 */
	public static void constructTree(LinkedList<Token> list, Node top) {
		
		top.setValue(list.removeLast());
		Node tmp = top;
		
		while (!list.isEmpty()) {
	
			
			if (tmp.getValue().getType() == 1) {
				tmp.setLeftSon(new Node(tmp, list.removeLast()));
				tmp = tmp.getLeftSon();
			}
			else if (tmp.getValue().getType() == 0) {
				tmp = tmp.getFather();
				while (tmp.getRightSon() != null) {
					tmp = tmp.getFather();
				}
				tmp.setRightSon(new Node(tmp, list.removeLast()));
				tmp = tmp.getRightSon();
			}
			
		}
		
	}
	
	/**
	 * A reverse post-order evaluation to calculate the value of the equation. Calls {@link #postOrderEvaluation(Node, Integer)}
	 * @throws UnexpectedMissingValueException if one of the variables is missing a value.
	 */
	public BigDecimal postOrderEvaluation(Integer year) throws UnexpectedMissingValueException {
		
		Node node = this.tree;
		
		if (node.getValue().getType() == 0) {
			return ((Operand) (node.getValue())).getValue(year);
		}

		BigDecimal rightValue = postOrderEvaluation(node.getRightSon(), year);

		BigDecimal leftValue = postOrderEvaluation(node.getLeftSon(), year);

		switch (node.getValue().getName()) {
		case "+": 
			return rightValue.add(leftValue);
			
		case "-":
			return rightValue.subtract(leftValue);
			
		case "/":
			return rightValue.divide(leftValue);
			
		case "*":
			return rightValue.multiply(leftValue);
			
		default:
			System.out.println(node.getValue().getName() + " wasn't recognized");
			return BigDecimal.ZERO;
				
		}
		
	}
	
	/**
	 * A reverse post-order evaluation to calculate the value of the equation. Calls itself to go thru the tree.
	 * @throws UnexpectedMissingValueException if one of the variables is missing a value.
	 */
	public static BigDecimal postOrderEvaluation(Node node, Integer year) throws UnexpectedMissingValueException {
		

		if (node.getValue().getType() == 0) {
			return ((Operand) (node.getValue())).getValue(year);
		}
		BigDecimal rightValue = postOrderEvaluation(node.getRightSon(), year);
		BigDecimal leftValue = postOrderEvaluation(node.getLeftSon(), year);

		switch (node.getValue().getName()) {
		case "+": 
			return rightValue.add(leftValue);
			
		case "-":
			return rightValue.subtract(leftValue);
			
		case "/":
			return rightValue.divide(leftValue);
			
		case "*":
			return rightValue.multiply(leftValue);
			
		default:
			System.out.println(node.getValue().getName() + " wasn't recognized");
			return BigDecimal.ZERO;
				
		}
		
	}
}
