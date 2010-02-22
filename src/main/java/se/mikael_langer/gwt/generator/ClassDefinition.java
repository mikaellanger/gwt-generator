package se.mikael_langer.gwt.generator;

import com.google.gwt.core.ext.typeinfo.JClassType;

public class ClassDefinition extends GeneratedClassDefinition {

	private JClassType superclass;
	
	public ClassDefinition(String name) {
		super(name);
	}

	public ClassDefinition thatExtends(JClassType superclass) {
		this.superclass = superclass;
		return this;
	}

	public ClassDefinition withFields(FieldDefinition... fields) {
		addFields(fields);
		return this;
	}
	
	public ClassDefinition withMethods(MethodDefinition... methods) {
		addMethods(methods);
		return this;
	}
	
	@Override
	protected void write() {
		p("public static class " + getName() + (superclass == null ? "" : " extends " + superclass.getParameterizedQualifiedSourceName()) + " {");
		super.write();
	}

}
