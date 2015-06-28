package com.kszit.stu.webInvoke.web.controle;

import java.lang.reflect.Method;

import com.kszit.stu.webInvoke.web.context.ContextManager;
import com.kszit.stu.webInvoke.web.out.IOut;
import com.kszit.stu.webInvoke.web.out.OutFactory;

public abstract class Controle {
	
	private Page page;
	public Controle(){
		
	}
	
	public void setPage(Page apage) {
		this.page = apage;
	}
	
	


	public void init() {


	}
	
	
	
	
	public void out(){
		IOut out = OutFactory.getOut(this.page);
		out.out();
	}
	
	
	public void dispatch() {
		boolean isError = false;
		String methodName = ContextManager.getContext().methord();
		
		Class<?> clazz = this.getClass();
		Method m1;
		try {
			m1 = clazz.getDeclaredMethod(methodName);
			m1.invoke(this);
		} 
		/*catch (NoSuchMethodException e) {
			//调用默认处理方法
			
			isError = true;
		} catch (SecurityException e) {
			e.printStackTrace();
			isError = true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			isError = true;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			isError = true;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			isError = true;
		}
		*/
		catch(Exception e){
			isError = true;
		}finally{
			if(isError){
				this.page.setMsg("系统发生错误");
				if(!ContextManager.getContext().isAjaxRequest()){
					//错误页面
					this.page.setVmName("errorMobile.vm");
				}
			}else{
				
			}
		}
		
		
		this.out();
	}
	
}
