package com.kszit.stu.excel.poi.utils;

import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelHelp {
	public Sheet sheet = null;
	public Workbook wb = null;
	public ExcelHelp(Sheet asheet,Workbook wb){
		this.sheet = asheet;
		this.wb = wb;
	}
	
	/**
	 * 读取单元格数据，单元格不存在时，返回null
	 * @param rowNum
	 * @param colNum
	 * @return
	 */
	public String readCellStr(int rowNum,Row arow,int colNum){
		Row row = null;
		if(arow!=null){
			row = arow;
		}else{
			row = sheet.getRow(rowNum);
		}
		if(row==null){
			return null;
		}
		
		Cell cell = row.getCell(colNum);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		if(cell==null){
			return null;
		}
		String returnv = null;
	     switch(cell.getCellType()){
	     case Cell.CELL_TYPE_BLANK :
	    	 returnv = null;
	    	 break;
	     case Cell.CELL_TYPE_STRING :
	    	 returnv = cell.getStringCellValue();
	    	 break;
	     case Cell.CELL_TYPE_FORMULA ://公式
	    	 returnv = cell.getCellFormula();
	    	 break;
	     case Cell.CELL_TYPE_BOOLEAN :
	    	 break;
	     case Cell.CELL_TYPE_ERROR :
	    	 break;
	     case Cell.CELL_TYPE_NUMERIC :
	    	 returnv = cell.getNumericCellValue()+"";
	    	 break;	 
	     }
	    
		
		return returnv;
	}
	
	/**
	 * 设置单元格数据类型
	 * @param rowBeg
	 * @param rowEnd
	 * @param colBeg
	 * @param colEnd
	 */
	public void setCellDataTypeToString(int rowBeg,int rowEnd,int colBeg,int colEnd){
		int strType = HSSFCell.CELL_TYPE_STRING;
		for(int rowIndex=rowBeg;rowIndex<=rowEnd;rowIndex++){
			for(int colIndex=colBeg;colIndex<=colEnd;colIndex++){
				Cell cell = getCell(rowIndex,colIndex);
				cell.setCellType(strType);
			}
		}
	}
	/**
	 * 清楚备注
	 * @param rowBeg
	 * @param rowEnd
	 * @param colBeg
	 * @param colEnd
	 */
	public void cleanCommont(int rowBeg,int rowEnd,int colBeg,int colEnd){
		
		int rowIndex = rowBeg;
		for(;rowIndex<=rowEnd;rowIndex++){

			Row row = this.getRow(rowIndex);
			if(row==null){
				continue;
			}
			
			int colIndex = colBeg;
			for(;colIndex<=colEnd;colIndex++){
				Cell cell = row.getCell(colIndex);
				if(cell==null){
					continue;
				}
				Comment comment = cell.getCellComment();
				if(comment!=null){
					cell.removeCellComment();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param rowNum               行号
	 * @param colNum               列号
	 * @param conmmonContent       备注内容
	 * @return
	 */
	public void addCommon(int rowNum,int colNum,String conmmonContent){
		Cell cell = getCell(rowNum, colNum);
		Comment existComment = sheet.getCellComment(rowNum, colNum);
		
		String exitsComStr = "";
		if(existComment!=null){
			exitsComStr = existComment.getString().getString();
			cell.removeCellComment();
		}
		
	    CreationHelper factory = wb.getCreationHelper();

	    Drawing drawing = sheet.createDrawingPatriarch();

	    // When the comment box is visible, have it show in a 1x3 space
	    ClientAnchor anchor = factory.createClientAnchor();
	    anchor.setCol1(colNum);
	    anchor.setCol2(colNum+1);
	    anchor.setRow1(rowNum);
	    anchor.setRow2(rowNum+3);

	    // Create the comment and set the text+author
	    Comment comment = drawing.createCellComment(anchor);
	    RichTextString str = factory.createRichTextString(conmmonContent+exitsComStr);
	    comment.setString(str);
	    comment.setAuthor("系统");

	    // Assign the comment to the cell
	    cell.setCellComment(comment);

	}
	
	/**
	 * 合并单元格
	 * @param rowBeg
	 * @param rowEnd
	 * @param colBeg
	 * @param colEnd
	 */
	public void mergedRegion(int rowBeg,int rowEnd,int colBeg,int colEnd){
		sheet.addMergedRegion(new CellRangeAddress(rowBeg, rowEnd, colBeg, colEnd));
		
	}
	
	/**
	 * 不为空的单元格 添加样式。
	 * @param rowBeg
	 * @param rowEnd
	 * @param colBeg
	 * @param colEnd
	 * @param style
	 */
	public void addStyleIfNotNull(int rowBeg,int rowEnd,int colBeg,int colEnd,CellStyle style){

		int rowIndex = rowBeg;
		for(;rowIndex<=rowEnd;rowIndex++){

			Row row = this.getRow(rowIndex);
			if(row==null){
				continue;
			}
			
			int colIndex = colBeg;
			for(;colIndex<=colEnd;colIndex++){
				Cell cell = row.getCell(colIndex);
				if(cell==null){
					continue;
				}
				cell.setCellStyle(style);
			}
		}
	}
	
	/**
	 * 设置单元格样式
	 * @param rowBeg 开始行
	 * @param rowEnd 结束行
	 * @param colBeg 开始列
	 * @param colEnd 结束列
	 * @param style  样式
	 */
	public void addStyle(int rowBeg,int rowEnd,int colBeg,int colEnd,CellStyle style){

		for(int rowIndex=rowBeg;rowIndex<=rowEnd;rowIndex++){
			for(int colIndex=colBeg;colIndex<=colEnd;colIndex++){
				Cell cell = getCell(rowIndex,colIndex);
				cell.setCellStyle(style);
			}
		}
	}
	/**
	 * 设置行高
	 * @param rowBeg
	 * @param rowEnd
	 * @param height
	 */
	public void setRowHeight(int rowBeg,int rowEnd,short height){
		int rowNumIndex = rowBeg;
		for(;rowNumIndex<=rowEnd;rowNumIndex++){
			Row row = getRow(rowNumIndex);
			row.setHeight(height);
		}
	}
	
	/**
	 * 获取excel行
	 * @param rowNum
	 * @return
	 */
	public Row getRow(int rowNum){
		Row row = this.sheet.getRow(rowNum);
		if(row==null){
			row = this.sheet.createRow(rowNum);
		}
		return row;
	}
	
	/**
	 * 获得单元格
	 * @param rowNum  行号
	 * @param colNum  列号
	 * @return
	 */
	public Cell getCell(int rowNum,int colNum){
		Row row = this.getRow(rowNum);
		
		Cell cell = row.getCell(colNum);
		if(cell==null){
			cell = row.createCell(colNum);
		}
		return cell;
	}
	
	/**
	 * 隐藏列
	 * @param colBeg
	 * @param colEnd
	 */
	public void columnHidden(int colBeg,int colEnd){
		int colIndex = colBeg;
		for(;colIndex<=colEnd;colIndex++){
			this.sheet.setColumnHidden(colIndex, true);
		}
	}
	
	/**
	 * 向单元格中添加内容
	 * @param rowIndex
	 * @param begainCol
	 * @param contents
	 */
	public void addContentToRow(int rowIndex,int begainCol,String[] contents){
		Row row = this.sheet.getRow(rowIndex);
		if(row == null){
			row = this.sheet.createRow(rowIndex);
		}
		
		int colIndex = begainCol;
		for(String value:contents){
			Cell cell = row.getCell(colIndex);
			if(cell==null){
				cell = row.createCell(colIndex);
			}
			cell.setCellValue(value);
			colIndex++;
		}
		
	}
	
	
	/**
	 * 向单元格添加下拉列表
	 * @param selectContent   下拉列表内容
	 * @param firstRow        开始行
	 * @param lastRow         结束行
	 * @param firstCol        开始列
	 * @param lastCol         结束列
	 */
	public void addSelect(String[] selectContent,int firstRow,int lastRow,int firstCol,int lastCol){
//		String[] selectCStr = (String[])selectContent.toArray();
		
		//生成下拉列表  
	       CellRangeAddressList regions = new CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);//CellRangeAddressList(int firstRow,int lastRow,int firstCol,int lastCol);  
	       //生成下拉框内容  
	       DVConstraint constraint = DVConstraint.createExplicitListConstraint(selectContent);  
	       //添加下拉菜单到sheet  
	       HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);  
	       sheet.addValidationData(data_validation);  
	   	
	}
	
	
	
}
