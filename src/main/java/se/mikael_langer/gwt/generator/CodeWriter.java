package se.mikael_langer.gwt.generator;

import com.google.gwt.user.rebind.SourceWriter;

public abstract class CodeWriter {
	private SourceWriter sourceWriter;
	public void write(SourceWriter sourceWriter) {
		this.sourceWriter = sourceWriter;
		try { 
			write(); 
		} finally { 
			sourceWriter = null; 
		}
	}
	protected abstract void write();
	
	protected void write(CodeWriter w) {
		w.write(sourceWriter);
	}
	
	protected void p(String s) {
		sourceWriter.println(s);
	}
	
	protected void i() {
		sourceWriter.indent();
	}
	
	protected void o() {
		sourceWriter.outdent();
	}
	
	protected String c(Class<?> c) {
		return c.getCanonicalName();
	}

}
