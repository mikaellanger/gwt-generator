package se.mikael_langer.gwt.generator;

public class GeneratedType implements Type {

	private String name;
	GeneratedType(String name) {
		this.name = name;
	}
	
	@Override
	public String getClassLiteral() {
		return name + ".class";
	}
	
	@Override
	public String toString() {
		return name;
	}
}
