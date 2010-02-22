package se.mikael_langer.gwt.generator;

public enum Access {
	PUBLIC("public"),
	DEFAULT(""),
	PRIVATE("private"),
	PROTECTED("protected");

	private String value;
	Access(String value) { this.value = value; }
	@Override
	public String toString() {
		return value;
	}
}
