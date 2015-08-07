package org.stu_webBase.modifyRequet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * 过滤掉request参数中的空格
 * @author Administrator
 *
 */
public class ModifyRequest extends HttpServletRequestWrapper {

	public ModifyRequest(HttpServletRequest request) {
		super(request);
	}
	
	/**
	 * 过滤值前后的空格
	 */
	public String[] getParameterValues(String param){
		String[] result = super.getParameterValues(param);
		
		if(result==null){
			return null;
		}
		
		String[] returnResult = new String[result.length];
		for(int i=0;i<result.length;i++){
			String r = result[i];
			returnResult[i] = r.trim();
		}
		return returnResult;
		
	}

}
