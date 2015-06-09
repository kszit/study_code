package com.kszit.util.ss2upload;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

import com.kszit.util.ssh2upload.SSH2UploadFileUtil;

public class SSH2FileUploadTest {

	@Test
	public void uploadTextfile(){
		

		SSH2UploadFileUtil uploadfileUtil = new SSH2UploadFileUtil("/usr/local/tomcat/webapps/datserver/upgradePackage/001",
				"172.31.201.169",
				"22",
				"root",
				"genertech");
		
		try {
			uploadfileUtil.upLoadFile("3333".getBytes(),"test.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void uploadZipfile(){
		

		SSH2UploadFileUtil uploadfileUtil = new SSH2UploadFileUtil(
				"/usr/local/tomcat/webapps/datserver/upgradePackage/001",
				"172.31.201.169",
				"22",
				"root",
				"genertech");
		
		try {
			File f = new File("D:\\PIP-release-version-direcotry\\001\\2015-06-09-62715\\5_201506090002.zip");
			byte[] filedata = new byte[(int) f.length()];
			
			InputStream fin = new FileInputStream(f);
			fin.read(filedata, 0, filedata.length);
			
			
			uploadfileUtil.upLoadFile(filedata,"5_201506090002.zip");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
