package com.kszit.stu.webInvoke.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kszit.stu.webInvoke.web.context.Context;
import com.kszit.stu.webInvoke.web.context.ContextManager;
import com.kszit.stu.webInvoke.web.controle.Controle;
import com.kszit.stu.webInvoke.web.controle.engine.Engine;
/**
 * 访问入口
 * @author Administrator
 *
 */
public class ServletDispatcher extends HttpServlet {
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Engine.getInstance();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	/**
	 * action： 
	 * method：	
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		System.out.println("getServletInfo:"+super.getServletInfo());
//		System.out.println("getServletName:"+super.getServletName());
		this.getServletContext();
		Context context = ContextManager.getContext();
		context.init(req, resp);
		
		Controle controle = Engine.getInstance().dispatcher();
		controle.dispatch();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	

	
}
