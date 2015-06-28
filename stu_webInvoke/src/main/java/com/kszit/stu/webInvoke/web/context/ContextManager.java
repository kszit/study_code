package com.kszit.stu.webInvoke.web.context;


public class ContextManager {
	
	private static ThreadLocal<Context> contexts = new ThreadLocal<Context>();
	
	public static Context getContext(){
		Context context = contexts.get();
		if(context==null){
			context = new Context();
			contexts.set(context);
		}
		return context;
	}
	
	public static void removeContext(){
		
	}
	
	
}