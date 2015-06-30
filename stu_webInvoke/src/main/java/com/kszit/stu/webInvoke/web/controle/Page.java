package com.kszit.stu.webInvoke.web.controle;

import java.util.HashMap;
import java.util.Map;

public class Page extends ParentPage{

	/**
	 * 模板名称
	 */
	private String vmName;
	
	private Map<String,Object> map = new HashMap<String,Object>();
	
	/**
	 * 请求的返回json信息
	 */
	private String jsonContent;
	
	
	
	
	
	public void put(String key,Object value){
		map.put(key, value);
	}
	
	public Map getMap(){
		return this.map;
	}
	
	public String getVmName() {
		return vmName;
	}


	public void setVmName(String vmName) {
		this.vmName = vmName;
	}




	public void setJsonContent(String jsonContent) {
		this.jsonContent = jsonContent;
	}

	@Override
	String getJsonData() {
		return this.jsonContent;
	}

	

	



	
	
}
