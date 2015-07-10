package com.kszit.util.jdbc;

import java.sql.ResultSet;

import org.junit.Test;

import com.kszit.util.jdbc.oracle.OracleConnection;

public class UpdateTest {

	/**
	 * 数据更新
	 * @throws Exception
	 */
	@Test
	public void update() throws Exception{
//		IConnection con = new OracleConnection(
//				"172.31.201.151",
//				"1521",
//				"BDCOR2",
//				"bdcor",
//				"develop2015");
//		
//		DbHandler dbhandler = new DbHandler(con.connection());
//		
//		dbhandler.update("update PIP_COMM_DICT_VERSION set dict_version='3' where dict_type='5'");
//		
//		dbhandler.closeCon();
		
	}
	
	/**
	 * 获取多行数据
	 * @throws Exception
	 */
	@Test
	public void getCount() throws Exception{
//		IConnection con = new OracleConnection(
//				"172.31.201.151",
//				"1521",
//				"BDCOR2",
//				"bdcor",
//				"develop2015");
//		
//		DbHandler dbhandler = new DbHandler(con.connection());
//		
//		dbhandler.getCount("select count(*) from t");
//		
//		dbhandler.closeCon();
		
	}
	/**
	 * 获取一行数据
	 * @throws Exception
	 */
	@Test
	public void queryObj() throws Exception{
//		IConnection con = new OracleConnection(
//				"172.31.201.151",
//				"1521",
//				"BDCOR2",
//				"bdcor",
//				"develop2015");
//		
//		DbHandler dbhandler = new DbHandler(con.connection());
//		
//		dbhandler.getOne("select * from t", new DbOperatorCallBack(){
//
//			public Object toObj(ResultSet rs) throws Exception {
//				Object o = new Object();
//				o.setXx(rs.getString(0));
//				return o;
//			}
//			
//		});
//		
//		dbhandler.closeCon();
		
	}
}
