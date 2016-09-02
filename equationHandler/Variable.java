package equationHandler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import databaseQueries.Connector;
import databaseQueries.UnexpectedMissingValueException;

/**
 * The class representing the variables (often named "series") 
 * @author hamme
 *
 */
public class Variable extends Operand {

	/**
	 * Is true when there are absolutely no values (for every year) corresponding to this serie. 
	 */
	private boolean empty;
	/**
	 * A connector to query the database.
	 */
	private Connector connect;
	
	/**
	 * The map containing for each year the corresponding value as soon as the query is done.
	 */
	private final Map<Integer, BigDecimal> entryMap = new HashMap<Integer, BigDecimal>();

	/**
	 * Constructor. Will retrieve the values using the provided connector.
	 * @param name Its name
	 * @param country The country to ask the values for.
	 * @param unit The units
	 * @param connect The connector to use
	 * @throws ClassNotFoundException 
	 * @throws SQLException
	 */
	public Variable(String name, String country, String unit, Connector connect) throws ClassNotFoundException, SQLException {
		super(name);
		this.connect = connect;
		this.queryValue(country);
		this.connect = connect;
	}

	/**
	 * Is called by the constructor, will retrieve the values of this operand for every year for the given country.
	 * @param country The country we need the values from.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void queryValue(String country) throws ClassNotFoundException, SQLException {
		
		// For now on, empty is true.
		empty = true;
		ResultSet rs = connect.query(super.getName());
		while (rs.next()) {
			// We found a value, store it.
			entryMap.put(rs.getInt(1), rs.getBigDecimal(2));
			// Meaning it is NOT empty.
			if (empty) {
				empty = false;
			}
		}
	}
	
	/**
	 * Gives the value for the year.
	 * @param year The year we want the value from.
	 * @return the value asked for or 0 if the variable is {@link #empty}.
	 * @throws UnexpectedMissingValueException if the variable isn't {@link #empty} but there is no value for the year asked for.
	 */
	@Override 
	public BigDecimal getValue(int year) throws UnexpectedMissingValueException {
		
		if (!empty) {
			
			BigDecimal value = entryMap.get(year);
			
			if (value == null) {
				// The value is then missing
				throw new UnexpectedMissingValueException(super.getName(), year);
			}
			else {
				// We found the value
				return value;
			}
		}
		// Will return 0 if the variable is empty.
		return BigDecimal.ZERO;
	
	}
	
}
