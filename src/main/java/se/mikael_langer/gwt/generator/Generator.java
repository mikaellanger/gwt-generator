package se.mikael_langer.gwt.generator;

import static se.mikael_langer.gwt.generator.Access.PUBLIC;
import static se.mikael_langer.gwt.generator.Scope.NON_STATIC;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JRealClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.generator.ast.Expression;
import com.google.gwt.dev.generator.ast.MethodCall;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public abstract class Generator extends com.google.gwt.core.ext.Generator {
	
	private TreeLogger logger;
	private GeneratorContext context;
	private TypeOracle oracle;
	private String suffix = "_impl";
	private String typeName;
	private String packageName;
	private String className;
	private SourceWriter sourceWriter;
	private JRealClassType realClassType;
	
	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		this.logger = logger;
		this.context = context;
		this.oracle = context.getTypeOracle();
		this.typeName = typeName;
		JClassType classType = oracle.findType(typeName);
		this.packageName = classType.getPackage().getName();
		String simpleSourceName = classType.getSimpleSourceName();
		this.className = simpleSourceName + suffix;

		tryGenerate();

		return packageName + "." + className;
	}

	protected String getClassName() {
		return className;
	}
	
	private void tryGenerate() {
		PrintWriter printWriter = context.tryCreate(logger, packageName, className);		
		if (printWriter == null) return;
		
		GeneratedClassDefinition classDef = new GeneratedClassDefinition(packageName + "." + className);
		realClassType = new JRealClassType(oracle, oracle.findPackage(packageName), null, false, className, false);
        generate(classDef);
        
        ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, className);
        composer.setSuperclass(typeName);
        sourceWriter = composer.createSourceWriter(context, printWriter);
        
        classDef.write(sourceWriter);
        
        context.commit(logger, printWriter); 
	}

	private void createJClass(String name, boolean isInterface) {
		new JRealClassType(oracle, oracle.getOrCreatePackage(packageName), className, true, name, true).setEnclosingType(realClassType);
	}

	protected abstract void generate(GeneratedClassDefinition classDef);
	
	protected JClassType type(Class<?> clazz) {
		return oracle.findType(clazz.getCanonicalName());
	}
	protected JClassType type(String name) {
		JClassType ret = oracle.findType(name);
		if (ret == null) {
			ret = oracle.findType(packageName, className + "." + name);
		}
		return ret;
	}
	protected JClassType template(JClassType type, JClassType... parameters) {
		return oracle.getParameterizedType(type.isGenericType(), parameters);
	}

	protected InterfaceDefinition defineInterface(String name) {
		return defineInterface(PUBLIC, NON_STATIC, name);
	}
	protected InterfaceDefinition defineInterface(Scope scope, String name) {
		return defineInterface(PUBLIC, scope, name);
	}
	protected InterfaceDefinition defineInterface(Access access, String name) {
		return defineInterface(access, NON_STATIC, name);
	}
	protected InterfaceDefinition defineInterface(Access access, Scope scope, String name) {
		createJClass(name, true);
		return new InterfaceDefinition(access, scope, name);
	}

	protected MethodDefinition defineMethod(String name) {
		return defineMethod(PUBLIC, NON_STATIC, name);
	}
	protected MethodDefinition defineMethod(JClassType returnType, String name) {
		return defineMethod(PUBLIC, NON_STATIC, returnType, name);
	}
	protected MethodDefinition defineMethod(Scope scope, String name) {
		return defineMethod(PUBLIC, scope, name);
	}
	protected MethodDefinition defineMethod(Access access, String name) {
		return defineMethod(access, NON_STATIC, name);
	}
	protected MethodDefinition defineMethod(Access access, Scope scope, String name) {
		return defineMethod(access, scope, null, name);
	}
	protected MethodDefinition defineMethod(Access access, Scope scope, JClassType returnType, String name) {
		return new MethodDefinition(access, scope, returnType, name);
	}
	
	protected MethodParamDefinition defineParam(JClassType type, String name) {
		return new MethodParamDefinition(type, name);
	}

	protected MethodCall callMethod(String name, String... arguments) {
		return new MethodCall(name, Arrays.asList(arguments));
	}

	protected MethodCall callMethod(Class<?> clazz, String method, String... arguments) {
		return new MethodCall(clazz.getCanonicalName() + "." + method, Arrays.asList(arguments));
	}
	
	protected MethodCall callMethod(String name, Expression... arguments) {
		List<String> argumentList = new ArrayList<String>();
		for (Expression arg : arguments) {
			argumentList.add(arg.toCode());
		}
		return new MethodCall(name, argumentList);
	}
	
	protected AnonymousClass newAnonymousClass(JClassType supertype) {
		return new AnonymousClass(supertype);
	}
	
	protected ClassDefinition defineClass(String name) {
		createJClass(name, false);
		return new ClassDefinition(name);
	}
	
	protected FieldDefinition defineField(JClassType type, String name) {
		return new FieldDefinition(type, name);
	}
	
	protected Expression classLiteral(JClassType type) {
		return new Expression(type.getQualifiedSourceName() + ".class");
	}
	
	protected Expression cast(JClassType type, String obj) {
		return new Expression("((" + type.getQualifiedSourceName() + ")" + obj + ")");
	}
	
	protected Expression returnValue(Expression expression) {
		return new Expression("return " + expression); 
	}
	
	protected Expression returnValue(String expression) {
		return returnValue(new Expression(expression));
	}
}