package com.kszit.stu.db1h2;

import java.io.File;

import com.bdcor.pip.client.DirectoryInfo;
import com.bdcor.pip.client.core.configInfo.constants.Constants;

public class H2 {
	private static String dbName = "databaseName";
	private static String dbPath = "";
	
	public static void init(){
		String dbNameUserSet =  Constants.getPropertyValue("dat_db_name");
		if(dbNameUserSet!=null && !"".equals(dbNameUserSet)){
			H2.dbName = dbNameUserSet;
		}
		
		dbPath = DirectoryInfo.instance().DAT_BASE_Dir()+File.separator+""+File.separator+"BIN"+File.separator+"DB"+File.separator+"";
	}
	
	

	public static String getDbPath() {
		return dbPath;
	}
	
	public static String getDbName(){
		return dbName;
	}
	
}
