package se.mikael_langer.gwt.generator;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.generator.ast.Expression;
import com.google.gwt.user.rebind.StringSourceWriter;

public class AnonymousClass extends Expression {
	
	private GeneratedClassDefinition classDef = new GeneratedClassDefinition("");
	private JClassType superclass;
	
	AnonymousClass(JClassType superclass) {
		this.superclass = superclass;
	}
	
	@Override
	public String toCode() {
		StringBuilder sb = new StringBuilder();
		sb.append("new ").append(superclass.getParameterizedQualifiedSourceName()).append("() {").append("\n");

		StringSourceWriter sw = new StringSourceWriter();
		classDef.write(sw);
		sb.append(sw);
		
		return sb.toString();
	}

	public AnonymousClass withFields(FieldDefinition... fields) {
		classDef.addFields(fields);
		return this;
	}

	public Expression withMethods(MethodDefinition... methods) {
		classDef.addMethods(methods);
		return this;
	}
}
