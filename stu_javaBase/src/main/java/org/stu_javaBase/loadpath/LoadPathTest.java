package org.stu_javaBase.loadpath;

import java.io.File;
import java.net.URISyntaxException;

public class LoadPathTest {

	public static void main(String[] a) throws URISyntaxException{
		LoadPathTest test = new LoadPathTest();
		test.loadPath();
	}
	
	
	public void loadPath() throws URISyntaxException{
		File f1 = new File(this.getClass().getClassLoader().getResource("").toURI());
		System.out.println(f1.getPath());
		
//		File f2 = new File(this.getClass().getClassLoader().getResource("/").toURI());
//		System.out.println(f2.getPath());
		
		
		System.out.println(System.getProperty("user.dir"));
		
		System.out.println(System.getProperty("java.class.path"));
		
	}
}
