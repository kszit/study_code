package com.kszit.stu.excel.poi.patientExcelResolving.obj;

import java.util.ArrayList;
import java.util.List;

import com.kszit.stu.excel.poi.patientExcelTemplate.obj.AreaObj;

/**
 * excel数据封装
 * @author Administrator
 *
 */
public class ExcelDataObj {

	AreaObj area;
	
	List<ExcelPatientObj> objs = new ArrayList<ExcelPatientObj>();

	public AreaObj getArea() {
		return area;
	}

	public void setArea(AreaObj area) {
		this.area = area;
	}

	public List<ExcelPatientObj> getObjs() {
		return objs;
	}

	public void setObjs(List<ExcelPatientObj> objs) {
		this.objs = objs;
	}
	
	
}
