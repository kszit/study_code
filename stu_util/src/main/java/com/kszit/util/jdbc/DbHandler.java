package com.kszit.util.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 1、数据库 配置信息，
 * 2、数据库初始化
 * 	       表创建、表更新
 * 3、数据库常用操作
 * 
 * @author Administrator
 *
 */
public class DbHandler{

	private Connection con;

	

	
	public DbHandler(Connection acon){
		this.con = acon;
	}
	
	
	/**
	 * 关闭数据库连接
	 * @throws SQLException 
	 */
	public void closeCon() throws SQLException{
		con.close();
	}
	
	
	
	public boolean update(String sql) throws SQLException {
		Statement st = null;
		
			Connection c = con; 
			st = c.createStatement(); 
			st.executeUpdate(sql);
		
			if(st!=null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		
		return true;
	}

	public int getCount(String sql) throws Exception {
		Object count =  this.getOne(sql, new DbOperatorCallBack(){
			public Object toObj(ResultSet rs) throws SQLException {
					return rs.getInt(1);
			}
		});
		return Integer.parseInt(count.toString());
	}

	
	public Object getOne(String sql,DbOperatorCallBack callB) throws Exception {
		Object o = null;
		Connection c = con; 
		Statement st = c.createStatement(); 
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()){
			o = callB.toObj(rs);
		}
		st.close(); 
		return o;
	}
	/*
	@Override
	public List<Object> getList(String sql,DbOperate.dbOperatorCallBack callB) {
		if(MultipleDbManage.instance().isMysql()){
//			sql = sql.replaceAll("rownum", "(@rowNum:=@rowNum+1)");
//			
//			
//			
//		
//			if(sql.contains("m)n")){
//				sql = sql.replace("m)n", "m,(select(@rowNum:=0)) b)n");
//			}else if(sql.contains("PIP_INTERVIEWEE a where 1=1")){
//				sql = sql.replace("PIP_INTERVIEWEE a where 1=1", "PIP_INTERVIEWEE a,(select(@rowNum:=0)) b where 1=1");
//			}
			

			sql = sql.replaceAll("dd", "%d");
			sql = sql.replaceAll("yyyy", "%Y");
			sql = sql.replaceAll("MM", "%m");
			
			sql = sql.replaceAll("\"", "");
			sql = sql.replaceAll("'r'", "r");
			
			sql = sql.replaceAll("rownum", "rownum()");
			if(sql.contains("TOP 20")){
				sql = sql.replace("TOP 20", "");
				sql = sql + " limit 0,20";
			}else if(sql.contains(".r")){
				int startIndex = sql.indexOf("m.r");
				if(startIndex==-1){
					startIndex = sql.indexOf("n.r");
				}
				String pageInfo = sql.substring(startIndex);
				sql = sql.substring(0, startIndex);
				
				int pageStartIndex = 0;
				int pageEndIndex = 0;
				Pattern p = Pattern.compile("\\D+");
				String[] numberStrs =  p.split(pageInfo);
				for(String number:numberStrs){
					if(number==null || "".equals(number) || "null".equals(number)){
						continue;
					}
					int intIndex = Integer.parseInt(number);
					if(pageStartIndex>=intIndex || pageStartIndex==0){
						pageStartIndex = intIndex;
					}
					if(pageEndIndex<=intIndex || pageEndIndex==0){
						pageEndIndex = intIndex;
					}
				}
				pageStartIndex -= 1;
				sql += "1=1 limit "+(pageStartIndex)+","+(pageEndIndex-pageStartIndex);
			}
			
			sql = sql.replaceAll("formatdatetime", "DATE_FORMAT");
			sql = sql.replaceAll(",'en','GMT'", "");
		}
		
		
		List<Object> reList = new ArrayList<Object>();
		try { 
			Connection c = getCon(); 
			Statement st = c.createStatement(); 
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				reList.add(callB.toObj(rs));
			}
			st.close(); 
		}catch (SQLException e) { 
			DbErrorHandler handler = new DbErrorHandler(e);
        	throw handler.handlerSqlError();
		}  
		DbManagerFactory.instance().queryLog(sql);
		return reList;
	}
	
	
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	DatLogger.logSysCommun(DbService.class, "关闭数据库连接");
		this.closeCon();
	}
	*/
}
