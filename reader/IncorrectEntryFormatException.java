package reader;

public class IncorrectEntryFormatException extends Exception {

	
	private static final long serialVersionUID = 1L;

	private String[] line;
	
	public IncorrectEntryFormatException(String[] line) {
		this.line = line;
	}

	public String[] getLine() {
		return line;
	}

	
}
