package com.kszit.stu.db1h2;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.h2.tools.Backup;
import org.h2.tools.Restore;
import org.h2.tools.RunScript;
import org.h2.tools.Script;
import org.h2.util.IOUtils;
import org.h2.util.JdbcUtils;

import com.bdcor.pip.client.db.DbService;
import com.bdcor.pip.client.util.DatLogger;
import com.bdcor.pip.client.util.FileUtils;
import com.bdcor.pip.client.util.StringUtils;

/**
 * 数据管理操作
 * @author Administrator
 *
 */
public class H2DbOperTools {
	
	
	/**
	 * 
	 * java -cp h2*.jar org.h2.tools.Script -url "jdbc:h2:~/test" -user "sa" -password "*" -script "~/backup.sql"
	 */
	public static void backupDBOfSql(){
		String backSql = "";
		
		 Statement stat = null;
	        try {
	        	OutputStream out = org.h2.store.fs.FileUtils.newOutputStream(backSql, false);
	        	Connection conn = DbService.instance().getCon();
	            stat = conn.createStatement();
	            PrintWriter writer = new PrintWriter(IOUtils.getBufferedWriter(out));
	            ResultSet rs = stat.executeQuery("SCRIPT");
	            while (rs.next()) {
	                String s = rs.getString(1);
	                writer.println(s);
	            }
	            writer.flush();
	        }catch(Exception e){
	        	DatLogger.loggError(e);
	        }finally {
	            JdbcUtils.closeSilently(stat);
	        }
	}
	
	/**
	 * 数据库备份<br>
	 * 备份前需关闭数据库；当前系统中，调用 DbService.instance().closeCon(); 关闭数据库。<br><br>
	 * java -cp h2*.jar org.h2.tools.Backup -file "~/backup.zip" -dir "~" -db "datDB"
	 */
	public static void backupDB(String dbPath,String dbName,String backupPName) throws Exception{
		String[] args = {"-file",backupPName,"-dir",dbPath,"-db",dbName};
		try {
			new Backup().runTool(args);
		} catch (SQLException e) {
			throw new RuntimeException("数据库备份失败",e);
		}
	}
	
	/**
	 * 从备份文件中恢复数据库<br>
	 * java -cp h2*.jar org.h2.tools.Restore -file "~/backup.zip" -dir "~" -db "datDb"
	 * 
	 * @param dbPath
	 * @param dbName
	 * @param backupPName
	 * 
	 */
	public static void restoresDB(String dbPath,String dbName,String backupPName){
		String[] args = {"-file",backupPName,"-dir",dbPath,"-db",dbName};
		try {
			new Restore().runTool(args);
		} catch (SQLException e) {
			throw new RuntimeException("数据库还原",e);
		}
	}
	
	/**
	 * 数据库修复
	 * java -cp h2*.jar org.h2.tools.Recover -dir "~" -db "datDB"
	 */
	public static void recover(){
//		String[] args = {"-dir",dbPath(),"-db",dbName()};
//		try {
//			new Recover().runTool(args);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 删除数据库文件
	 * java -cp h2*.jar org.h2.tools.DeleteDbFiles -dir "~" -db "delete"
	 */
	public static void delete(){
		
	}
	
	/**
	 * 导出数据库脚本到sql脚本
	 * java -cp h2*.jar org.h2.tools.Script -url "jdbc:h2:~/test" -user "sa" -script "~/backup.sql"
	 */
	public static void backupToScript(String dbPath,String dbName,String scriptPName){
		String[] args = {"-url","jdbc:h2:"+dbPath+dbName,"-user","sa","-password","sa","-script",scriptPName};
		try {
			new Script().runTool(args);
		} catch (SQLException e) {
			throw new RuntimeException("执行数据库脚本错误"+scriptPName,e);
		}
	}
	
	
	/**
	 * 导出数据库脚本到sql脚本
	 * java -cp h2*.jar org.h2.tools.Script -url "jdbc:h2:~/test" -user "sa" -script "~/backup.sql"
	 */
	public static void backupToScriptOfMysql(String dbPath,String dbName,String scriptPName){
		String tempSqlFile = "c:\\h2sql_temp.sql";
		String[] args = {"-url","jdbc:h2:"+dbPath+dbName,"-user","sa","-password","sa","-script",tempSqlFile};
		try {
			new Script().runTool(args);
		} catch (SQLException e) {
			throw new RuntimeException("执行数据库脚本错误"+scriptPName,e);
		}
		
		
		
		try {
			new File(scriptPName).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		List<String> sqlLines = FileUtils.readLines(new File(tempSqlFile));
		boolean isInsertLineBegin = false;
		for(String sql:sqlLines){
			
			if(sql.contains("INSERT") || isInsertLineBegin){
				isInsertLineBegin = true;
			}else{
				continue;
			}
			
			if(isInsertLineBegin){
				while(sql.contains("STRINGDECODE")){
					int start = sql.indexOf("STRINGDECODE('");
					String tempSql = sql.substring(start);
					String unicodeStr = tempSql.substring("STRINGDECODE('".length(),tempSql.indexOf("')"));
					
					sql = sql.replace("STRINGDECODE('"+unicodeStr+"')", "'"+StringUtils.convertUnicode(unicodeStr)+"'");
				}
				while(sql.contains("PUBLIC.")){
					sql = sql.replace("PUBLIC.", "");
				}
				
				if(sql.endsWith(");")){
					sql += "\n";
					isInsertLineBegin = false;
				}
				FileUtils.write(new File(scriptPName), sql, true);
			}
		}
		new File(tempSqlFile).delete();
	}
	
	/**
	 * 执行数据库脚本文件
	 * java -cp h2*.jar org.h2.tools.RunScript -url "jdbc:h2:~/test" -user "sa" -password "*" -script "~/backup.sql"
	 */
	public static void runScript(String dbPath,String dbName,String scriptPName){
		String[] args = {"-url","jdbc:h2:"+dbPath+dbName,"-user","sa","-password","sa","-script",scriptPName};
		try {
			new RunScript().runTool(args);
		} catch (SQLException e) {
			DatLogger.loggError(e);
			throw new RuntimeException("执行数据库脚本错误"+scriptPName,e);
		}
	}
	
	/**
	 * 数据库修复
	 * java -cp h2-1.4.181.jar org.h2.tools.ConvertTraceFile -traceFile "D:/DB2/test.trace.db" -script "D:/DB2/test.sql" -javaClass "D:/DB2/Test"
	 */
	public static void convertTraceFile(){
		
	}
	
}
