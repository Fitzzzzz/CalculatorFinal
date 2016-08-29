package reader;

public class IncorrectCountryEntryException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	
	public IncorrectCountryEntryException(String errorMessage) {
		
		this.errorMessage = errorMessage;
		
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
