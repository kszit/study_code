package com.kszit.stu.excel.poi.patientExcelResolving.dataChecking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.util.StringUtils;

import com.kszit.stu.excel.poi.patientExcelResolving.obj.ExcelPatientObj;

/**
 * 身份证数据导入数据验证
 * @author Administrator
 *
 */
public abstract class DataChecking {

	
	
	List<ExcelErrorMsg> retMsg = new ArrayList<ExcelErrorMsg>();
	ExcelPatientObj obj = null;
	
	public List<ExcelErrorMsg> dataChecking(ExcelPatientObj obj){
		retMsg.clear();
		this.obj = obj;
		
		if(obj==null){
			return retMsg;
		}
		
		checkLccCode();
		checkName();
		checkIdNumber();
		checkPhone();
		checkMobile();
		
		return retMsg;
	}
	
	private void checkLccCode(){
		obj.setLccCode(StringUtils.deleteWhitespace(obj.getLccCode()));
		String code = obj.getLccCode();
		
		int colNum = lccColNum();
		if(isNull(code)){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.empty,
					"所属项目点单位不能为空"));
			return;
		}
		
	}
	protected abstract int lccColNum();

	/**
	 * 验证用户名
	 */
	private void checkName(){
		obj.setPatientName(StringUtils.deleteWhitespace(obj.getPatientName()));
		int nameMaxLength = 30;
		String name = obj.getPatientName();
		
		int colNum = nameColNum();
		if(isNull(name)){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.empty,
					"用户名不能为空"));
			return;
		}
		
		if(isBeyongLength(name, nameMaxLength)){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.beyongdLength,
					"用户名超长。最大长度为"+nameMaxLength));
			return;
		}
		
		//是否包括除汉字和字母外的其他字符
		//String regex="^[a-zA-Z0-9\u4E00-\u9FA5]+$";
		String regex="^[a-zA-Z\u4E00-\u9FA5]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match=pattern.matcher(name);
		boolean b=match.matches();
		if(!b){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.specialChars,
					"姓名中包括特殊字符。姓名中只能包括汉字和英文字母"));
			return;
		}
	}
	protected abstract int nameColNum();
	
	//身份证号
	protected void checkIdNumber(){
		obj.setIdNumber(StringUtils.deleteWhitespace(obj.getIdNumber()));
		String idNumber = obj.getIdNumber();
		
		
		int colNum = idnumberColNum();
		if(isNull(idNumber)){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.empty,
					"身份证号不能为空"));
			return;
		}
		
		//身份证位数验证
		Pattern pa = Pattern.compile("(\\d{15})|(\\d{17}[0-9X])");
		Matcher m = pa.matcher(idNumber); 
		if(!m.matches()){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.specialChars,
					"身份证号不正确。请确认身份证号位数是否为15位或者18位。"));
			return;
		}
		
		

		//身份证号是否正确，验证生日
		//生日
		String dtxt = idNumber.substring(6,14);
		if ( idNumber.length() == 15){
			dtxt = "19" + idNumber.substring(6,12);
		}
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date dd = sdf.parse(dtxt);
		}catch(Exception ex){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.specialChars,
					"身份证的生日不正确。请确认身份证号是否正确。"));
			return;
		}
	
		
		//身份证号是否已经导入
		boolean isExit = IdRepeatVerify.instance().repeatId(idNumber);
		if(isExit){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.specialChars,
					"此身份证号已经导入。"));
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
					"此身份证号已经导入。"));
		}
		
		
	}
	protected abstract int idnumberColNum();
	
	//电话
	private void checkPhone(){
		obj.setPhone(StringUtils.deleteWhitespace(obj.getPhone()));
		int nameMaxLength = 100;
		String phone = obj.getPhone();
//		if(isNull(phone)){
//			retMsg.add(new ExcelErrorMsg(ExcelPatientOfIdCordObj.COL_NUM_PHONE,
//					ExcelErrorMsg.errorType.empty,
//					"电话不能为空"));
//			return;
//		}
		int colNum = phoneColNum();
		if(phone==null){
			return;
		}
		if(isBeyongLength(phone, nameMaxLength)){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.beyongdLength,
					"电话超长。限制的最大长度为"+nameMaxLength));
			return;
		}
	}
	protected abstract int phoneColNum();
	
	//手机号
	private void checkMobile(){
		obj.setMobile(StringUtils.deleteWhitespace(obj.getMobile()));
		int length = 11;
		String mobile = obj.getMobile();
		
		int colNum = mobileColNum();
//		if(isNull(mobile)){
//			retMsg.add(new ExcelErrorMsg(ExcelPatientOfIdCordObj.COL_NUM_MOBILE,
//					ExcelErrorMsg.errorType.empty,
//					"用户名不能为空"));
//			return;
//		}
		if(mobile==null){
			return;
		}
		
		if(!isLength(mobile, length)){
			retMsg.add(new ExcelErrorMsg(colNum,
					ExcelErrorMsg.errorTypeEnum.lengthError,
					"手机号长度不正确。手机号长度应为"+length));
			return;
		}
	}  
	
	protected abstract int mobileColNum();
	
	
	
	
	/**
	 * 是否为空
	 * @param s
	 * @return
	 */
	protected boolean isNull(String s){
		return StringUtils.isBlank(s);
	}
	
	/**
	 * 是否超长
	 * @param s
	 * @return
	 */
	protected boolean isBeyongLength(String s,int length){
		if(s==null){
			return true;
		}
		if(s.length()>length){
			return true;
		}
		return false;
	}
	
	/**
	 * 长度是否正确
	 * @param s
	 * @param length
	 * @return
	 */
	protected boolean isLength(String s,int length){
		if(s==null){
			return false;
		}
		if(s.length()==length){
			return true;
		}
		return false;
	}
	
	
}
