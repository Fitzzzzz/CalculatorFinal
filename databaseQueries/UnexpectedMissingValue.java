package databaseQueries;

public class UnexpectedMissingValue extends Exception {

	
	private static final long serialVersionUID = 1L;
	
	private String serie;
	public String getSerie() {
		return serie;
	}


	private int year;
	public int getYear() {
		return year;
	}


	public UnexpectedMissingValue(String serie, int year) {
		
		this.serie = serie;
		this.year = year;
	}

}
