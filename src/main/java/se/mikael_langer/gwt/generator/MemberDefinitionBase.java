package se.mikael_langer.gwt.generator;

public abstract class MemberDefinitionBase extends CodeWriter {
	private String name;
	private Access access;
	private Scope scope;
	
	protected MemberDefinitionBase(Access access, Scope scope, String name) {
		this.name = name;
		this.scope = scope;
		this.access = access;
	}

	public String getName() { return name; }
	public Access getAccess() { return access; }
	public Scope getScope() { return scope; }
	
}
