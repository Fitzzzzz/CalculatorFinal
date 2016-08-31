package reader;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import equationHandler.YearValueDuo;



/**
 * 
 * @author hamme
 *
 */
public class EquationDatas {

	public String getEquation() {
		return equation;
	}

	public String getUnit() {
		return unit;
	}

	public String getDatabase() {
		return database;
	}

	public double getPrecision() {
		return precision;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public String getTemporality() {
		return temporality;
	}

	public int getArgumentsNb() {
		return argumentsNb;
	}

	/**
	 * The input line. Contains the equation and its parameters.
	 */
	private String[] line;
	private String equation;
	private String unit;
	private String database;
	private double precision;
	private int start;
	private int end;
	private String temporality;
	private static final int defaultStartYear = 1971;
	private static final double defaultPrecision = 0.05;
	private static final int defaultEndYear = Calendar.getInstance().get(Calendar.YEAR); // TODO : CHANGE TO CURRENT YEAR
	
	
	private int argumentsNb;
	
	private Map<String, Set<Integer>> missing;
	
	public Map<String, Set<Integer>> getMissing() {
		return missing;
	}

	public void setMissing(Map<String, Set<Integer>> missing) {
		this.missing = missing;
	}

	private LinkedList<YearValueDuo> errors;
	
	public LinkedList<YearValueDuo> getErrors() {
		return errors;
	}

	public void setErrors(LinkedList<YearValueDuo> errors) {
		this.errors = errors;
	}

	
	public EquationDatas(String[] line, Configuration config) throws IncorrectEntryFormatException {
		
		this.argumentsNb = line.length;
//		System.out.println(argumentsNb + "     " + line.length);
		this.line = line;
		this.equation = line[0];
		this.unit = line[1];
		this.database = config.getDatabase();
		this.precision = defaultPrecision;
//		for (int i = 0; i < line.length; i++) {
//			System.out.println("+   " + line[i]);
//		}
		this.start = defaultStartYear;
		if (argumentsNb == 3) {
			this.start = Integer.parseInt(line[2]);
		}
		else if (argumentsNb != 2) {
			throw new IncorrectEntryFormatException(line); 
		}
		
	}

	@Override
	public String toString() {
		
		String eq = "";
		for (int i = 0; i < argumentsNb - 1; i++) {
			eq = eq + line[i] + ";";
		}
		return eq + line[argumentsNb - 1];
		
	}

	public static int getDefaultEndYear() {
		return defaultEndYear;
	}
	
	
	
}
