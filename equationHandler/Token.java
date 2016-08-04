package equationHandler;

public abstract class Token {

	private String name;
	
	public String getName() {
		return name;
	}

	private int type;
	
	public int getType() {
		return type;
	}

	public Token(String op, int type) {
		
		this.name = op;
		this.type = type;
		
	}

	@Override
	public String toString() {

		return this.name;
	}

	public boolean equals(String tested) {
		
		return tested.equals(this.toString());
		
	}
	
	
	
	
}
