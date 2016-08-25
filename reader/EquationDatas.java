package reader;

import java.util.Calendar;
import java.util.LinkedList;

import equationHandler.YearValueDuo;
import testing.Config;

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
	private static final int defaultEndYear = 2016; // TODO : CHANGE TO CURRENT YEAR
	
	
	private int argumentsNb;
	
	private LinkedList<YearValueDuo> errors;
	
	public LinkedList<YearValueDuo> getErrors() {
		return errors;
	}

	public void setErrors(LinkedList<YearValueDuo> errors) {
		this.errors = errors;
	}

	
	public EquationDatas(String[] line) throws IncorrectEntryFormatException {
		
		this.argumentsNb = line.length;
		System.out.println(argumentsNb + "     " + line.length);
		this.line = line;
		this.equation = line[0];
		this.unit = line[1];
		
		for (int i = 0; i < line.length; i++) {
			System.out.println("+   " + line[i]);
		}
		
		if (argumentsNb == 2) {
			this.database = Config.database;
			this.precision = defaultPrecision;
			this.start = defaultStartYear;
		}
		else if (argumentsNb == 4 || argumentsNb == 7) {
			this.database = line[2];
			if (line[3] == "") {
				throw new IncorrectEntryFormatException(this.equation, "Incorrect Entry Format, no ';' shoud end a line.");
			}
			this.precision = Float.parseFloat(line[3]);
			this.start = defaultStartYear;

			if (argumentsNb == 7) {
				if (line[5] == "") {
					throw new IncorrectEntryFormatException(this.equation, "Incorrect Entry Format, no ';' shoud end a line.");
				}
				this.start = Integer.parseInt(line[4]);
				this.end = Integer.parseInt(line[5]);
				this.temporality = line[6];
			}
		}
		else if (argumentsNb == 3 || argumentsNb == 6) {
			this.database = Config.database;
			if (line[2] == "") {
				throw new IncorrectEntryFormatException(this.equation, "Incorrect Entry Format, no ';' shoud end a line.");
			}
			this.precision = Float.parseFloat(line[2]);
			this.start = defaultStartYear;
			
			if (argumentsNb == 6) {
				if (line[4] == "") {
					throw new IncorrectEntryFormatException(this.equation, "Incorrect Entry Format, no ';' shoud end a line.");
				}
				this.start = Integer.parseInt(line[3]);
				this.end = Integer.parseInt(line[4]);
				this.temporality = line[5];
			}
		}
		else {
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
