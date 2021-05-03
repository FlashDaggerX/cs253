public class InvalidExpressionException extends RuntimeException {

	/** */
	private static final long serialVersionUID = 3561493681053870872L;

	private String expression;
	private char token;
	private int place;

	public InvalidExpressionException(String expression, char token, int place) {
		this.expression = expression;
		this.token = token;
		this.place = place;
	}

	@Override
	public String getMessage() {
		return String.format("Bad expression \"%s\"! Invalid token '%s' at position %d.",
				expression, token, place);
	}

	public char getToken() {
		return token;
	}

	public int getPlace() {
		return place;
	}
}
