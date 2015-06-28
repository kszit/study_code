package com.kszit.stu.webInvoke.web.controle;

import java.util.HashMap;
import java.util.Map;

public class Page extends ParentPage{

	/**
	 * 妯℃澘鍚嶇О
	 */
	private String vmName;
	
	private Map<String,Object> map = new HashMap<String,Object>();
	
	/**
	 * 璇锋眰鐨勮繑鍥瀓son淇℃伅
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
