package com.kszit.stu.webInvoke.web.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kszit.stu.webInvoke.utils.StringUtils;
import com.kszit.stu.webInvoke.web.UrlParam;
/**
 * 请求和响应的上下文
 * @author Administrator
 *
 */
public class Context {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public Context(){
	}
	
	public String getRequestParam(String key){
		return request.getParameter(key);
	}
	public void sendRedirect(String url){
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setParamToObj(Object o){
		Class<?> c = o.getClass();
		Method[] methods = c.getMethods();
		for(Method m:methods){
			String methodName = m.getName();
			if(methodName.startsWith("set")){
				Class[] paramTypes = m.getParameterTypes();
				if(paramTypes==null){
					continue;
				}
				Class oMethod = paramTypes[0];
				String methodNameNoSet = methodName.replace("set", "");
				methodNameNoSet = StringUtils.firstCharToLower(methodNameNoSet);
				String fieldValue = request.getParameter(methodNameNoSet);
				
				try{
					if(oMethod==String.class){
						m.invoke(o,fieldValue);
					}else if(oMethod==Date.class){
						
					}else if(oMethod==Integer.class){
						m.invoke(o,Integer.parseInt(fieldValue));
					}	
				}catch(Exception e){
					
				}
			}
		}
	}
	
	public void init(HttpServletRequest req, HttpServletResponse resp){
		this.request = req;
		this.response = resp;
	}
	
	public String action(){
		return request.getParameter(UrlParam.ACTION_NAME);
	}
	
	public String methord(){
		return request.getParameter(UrlParam.METHORD_NAME);
	}
	
	public String getSessionId(){
		if(request==null || request.getSession()==null){
			return null;
		}
		return request.getSession().getId();
	}
	
	public boolean isAjaxRequest(){
		String requestType = request.getHeader("X-Requested-With");  
		if(requestType==null){
			return false;
		}else{
			return true;
		}
	}
	
	public void out(String s){
//		response.setContentType("text/html");
//		response.setCharacterEncoding("UTF-8");
		
		 response.setContentType("text/html;charset=utf-8");
//		response.setContentLength(s.length());
		try {
			PrintWriter writer = response.getWriter();
			writer.append(s);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void out(InputStream inputStream){
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			InputStreamReader bIn = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(bIn);
			String line = null;
			while((line=br.readLine())!=null){
				writer.append(line);
			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			writer.close();			
		}
	}
	
	
	public PrintWriter getWriter(){
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;	
	}
}
