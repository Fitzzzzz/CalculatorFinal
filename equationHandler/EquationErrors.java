package equationHandler;

import java.util.LinkedList;

public class EquationErrors {

	private String equation;
	public String getEquation() {
		return equation;
	}

	private LinkedList<YearValueDuo> duo;

	public LinkedList<YearValueDuo> getDuo() {
		return duo;
	}

	public EquationErrors(String eq, LinkedList<YearValueDuo> duo) {

		this.equation = eq;
		this.duo = duo;
		
	}
	
}
