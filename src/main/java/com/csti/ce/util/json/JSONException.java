package com.csti.ce.util.json;
public class JSONException extends Exception {
	private Throwable cause;

	/**
	 * Constructs a JSONException with an explanatory message.
	 * 
	 * @param message
	 *            Detail about the reason for the exception.
	 */
	public JSONException(String message) {
		super(message);
	}

	public JSONException(Throwable t) {
		super(t.getMessage());
		this.cause = t;
	}

	public Throwable getCause() {
		return this.cause;
	}
}
