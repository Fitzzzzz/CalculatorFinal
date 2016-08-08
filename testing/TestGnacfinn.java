package testing;

import arithmeticParsing.Parser;
import binaryTree.TreeBuilder;
import binaryTree.TreePrinter;
import equationHandler.Equation;

public class TestGnacfinn {

	public static void main(String[] args) {
		
		String equation = "ethpd-(ecmpd+eptpd+egzpd+ebipd+evapd)=0";
		String codePays = "fra";
		
		
		
		
		Equation eq = new Equation(equation, codePays, "GWh");
		Parser tokenParser = new Parser(eq.getTokens());
//		System.out.println(tokenParser.getLList(tokenParser.getOutput()));
		TreeBuilder tree = new TreeBuilder(tokenParser.getOutput());
		TreePrinter.print(tree.getTree());
//		System.out.print("sol = " + tree.postOrderEvaluation(2010));
		eq.queryBodyValue(tree);
//		eq.compare();		 NOT FINISHED
//		eq.printComparaison(); NOT FINISHED
		eq.printBody();
		eq.printMissingValues();
		
		
	}
}
