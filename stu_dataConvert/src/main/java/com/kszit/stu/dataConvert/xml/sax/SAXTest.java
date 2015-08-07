package com.kszit.stu.dataConvert.xml.sax;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.kszit.util.findResource.Resources;

public class SAXTest {

	/**
	 * @param args
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public static void main(String[] args)  {
		SAXTest test = new SAXTest();
		try {
			test.saxparseXml();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	
	}
	
	/**
	 * sax方式解析xml文件，100M左右的xml文件，耗时3.5分钟
	 */
	public void saxparseXml()throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		
		File xmlfile = Resources.getResourceAsFile("bigXmlTest/PIP_COMM_DICT_DISTRICT_big.xml");
		
		saxParser.parse(xmlfile, new DefaultHandler(){

			@Override
			public void endDocument() throws SAXException {
				// TODO Auto-generated method stub
				super.endDocument();
			}

			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException {
				// TODO Auto-generated method stub
				super.endElement(uri, localName, qName);
			}

			@Override
			public void startDocument() throws SAXException {
				// TODO Auto-generated method stub
				super.startDocument();
			}

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				System.out.println(qName+":"+localName);
				super.startElement(uri, localName, qName, attributes);
			}
			/* 
			 * 节点内容
			 * 
			 * 此方法有三个参数
		       arg0是传回来的字符数组，其包含元素内容
		       arg1和arg2分别是数组的开始位置和结束位置
		        */
		    @Override
		    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		        String content = new String(arg0, arg1, arg2);
		        System.out.println(content);
		        super.characters(arg0, arg1, arg2);
		    }
			
		});

	}

}
