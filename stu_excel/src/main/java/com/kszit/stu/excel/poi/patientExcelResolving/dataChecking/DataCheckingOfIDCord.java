package com.kszit.stu.excel.poi.patientExcelResolving.dataChecking;

import java.util.List;

import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientOfIdCordObj;

/**
 * 身份证数据导入数据验证
 * @author Administrator
 *
 */
public class DataCheckingOfIDCord extends DataChecking{

	
	
	
	
	ExcelPatientOfIdCordObj obj = null;
	
	public List<ExcelErrorMsg> dataChecking(ExcelPatientOfIdCordObj obj){
		List<ExcelErrorMsg> l = super.dataChecking(obj);
		return l;
	}

	@Override
	protected int lccColNum() {
		return ExcelPatientOfIdCordObj.COL_NUM_LCCCODE;
	}

	@Override
	protected int nameColNum() {
		return ExcelPatientOfIdCordObj.COL_NUM_PATIENTNAME;
	}

	@Override
	protected int idnumberColNum() {
		return ExcelPatientOfIdCordObj.COL_NUM_IDNUMBER;
	}

	@Override
	protected int phoneColNum() {
		return ExcelPatientOfIdCordObj.COL_NUM_PHONE;
	}

	@Override
	protected int mobileColNum() {
		return ExcelPatientOfIdCordObj.COL_NUM_MOBILE;
	}
	
	
	
}
