package se.mikael_langer.gwt.generator;

public enum Scope {
	STATIC("static"),
	NON_STATIC("");
	
	private String value;
	private Scope(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
	
}
