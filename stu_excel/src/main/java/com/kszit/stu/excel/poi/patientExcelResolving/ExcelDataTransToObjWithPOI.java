package com.kszit.stu.excel.poi.patientExcelResolving;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tools.ant.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.kszit.stu.excel.poi.patientExcelResolving.exception.ExcelDataException;
import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelDataObj;
import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientObj;
import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientOfIdCordObj;
import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientOfOtherObj;
import com.kszit.stu.excel.poi.patientExcelTemplate.ExcelHelper2;
import com.kszit.stu.excel.poi.patientExcelTemplate.GenerateExcelTemplate;
import com.kszit.stu.excel.poi.patientExcelTemplate.GenerateExcelTemplateOfIdCord;
import com.kszit.stu.excel.poi.patientExcelTemplate.GenerateExcelTemplateOfOther;
import com.kszit.stu.excel.poi.patientExcelTemplate.obj.AreaObj;
/**
 * excel数据装换为对象
 * @author Administrator
 *
 */
public class ExcelDataTransToObjWithPOI {

	
	
	
	static Logger log = LoggerFactory.getLogger(ExcelDataTransToObjWithPOI.class);
	
	
	//模板类型
	private String templateFlag = null;
	
	
	ExcelDataObj dataObj = new ExcelDataObj();
	
	File excelFile = null;
	
	Workbook book = null;
	Sheet sheet = null;
	
	ExcelHelper2 excelHelper = null;
	
	public ExcelDataTransToObjWithPOI(File excelDataFile) throws IOException, SAXException, InvalidFormatException, ExcelDataException{
		
		this.excelFile = excelDataFile;

		
		book = new HSSFWorkbook(new FileInputStream(excelDataFile));

		
		
		
		
	}
	
	
	
	public ExcelDataObj getExcelData() throws ExcelDataException{
		long startTime = System.currentTimeMillis();
		
		
		findSheet();
		
		readArea();
		
		readData();
		
		long endTime = System.currentTimeMillis();
		log.info(excelFile.getPath()+"转换为对象耗时："+((endTime-startTime)/1000)+"秒");
		
		return this.dataObj;
	}
	
	private void findSheet() throws ExcelDataException{
		
		Sheet sh = null;
		for(int i=0;i<10;i++){
			sh = this.book.getSheetAt(i);
			if(sh==null){
				continue;
			}
			
			String templateTypeFlag = templeteType(sh);
			if(templateTypeFlag==null){
				continue;
			}
			this.templateFlag = templateTypeFlag;
			break;
		}
		
		if(sh==null){
			throw new ExcelDataException("再上传的excel中没有找到模板数据");
		}
		
		this.sheet = sh;
		
		
		
		
	
		
		this.excelHelper = new ExcelHelper2(this.sheet,this.book);
	}
	
	/**
	 * 模板类型
	 * @return
	 */
	private String templeteType(Sheet sh){
		ExcelHelper2 eHelper = new ExcelHelper2(sh, null);
		
		
		int row = 1;
		int colInId = GenerateExcelTemplateOfIdCord.editColCount;
		int colInOther = GenerateExcelTemplateOfOther.editColCount;
		
		String templateOfId = GenerateExcelTemplateOfIdCord.templateFlag;
		String templateOfOther = GenerateExcelTemplateOfOther.templateFlag;
		
		
	
		String templateFlag1 = eHelper.readCellStr(row, null, colInId);
		if(templateOfId.equals(templateFlag1)){
			return templateOfId;
		}
		
		
		String templateFlag2 = eHelper.readCellStr(row, null, colInOther);
		if(templateOfOther.equals(templateFlag2)){
			return templateOfOther;
		}
		
		return null;
		
	}
	
	/**
	 * 读取数据行
	 */
	private void readData(){
		int dataRowBeg = GenerateExcelTemplate.editRowBegin;
		int dataRowCount = GenerateExcelTemplate.editRowCount;
		
		for(int i=0;i<dataRowCount;i++){

			int dataIndex = dataRowBeg+i;
			
			Row row = this.sheet.getRow(dataIndex);
			if(row!=null){
				ExcelPatientObj obj = ontRowToObj(row);
				if(obj==null){
					continue;
				}
				obj.setRow(dataIndex);
				
				this.dataObj.getObjs().add(obj);
			}
		}
		
		
	}
	
	/**
	 * 行数据转换为对象
	 * @return
	 */
	protected ExcelPatientObj ontRowToObj(Row row){
		ExcelPatientObj obj = null;
		
		if(this.templateFlag.equals(GenerateExcelTemplateOfIdCord.templateFlag)){

			ExcelPatientOfIdCordObj obj2 = new ExcelPatientOfIdCordObj();
			
			obj2.setLccCode(excelHelper.readCellStr(0,row,ExcelPatientOfIdCordObj.COL_NUM_LCCCODE));
			obj2.setIdNumber(excelHelper.readCellStr(0,row,ExcelPatientOfIdCordObj.COL_NUM_IDNUMBER));
			obj2.setPatientName(excelHelper.readCellStr(0,row,ExcelPatientOfIdCordObj.COL_NUM_PATIENTNAME));
			obj2.setPhone(excelHelper.readCellStr(0,row,ExcelPatientOfIdCordObj.COL_NUM_PHONE));
			obj2.setMobile(excelHelper.readCellStr(0,row,ExcelPatientOfIdCordObj.COL_NUM_MOBILE));
			
			if(StringUtils.isBlank(obj2.getLccCode())&&
					StringUtils.isBlank(obj2.getIdNumber())&&
					StringUtils.isBlank(obj2.getPatientName())&&
					StringUtils.isBlank(obj2.getPhone())&&
					StringUtils.isBlank(obj2.getMobile())){
				return null;
			}
			obj = obj2;
		}
		
		if(this.templateFlag.equals(GenerateExcelTemplateOfOther.templateFlag)){
			ExcelPatientOfOtherObj obj2 = new ExcelPatientOfOtherObj();

			obj2.setLccCode(excelHelper.readCellStr(0,row,ExcelPatientOfOtherObj.COL_NUM_LCCCODE));
			obj2.setIdNumber(excelHelper.readCellStr(0,row,ExcelPatientOfOtherObj.COL_NUM_IDNUMBER));
			obj2.setPatientName(excelHelper.readCellStr(0,row,ExcelPatientOfOtherObj.COL_NUM_PATIENTNAME));
			obj2.setSex(excelHelper.readCellStr(0,row,ExcelPatientOfOtherObj.COL_NUM_SEX));
			obj2.setBirthday(excelHelper.readCellStr(0,row,ExcelPatientOfOtherObj.COL_NUM_BIRTHDAY));
			obj2.setCertType(excelHelper.readCellStr(0,row,ExcelPatientOfOtherObj.COL_NUM_CERT_TYPE));
			obj2.setPhone(excelHelper.readCellStr(0,row,ExcelPatientOfOtherObj.COL_NUM_PHONE));
			obj2.setMobile(excelHelper.readCellStr(0,row,ExcelPatientOfOtherObj.COL_NUM_MOBILE));
			
			if(StringUtils.isBlank(obj2.getLccCode())&&
					StringUtils.isBlank(obj2.getIdNumber())&&
					StringUtils.isBlank(obj2.getSex())&&
					StringUtils.isBlank(obj2.getBirthday())&&
					StringUtils.isBlank(obj2.getCertType())&&
					StringUtils.isBlank(obj2.getPatientName())&&
					StringUtils.isBlank(obj2.getPhone())&&
					StringUtils.isBlank(obj2.getMobile())){
				return null;
			}

			obj = obj2;
		}
		
		return obj;
	}
	
	/**
	 * 读取地区
	 */
	private void readArea(){
		int row = GenerateExcelTemplate.areaRowNum;
		int shengIdCol = 0;
		
		if(this.templateFlag.equals(GenerateExcelTemplateOfIdCord.templateFlag)){
			shengIdCol = GenerateExcelTemplateOfIdCord.areaColBegin;
		}
		
		if(this.templateFlag.equals(GenerateExcelTemplateOfOther.templateFlag)){
			shengIdCol = GenerateExcelTemplateOfOther.areaColBegin;
		}
		
		
		
		AreaObj area = new AreaObj();
		
		
		Row arearRow = sheet.getRow(row);
		
		area.setProvinceId(excelHelper.readCellStr(0,arearRow,shengIdCol));
		area.setProvinceName(excelHelper.readCellStr(0,arearRow,shengIdCol+1));
		area.setCityId(excelHelper.readCellStr(0,arearRow,shengIdCol+2));
		area.setCityName(excelHelper.readCellStr(0,arearRow,shengIdCol+3));
		area.setDistrictId(excelHelper.readCellStr(0,arearRow,shengIdCol+4));
		area.setDistrictName(excelHelper.readCellStr(0,arearRow,shengIdCol+5));
		area.setTownId(excelHelper.readCellStr(0,arearRow,shengIdCol+6));
		area.setTownName(excelHelper.readCellStr(0,arearRow,shengIdCol+7));
		area.setVillageId(excelHelper.readCellStr(0,arearRow,shengIdCol+8));
		area.setVillageName(excelHelper.readCellStr(0,arearRow,shengIdCol+9));
		
		dataObj.setArea(area);
	}
	
	
	
	
	
}
