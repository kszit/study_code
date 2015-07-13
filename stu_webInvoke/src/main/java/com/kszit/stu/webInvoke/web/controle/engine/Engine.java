package com.kszit.stu.webInvoke.web.controle.engine;

import com.kszit.stu.webInvoke.web.context.ContextManager;
import com.kszit.stu.webInvoke.web.controle.Controle;

public class Engine {

	
	
	private static Engine engine = null;
	public static Engine getInstance(){
		if(engine==null){
			engine = new Engine();
		}
		return engine;
	}
	
	private Engine(){
		Container.getInstance();
		//自动查找类
		init();
	}

	
	private void init(){
		
	}
	
	public void registerControle(Class c){
		 Container.getInstance().addControler(c);
	}
	
	/**
	 * 查找合适的controle处理类
	 * @return
	 */
	public Controle dispatcher(){
		
		String actionName = ContextManager.getContext().action();
		
		
		
		
		Controle controle = Container.getInstance().getControle(actionName.toLowerCase());
		if(controle==null){
			//未找到，
			
		}
		return controle;
		
	}
	
	public void destroy(){
		
		
	}
	
}
