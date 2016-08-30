package equationHandler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import databaseQueries.Connector;
import databaseQueries.UnexpectedMissingValueException;

public class Variable extends Operand {


	private boolean empty;
	private Connector connect;
	
	private final Map<Integer, BigDecimal> entryMap = new HashMap<Integer, BigDecimal>();

	public Variable(String name, String country, String unit, Connector connect) throws ClassNotFoundException, SQLException {
		super(name);
//		System.out.print("Variable " + name + " starting");
		this.connect = connect;
		this.queryValue(country);
		this.connect = connect;
	}

	
	public void queryValue(String country) throws ClassNotFoundException, SQLException {
		
	
		empty = true;
		ResultSet rs = connect.query(super.getName());
		while (rs.next()) {
			
			entryMap.put(rs.getInt(1), rs.getBigDecimal(2));
			if (empty) {
				empty = false;
			}
		}
	}
	
	@Override
	public BigDecimal getValue(int year) throws UnexpectedMissingValueException {
		if (!empty) {
			BigDecimal value = entryMap.get(year);
			if (value == null) {
//				System.out.println("UNEXPECTED MISSING");
				throw new UnexpectedMissingValueException(super.getName(), year);
			}
			else {
//				System.out.println(this.getName() + " : " + value);
				return value;
			}
		}
//		System.out.println(super.getName() + " is empty");
		return BigDecimal.ZERO;
	
	}
	
}
