package com.kszit.stu.db1h2;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.bdcor.pip.client.DirectoryInfo;
import com.bdcor.pip.client.core.configInfo.constants.Constants;
import com.bdcor.pip.client.db.DbErrorHandler;
import com.bdcor.pip.client.db.h2.H2;
import com.bdcor.pip.client.db.tools.DbManagerFactory;

public class H2Connect implements IConnect {
	private String dbName = "";
	private String dbPath = "";
	
	//连接是否在使用
	private boolean isUseCon = false;
	private Connection con = null;
	
	
	public H2Connect(){
		H2.init();
	}
	
	@Override
	public void initParam() {
		this.dbName = H2.getDbName();
		this.dbPath = H2.getDbPath();
	}

	@Override
	public void initConnect() {
		Connection con = null;
		try{
			con = getCon();
		}catch(Exception e){
			throw new RuntimeException("数据库不能正常启动("+e.getMessage()+").");
		}
		
		if(con==null){
			throw new RuntimeException("数据库不能正常启动.");
		}
	}

	@Override
	public void closeCon() {
		try {
			if(this.con!=null){
				this.con.close();
				//while(this.con.isClosed())
			}
		} catch (SQLException e) {
			DbErrorHandler handler = new DbErrorHandler(e);
        	throw handler.handlerDbError();
		}
		this.con = null;
	}

	@Override
	public Connection getCon() {

		if(DbManagerFactory.instance().dbIsError()){
			//备份错误的数据库
			DbManagerFactory.instance().backupErrorDB();
			//数据库还原
			DbManagerFactory.instance().dbRestores();
			//设置数据库为正常
			DbManagerFactory.instance().setDBNormal();
		}
		
		if(con==null){
//			con = (Connection)CurrentLoginUser.getServletContext().getAttribute("connection");
			Connection conn = null;
			String url = null;
		      try {
//		            String url = "jdbc:h2:"+DbService.instance().getDbPath()+DbService.instance().getDbName();
		    	  	//url = "jdbc:h2:C:"+File.separator+"PIP"+File.separator+"001"+
		    	  	url = "jdbc:h2:"+DirectoryInfo.instance().DAT_BASE_Dir()+
		    			  	File.separator+"BIN"+File.separator+"DB"+File.separator+this.dbName
		    			  	+";DB_CLOSE_ON_EXIT=FALSE"+
		    			  	";DB_CLOSE_DELAY=0"+
		    			  	";TRACE_LEVEL_SYSTEM_OUT=2"+
		    			  	";TRACE_LEVEL_FILE=2";
//		    			  	";AUTO_SERVER=TRUE"+
//		    			  	";AUTO_SERVER_PORT=58092"; 
		    	  	String user = "sa";
		            String password = "sa";

		            Class.forName("org.h2.Driver");
		            conn = DriverManager.getConnection(url, "sa", password);
//		            if(conn!=null){
//		            	DbManagerFactory.instance().savePriRebackDbName("0");
//		            }
		            this.con = conn;
		        } catch (Exception e) {
		        	DbErrorHandler handler = new DbErrorHandler(e);
		        	throw handler.handlerDbError();
		        }
		}
		return con;
	}
	
	public boolean isUseCon(){
		return this.isUseCon;
	}
}
