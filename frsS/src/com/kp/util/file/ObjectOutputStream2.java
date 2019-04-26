package com.kp.util.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectOutputStream2 extends ObjectOutputStream { 
	
	protected ObjectOutputStream2() throws IOException, SecurityException {
		super();
	}
	public ObjectOutputStream2(OutputStream out) throws IOException {
		super(out);
	} 
	public ObjectOutputStream2(ByteArrayOutputStream out) throws IOException {
		super(out);
	} 
	@Override 

	protected void writeStreamHeader() throws IOException { 
		return;
	}
}