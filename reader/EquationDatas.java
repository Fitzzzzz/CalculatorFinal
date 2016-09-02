package reader;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import equationHandler.YearValueDuo;



/**
 * 
 * @author hamme
 *	An object that contains informations relative to an equation.
 */
public class EquationDatas {


	/**
	 * The input line. Contains the equation and its parameters.
	 */
	private String[] line;
	/**
	 * The equation
	 */
	private String equation;
	/**
	 * The unit
	 */
	private String unit;
	/**
	 * The database (unused)
	 */
	private String database;
	/**
	 * The precision (unused)
	 */
	private double precision;
	/**
	 * The year to start with.
	 */
	private int start;
	/**
	 * The year to end with.
	 */
	private int end;
	/**
	 * The temporality (monthly, yearly) (its handling is not implemented)
	 */
	private String temporality;
	/**
	 * default year to start with
	 */
	private static final int defaultStartYear = 1971;
	/**
	 * default precision accepted
	 */
	private static final double defaultPrecision = 0.05;
	/**
	 * default year to end with
	 */
	private static final int defaultEndYear = Calendar.getInstance().get(Calendar.YEAR); 
	
	/**
	 * number of arguments in the line containing the equation (should be 2 or 3)
	 */
	private int argumentsNb;
	
	/**
	 * A map containing for each serie EXISTING of the equation the year for which its informations are missing. 
	 */
	private Map<String, Set<Integer>> missing;
	
	/**
	 * The years and the relative value where the output of the calculus doesn't respect the equation (meaning > 0 if < 0 expected or the opposite or != 0 when = 0 expected).
	 */
	private LinkedList<YearValueDuo> errors;

	

	/**
	 * Constructor. Will separate the {@link #line} into different informations.
	 * @param line the line containing the equations and relatives informations.
	 * @param config the configuration file to get the informations relative to the database.
	 * @throws IncorrectEntryFormatException if a line isn't formatted as it should.
	 */
	public EquationDatas(String[] line, Configuration config) throws IncorrectEntryFormatException {
		
		this.argumentsNb = line.length;
		this.line = line;
		this.equation = line[0];
		this.unit = line[1];
		this.database = config.getDatabase();
		this.precision = defaultPrecision;
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
	public Map<String, Set<Integer>> getMissing() {
		return missing;
	}
	public void setMissing(Map<String, Set<Integer>> missing) {
		this.missing = missing;
	}
	

	public LinkedList<YearValueDuo> getErrors() {
		return errors;
	}

	public void setErrors(LinkedList<YearValueDuo> errors) {
		this.errors = errors;
	}

}
