package reader;

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

	public String getPrecision() {
		return precision;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public String getTemporality() {
		return temporality;
	}

	public int getArgumentsNb() {
		return argumentsNb;
	}

	private String equation;
	private String unit;
	private String database;
	private String precision;
	private String start;
	private String end;
	private String temporality;
	private int argumentsNb;
	
	// rajouter exceptions si mauvais formattage
	public EquationDatas(String[] line) throws IncorrectEntryFormatException {
		
		this.argumentsNb = line.length;
		this.equation = line[0];
		this.unit = line[1];
		
		if (argumentsNb == 4 || argumentsNb == 7) {
			this.database = line[2];
			this.precision = line[3];
			if (argumentsNb == 7) {
				this.start = line[4];
				this.end = line[5];
				this.temporality = line[6];
			}
		}
		else if (argumentsNb == 3 || argumentsNb == 6) {
			this.precision = line[2];
			if (argumentsNb == 6) {
				this.start = line[3];
				this.end = line[4];
				this.temporality = line[5];
			}
		}
		else {
			throw new IncorrectEntryFormatException(line); 
		}
		
	}
	
}
