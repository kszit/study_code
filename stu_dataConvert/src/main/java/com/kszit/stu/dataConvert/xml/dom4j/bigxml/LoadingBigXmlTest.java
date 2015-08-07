package com.kszit.stu.dataConvert.xml.dom4j.bigxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;

import com.kszit.stu.dataConvert.xml.dom4j.bigxml.Dict.DictItem;
import com.kszit.util.findResource.Resources;

/**
 * 100M左右的xml文件解析
 * 
 * @author Administrator
 *
 */
public class LoadingBigXmlTest {

	private File bigXmlFile = null;
	private File smallXmlFile = null;
	
	public LoadingBigXmlTest() throws IOException{
		smallXmlFile = Resources.getResourceAsFile("bigXmlTest/PIP_COMM_DICT_PUBLIC_small.xml");
		bigXmlFile = Resources.getResourceAsFile("bigXmlTest/PIP_COMM_DICT_DISTRICT_big.xml");
	}
	
	/**
	 * xpath方式解析xml文件
	 * @throws DocumentException 
	 * 
	 */
	public void loadBigXmlXpath(){
		SAXReader reader = new SAXReader();
		try {
//			System.out.println("");
//			Document document = reader.read(new FileInputStream(smallXmlFile));
//			System.out.println("");
//			Element rootEle = document.getRootElement();
//			
//			Iterator iter = rootEle.elementIterator();
//			while(iter.hasNext()){
//				Element ele = (Element)iter.next();
//				System.out.println(ele.getName());
//			}
//			System.out.println("");
			reader.addHandler("ITEM", new ElementHandler(){

				public void onStart(ElementPath elementPath) {
					System.out.println("");
				}

				public void onEnd(ElementPath elementPath) {
					
				}
				
			});
			
			System.out.println("");
			Document document = reader.read(new FileInputStream(smallXmlFile));
//			System.out.println("");
//			Element rootEle = document.getRootElement();
//			
//			Iterator iter = rootEle.elementIterator();
//			while(iter.hasNext()){
//				Element ele = (Element)iter.next();
//				System.out.println(ele.getName());
//			}
			System.out.println("");
		} catch(FileNotFoundException e2){
			
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	/**
	 * 97Mxml文件：
	 * 		堆内存溢出（Java heap space）
	 * 
	 * 调整堆大小：-Xms512m -Xmx512m，成功执行。堆峰值501M，平衡值325M
	 * 		
	 * 
	 */
	public void loadBigXml(){
		Dict bigDict = new Dict(this.bigXmlFile);
		
		for(DictItem item:bigDict.getItem()){
			System.out.println(item);
		}
		
		System.out.print("");
	}
	
	/**
	 * 18M的xml文件：
	 * 		未加载前堆大小为5M左右；
	 * 		加载后堆大小为90M左右；
	 */
	public void loadsmallXml(){
		Dict smallDict = new Dict(this.smallXmlFile);
		
		for(DictItem item:smallDict.getItem()){
			System.out.println(item);
		}
		
		System.out.print("");
	}
	
	
	public static void main(String[] a) throws IOException{
		LoadingBigXmlTest loadBigXml = new LoadingBigXmlTest();
		

//		loadBigXml.loadsmallXml();
//		loadBigXml.loadBigXml();
		
		loadBigXml.loadBigXmlXpath();
	}
	
}
