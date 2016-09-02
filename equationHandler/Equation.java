package equationHandler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import binaryTree.TreeBuilder;
import databaseQueries.Connector;
import databaseQueries.UnexpectedMissingValueException;
import reader.Configuration;
import reader.EquationDatas;
import reader.IncorrectEntryFormatException;

/**
 * A class handling equations : will detect its type, split it in different tokens and query the different values before making the calcul.
 * @author hamme
 *
 */
public class Equation {

	/**
	 * Represents the PLUS token
	 */
	public static PriorityToken PLUS = new Operator("+", 1);
	/**
	 * Represents the MINUS token
	 */
	public static PriorityToken MINUS = new Operator("-", 1);
	/**
	 * Represents the MULTIPLY token
	 */
	public static PriorityToken MULTIPLY = new Operator("*", 2);
	/**
	 * Represents the DIVIDE token
	 */
	public static PriorityToken DIVIDE = new Operator("/", 2);
	/**
	 * Represents the LEFT PARENTHESIS token
	 */
	public static PriorityToken OPENPARENTHESES = new Parenthesis("(" , 4, true);
	/**
	 * Represents the RIGHT PARENTHESIS token
	 */
	public static PriorityToken CLOSEPARENTHESES = new Parenthesis(")", 4, false);
	/**
	 * The country being tested
	 */
	private String country;
	/**
	 * The unit being tested
	 */
	private String unit;
	/**
	 * The precision needed for the '= 0' equations.
	 */
	private double precision;
	/**
	 * The connector to the databse
	 */
	private Connector connect;
	/**
	 * The equation type : can be either '>', '<' or '='
	 */
	private String equationType;
	
	
	/**
	 * Constructor
	 * @param datas The informations about the equation
	 * @param country The country being tested
	 * @param config The informations to connect to the database
	 * @param endYear The last year to test
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IncorrectEntryFormatException if the equation and its informations aren't correctly formatted.
	 */
	public Equation(EquationDatas datas, String country, Configuration config, int endYear) throws ClassNotFoundException, SQLException, IncorrectEntryFormatException {
		
		
		
		
		this.country = country;
		this.unit = datas.getUnit();
		this.equation = datas.getEquation();
		this.precision = datas.getPrecision();
		
		
		// Testing what kind of equation this is
		 if (equation.contains("=")) {
			String[] parts = equation.split("=");
			this.receiver = parts[1];
			this.body = parts[0];
			this.equationType = "=";
		}
		else if (equation.contains("<")) {
			String[] parts = equation.split("<");
			this.receiver = parts[1];
			this.body = parts[0];
			this.equationType = "<";
		}
		else if (equation.contains(">")) {
			String[] parts = equation.split(">");
			this.receiver = parts[1];
			this.body = parts[0];
			this.equationType = ">";
		}
		else {
			// The equation-type could not be recognized
			throw new IncorrectEntryFormatException(equation, "Missing an operator ('=', '<' or '>') in the equation : " + equation);
		}
		
		String[] tmp = {body};
		
		// Splitting the equation in what will become tokens
		tmp = this.split(tmp, PLUS);	
		tmp = this.split(tmp, MINUS);
		tmp = this.split(tmp, MULTIPLY);
		tmp = this.split(tmp, DIVIDE);		
		tmp = this.split(tmp, OPENPARENTHESES);	
		tmp = this.split(tmp, CLOSEPARENTHESES);	
		
		// Creating a connector to the database
		this.connect = new Connector(unit, country, config);
		// Creating the tokens out of the split parts of the equation
		this.tokens = this.convertStringToToken(tmp);
		
		// Should ALWAYS be the case
		if (receiver.equals("0")) {
			// Creating all the years that will be used		
			for (int i = datas.getStart(); i <= endYear; i++) {
				this.years.add(i);
			}
			
		}
		// Not implemented yet, might be used later to verify equations that aren't compared to 0
		else {
			this.queryReceiverValue();
		}		
	}
	
	/**
	 * The equation in the form of a String
	 */
	private String equation;
	
	public String getEquation() {
		return equation;
	}
	/**
	 * Not implemented yet, might be used later to verify equations that aren't compared to 0
	 */
	private String receiver;
	
	public String getReceiver() {
		return receiver;
	}
	/**
	 * Not implemented yet, might be used later to verify equations that aren't compared to 0
	 */
	private int receiverValue;
	
	public int getReceiverValue() {
		return receiverValue;
	}
	
	/**
	 * Not implemented yet, might be used later to verify equations that aren't compared to 0
	 */
	private final Map<Integer, BigDecimal> receiverMap = new HashMap<Integer, BigDecimal>();

	/**
	 * The part of the equation containing the calculus.
	 */
	private String body;
 
 	public String getBody() {
		return body;
	}
 	
 	/**
 	 * A Map whose keys are the different years tested and the values are the value of the body for those different years.
 	 */
 	private final Map<Integer, BigDecimal> bodyMap = new HashMap<Integer, BigDecimal>();
 	
 	/**
 	 * The array containing all the tokens
 	 */
 	private Token[] tokens;
 	
	public Token[] getTokens() {
		return tokens;
	}
	
	/**
	 * The list of the years whose values are going to be tested
	 */
	private final List<Integer> years = new LinkedList<Integer>();
	
 	public List<Integer> getYears() {
		return years;
	}

 	/**
 	 * A map whose keys are the different years tested and the values are boolean whose value are true if the calculus gave the expected result, false otherwise.
 	 */
	private final Map<Integer, Boolean> resultMap = new HashMap<Integer, Boolean>();

	/**
 	 * A map whose keys are the different years tested and the values are the difference between the expected result and the real result. Not implemented yet, might be useful later to verify equations that aren't compared to 0
 	 */
 	private final Map<Integer, BigDecimal> differenceMap = new HashMap<Integer, BigDecimal>();
 	
 	/**
 	 * A map whose keys are the different series of the equation and each value consists of a set of the years that are missing for the concerned serial.
 	 */
 	private final Map<String, Set<Integer>> missingValues = new HashMap<String, Set<Integer>>();

 	public Map<String, Set<Integer>> getMissingValues() {
		return missingValues;
	}

 	/**
 	 * A set containing the String representation of the different series of the equation.
 	 */
	private final Set<String> series = new HashSet<String>();

	/**
	 * Will split each value of the array between the given separator if found (each time it is found). 
	 * Each time it is found, will replace the considered case by three new cases, one containing the left part, the second one containing the separator and the last one containing the right part.
	 * Uses {@link #mySplit(String, String)} 
	 * @param toSplit The array whose cases are going to be split if the separator is found.
	 * @param separator The PriorityToken (operator) that will separate the different parts of the values of the array if found.
	 * @return A new Array whose cases contain either the same value as before for every case where the separator wasn't found or three new cases each time the separator was found.
	 */
	private String[] split(String[] toSplit, PriorityToken separator) {
		
		
		String operator = separator.getName(); 
		
		String[] tmp;
		String[] tmp2 = new String[0];
		String[] tmp3;
		
		// For each case
		for (int i = 0; i < toSplit.length; i++) {		
			
			tmp = Equation.mySplit(toSplit[i], operator);
			tmp3 = tmp2;
			tmp2 = new String[tmp3.length + tmp.length];
			System.arraycopy(tmp3, 0, tmp2, 0, tmp3.length);
			System.arraycopy(tmp, 0, tmp2, tmp3.length, tmp.length);
			
		}
		return tmp2;
	}
	
	/**
	 * Splits a String in three parts each time the regular expression is found inside the String
	 * @param str The string to search into
	 * @param regex The regular expression that is being looked for
	 * @return An array whose cases contain individuals parts of the input String separated by the regular expression if it was found one or more times.
	 */
	public static String[] mySplit(String str, String regex) {
		
	    Vector<String> result = new Vector<String>();
	    int start = 0;
	    int pos = str.indexOf(regex);
	    while (pos>=start) {
	        if (pos>start) {
	            result.add(str.substring(start,pos));
	        }
	        start = pos + regex.length();
	        result.add(regex);
	        pos = str.indexOf(regex,start); 
	    }
	    if (start<str.length()) {
	        result.add(str.substring(start));
	    }
	    String[] array = result.toArray(new String[0]);
	    return array;
	}
	
	/**
	 * Converts an array of String into an array of Tokens
	 * @param toConvert The array to convert
	 * @return An array of Tokens corresponding to the input Strings.
	 */
	public Token[] convertStringToToken(String[] toConvert) {
		
		Token[] converted = new Token[toConvert.length];
		for (int i = 0; i < toConvert.length; i++) {
		
			
			if (PLUS.equals(toConvert[i])) {
				converted[i] = PLUS;
			}
			else if (MINUS.equals(toConvert[i])) {
				converted[i] = MINUS;
			}
			else if (MULTIPLY.equals(toConvert[i])) {
				converted[i] = MULTIPLY;
			}
			else if (DIVIDE.equals(toConvert[i])) {
				converted[i] = DIVIDE;
			}
			else if (OPENPARENTHESES.equals(toConvert[i])) {
				converted[i] = OPENPARENTHESES;
			}
			else if (CLOSEPARENTHESES.equals(toConvert[i])) {
				converted[i] = CLOSEPARENTHESES;
			}
			else {
				
				try {
					int value = Integer.parseInt(toConvert[i]);
					converted[i] = new Constant(toConvert[i], value);
				} catch (NumberFormatException e) {
					try {
						converted[i] = new Variable(toConvert[i], this.country, this.unit, this.connect);
					} catch (ClassNotFoundException | SQLException e1) {
						System.out.println("ClassNotFoundException  or SQLException when querying for values. Stacktrace following : ");
						e1.printStackTrace();
					}
					series.add(toConvert[i]);
				}
			
			}
		
		}
		return converted;
		
	}
	
	
	/**
	 * Not used yet. Might be useful later if equation get compared to serials and not '0' as today.
	 */
	public void queryReceiverValue() {
		try {
			ResultSet rs = connect.query(this.getReceiver());
			while (rs.next()) {
			
			receiverMap.put(rs.getInt(1), rs.getBigDecimal(2));
			
			}	

		} catch (SQLException e) {
			System.out.println("SQLException while trying to query " + this.getReceiver());
			e.printStackTrace();
		}
	}
	

	
	
	
	/**
	 * Will make the calculus of the body part of the equation. 
	 * Stores values and missing values for the concerned years.
	 * Needs the binary tree of tokens to have been implemented.
	 * @param tree The tree of tokens that represents the equation.
	 */
	public void queryBodyValue(TreeBuilder tree) {
		
		Iterator<Integer> itr = years.iterator();
		while (itr.hasNext()) {
			Integer year = itr.next();
			try {
				bodyMap.put(year, tree.postOrderEvaluation(year));
			} catch (UnexpectedMissingValueException e) {
				
				if (missingValues.get(e.getSerie()) == null) {
					Set<Integer> missingYears = new HashSet<Integer>();
					missingYears.add(e.getYear());
					missingValues.put(e.getSerie(), missingYears);
				}
				
				else {
					missingValues.get(e.getSerie()).add(e.getYear());
				}
			}
		}
	}
	
	/**
	 * Will compare the {@link #bodyMap} to the expected result. 
	 * Calls either {@link #compareEqual()}, {@link #compareGreater()}, {@link #compareSmaller()} depending on the {@link #equationType}.
	 * @return the errors (the year-value couples that don't respect the equation)
	 */
	public LinkedList<YearValueDuo> compare() {
		
		switch (this.equationType) 
		{
		case "=":
			return compareEqual();
		case ">":
			return compareGreater();
		case "<":
			return compareSmaller();
		default:
			return null;
		}
		
	}
	
	/**
	 * Will go thru {@link #bodyMap} using the {@link #years} to test if it is equal to 0.
	 * @return the errors (the year-value couples that don't respect the equation)
	 */
	private LinkedList<YearValueDuo> compareEqual() {
		
		Iterator<Integer> itr = years.iterator();
		LinkedList<YearValueDuo> errors = new LinkedList<YearValueDuo>();
		
		// For each couple
		while (itr.hasNext()) {
			
			Integer year = itr.next();
			BigDecimal value = bodyMap.get(year);

			
			if (value != null) {
				// If the difference between the expected result and the result is smaller than or equal to the expected precision
				if (value.abs().compareTo(new BigDecimal(this.precision)) <= 0) {
					// Put it as OK in the resultMap
					resultMap.put(year, true);
				}
				// If the difference between the expected result and the result is higher than the expected precision
			else {
					// Put it as not OK in the resultMap and add it to the errors Map.
					resultMap.put(year, false);
					errors.addLast(new YearValueDuo(year, value));
				}
			}
			
			
		}
		return errors;
	}
	/**
	 * Will go thru {@link #bodyMap} using the {@link #years} to test if it is smaller than 0.
	 * @return the errors (the year-value couples that don't respect the equation)
	 */
	private LinkedList<YearValueDuo> compareSmaller() {
		
		Iterator<Integer> itr = years.iterator();
		
		LinkedList<YearValueDuo> errors = new LinkedList<YearValueDuo>();
		// For each couple
		while (itr.hasNext()) {
			Integer year = itr.next();
			BigDecimal value = bodyMap.get(year);
			
			
			if (value != null) {
				// If the result is indeed smaller or equal to 0
				if (value.compareTo(BigDecimal.ZERO) <= 0) {
					// Put it as OK in the resultMap
					resultMap.put(year, true);
				}
				// If it's higher
				else {
					// Put it as not OK in the resultMap and add it to the errors Map.
					resultMap.put(year, false);
					errors.addLast(new YearValueDuo(year, value));
				}
			}
		}
		return errors;
	}
	
	/**
	 * Will go thru {@link #bodyMap} using the {@link #years} to test if it is equal to 0.
	 * @return the errors (the year-value couples that don't respect the equation)
	 */
	private LinkedList<YearValueDuo> compareGreater() {
		
		Iterator<Integer> itr = years.iterator();
		LinkedList<YearValueDuo> errors = new LinkedList<YearValueDuo>();
		
		// For each couple
		while (itr.hasNext()) {
			Integer year = itr.next();
			BigDecimal value = bodyMap.get(year);
			
			if (value != null) {
				// If the result is indeed bigger or equal to 0
				if (value.compareTo(BigDecimal.ZERO) >= 0) {
					// Put it as OK in the resultMap
					resultMap.put(year, true);
				}
				// If it's smaller
				else {
					// Put it as not OK in the resultMap and add it to the errors Map.
					resultMap.put(year, false);
					errors.addLast(new YearValueDuo(year, value));
				}
			}
		}
		return errors;
	}
	
	/**
	 * Prints the {@link #resultMap} and the {@link #differenceMap}
	 */
	public void printComparaison() {
		Iterator<Integer> itr = years.iterator();
		while (itr.hasNext()) {
			Integer year = itr.next();
			
			System.out.println((year) + " : " + (resultMap.get(year)) + " // diff = " + differenceMap.get(year));
		}
		
	}

	/**
	 * Prints the {@link #bodyMap}
	 */
	public void printBody() {
		
		System.out.println("printBody starting");
		Iterator<Integer> itr = years.iterator();
		while (itr.hasNext()) {
			Integer year = itr.next();
			System.out.println(year + " : " + bodyMap.get(year));
		}
		
	}
	
	/**
	 * Prints the {@link #printMissingValues()}
	 */
	public void printMissingValues() {
		
		Iterator<String> itr = series.iterator();
		while (itr.hasNext()) {
			
			String serie = itr.next();
//			System.out.println("iterating on " + serie);
			Set<Integer> missingYears = missingValues.get(serie);
			if (missingYears != null) {
				Iterator<Integer> itrYears = missingYears.iterator();
				while (itrYears.hasNext()) {
					System.out.println(serie + " missing " + itrYears.next());
				}
			}
			
			
		}
		
	}
	
	/**
	 * Closes the connection with the database.
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		this.connect.close();
	}
	 
}
