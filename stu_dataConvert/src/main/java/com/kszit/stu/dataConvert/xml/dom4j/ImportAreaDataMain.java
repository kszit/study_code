package com.kszit.stu.dataConvert.xml.dom4j;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 导入乡和村的数据到数据库
 * @author Administrator
 *
 */
public class ImportAreaDataMain {
	
	static Logger log = LoggerFactory.getLogger(ImportAreaDataMain.class);
	

	
	static String[] xmlFiles = new String[]{
		"上海市.xml",
		"云南省.xml",
		"内蒙古自治区.xml",
		"北京市.xml",
		"吉林省.xml",
		"四川省.xml",
		"天津市.xml",
		"宁夏回族自治区.xml",
		"安徽省.xml",
		"山东省.xml",
		"山西省.xml",
		"广东省.xml",
		"广西壮族自治区.xml",
		"新疆维吾尔自治区.xml",
		"江苏省.xml",
		"江西省.xml",
		"河北省.xml",
		"河南省.xml",
		"浙江省.xml",
		"海南省.xml",
		"湖北省.xml",
		"湖南省.xml",
		"甘肃省.xml",
		"福建省.xml",
		"西藏自治区.xml",
		"贵州省.xml",
		"辽宁省.xml",
		"重庆市.xml",
		"陕西省.xml",
		"青海省.xml",
		"黑龙江省.xml"};
	
	/**
	 *	使用dom4j解析xml文件（xpath方式）
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		SAXReader reader = new SAXReader();
		InputStream inputStream = ImportAreaDataMain.class.getClassLoader().getResourceAsStream("areaData/城乡划分代码/重庆市.xml");
		Document document = reader.read(inputStream);

		List xianList = document.selectNodes("//areas/area/areas/area/areas/area");
		for(Object o:xianList){
			Node node = (Node)o;
			String leveNode = node.selectSingleNode("level").getText();
			String nameNode = node.selectSingleNode("name").getText();
			String codeNode = node.selectSingleNode("code").getText();
			
			log.info(leveNode+"==>"+codeNode+":"+nameNode);
		}
	}

}
