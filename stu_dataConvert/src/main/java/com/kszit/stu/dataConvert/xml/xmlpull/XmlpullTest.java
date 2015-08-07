package com.kszit.stu.dataConvert.xml.xmlpull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.kszit.util.findResource.Resources;

public class XmlpullTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws XmlPullParserException 
	 */
	public static void main(String[] args) throws XmlPullParserException, IOException {
		XmlpullTest test = new XmlpullTest();
		
		test.parseXml();
	}
	
	public void parseXml() throws XmlPullParserException, IOException{
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		
		InputStream infile = Resources.getResourceAsStream("bigXmlTest/PIP_COMM_DICT_PUBLIC_small.xml");
 		
		parser.setInput(infile, "utf-8");
		
		int type = parser.getEventType();
		while(type!=XmlPullParser.END_DOCUMENT){
			switch(type){
			case XmlPullParser.START_TAG:
				System.out.println(parser.getName());
				break;
			case XmlPullParser.TEXT:
				System.out.println(parser.getText());
				break;
				
			}
			parser.next();
			type = parser.getEventType();
		}
		
	}

}
