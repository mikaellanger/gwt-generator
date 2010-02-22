package se.mikael_langer.gwt.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.generator.ast.Statement;

public class GeneratedClassDefinition extends CodeWriter {

	private String name;
	private List<InterfaceDefinition> nestedInterfaces = new ArrayList<InterfaceDefinition>();
	private List<ClassDefinition> nestedClasses = new ArrayList<ClassDefinition>();
	private Map<String, ConstructorDefinition> constructors = new HashMap<String, ConstructorDefinition>();

	List<FieldDefinition> fields = new ArrayList<FieldDefinition>();
	List<MethodDefinition> methods = new ArrayList<MethodDefinition>();

	public GeneratedClassDefinition(String name) {
		this.name = name;
	}

	public String getName() { return name; }
	public String getSimpleName() { return name.substring(name.lastIndexOf(".") + 1); }

	public void addFields(FieldDefinition... fields) {
		this.fields.addAll(Arrays.asList(fields));
	}
	
	public void addMethods(MethodDefinition... methods) {
		this.methods.addAll(Arrays.asList(methods));
	}
	
	public void addNestedInterface(InterfaceDefinition interfaceDef) {
		nestedInterfaces.add(interfaceDef);
	}

	public void addNestedClass(ClassDefinition classDef) {
		nestedClasses.add(classDef);
	}
	
	public ConstructorDefinition getConstructor(JClassType... parameters) {
		String signature = StringUtils.join(parameters, ",");
		if (!constructors.containsKey(signature)) {
			constructors.put(signature, new ConstructorDefinition(getSimpleName()));
		}
		return constructors.get(signature);
	}
	
	@Override
	protected void write() {
		for (InterfaceDefinition i : nestedInterfaces) {
			write(i);
		}

		for (ClassDefinition c : nestedClasses) {
			write(c);
		}
		
		for (FieldDefinition f : fields) {
			p(new Statement(f).toString());
		}
		
		for (ConstructorDefinition c : constructors.values()) {
			write(c);
		}

		for (MethodDefinition m : methods) {
			write(m);
		}
		
		o();
		p("}");
	}
}
