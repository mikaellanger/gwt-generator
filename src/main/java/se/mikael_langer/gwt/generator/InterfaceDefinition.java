package se.mikael_langer.gwt.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

//TODO: Divide into builder classes
public class InterfaceDefinition extends MemberDefinitionBase {
	List<String> supertypes = new ArrayList<String>();
	List<MethodDefinition> methods = new ArrayList<MethodDefinition>();
	
	InterfaceDefinition(Access access, Scope scope, String name) {
		super(access, scope, name);
	}

	public List<String> getSupertypes() { return supertypes; }
	
	public InterfaceDefinition thatExtends(Class<?>... types) {
		for (Class<?> c : types) {
			supertypes.add(c.getCanonicalName());
		}
		return this;
	}
	
	public InterfaceDefinition withMethods(MethodDefinition... methods) {
		this.methods.addAll(Arrays.asList(methods));
		return this;
	}

	@Override
	protected void write() {
		StringBuilder sb = new StringBuilder();
		sb.append(getAccess()).append(" ").append(getScope()).append(" interface ").append(getName());
		if (!supertypes.isEmpty()) sb.append(" extends ").append(StringUtils.join(supertypes, ", "));
		sb.append("{");
		p(sb.toString());
		i(); //TODO: autodetect intentation?!
		
		for (MethodDefinition m : methods) {
			write(m);
		}
		
		o();
		p("}");
	}
}
