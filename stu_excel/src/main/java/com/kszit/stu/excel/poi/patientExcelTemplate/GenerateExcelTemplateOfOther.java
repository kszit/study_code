package com.kszit.stu.excel.poi.patientExcelTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientOfOtherObj;
import com.kszit.stu.excel.poi.patientExcelTemplate.obj.AreaObj;

/**
 * 生成 身份证 导入的excel 模板
 * @author Administrator
 *
 */
public class GenerateExcelTemplateOfOther extends GenerateExcelTemplate{
	
	public static int areaColBegin = 8;
	/* 可编辑的列数   */
	public static int editColCount = 8;
	/* 模板标记 */
	public static String templateFlag = "other";
	
	private static String[] headColNames = new String[]{"所属项目单位编码*","姓名*","性别*","出生日期*","证件类型*","证件号*","电话","手机"};


	private static String[] sexes = new String[]{"男","女"};


	private static String[] certTypes = new String[]{"居民户口簿","护照","军官证","驾驶证","港涣居民来往内地通行证","台湾居民来往内地通行证","其他法定有效证件"};
	
	
	public GenerateExcelTemplateOfOther(File templetFile,AreaObj area) throws FileNotFoundException, IOException{
		super(templetFile,area,headColNames);
	}



	@Override
	protected void otherSet() {
		excelhelp.addSelect(sexes, 
				super.editRowBegin, 
				super.editRowCount, 
				ExcelPatientOfOtherObj.COL_NUM_SEX, 
				ExcelPatientOfOtherObj.COL_NUM_SEX);

		
		
		excelhelp.addSelect(certTypes, 
				super.editRowBegin, 
				super.editRowCount, 
				ExcelPatientOfOtherObj.COL_NUM_CERT_TYPE, 
				ExcelPatientOfOtherObj.COL_NUM_CERT_TYPE);
		/*
		CreationHelper createHelper = book.getCreationHelper();
		CellStyle cellStyle = book.createCellStyle();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
		cellStyle.setLocked(false); 
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		excelhelp.addStyle(editRowBegin, editRowCount, 3, 3, cellStyle);
		*/
	}



	@Override
	protected String templateFlag() {
		return templateFlag;
	}
	
	

	
}
