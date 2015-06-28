package com.kszit.stu.webInvoke.web.controle;

import com.kszit.stu.webInvoke.web.context.ContextManager;

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
		init();
	}

	
	private void init(){
		
	}
	
	/**
	 * 鏌ユ壘鍚堥�鐨刢ontrole澶勭悊绫�
	 * @return
	 */
	public Controle dispatcher(){
		
		String actionName = ContextManager.getContext().action();
		
		
		
		
		Controle controle = Container.getInstance().getControle(actionName.toLowerCase());
		if(controle==null){
			//鏈壘鍒帮紝
			
		}
		return controle;
		
	}
	
	public void destroy(){
		
		
	}
	
}
