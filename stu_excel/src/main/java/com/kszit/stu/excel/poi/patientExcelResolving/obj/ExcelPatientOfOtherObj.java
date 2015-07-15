package com.kszit.stu.excel.poi.patientExcelResolving.obj;


/**
 * 其他证件类型excel数据
 * @author Administrator
 *
 */
public class ExcelPatientOfOtherObj  extends ExcelPatientObj{


	public static final int COL_NUM_LCCCODE = 0;
	public static final int COL_NUM_PATIENTNAME = 1;
	
	public static final int COL_NUM_SEX = 2;
	public static final int COL_NUM_BIRTHDAY = 3;
	public static final int COL_NUM_CERT_TYPE = 4;
	
	
	public static final int COL_NUM_IDNUMBER = 5;
	public static final int COL_NUM_PHONE = 6;
	public static final int COL_NUM_MOBILE = 7;
	public static final int COL_NUM_ERROR = 8;
	
	
	private String sex;
	private String birthday;
	private String certType;
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	
}
