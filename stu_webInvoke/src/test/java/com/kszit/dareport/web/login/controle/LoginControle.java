package com.kszit.dareport.web.login.controle;

import com.kszit.stu.webInvoke.web.controle.Controle;
import com.kszit.stu.webInvoke.web.controle.Page;
import com.kszit.stu.webInvoke.web.controle.engine.Engine;

/**
 * 返回json格式数据的测试类
 * @author Administrator
 *
 */
public class LoginControle extends Controle{
	

	/**
	 * webinvoke/dareport.do?a=LoginControle&m=loginPage
	 */
	public void loginPage(){
		Page page = new Page();
		page.setVmName("index2.html");
		page.put("name", "name中");
		super.setPage(page);
	}
	/**
	 * 调用：webinvoke/dareport.do?a=JsonDataTest&m=getJsonData
	 */
	public void getJsonData(){
		Page page = new Page();
		page.setJsonContent("中国打底");
		super.setPage(page);
	}
	
	
}
