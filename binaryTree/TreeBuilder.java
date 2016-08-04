package binaryTree;

import java.math.BigDecimal;
import java.util.LinkedList;

import equationHandler.Operand;
import equationHandler.Token;


public class TreeBuilder {

	public TreeBuilder(LinkedList<Token> list) {
		

		System.out.println(list);
		this.tree = new Node();
		constructTree(list, tree);
		System.out.println(list);
//		System.out.println("result = " + postOrderEvaluation(tree, 5));
		
	}

	
	private Node tree;
	public Node getTree() {
		return tree;
	}
	public void setTree(Node tree) {
		this.tree = tree;
	}


	public static void constructTree(LinkedList<Token> list, Node top) {
		
		top.setValue(list.removeLast());
		Node tmp = top;
		
		while (!list.isEmpty()) {
	
			System.out.println(list.peekLast().toString());
			
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
	 * Is actually a reverse post-order evaluation...
	 */
	public BigDecimal postOrderEvaluation(Integer year) {
		
		Node node = this.tree;
		System.out.print(node.getValue().getName());
		
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
	 * Is actually a reverse post-order evaluation...
	 */
	public static BigDecimal postOrderEvaluation(Node node, Integer year) {
		
		System.out.print(node.getValue().getName());
		
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
