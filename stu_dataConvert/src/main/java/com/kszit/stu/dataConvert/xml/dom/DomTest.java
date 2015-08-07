package com.kszit.stu.dataConvert.xml.dom;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.kszit.util.findResource.Resources;

public class DomTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DomTest test = new DomTest();
		try {
			test.loadXml();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 加载18M的xml文件 实际内存占用 70M
	 * 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void loadXml() throws ParserConfigurationException, IOException, SAXException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		
		File f = Resources.getResourceAsFile("bigXmlTest/PIP_COMM_DICT_PUBLIC_small.xml");

		Document document = documentBuilder.parse(f);
		
		NodeList nodeList = document.getElementsByTagName("ITEM");
		
		for(int i=0;i<nodeList.getLength();i++){
			Node node = nodeList.item(i);
			Node childNode = node.getFirstChild();
			System.out.println(childNode.getNodeName()+":"+childNode.getTextContent());
			System.out.println(node.getNodeName());
		}
		
		
		System.out.println("");
		
		
	}
	
	
	
}
