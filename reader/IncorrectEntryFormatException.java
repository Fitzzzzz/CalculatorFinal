package reader;

public class IncorrectEntryFormatException extends Exception {

	
	private static final long serialVersionUID = 1L;

	private String[] line;
	
	public String[] getLine() {
		return line;
	}

	private String equation;

	public String getEquation() {
		return equation;
	}

	private String errorMsg;
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public IncorrectEntryFormatException(String[] line) {
		this.line = line;
	}

	public IncorrectEntryFormatException(String equation, String errorMsg) {
		this.equation = equation;
		this.errorMsg = errorMsg;
	}
	


	
}
