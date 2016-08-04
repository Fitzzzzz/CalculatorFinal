package binaryTree;

import binaryTree.TreePrinter.PrintableNode;
import equationHandler.Token;

public class Node implements PrintableNode {

	public Node() {
		
	}
	public Node(Node leftSon) {
		
		this.leftSon = leftSon;
	}	
	public Node(Node leftSon, Node rightSon) {
		
		this.leftSon = leftSon;
		this.rightSon = rightSon;
		
	}
	public Node(Token value) {
		this.value = value;
	}
	public Node(Node father, Token value) {
		this.value = value;
		this.father = father;
	}
	public Node(Node leftSon, Node rightSon, Token value) {
		
		this.leftSon = leftSon;
		this.rightSon = rightSon;
		this.value = value;
	}
	
	private Node leftSon;
	public Node getLeftSon() {

		return leftSon;
		
	}	
	public void setLeftSon(Node leftSon) {
		this.leftSon = leftSon;
	}

	private Node rightSon;
	public Node getRightSon() {
		return rightSon;
	}
	public void setRightSon(Node rightSon) {
		this.rightSon = rightSon;
	}

	private Node father;
	public Node getFather() {
		return father;
	}
	public void setFather(Node father) {
		this.father = father;
	}

	private Token value;
	public Token getValue() {
		return value;
	}
	public void setValue(Token value) {
		this.value = value;
	}
	@Override
	public PrintableNode getLeft() {
		return this.getLeftSon();
	}
	@Override
	public PrintableNode getRight() {
		return this.getRightSon();
	}
	@Override
	public String getText() {
		return this.getValue().getName();
	}


	
}
