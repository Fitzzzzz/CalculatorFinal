package equationHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import binaryTree.TreeBuilder;
public class Equation {

	public static PriorityToken PLUS = new Operator("+", 1);
	public static PriorityToken MINUS = new Operator("-", 1);
	public static PriorityToken MULTIPLY = new Operator("*", 2);
	public static PriorityToken DIVIDE = new Operator("/", 2);
	public static PriorityToken OPENPARENTHESES = new Parenthesis("(" , 4, true);
	public static PriorityToken CLOSEPARENTHESES = new Parenthesis(")", 4, false);
	
	public Equation(String equation) {
		
		this.equation = equation;
		String[] parts = equation.split("=");
		this.receiver = parts[0];
		this.body = parts[1];
		String[] tmp = {body};
		
		tmp = this.split(tmp, PLUS);	
		tmp = this.split(tmp, MINUS);
		tmp = this.split(tmp, MULTIPLY);
		tmp = this.split(tmp, DIVIDE);		
		tmp = this.split(tmp, OPENPARENTHESES);	
		tmp = this.split(tmp, CLOSEPARENTHESES);	
		
		
		// TODO : to remove
		for (int i = 0; i < tmp.length; i++) {
			System.out.print(tmp[i] + "______");
		}
		System.out.println("");
		
		tokens = this.convertStringToToken(tmp);
		
		// TODO : remove
		for (int i = 0; i < tokens.length; i++) {
			System.out.print(tokens[i] + "______");
		}
		System.out.println("");
		
		this.queryReceiverValue();
		
		
	}
	
	private String equation;
	
	public String getEquation() {
		return equation;
	}

	private String receiver;
	
	public String getReceiver() {
		return receiver;
	}
	
	private int receiverValue;
	
	public int getReceiverValue() {
		return receiverValue;
	}

	private final Map<Integer, BigDecimal> receiverMap = new HashMap<Integer, BigDecimal>();

	
	private String body;
 
 	public String getBody() {
		return body;
	}

 	private final Map<Integer, BigDecimal> bodyMap = new HashMap<Integer, BigDecimal>();
 	
 	private Token[] tokens;
 	
	public Token[] getTokens() {
		return tokens;
	}
	
	private Set<Integer> years;
	
 	private final Map<Integer, Boolean> resultMap = new HashMap<Integer, Boolean>();

 	private final Map<Integer, BigDecimal> differenceMap = new HashMap<Integer, BigDecimal>();


	private String[] split(String[] toSplit, PriorityToken separator) {
		
		
		String operator = separator.getName(); // TODO: Ugly
		
		String[] tmp;
		String[] tmp2 = new String[0];
		String[] tmp3;
		
		for (int i = 0; i < toSplit.length; i++) {		
			
			tmp = Equation.mySplit(toSplit[i], operator);
			tmp3 = tmp2;
			tmp2 = new String[tmp3.length + tmp.length];
			System.arraycopy(tmp3, 0, tmp2, 0, tmp3.length);
			System.arraycopy(tmp, 0, tmp2, tmp3.length, tmp.length);
			
		}
		return tmp2;
	}
	
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
					converted[i] = new Variable(toConvert[i]);
				}
			
			}
		
		}
		return converted;
		
	}
	
	
	// To use only if it's an equation, not a calculation.
	public void queryReceiverValue() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@srv_oracle_prod:1521/bdenerdata.enerdata", 
					"hamme", 
					"emmanuel");
			Statement stmt = con.createStatement();
			String query = "SELECT tyear, valeur "
					+ "FROM Valeurs_tab "
					+ "WHERE Ticker = (SELECT numero FROM Series WHERE Code_serie = '"
					+ this.getReceiver()
					+ "' AND code_pays = 'fra' AND unite = 'Mm3') "
					+ "ORDER BY tyear";
			
			System.out.println(query);
			ResultSet 	rs = stmt.executeQuery(query);
			
			
			while (rs.next()) {
				
				receiverMap.put(rs.getInt(1), rs.getBigDecimal(2));
				
			}	
			
			years = receiverMap.keySet();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// To get the years
	public void queryYears(TreeBuilder tree) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@srv_oracle_prod:1521/bdenerdata.enerdata", 
					"hamme", 
					"emmanuel");
			Statement stmt = con.createStatement();
			String query = "SELECT tyear "
					+ "FROM Valeurs_tab "
					+ "WHERE Ticker = (SELECT numero FROM Series WHERE Code_serie = '"
					+ this.getReceiver()
					+ "' AND code_pays = 'fra' AND unite = 'Mm3') "
					+ "ORDER BY tyear";
			
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			
			
			while (rs.next()) {
				
				years.add(rs.getInt(1));
				
			}	
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// queryYears or queryReceiverValue need to have been called
	// The tree has to be constructed
	public void queryBodyValue(TreeBuilder tree) {
		
		Iterator<Integer> itr = years.iterator();
		while (itr.hasNext()) {
			Integer year = itr.next();
			bodyMap.put(year, tree.postOrderEvaluation(year));
		}
	}
	
	public void compare() {
		
		Iterator<Integer> itr = years.iterator();
		BigDecimal diff;
		while (itr.hasNext()) {
			Integer year = itr.next();
			diff = bodyMap.get(year).subtract(bodyMap.get(year));
			differenceMap.put(year, diff);
			
			// if the difference between the expected result and the result is smaller or equal to 0.5
			if (diff.abs().compareTo(new BigDecimal(0.5)) <= 0) {
				resultMap.put(year, true);	
			}
			// if it's higher
			else {
				resultMap.put(year, false);
			}
		}
	}
	
	public void printComparaison() {
		Iterator<Integer> itr = years.iterator();
		while (itr.hasNext()) {
			Integer year = itr.next();
			
			System.out.println((year) + " : " + (resultMap.get(year)) + " // diff = " + differenceMap.get(year));
		}
		
	}
	
	
}
