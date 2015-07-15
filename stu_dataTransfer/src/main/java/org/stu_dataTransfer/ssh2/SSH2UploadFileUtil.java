package org.stu_dataTransfer.ssh2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

public class SSH2UploadFileUtil {

	private final static Logger logger = LoggerFactory
			.getLogger(SSH2UploadFileUtil.class);

	public SSH2UploadFileUtil(String apath,String aaddr,String aport,String ausername,String apwd){
		this.scpAddress = apath;
		this.scpAddress = aaddr;
		this.scpPort = aport;
		this.username = ausername;
		this.password = apwd;
	}
	
	// scp文件目录
				String scpPath = "/usr/local/tomcat/webapps/datserver/upgradePackage/001";// "/data01/javadev/";//
				// scp ip地址
				String scpAddress = "172.31.201.169";// "192.168.100.220"; //
				// scp 端口�?
				String scpPort = "22";// "222";//WebSrvUtil.srv("scpPort").trim();
				// scp 用户�?
				String username = "root";// "javadev";
				// scp 密码
				String password = "genertech";// "javadev123!@#";//WebSrvUtil.srv("password").trim();
	
	
	/**
	 * 方法功能说明：�?过scp上传任何文件
	 * 
	 * @param fileName
	 * @throws Exception
	 * @return 返回"200"则上传成功，"500" 则上传失败出现异�?�?506"则登陆远程服务器失败
	 */
	public String upLoadFile(byte[] filedata,String fileName) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("==============�?��上传====" + fileName + "==============");
		}
		try {
			

			if (logger.isDebugEnabled()) {
				logger.debug("加入SCP实现远程传输文件");
			}
			// 加入SCP实现远程传输文件
			Connection con = new Connection(scpAddress,
					Integer.parseInt(scpPort));
			if (logger.isDebugEnabled()) {
				logger.debug("�?��连接");
			}
			// 连接
			con.connect();
			if (logger.isDebugEnabled()) {
				logger.debug("登陆远程服务" + username + "," + password);
			}
			// 登陆远程服务器的用户名密�?
			boolean isAuthed = con.authenticateWithPassword(username, password);
			// 登陆失败
			if (!isAuthed) {
				if (logger.isDebugEnabled()) {
					logger.debug("登陆远程服务器失败");
				}
				return "506";
			}
			if (logger.isDebugEnabled()) {
				logger.debug("建立scp客户");
			}

			// 建立SCP客户�?
			SCPClient scpClient = con.createSCPClient();
			if (logger.isDebugEnabled()) {
				logger.debug("�?��上传文件到服务器");
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
	

}