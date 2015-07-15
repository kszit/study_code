package com.kszit.stu.excel.poi.patientExcelTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

import com.kszit.stu.excel.poi.patientExcelTemplate.obj.AreaObj;

/**
 * 生成excel 模板
 * @author Administrator
 *
 */
public abstract class GenerateExcelTemplate {
	
	protected HSSFWorkbook book = null;
	protected HSSFSheet sheet = null;
	
	/** 标题行 **/
	public static int titleRowNum = 0;
	/** 区域行 **/
	public static int areaRowNum = 0;
	/** head行 **/
	public static int headRowNum = 1;
	/** 数据开始行 **/
	public static int editRowBegin = 2;
	
	private int headColCount;
	//数据 行数
	public static int editRowCount = 5000;
	private int editColCount;
	
	private String[] headColNames = null;
	
	private AreaObj area;
	
	//数据单元格宽度
	private int dataCellWidth = 5000;
	//数据单元格高度
	private short dataCellHeight = 400;
	//数据单元格样式
	private CellStyle dataCellStyle = null;
	
	//标题单元格样式
	private CellStyle titleCellStyle = null;
	private short titleRowHeight = 500;
	
	//列头单元格样式
	private CellStyle headCellStyle = null;
	private short headRowHeight = 400;
	
	
	
	ExcelHelper2 excelhelp = null;
	
	
	
	public GenerateExcelTemplate(File templetFile,AreaObj area,String[] aheadColNames) throws FileNotFoundException, IOException{
		this.area = area;
		this.headColNames = aheadColNames;
		this.editColCount = this.headColNames.length;
		this.headColCount = this.headColNames.length;
		//book = new HSSFWorkbook(new FileInputStream(templetFile));
		book = new HSSFWorkbook();
		sheet = book.createSheet("Sheet1");
		
//		HSSFCellStyle sheetStyle = book.createCellStyle();
//		sheetStyle.setFillBackgroundColor(new HSSFColor.BLUE().getIndex());
//		sheet.setDefaultColumnStyle(10, (CellStyle)sheetStyle);

		this.excelhelp = new ExcelHelper2(this.sheet,this.book);
	}
	
	

	
	/**
	 * 输出excel模板
	 * @param out
	 * @throws IOException
	 */
	public void outTemplet(OutputStream out) throws IOException{
		this.setTitle();
		this.createHeadCol();
		this.setArea();
		this.setFlag();
		
		this.lockSheet();
		
		this.setDataType();
		
		//表头冻结
		sheet.createFreezePane( 0, 2, 0, 2);
		
		
		
		otherSet();
		
		book.write(out);
	}
	
	/**
	 * 
	 */
	protected abstract void otherSet();
	/**
	 * 标题单元格样式
	 * @return
	 */
	protected CellStyle titleCellStyle(){
		if(titleCellStyle==null){
			//创建单元格样式
			titleCellStyle = this.book.createCellStyle();
			//设置单元为可编辑
			titleCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			titleCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			titleCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			titleCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			
			
			titleCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
			titleCellStyle.setFillPattern(CellStyle.ALIGN_FILL);
			
			Font font = this.book.createFont();
		    font.setFontHeightInPoints((short)18);
		    font.setFontName("Courier New");
//		    font.setItalic(true);
//		    font.setStrikeout(true);
		    
			titleCellStyle.setFont(font);
			
		}
		return titleCellStyle;
	}
	
	/**
	 * 列头单元格样式
	 * @return
	 */
	protected CellStyle headCellStyle(){
		if(headCellStyle==null){
			//创建单元格样式
			headCellStyle = this.book.createCellStyle();
			//设置单元为可编辑
			headCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			headCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			headCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			headCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			
			titleCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
			titleCellStyle.setFillPattern(CellStyle.ALIGN_FILL);
			
		}
		return headCellStyle;
	}
	
	
	/**
	 * 数据单元格样式
	 * @return
	 */
	protected CellStyle dataCellStyle(){
		if(dataCellStyle==null){
			//创建单元格样式
			dataCellStyle = this.book.createCellStyle();
	
			//设置单元为可编辑
			dataCellStyle.setLocked(false); 
			dataCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			dataCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			dataCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			dataCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			
			
		}
		return dataCellStyle;
	}
	
	

	
	/**
	 * 设置标题
	 * @param title
	 */
	private void setTitle(){
		//标题跨单元格个数
		int endTitleNum = headColNames.length-1;
		
		String title = area.getProvinceName()+
				area.getCityName()+
				area.getDistrictName()+
				area.getTownName()+
				area.getVillageName();
		
		//设置标题
		excelhelp.addContentToRow(titleRowNum,0,new String[]{title});

		//合并单元格
		excelhelp.mergedRegion(titleRowNum, 0, 0, endTitleNum);
		//添加样式
		excelhelp.addStyle(this.titleRowNum, this.titleRowNum, 0, endTitleNum, this.titleCellStyle());

		
		//设置行高
		excelhelp.setRowHeight(this.titleRowNum,this.titleRowNum,this.titleRowHeight);
		
	}
	
	/**
	 * 设置excel头
	 */
	private void createHeadCol(){
		int headRowNum = this.headRowNum;
		int colNumBeg = 0;
		int colNumEnd = headColCount-1;
		
		//设置标题
		excelhelp.addContentToRow(headRowNum,colNumBeg,this.headColNames);
		
		//设置样式
		excelhelp.addStyle(headRowNum,headRowNum,colNumBeg,colNumEnd,this.headCellStyle());
		
		//设置行高
		excelhelp.setRowHeight(headRowNum,headRowNum,this.headRowHeight);
		
		
	}
	
	/**
	 * 设置地区
	 * @param areas
	 */
	private void setArea(){
		String[] contents = new String[]{
				area.getProvinceId(),area.getProvinceName(),
				area.getCityId(),area.getCityName(),
				area.getDistrictId(),area.getDistrictName(),
				area.getTownId(),area.getTownName(),
				area.getVillageId(),area.getVillageName()};
		
		excelhelp.addContentToRow(0,editColCount,contents);
		
		excelhelp.columnHidden(editColCount,editColCount+10);
	}
	
	/**
	 * 设置模板标记
	 * @param areas
	 */
	private void setFlag(){
		String flag = this.templateFlag();
		
		String[] contents = new String[]{flag};
		
		excelhelp.addContentToRow(1,editColCount,contents);
		
	}
	

	
	
	
	/**
	 * 锁定excel非数据单元格
	 */
	private void lockSheet(){
		//设置工作表保护密码
		sheet.protectSheet("密码123");
		
		for(int j=0;j<this.editColCount;j++){
			this.sheet.setColumnWidth(j, dataCellWidth);
		}
		

		
		
		for(int i=0;i<this.editRowCount;i++){
			int rowNum = i+this.editRowBegin;
			Row crow = excelhelp.getRow(rowNum);
			
			crow.setHeight(dataCellHeight);
			for(int j=0;j<this.editColCount;j++){
				Cell cCell = excelhelp.getCell(rowNum,j);
				
				cCell.setCellType(Cell.CELL_TYPE_STRING);
				cCell.setCellStyle(dataCellStyle());
			}
			
		}
	}
	
	private void setDataType(){
		excelhelp.setCellDataTypeToString(editRowBegin, editRowCount, 0, editColCount-1);
		
	}
	
	protected abstract String templateFlag();

	
}
