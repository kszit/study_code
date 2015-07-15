package com.kszit.stu.excel.jxls;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.ReaderConfig;
import net.sf.jxls.reader.XLSReadStatus;
import net.sf.jxls.reader.XLSReader;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.kszit.stu.excel.poi.patientExcelResolving.exception.ExcelDataException;
import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelDataObj;
import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientObj;
import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientOfIdCordObj;
import com.kszit.stu.excel.poi.patientExcelTemplate.obj.AreaObj;
/**
 * excel数据装换为对象
 * @author Administrator
 *
 */
public class ExcelDataTransToObj {

	static Logger log = LoggerFactory.getLogger(ExcelDataTransToObj.class);
	ExcelDataObj dataObj = new ExcelDataObj();
	
	public ExcelDataTransToObj(File excelDataFile,File xmlFile) throws IOException, SAXException, InvalidFormatException, ExcelDataException{
		long startTime = System.currentTimeMillis();
		
		

		ExcelPatientOfIdCordObj p = new ExcelPatientOfIdCordObj();
		AreaObj area = new AreaObj();
		List<ExcelPatientObj> list = new ArrayList<ExcelPatientObj>();
		Map beans = new HashMap(); 
		beans.put("area", area);
		beans.put("pat", p); 
		beans.put("pats", list); 
		
		
		ReaderConfig.getInstance().setSkipErrors(true);
		XLSReader mainReader = ReaderBuilder.buildFromXML(new BufferedInputStream(new FileInputStream(xmlFile)));
		InputStream inputXLS = new BufferedInputStream(new FileInputStream(excelDataFile));
		
		XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
		
		long endTime = System.currentTimeMillis();
		log.info(excelDataFile.getPath()+"转换为对象耗时："+((endTime-startTime)/1000)+"秒");
		
		if(list.size()==0){
			throw new ExcelDataException("excel模板没有填写数据");
		}else if(list.size()>20000){
			throw new ExcelDataException("数据表格需要限制在20000行以内");
		}
		
		dataObj.setArea(area);
		dataObj.setObjs(list);
		
	}
	
	public ExcelDataObj getExcelData(){
		return this.dataObj;
	}
	
	
	
	
	
}
