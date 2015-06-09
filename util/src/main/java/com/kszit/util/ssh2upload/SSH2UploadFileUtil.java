package com.kszit.util.ssh2upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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
	
	// scp�ļ�Ŀ¼
				String scpPath = "/usr/local/tomcat/webapps/datserver/upgradePackage/001";// "/data01/javadev/";//
				// scp ip��ַ
				String scpAddress = "172.31.201.169";// "192.168.100.220"; //
				// scp �˿ں�
				String scpPort = "22";// "222";//WebSrvUtil.srv("scpPort").trim();
				// scp �û���
				String username = "root";// "javadev";
				// scp ����
				String password = "genertech";// "javadev123!@#";//WebSrvUtil.srv("password").trim();
	
	
	/**
	 * ��������˵����ͨ��scp�ϴ��κ��ļ�
	 * 
	 * @param fileName
	 * @throws Exception
	 * @return ����"200"���ϴ��ɹ���"500" ���ϴ�ʧ�ܳ����쳣 ��"506"���½Զ�̷�����ʧ��
	 */
	public String upLoadFile(byte[] filedata,String fileName) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("==============��ʼ�ϴ�====" + fileName + "==============");
		}
		try {
			

			if (logger.isDebugEnabled()) {
				logger.debug("����SCPʵ��Զ�̴����ļ�");
			}
			// ����SCPʵ��Զ�̴����ļ�
			Connection con = new Connection(scpAddress,
					Integer.parseInt(scpPort));
			if (logger.isDebugEnabled()) {
				logger.debug("��ʼ����");
			}
			// ����
			con.connect();
			if (logger.isDebugEnabled()) {
				logger.debug("��½Զ�̷�����" + username + "," + password);
			}
			// ��½Զ�̷��������û�������
			boolean isAuthed = con.authenticateWithPassword(username, password);
			// ��½ʧ��
			if (!isAuthed) {
				if (logger.isDebugEnabled()) {
					logger.debug("��½Զ�̷�����ʧ��");
				}
				return "506";
			}
			if (logger.isDebugEnabled()) {
				logger.debug("����scp�ͻ���");
			}

			// ����SCP�ͻ���
			SCPClient scpClient = con.createSCPClient();
			if (logger.isDebugEnabled()) {
				logger.debug("��ʼ�ϴ��ļ���������");
			}

			// 0755��ָȨ�ޱ��
			scpClient.put(filedata,fileName, scpPath, "0755");
			if (logger.isDebugEnabled()) {
				logger.debug("�ر�����");
			}
			
			
			
			con.close();
			if (logger.isDebugEnabled()) {
				logger.debug("�ϴ����");
			}
			return "200";
		} catch (Exception e) {
			logger.error("�ϴ�ʧ��", e.getMessage(), e);
			return "500";
		}
	}
	

}