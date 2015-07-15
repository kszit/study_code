package com.kszit.stu.excel.poi.patientExcelResolving.obj;


/**
 * 用户填写的excel模板中数据
 * @author Administrator
 *
 */
public class ExcelPatientObj {

	
	private int row;
    private String lccCode;
    private String patientName;
    private String idNumber;
    private String phone;
    private String mobile;
	public String getLccCode() {
		return lccCode;
	}
	public void setLccCode(String lccCode) {
		this.lccCode = lccCode;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
    
}
