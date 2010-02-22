package se.mikael_langer.gwt.generator;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.generator.ast.Expression;

public class FieldDefinition extends Expression {

	private JClassType type;
	private String name;
	private Expression value;
	
	FieldDefinition(JClassType type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public FieldDefinition assignValue(Expression value) {
		this.value = value;
		return this;
	}
	
	@Override
	public String toCode() {
		StringBuilder sb = new StringBuilder().append(type.getParameterizedQualifiedSourceName()).append(" ").append(name);
		if (value != null) sb.append(" = ").append(value);
		return sb.toString(); 
	}
}
