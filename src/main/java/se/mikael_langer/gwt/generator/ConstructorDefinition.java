package se.mikael_langer.gwt.generator;

import com.google.gwt.dev.generator.ast.Expression;
import com.google.gwt.dev.generator.ast.Statement;
import com.google.gwt.dev.generator.ast.StatementsList;

public class ConstructorDefinition extends CodeWriter {
	String name;
	StatementsList body = new StatementsList();
	
	ConstructorDefinition(String name) {
		this.name = name;
	}
	
	public ConstructorDefinition addToBody(Expression... body) {
		for (Expression e : body) {
			this.body.getStatements().add(new Statement(e));
		}
		return this;
	}

	@Override
	protected void write() {
		p(name + "() {");
		i();
		p(body.toCode());
		o();
		p("}");
	}

	public String getSignature() {
		return name;
	}
}
