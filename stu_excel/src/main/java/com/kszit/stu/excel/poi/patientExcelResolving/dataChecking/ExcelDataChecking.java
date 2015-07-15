package com.kszit.stu.excel.poi.patientExcelResolving.dataChecking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelDataObj;
import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientObj;
import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientOfIdCordObj;
import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientOfOtherObj;
import com.kszit.stu.excel.poi.patientExcelTemplate.ExcelHelper2;
import com.kszit.stu.excel.poi.patientExcelTemplate.GenerateExcelTemplate;
import com.kszit.stu.excel.poi.patientExcelTemplate.GenerateExcelTemplateOfIdCord;

/**
 * excel数据验证
 * @author Administrator
 *
 */
public class ExcelDataChecking {
	Workbook wb = null;
	Sheet dataSheet = null;
	ExcelDataObj excelData = null;
	
	private StringBuffer errorMsgStr = new StringBuffer();
	private boolean hasError = false;
	
	//数据开始行
	int excelBeginRow = 2;
	
	//数据验证类
	private DataCheckingOfIDCord idCordDataChecking = new DataCheckingOfIDCord();
	private DataCheckingOfOther otherDataChecking = new DataCheckingOfOther();
	
	ExcelHelper2 excelHelper = null;
	
	public ExcelDataChecking(File excelFile,ExcelDataObj excelData) throws FileNotFoundException, IOException{
		wb = new HSSFWorkbook(new FileInputStream(excelFile));
		dataSheet = wb.getSheetAt(0);
		
		excelHelper = new ExcelHelper2(dataSheet,wb);
		
		this.excelData = excelData;
		
		cleanCommonAndErrorStyle();
		
	}
	
	/**
	 * 清楚错误标记和备注
	 */
	private void cleanCommonAndErrorStyle(){
		excelHelper.cleanCommont(excelBeginRow, 
				GenerateExcelTemplate.editRowCount, 
				0,
				GenerateExcelTemplateOfIdCord.editColCount-1);
		
		CellStyle cStyle = wb.createCellStyle();
		//设置单元为可编辑
		cStyle.setLocked(false); 
		cStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cStyle.setBorderRight(CellStyle.BORDER_THIN);
		cStyle.setBorderTop(CellStyle.BORDER_THIN);
		cStyle.setBorderBottom(CellStyle.BORDER_THIN);
		
		excelHelper.addStyleIfNotNull(excelBeginRow, 
				GenerateExcelTemplate.editRowCount, 
				0,
				GenerateExcelTemplateOfIdCord.editColCount-1,
				cStyle);
	}

	
	
	public void dataChecking(){
		if(excelData==null){
			return ;
		}
		
		List<ExcelPatientObj> dList = excelData.getObjs();
		 
		if(dList==null || dList.size()==0){
			return;
		}
		
		
		
		for(ExcelPatientObj dObj:dList){
			
			
			//数据验证
			List<ExcelErrorMsg> errorMsg = null;
			if(dObj instanceof ExcelPatientOfIdCordObj){
				ExcelPatientOfIdCordObj o = (ExcelPatientOfIdCordObj)dObj;
				errorMsg = idCordDataChecking.dataChecking(o);
			}else if(dObj instanceof ExcelPatientOfOtherObj){
				ExcelPatientOfOtherObj o = (ExcelPatientOfOtherObj)dObj;
				errorMsg = otherDataChecking.dataChecking(o);
			}
			
			int rowNum = dObj.getRow();
			StringBuffer sb = new StringBuffer();
			for(ExcelErrorMsg eMsg:errorMsg){
				hasError = true;
				int colNum = eMsg.getColRum();
				excelHelper.addStyle(rowNum, rowNum, colNum, colNum, getErrorCellStyle());
				excelHelper.addCommon(rowNum, colNum, eMsg.getMsg());
				sb.append("第"+rowNum+"行,第"+colNum+"列："+eMsg.getMsg()+"     ");
			}
			
			//输出行错误系信息
//			excelHelper.addContentToRow(rowNum, ExcelPatientOfIdCordObj.COL_NUM_ERROR+11, new String[]{sb.toString()});
			
			errorMsgStr.append(sb.toString()+"<br>");
			
		}
		
		
		
		
	}
	
	private CellStyle erroryStyle = null;
	/**
	 * 错误单元格样式
	 * @return
	 */
	private CellStyle getErrorCellStyle() {
		if (erroryStyle == null) {
			erroryStyle = wb.createCellStyle();
			erroryStyle.setLocked(false); 
			erroryStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			erroryStyle.setFillForegroundColor(HSSFColor.RED.index);
			erroryStyle.setFillBackgroundColor(HSSFColor.RED.index);
		}
		return erroryStyle;
	}
	
	/**
	 * 是否发生错误
	 * @return
	 */
	public boolean hasError(){
		return this.hasError;
	}
	
	/**
	 * 数据处理信息。错误或者成功条数
	 * @return
	 */
	public String getMsg(){
		if(hasError()){
			return errorMsgStr.toString();
		}
		return "成功导入："+excelData.getObjs().size()+"条";
	}	
	/**
	 * 输出验证后的excel
	 * @param out
	 * @throws IOException
	 */
	public void writeCheckingExcel(OutputStream out) throws IOException{
		this.wb.write(out);
	}
	
}
