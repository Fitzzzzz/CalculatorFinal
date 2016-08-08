package testing;

import java.util.Iterator;

import arithmeticParsing.Parser;
import binaryTree.TreeBuilder;
import binaryTree.TreePrinter;
import equationHandler.Equation;
import reader.EquationDatas;
import reader.EquationReader;

public class TestGnacfinn {

	public static void main(String[] args) {
		
		
		EquationReader reader = new EquationReader();
		
		String equation = "ethpd-(ecmpd+eptpd+egzpd+ebipd+evapd)=0";
		String codePays = "fra";
		
		Iterator<EquationDatas> itr = reader.getEquations().iterator();
		
		while (itr.hasNext()) {
			
			EquationDatas current = itr.next();
			Equation eq = new Equation(current, "fra");
			Parser tokenParser = new Parser(eq.getTokens());
//			System.out.println(tokenParser.getLList(tokenParser.getOutput()));
			TreeBuilder tree = new TreeBuilder(tokenParser.getOutput());
			TreePrinter.print(tree.getTree());
//			System.out.print("sol = " + tree.postOrderEvaluation(2010));
			eq.queryBodyValue(tree);
//			eq.compare();		 NOT FINISHED
//			eq.printComparaison(); NOT FINISHED
			eq.printBody();
			eq.printMissingValues();
			
			
		}
		
		
		
		
	}
}
