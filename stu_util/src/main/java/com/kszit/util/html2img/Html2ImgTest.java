package com.kszit.util.html2img;

import java.awt.Dimension;

import gui.ava.html.image.generator.HtmlImageGenerator;

public class Html2ImgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Dimension dimension = new Dimension(2400,2040);
		HtmlImageGenerator test = new HtmlImageGenerator();
		test.setSize(dimension);
		test.loadUrl("http://www.baidu.com");
		
		test.saveAsImage("d://test.png");
	}

}
