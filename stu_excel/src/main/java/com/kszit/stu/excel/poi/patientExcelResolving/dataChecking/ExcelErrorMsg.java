package com.kszit.stu.excel.poi.patientExcelResolving.dataChecking;

public class ExcelErrorMsg {
	

	public enum errorTypeEnum{
		empty,beyongdLength,lengthError,specialChars
	}
	/** 为空 **/
	public static final String MSG_NULL = "";
	/** 长度超长 **/
	public static final String MSG_BEYONGD_LENGTH = "";
	
	
	private Integer colRum;
	private errorTypeEnum errorType;
	private String msg;
	
	public ExcelErrorMsg(Integer acolRum,errorTypeEnum aerrorType,String amsg){
		this.colRum = acolRum;
		this.errorType = aerrorType;
		this.msg = amsg;
	}
	
	public Integer getColRum() {
		return colRum;
	}
	public void setColRum(Integer colRum) {
		this.colRum = colRum;
	}
	public errorTypeEnum getErrorType() {
		return errorType;
	}
	public void setErrorType(errorTypeEnum errorType) {
		this.errorType = errorType;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
