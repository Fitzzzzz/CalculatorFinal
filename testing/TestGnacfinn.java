package testing;

import arithmeticParsing.Parser;
import binaryTree.TreeBuilder;
import binaryTree.TreePrinter;
import equationHandler.Equation;

public class TestGnacfinn {

	public static void main(String[] args) {
		
		String equation = "gnacfinn=gnacfind+gnacfnen";
		String codePays = "fra";
		Equation eq = new Equation(equation);
		Parser tokenParser = new Parser(eq.getTokens());
//		System.out.println(tokenParser.getLList(tokenParser.getOutput()));
		TreeBuilder tree = new TreeBuilder(tokenParser.getOutput());
		TreePrinter.print(tree.getTree());
//		System.out.print("sol = " + tree.postOrderEvaluation(2010));
		eq.queryReceiverValue();
		eq.queryBodyValue(tree);
		eq.compare();
		eq.printComparaison();
	}
}
