package se.mikael_langer.gwt.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.generator.ast.Expression;
import com.google.gwt.dev.generator.ast.Statement;
import com.google.gwt.dev.generator.ast.StatementsList;


public class MethodDefinition extends MemberDefinitionBase {

	private List<String> annotations = new ArrayList<String>(); 
	private JClassType returnType;
	private List<MethodParamDefinition> params = new ArrayList<MethodParamDefinition>();
	private StatementsList body; //TODO: Share common method stuff with constructor
	
	MethodDefinition(Access access, Scope scope, JClassType returnType, String name) {
		super(access, scope, name);
		this.returnType = returnType;
	}
	
	public MethodDefinition annotatedWith(Class<?>... annotations) {
		for (Class<?> a : annotations) {
			this.annotations.add("@" + a.getCanonicalName());
		}
		return this;
	}

	public MethodDefinition withParams(MethodParamDefinition... params) {
		this.params.addAll(Arrays.asList(params));
		return this;
	}

	public MethodDefinition withBody(Expression... body) { 
		this.body = new StatementsList();
		for (Expression e : body) {
			this.body.getStatements().add(new Statement(e));
		}
		return this;
	}
	
	@Override
	protected void write() {
		for (String a : annotations) {
			p(a);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(getAccess()).append(" ").append(getScope()).append(returnType == null ? "void" : returnType.getQualifiedSourceName()).append(" ").append(getName()).append("(");
		ArrayList<String> params = new ArrayList<String>();
		for (MethodParamDefinition p : this.params) {
			StringBuilder pb = new StringBuilder();
			pb.append(p.getType().getParameterizedQualifiedSourceName()).append(" ").append(p.getName());
			params.add(pb.toString());
		}
		sb.append(StringUtils.join(params, ", ")).append(")");
		
		if (body != null) {
			sb.append(" {\n");
			sb.append(body.toCode());
			sb.append("\n}");
		} else {
			sb.append(";");
		}
		p(sb.toString());
	}
}
