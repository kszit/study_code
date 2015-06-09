package com.kszit.stu_mavnePlugin.versionRelease;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtils extends org.apache.commons.io.FileUtils {

	
	public static void allFileInDirect(List<File> files,File file){
		if(!file.isDirectory()){
			files.add(file);
			return;
		}
		for(File f:file.listFiles()){
			if(f.isDirectory()){
				allFileInDirect(files,f);
			}else{
				files.add(f);
			}
		}
		
		
	}
	/**
	 * 
	 * @param zipFileDir
	 * @param outFile
	 */
	public static void zipFiles(File zipFileDir,File outFile){
		
		
		
		
		List files = new ArrayList();
		allFileInDirect(files,zipFileDir);
		
		
		
		
		
		
		
		
		
		 int      bufSize = 512;
	    byte[]          buf = new byte[bufSize]; 
	    int             readedBytes; 
		
	    
		
		ZipOutputStream zipOut;
		try{ 
            zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFile))); 
            FileInputStream fileIn; 
            for(Object ff:files){
            	File f = (File)ff;
            	fileIn = new FileInputStream(f); 
            	
            	String parentD = f.getParent().replace(zipFileDir.getPath(),"");
            	
                zipOut.putNextEntry(new ZipEntry(parentD+File.separator+f.getName())); 

                while((readedBytes = fileIn.read(buf))>0){ 
                    zipOut.write(buf , 0 , readedBytes); 
                } 
                fileIn.close();
                zipOut.closeEntry(); 
            }
            zipOut.close(); 
        }catch(IOException ioe){ 
            ioe.printStackTrace(); 
        } 
		
	}
	
}
