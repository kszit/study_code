package com.kszit.util.jdbc.oracle;

import java.sql.Connection;
import java.sql.DriverManager;

import com.kszit.util.jdbc.IConnection;

public class OracleConnection implements IConnection {

	private String ip;
	private String port;
	private String dbName;
	private String uname;
	private String pwd;
	
	/**
	 * 
	 * @param aip
	 * @param aport
	 * @param dbName
	 * @param auname
	 * @param apwd
	 */
	public OracleConnection(String aip,String aport,String dbName,String auname,String apwd){
		this.ip = aip;
		this.port = aport;
		this.dbName = dbName;
		this.uname = auname;
		this.pwd = apwd;
	}
	
	
	public Connection connection() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
        System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:" + "thin:@"+this.ip+":"+this.port+":"+this.dbName;// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
        String user = this.uname;// 用户名,系统默认的账户名
        String password = this.pwd;// 你安装时选设置的密码
        System.out.println("连接数据库参数：url="+url+",user="+user+",pwd="+password);
        
        Connection con = DriverManager.getConnection(url, user, password);// 获取连接
		
        return con;
	}

}
