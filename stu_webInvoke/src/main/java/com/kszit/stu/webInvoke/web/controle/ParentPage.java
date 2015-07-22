package com.kszit.stu.webInvoke.web.controle;


public abstract class ParentPage {

	
	
	/**
	 * 处理结果信息。true：正常处理的返回，否则为错误信息
	 */
	private String msg = "";
	/**
	 * 处理状态，
	 * 1：正确
	 * 2：错误
	 */
	private int state;
	
	public String jsonOut(){
		
		String returnStr = "{\"state\":"+this.state+"," +
					"\"msg\":\""+this.msg+"\"," +
					"\"data\":"+((this.getJsonData()!=null &&(this.getJsonData().startsWith("{")||this.getJsonData().startsWith("[")))?this.getJsonData():"\""+this.getJsonData()+"\"")+"}";
		
		return returnStr;
	}
	
	abstract String getJsonData();

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setState(int state) {
		this.state = state;
	}



	
	
}
