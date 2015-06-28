package com.kszit.stu.webInvoke.web.out;

import org.apache.commons.lang.StringUtils;

import com.kszit.stu.webInvoke.web.controle.Page;

public class OutFactory {

	public static IOut getOut(Page page){
		String outContent = "";
		String pageName = "";
		if(page!=null){
			outContent = page.jsonOut();
			pageName = page.getVmName();	
		}
		IOut out = null;
		if(!StringUtils.isBlank(pageName)){
			out = new PageOut(page);
		}else if(!StringUtils.isBlank(outContent)){
			out = new StringOut(outContent);
		}else{
			out = new StringOut(outContent);
		}
		
		return out;
	}
}
