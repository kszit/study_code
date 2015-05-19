package com.kszit.util.resource;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.kszit.util.findResource.Resources;

public class FindResourceTest {

	@Test
	public void findResouce() throws IOException{
		File f = Resources.getResourceAsFile("test.txt");
		System.out.println(f.getPath());
	}
}
