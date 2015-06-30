package com.kszit.stu.webInvoke.web.controle.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kszit.stu.webInvoke.web.controle.Controle;
import com.kszit.stu.webInvoke.web.controle.utils.FindClassExtendsSubClass;

/**
 * 缓存所有controle
 * @author Administrator
 *
 */
public class Container {
	
	private static Logger log = LoggerFactory.getLogger(Container.class);
	
	Map<String,Object> controleName2Obj = new HashMap<String,Object>();
	
	private static Container container = null;
	public static Container getInstance(){
//		DatLogger.logSysStartDown(Container.class, "控制器容器："+container);
		if(container==null){
			
			container = new Container();
		}
		return container;
	}
	
	private Container(){
		init();
	}
	
	private void init(){
		
		FindClassExtendsSubClass<Controle> controleClassesHandler = new FindClassExtendsSubClass<Controle>("com.kszit.dareport",Controle.class);
		List<Class<Controle>> controleClasses = controleClassesHandler.getClassSubCalss(true);
		
		Class<Controle> webContail = null;
		try {
			webContail = (Class<Controle>) Class.forName("com.kszit.dareport.web.controle.test.JsonDataTest");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(webContail!=null){
			controleClasses.add(webContail);
		}
		
		
		for(Class<Controle> c:controleClasses){
			log.debug("添加控制器类："+c.getName());
			addControler(c);
		}
		
	}
	
	public Controle getControle(String name){
		return (Controle)controleName2Obj.get(name);
	}
	
	/**
	 * 注册控制类
	 * @param c
	 */
	private void addControler(Class c){
//		DatLogger.logSysStartDown(getClass(), "添加控制类："+"==>"+c.getName());
		String classNameWithPage = c.getName();
		Pattern pattern = Pattern.compile("[.]+");
		String[] nameAndPages = pattern.split(classNameWithPage);
		
		String controleName = nameAndPages[nameAndPages.length-1];
		try {
			String controlNameLow = controleName.toLowerCase();
			controleName2Obj.put(controlNameLow,c.newInstance());
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
