package equationHandler;

public abstract class PriorityToken extends Token {

	public PriorityToken(String op, int type, int priority) {
		
		super(op, type);
		this.priority = priority;
		
	}

	private int priority;

	public int getPriority() {
		return priority;
	}
	
	public boolean isOfSuperiorPriority(PriorityToken opponent) {
		
		return (this.priority >= opponent.getPriority());
		
	}

}
