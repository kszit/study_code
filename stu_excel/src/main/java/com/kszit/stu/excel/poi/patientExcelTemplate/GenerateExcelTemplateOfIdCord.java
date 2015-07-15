package com.kszit.stu.excel.poi.patientExcelTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.kszit.stu.excel.poi.patientExcelTemplate.obj.AreaObj;

/**
 * 生成 身份证 导入的excel 模板
 * @author Administrator
 *
 */
public class GenerateExcelTemplateOfIdCord extends GenerateExcelTemplate{
	
	public static int areaColBegin = 5;
	/* 可编辑的列数   */
	public static int editColCount = 5;
	/*  模板标记    */
	public static String templateFlag = "idCord";
	
	private static String[] headColNames = new String[]{"所属项目单位编码*","姓名*","身份证号*","电话","手机"};

	
	public GenerateExcelTemplateOfIdCord(File templetFile,AreaObj area) throws FileNotFoundException, IOException{
		super(templetFile,area,headColNames);
	}


	@Override
	protected void otherSet() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected String templateFlag() {
		return templateFlag;
	}
	
	

	
}
