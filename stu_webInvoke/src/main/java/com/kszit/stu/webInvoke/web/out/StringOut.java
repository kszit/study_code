package com.kszit.stu.webInvoke.web.out;

import com.kszit.stu.webInvoke.web.context.ContextManager;

public class StringOut implements IOut{

	private String outContext;
	
	public StringOut(String aoutContext){
		this.outContext = aoutContext;
	}
	
	public void out() {
		ContextManager.getContext().out(outContext);
	}

	
}
