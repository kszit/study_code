package com.kszit.stu.webInvoke.web.controle;


public abstract class ParentPage {

	
	
	/**
	 * 澶勭悊缁撴灉淇℃伅銆倀rue锛氭甯稿鐞嗙殑杩斿洖锛屽惁鍒欎负閿欒淇℃伅
	 */
	private String msg = "";
	/**
	 * 澶勭悊鐘舵�锛�
	 * 1锛氭纭�
	 * 2锛氶敊璇�
	 */
	private int state;
	
	public String jsonOut(){
		return "{\"state\":"+this.state+"," +
					"\"msg\":\""+this.msg+"\"," +
					"\"data\":"+this.getJsonData()+"}";
	}
	
	abstract String getJsonData();

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setState(int state) {
		this.state = state;
	}



	
	
}
