package com.kszit.stu.excel.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.kszit.util.findResource.Resources;

public class PoiTest {

	/**
	 * 冻结单元格
	 * @param a
	 * @throws IOException
	 */
	public static void main2(String[] a) throws IOException{
		File f = Resources.getResourceAsFile("workbook2.xls");
		
		 Workbook wb = new HSSFWorkbook();
		    Sheet sheet1 = wb.createSheet("new sheet");
		    Sheet sheet2 = wb.createSheet("second sheet");
		    Sheet sheet3 = wb.createSheet("third sheet");
		    Sheet sheet4 = wb.createSheet("fourth sheet");

		    // Freeze just one row
		    sheet1.createFreezePane( 0, 1, 0, 1 );
		    // Freeze just one column
		    sheet2.createFreezePane( 1, 0, 1, 0 );
		    // Freeze the columns and rows (forget about scrolling position of the lower right quadrant).
		    sheet3.createFreezePane( 2, 2 );
		    // Create a split with the lower left side being the active quadrant
		    sheet4.createSplitPane( 2000, 2000, 0, 0, Sheet.PANE_LOWER_LEFT );
		    
		    
		    

		    FileOutputStream fileOut = new FileOutputStream(f);
		    wb.write(fileOut);
		    fileOut.close();
		
	}
	
	/**
	 * 某单元格不可编辑:（只有被保护的sheet才可以锁定单元格）
	 * 1、给工作表sheet设置保护密码
	   2、将需要修改的单元格样式中的locked 设为false
	 * @param a
	 * @throws IOException
	 */
	public static void main33(String[] a) throws IOException{
		//创建Excel工作簿对象
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建Excel工作表对象
		HSSFSheet sheet = wb.createSheet("new sheet-加密");  
		//设置工作表保护密码
		sheet.protectSheet("加密");
		//创建Excel工作表的行
		
		//创建单元格样式
		HSSFCellStyle cellStyle = wb.createCellStyle();
		//设置单元为可编辑
		cellStyle.setLocked(false); 
		
		HSSFRow row = sheet.createRow((short)0); 
		HSSFCell cell= row.createCell(1);
		cell.setCellStyle(cellStyle);
		
		
		
		File f = Resources.getResourceAsFile("workbook5.xls");
		FileOutputStream fileOut = new FileOutputStream(f);
	    wb.write(fileOut);
	    fileOut.close();
	    
	    
	}
	

	
}
