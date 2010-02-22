package se.mikael_langer.gwt.generator;

import com.google.gwt.core.ext.typeinfo.JClassType;

public class MethodParamDefinition {
	private String name;
	private JClassType type;
	
	MethodParamDefinition(JClassType type, String name) {
		this.name = name;
		this.type = type;
	}
	
	String getName() { return name; }
	JClassType getType() { return type; }
}
