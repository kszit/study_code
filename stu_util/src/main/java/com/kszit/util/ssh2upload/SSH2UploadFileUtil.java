package com.kszit.util.ssh2upload;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSH2UploadFileUtil {

	private final static Logger logger = LoggerFactory
			.getLogger(SSH2UploadFileUtil.class);

	public SSH2UploadFileUtil(String apath,String aaddr,String aport,String ausername,String apwd){
		
		this.scpPath = apath;
		this.scpAddress = aaddr;
		this.scpPort = aport;
		this.username = ausername;
		this.password = apwd;
		
	}
	
	public SSH2UploadFileUtil(){}
	
	// scp文件目录
				String scpPath = "/usr/local/tomcat/webapps/datserver/upgradePackage/006";// "/data01/javadev/";//
				// scp ip地址
				String scpAddress = "172.31.201.169";// "192.168.100.220"; //
				// scp 端口号
				String scpPort = "22";// "222";//WebSrvUtil.srv("scpPort").trim();
				// scp 用户名
				String username = "root";// "javadev";
				// scp 密码
				String password = "genertech";// "javadev123!@#";//WebSrvUtil.srv("password").trim();
	
	
	/**
	 * 方法功能说明：通过scp上传任何文件
	 * 
	 * @param fileName
	 * @throws Exception
	 * @return 返回"200"则上传成功，"500" 则上传失败出现异常 ，"506"则登陆远程服务器失败
	 */
	public String upLoadFile(byte[] filedata,String fileName) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("==============开始上传====" + fileName + "==============");
		}
		try {
			

			if (logger.isDebugEnabled()) {
				logger.debug("加入SCP实现远程传输文件");
			}
			// 加入SCP实现远程传输文件
			Connection con = new Connection(scpAddress,
					Integer.parseInt(scpPort));
			if (logger.isDebugEnabled()) {
				logger.debug("开始连接");
			}
			// 连接
			con.connect();
			if (logger.isDebugEnabled()) {
				logger.debug("登陆远程服务器" + username + "," + password);
			}
			// 登陆远程服务器的用户名密码
			boolean isAuthed = con.authenticateWithPassword(username, password);
			// 登陆失败
			if (!isAuthed) {
				if (logger.isDebugEnabled()) {
					logger.debug("登陆远程服务器失败");
				}
				return "506";
			}
			if (logger.isDebugEnabled()) {
				logger.debug("建立scp客户端");
			}

			// 建立SCP客户端
			SCPClient scpClient = con.createSCPClient();
			if (logger.isDebugEnabled()) {
				logger.debug("开始上传文件到服务器");
			}

			// 0755是指权限编号
			scpClient.put(filedata,fileName, scpPath, "0755");
			if (logger.isDebugEnabled()) {
				logger.debug("关闭连接");
			}
			
			
			
			con.close();
			if (logger.isDebugEnabled()) {
				logger.debug("上传完成");
			}
			return "200";
		} catch (Exception e) {
			logger.error("上传失败", e.getMessage(), e);
			return "500";
		}
	}
	
	
	/**
	 * 查看目录结构
	 * 
	 * @param fileName
	 * @throws Exception
	 * @return 返回"200"则上传成功，"500" 则上传失败出现异常 ，"506"则登陆远程服务器失败
	 */
	public List<String> dirLs(String dir) throws Exception {
		
		List<String> returnList = new ArrayList<String>();
		
		byte[] returnByte = null;
		
		
			

			if (logger.isDebugEnabled()) {
				logger.debug("加入SCP实现远程传输文件");
			}
			// 加入SCP实现远程传输文件
			Connection con = new Connection(scpAddress,
					Integer.parseInt(scpPort));
			if (logger.isDebugEnabled()) {
				logger.debug("开始连接");
			}
			// 连接
			con.connect();
			if (logger.isDebugEnabled()) {
				logger.debug("登陆远程服务器" + username + "," + password);
			}
			// 登陆远程服务器的用户名密码
			boolean isAuthed = con.authenticateWithPassword(username, password);
			// 登陆失败
			if (!isAuthed) {
				if (logger.isDebugEnabled()) {
					logger.debug("登陆远程服务器失败");
				}
				//return "506";
			}
			if (logger.isDebugEnabled()) {
				logger.debug("建立scp客户端");
			}

			
			
			Session session = con.openSession();
			session.execCommand("cd "+dir+"\n ls");
			
			InputStream stuout = new StreamGobbler(session.getStdout());
			
			BufferedReader br = new BufferedReader(new InputStreamReader(stuout));
			while(true){
				String line = br.readLine();
				
				if(line==null){
					break;
				}
				returnList.add(line);
			}
			session.close();
			stuout.close();
			br.close();
			
			
			
			
			
			con.close();
			
			
			if (logger.isDebugEnabled()) {
				logger.debug("上传完成");
			}
			//return "200";
		
		
		return returnList;
	}
	
	
	/**
	 * 方法功能说明：通过scp上传任何文件
	 * 
	 * @param fileName
	 * @throws Exception
	 * @return 返回"200"则上传成功，"500" 则上传失败出现异常 ，"506"则登陆远程服务器失败
	 */
	public byte[] loadFile(String fileName) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("==============开始上传====" + fileName + "==============");
		}
		
		byte[] returnByte = null;
		
		
			

			if (logger.isDebugEnabled()) {
				logger.debug("加入SCP实现远程传输文件");
			}
			// 加入SCP实现远程传输文件
			Connection con = new Connection(scpAddress,
					Integer.parseInt(scpPort));
			if (logger.isDebugEnabled()) {
				logger.debug("开始连接");
			}
			// 连接
			con.connect();
			if (logger.isDebugEnabled()) {
				logger.debug("登陆远程服务器" + username + "," + password);
			}
			// 登陆远程服务器的用户名密码
			boolean isAuthed = con.authenticateWithPassword(username, password);
			// 登陆失败
			if (!isAuthed) {
				if (logger.isDebugEnabled()) {
					logger.debug("登陆远程服务器失败");
				}
				//return "506";
			}
			if (logger.isDebugEnabled()) {
				logger.debug("建立scp客户端");
			}

			// 建立SCP客户端
			SCPClient scpClient = con.createSCPClient();
			if (logger.isDebugEnabled()) {
				logger.debug("开始上传文件到服务器");
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			// 0755是指权限编号
			scpClient.get(fileName, out);
			if (logger.isDebugEnabled()) {
				logger.debug("关闭连接");
			}
			
			returnByte  = out.toByteArray();
			
			out.close();
			con.close();
			if (logger.isDebugEnabled()) {
				logger.debug("上传完成");
			}
			//return "200";
		
		
		return returnByte;
	}
	
	
	public static void main(String[] a) throws Exception{
		
		SSH2UploadFileUtil uploadFileU = new SSH2UploadFileUtil();
		/*
		byte[] data = uploadFileU.loadFile("/usr/local/tomcat/webapps/datserver/upgradePackage/006/201511190002.zip");
		File f = new File("H://eee.zip");
		f.createNewFile();
		FileOutputStream fout = new FileOutputStream(f);
		
		fout.write(data);
		fout.close();
		*/
		
		uploadFileU.dirLs("/usr/local/");
	}
}