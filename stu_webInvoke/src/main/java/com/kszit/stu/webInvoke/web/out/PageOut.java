package com.kszit.stu.webInvoke.web.out;

import java.util.Properties;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.kszit.stu.webInvoke.web.context.ContextManager;
import com.kszit.stu.webInvoke.web.controle.Page;

public class PageOut implements IOut{

	private Page page;
	public PageOut(Page apage){
		this.page = apage;
	}
	
	
	public void out() {

		// 取得velocity的模版
		Template t = VelocityOut.getInstance().getVelocityTemplate(page.getVmName());

		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();
		Set<String> keys = page.getMap().keySet();
		for(String key:keys){
			Object obj = page.getMap().get(key);
			context.put(key,obj);
		}

		try {
			t.merge(context, ContextManager.getContext().getWriter());
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			e.printStackTrace();
		}

		
	}

}



class VelocityOut{
	private static VelocityOut velocity = null;
	public static VelocityOut getInstance(){
		if(velocity==null){
			velocity = new VelocityOut();
		}
		return velocity;
	}
	
	private VelocityEngine ve = new VelocityEngine();
	private VelocityOut(){
		Properties p = new Properties();
		// 设置输入输出编码类型。和这次说的解决的问题无关
		p.setProperty(Velocity.INPUT_ENCODING, "utf-8");
		p.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
		
		// 设置velocity资源加载方式为class
		p.setProperty("resource.loader", "class");
		// 设置velocity资源加载方式为file时的处理类
		p.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		// 初始化并取得Velocity引擎
		try {
			ve.init(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	private VelocityEngine getVelocityEngine(){
//		return ve;
//	}
	
	public Template getVelocityTemplate(String name){
		Template t = null;
		try {
			t = ve.getTemplate(name);
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	
	
}
