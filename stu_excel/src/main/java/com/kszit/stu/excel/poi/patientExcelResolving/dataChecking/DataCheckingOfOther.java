package com.kszit.stu.excel.poi.patientExcelResolving.dataChecking;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.tools.ant.util.DateUtils;
import org.apache.tools.ant.util.StringUtils;

import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientOfOtherObj;

/**
 * 其他证件数据导入数据验证
 * @author Administrator
 *
 */
public class DataCheckingOfOther extends DataChecking{

	ExcelPatientOfOtherObj obj2 = null;
	
	public List<ExcelErrorMsg> dataChecking(ExcelPatientOfOtherObj obj){
		super.dataChecking(obj);
		
		this.obj2 = obj;
		
		checkSex();
		checkBirthday();
		checkCardType();
		
		return super.retMsg;
	}
	
	/**
	 * 
	 */
	private void checkSex(){
		obj2.setSex(StringUtils.deleteWhitespace(obj2.getSex()));
		String sex = obj2.getSex();
		int colNum = ExcelPatientOfOtherObj.COL_NUM_SEX;
		if(isNull(sex)){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.empty,
					"性别不能为空"));
			return;
		}
	}
	
	private void checkBirthday(){
		obj2.setBirthday(StringUtils.deleteWhitespace(obj2.getBirthday()));
		String brithday = obj2.getBirthday();
		if(isNull(brithday)){
			return;
		}
		
		
		//日期格式
		int colNum = ExcelPatientOfOtherObj.COL_NUM_BIRTHDAY;
		Date d = null;
		try {
			d = DateUtils.parseDateStrictly(brithday, new String[]{"yyyy-MM-dd","yyyy-M-d",
					"yyyy.MM.dd","yyyy.M.d",
					"yyyy年MM月dd日"});
		} catch (ParseException e) {
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.specialChars,
					"日期格式不能正确。日期格式应为：yyyy-MM-dd或者yyyy.MM.dd或者yyyy年MM月dd日"));
			return;
		}
		
		
		//150岁验证
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(calendar.YEAR, 150);
		
		if(new Date().after(calendar.getTime())){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.specialChars,
					"输入的出生日期已超过150岁。"));
			return;
		}
	}
	
	private void checkCardType(){
		obj2.setCertType(StringUtils.deleteWhitespace(obj2.getCertType()));
		String certType = obj2.getCertType();
		int colNum = ExcelPatientOfOtherObj.COL_NUM_CERT_TYPE;
		if(isNull(certType)){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.empty,
					"证件类型不能为空"));
			return;
		}
	}

	
	//证件号
	@Override
	protected void checkIdNumber(){
		obj.setIdNumber(StringUtils.deleteWhitespace(obj.getIdNumber()));
		String idNumber = obj.getIdNumber();
		
		int colNum = idnumberColNum();
		if(isNull(idNumber)){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.empty,
					"证件号不能为空"));
			return;
		}
		
		if(super.isBeyongLength(idNumber, 100)){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.specialChars,
					"证件号的长度不能超过100位。"));
			return;
		}
		
		//身份证号是否已经导入
		boolean isExit = IdRepeatVerify.instance().repeatId(idNumber);
		if(isExit){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.specialChars,
					"证件号已经导入。"));
			return;
		}
		
		//身份证号是否已经正式入库
		PipCommPatientInputMapper patientInputM = SpringContextHolder.getBean(PipCommPatientInputMapper.class);
				
		PipCommPatientInputExample example = new PipCommPatientInputExample();
		example.createCriteria().andIdNumberEqualTo(idNumber.toUpperCase());
		example.or().andIdNumberEqualTo(idNumber.toLowerCase());
		
		List plist = patientInputM.selectByExample(example);
		if ( plist != null && plist.size() > 0 ){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.specialChars,
					"证件号已经导入。"));
		}
	}
	
	@Override
	protected int lccColNum() {
		return ExcelPatientOfOtherObj.COL_NUM_LCCCODE;
	}

	@Override
	protected int nameColNum() {
		return ExcelPatientOfOtherObj.COL_NUM_PATIENTNAME;
	}

	@Override
	protected int idnumberColNum() {
		return ExcelPatientOfOtherObj.COL_NUM_IDNUMBER;
	}

	@Override
	protected int phoneColNum() {
		return ExcelPatientOfOtherObj.COL_NUM_PHONE;
	}

	@Override
	protected int mobileColNum() {
		return ExcelPatientOfOtherObj.COL_NUM_MOBILE;
	}
	
	
}
