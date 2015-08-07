package com.kszit.stu.dataConvert.xml.dom4j.bigxml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

public class Dict extends DictProperty {
	
	private List<DictItem> item = new ArrayList<DictItem>();
	
	public Dict(File xmlPName){
		super.xmlToObj(xmlPName);
	}
	public List<DictItem> getItem() {
		return item;
	}
	
	/**
	 * 分页加载字典数据
	 * @param startIndex  开始索引
	 * @param pageSize    个数
	 * @return
	 */
	public List<DictItem> getItem(int startIndex,int pageSize) {
		List<DictItem> r = new ArrayList<DictItem>();
		int itemSize = item.size();
		if(startIndex>=itemSize){
			return r;
		}
		
		int j = 0;
		for(int i=startIndex;i<itemSize;i++,j++){
			if(j==pageSize){
				break;
			}
			r.add(item.get(i));
		}
		return r;
	}
	public void setItem(List<DictItem> item) {
		this.item = item;
	}

	public static class DictItem{
		//国家字典属性值
		public String COUNTRY_CODE;
		public String COUNTRY_NAME;
		public String ENGLISH_ABBR;
		public String TWO_CODE;
		public String THREE_CODE;
		public String FULL_NAME;
		//通用字典的属性值
		public String DICT_CODE;
		public String DICT_NAME;
		public String VCODE;
		public String VNAME;
		public String V_ID;
		public String HELP_CODE;
		//药品字典属性
		public String MEDICINE_CODE;
		public String MEDICINE_NAME;
		public String MEDICINE_ENGLISH_NAME;
		public String TYPE_CODE;
		public String DOSE_UNIT;
		public String MAINTAIN_DOSE_UNIT;
		
		public String getCOUNTRY_CODE() {
			return COUNTRY_CODE;
		}
		public void setCOUNTRY_CODE(String cOUNTRY_CODE) {
			COUNTRY_CODE = cOUNTRY_CODE;
		}
		public String getCOUNTRY_NAME() {
			return COUNTRY_NAME;
		}
		public void setCOUNTRY_NAME(String cOUNTRY_NAME) {
			COUNTRY_NAME = cOUNTRY_NAME;
		}
		public String getENGLISH_ABBR() {
			return ENGLISH_ABBR;
		}
		public void setENGLISH_ABBR(String eNGLISH_ABBR) {
			ENGLISH_ABBR = eNGLISH_ABBR;
		}
		public String getTWO_CODE() {
			return TWO_CODE;
		}
		public void setTWO_CODE(String tWO_CODE) {
			TWO_CODE = tWO_CODE;
		}
		public String getTHREE_CODE() {
			return THREE_CODE;
		}
		public void setTHREE_CODE(String tHREE_CODE) {
			THREE_CODE = tHREE_CODE;
		}
		public String getFULL_NAME() {
			return FULL_NAME;
		}
		public void setFULL_NAME(String fULL_NAME) {
			FULL_NAME = fULL_NAME;
		}
		public String getDICT_CODE() {
			return DICT_CODE;
		}
		public void setDICT_CODE(String dICT_CODE) {
			DICT_CODE = dICT_CODE;
		}
		public String getDICT_NAME() {
			return DICT_NAME;
		}
		public void setDICT_NAME(String dICT_NAME) {
			DICT_NAME = dICT_NAME;
		}
		public String getVCODE() {
			return VCODE;
		}
		public void setVCODE(String vCODE) {
			VCODE = vCODE;
		}
		public String getVNAME() {
			return VNAME;
		}
		public void setVNAME(String vNAME) {
			VNAME = vNAME;
		}
		public String getHELP_CODE() {
			return HELP_CODE;
		}
		public void setHELP_CODE(String hELP_CODE) {
			HELP_CODE = hELP_CODE;
		}
		public String getMEDICINE_CODE() {
			return MEDICINE_CODE;
		}
		public void setMEDICINE_CODE(String mEDICINE_CODE) {
			MEDICINE_CODE = mEDICINE_CODE;
		}
		public String getMEDICINE_NAME() {
			return MEDICINE_NAME;
		}
		public void setMEDICINE_NAME(String mEDICINE_NAME) {
			MEDICINE_NAME = mEDICINE_NAME;
		}
		public String getMEDICINE_ENGLISH_NAME() {
			return MEDICINE_ENGLISH_NAME;
		}
		public void setMEDICINE_ENGLISH_NAME(String mEDICINE_ENGLISH_NAME) {
			MEDICINE_ENGLISH_NAME = mEDICINE_ENGLISH_NAME;
		}
		public String getTYPE_CODE() {
			return TYPE_CODE;
		}
		public void setTYPE_CODE(String tYPE_CODE) {
			TYPE_CODE = tYPE_CODE;
		}
		public String getDOSE_UNIT() {
			return DOSE_UNIT;
		}
		public void setDOSE_UNIT(String dOSE_UNIT) {
			DOSE_UNIT = dOSE_UNIT;
		}
		public String getMAINTAIN_DOSE_UNIT() {
			return MAINTAIN_DOSE_UNIT;
		}
		public void setMAINTAIN_DOSE_UNIT(String mAINTAIN_DOSE_UNIT) {
			MAINTAIN_DOSE_UNIT = mAINTAIN_DOSE_UNIT;
		}
		public String getV_ID() {
			return V_ID;
		}
		public void setV_ID(String v_ID) {
			V_ID = v_ID;
		}
		
		public String toString(){
			return (StringUtils.isBlank(this.COUNTRY_CODE)?"":"COUNTRY_CODE:"+this.COUNTRY_CODE)+
					(StringUtils.isBlank(this.ENGLISH_ABBR)?"":"\nENGLISH_ABBR:"+this.ENGLISH_ABBR)+
					(StringUtils.isBlank(this.TWO_CODE)?"":"\nTWO_CODE:"+this.TWO_CODE)+
					(StringUtils.isBlank(this.THREE_CODE)?"":"\nTHREE_CODE:"+this.THREE_CODE)+
					(StringUtils.isBlank(this.FULL_NAME)?"":"\nFULL_NAME:"+this.FULL_NAME)+
					(StringUtils.isBlank(this.DICT_CODE)?"":"\nDICT_CODE:"+this.DICT_CODE)+
					(StringUtils.isBlank(this.DICT_NAME)?"":"\nDICT_NAME:"+this.DICT_NAME)+
					(StringUtils.isBlank(this.VCODE)?"":"\nVCODE:"+this.VCODE)+
					(StringUtils.isBlank(this.VNAME)?"":"\nVNAME:"+this.VNAME)+
					(StringUtils.isBlank(this.V_ID)?"":"\nV_ID:"+this.V_ID)+
					(StringUtils.isBlank(this.HELP_CODE)?"":"\nHELP_CODE:"+this.HELP_CODE)+
					(StringUtils.isBlank(this.MEDICINE_CODE)?"":"\nMEDICINE_CODE:"+this.MEDICINE_CODE)+
					(StringUtils.isBlank(this.MEDICINE_NAME)?"":"\nMEDICINE_NAME:"+this.MEDICINE_NAME)+
					(StringUtils.isBlank(this.MEDICINE_ENGLISH_NAME)?"":"\nMEDICINE_ENGLISH_NAME:"+this.MEDICINE_ENGLISH_NAME)+
					(StringUtils.isBlank(this.TYPE_CODE)?"":"\nTYPE_CODE:"+this.TYPE_CODE)+
					(StringUtils.isBlank(this.DOSE_UNIT)?"":"\nDOSE_UNIT:"+this.DOSE_UNIT)+
					(StringUtils.isBlank(this.MAINTAIN_DOSE_UNIT)?"":"\nMAINTAIN_DOSE_UNIT:"+this.MAINTAIN_DOSE_UNIT);
		}
	}

	@Override
	void addObj(Object o) {
		this.item.add((DictItem) o);	
	}

	
	@Override
	Object childObject(Element element) {
		DictItem o = new DictItem();
		setChildNodeToObj(element,DictItem.class,o);
		return o;
	}
}
