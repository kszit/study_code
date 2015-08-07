package com.kszit.stu.dataConvert.xml.dom4j.bigxml;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public abstract class DictProperty {
	public int dictType;
	public String dictTable;
	public String dictName;
	public float version;
	public String dictVersion;
	public int total;
	
	abstract void addObj(Object o);
	abstract Object childObject(Element element);
	
	public void xmlToObj(File xmlPFile){
		SAXReader saxReader = new SAXReader();
        Document document = null;
		try {
			document = saxReader.read(new FileInputStream(xmlPFile));
		} catch (Exception e1) {
			
		}

		// 获取根元素
        Element root = document.getRootElement();
        setProperToObj(root,DictProperty.class,this);
        
        for (Iterator iter = root.elementIterator(); iter.hasNext();)
        {
            Element e = (Element) iter.next();
			addObj(childObject(e));
        }
	}
	

	
	
	private void setProperToObj(Element root,Class c,Object o){
				
		Field[] fields = c.getDeclaredFields();
        for(Field field:fields){
        	String fieldValue = root.attributeValue(field.getName());
        	try {
	        	if(field.getType()==int.class){
	        		if(fieldValue!=null && !"".equals(fieldValue)){
	        			field.setInt(o, Integer.parseInt(fieldValue));
	        		}
	        	}else if(field.getType()==float.class){
	        		if(fieldValue!=null && !"".equals(fieldValue)){
	        			field.setFloat(o, Float.parseFloat(fieldValue));
	        		}
	        	}else if(field.getType()==String.class){
	        		field.set(o, fieldValue);
	        	}
        	} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	
	public static void setChildNodeToObj(Element root,Class c,Object o){
		Map<String,String> nodeMap = new HashMap<String,String>();
		for(Iterator iter = root.elementIterator();iter.hasNext();){
			Element e = (Element) iter.next();
			nodeMap.put(e.getName(), e.getStringValue());
		}

		Field[] fields = c.getDeclaredFields();
        for(Field field:fields){
        	String fieldValue = nodeMap.get(field.getName());
        	try {
	        	if(field.getType()==int.class){
					field.setInt(o, Integer.parseInt(fieldValue));
	        	}else if(field.getType()==float.class){
	        		field.setFloat(o, Float.parseFloat(fieldValue));
	        	}else if(field.getType()==String.class){
	        		field.set(o, fieldValue);
	        	}
        	} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	public int getDictType() {
		return dictType;
	}
	public void setDictType(int dictType) {
		this.dictType = dictType;
	}
	public String getDictTable() {
		return dictTable;
	}
	public void setDictTable(String dictTable) {
		this.dictTable = dictTable;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public float getVersion() {
		return version;
	}
	public void setVersion(float version) {
		this.version = version;
	}
	public String getDictVersion() {
		return dictVersion;
	}
	public void setDictVersion(String dictVersion) {
		this.dictVersion = dictVersion;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
	
}
