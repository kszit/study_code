package com.kszit.stu_mavnePlugin.versionRelease;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

import com.kszit.util.jdbc.DbHandler;
import com.kszit.util.jdbc.IConnection;
import com.kszit.util.jdbc.oracle.OracleConnection;
import com.kszit.util.ssh2upload.SSH2UploadFileUtil;


/**
 *
 * �����޸ĺ�����ļ��������������򷢲������Ի���
 * @author Administrator
 *
 *
 */
@Mojo( name = "vrelease", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class VersionRelease extends AbstractMojo {
	
	 	@Parameter( defaultValue = "${project.basedir}", required = true,readonly=true)
	    private File basedir;

	    @Parameter( defaultValue = "${project.build.sourceDirectory}", required = true,readonly=true )
	    private File sourceDirectory;
	    
	    @Parameter( defaultValue = "${project.build.testSourceDirectory}", required = true,readonly=true )
	    private File testSourceDirectory;
	    
	    /**
	     * jar�ļ�����Ŀ¼
	     */
	    @Parameter( defaultValue = "${project.build.directory}", required = true,readonly=true )
	    private File jarDirectory;
	    /**
	     * jar�ļ�����
	     */
	    @Parameter( defaultValue = "${project.build.finalName}", required = true,readonly=true )
	    private String jarName;
	    
	    @Parameter
	    private String jarPurposeDir;
	 
	    
	    /**
	     * �����ĵ�����Ŀ¼
	     */
	    @Parameter( defaultValue = "${project.build.outputDirectory}", required = true,readonly=true )
	    private File resoucesDirectory;
	    /**
	     * ��Դ�ļ�����
	     */
	    private String[] resorurceFileType = {".json",".properties",".xml",".bat"};
	    
	    @Parameter
	    private String resourcePurposeDir;

	    /**
	     * web��Դ����Ŀ¼
	     */
	    @Parameter
	    private String webDirectory;
	    /**
	     * web��Դ����Ŀ¼
	     */
	    @Parameter
	    private String webPurposeDir;
	    
	    /**
	     * �����ļ����Ŀ¼
	     */
	    @Parameter
	    private String pipReleaseVersionDir;
	    
	    
	    private String zipFileName;
	    
	    private String zipFileVersion;
	    
	    /**
	     * �ϴ�·��
	     */
	    @Parameter
	    private String uploadPath;
	    /**
	     * ip
	     */
	    @Parameter
	    private String uploadIp;
	    /**
	     * port
	     */
	    @Parameter
	    private String uploadPort;
	    /**
	     * �˺���
	     */
	    @Parameter
	    private String uploadUName;
	    /**
	     * �˺�����
	     */
	    @Parameter
	    private String uploadPwd;
	    
	    @Parameter
	    private List<String> exFiles;
	    
	    
	    
	    
	    
	    
		/**
		 * ���ݿ�ip
		 */
		@Parameter
	    private String dbip;
	    
	    /**
		 * ���ݿ�˿ں�
		 */
	    @Parameter
	    private String dbport;
	    /**
		 * ���ݿ�����
		 */
	    @Parameter
	    private String dbName;
	    /**
		 * ���ݿ��û���
		 */
	    @Parameter
	    private String dbusername;
	    /**
		 * ���ݿ�����
		 */
	    @Parameter
	    private String dbpwd;
	    
	    @Parameter
	    private String updatesql;
		
	    /**
	     * 
	     */
	    @Parameter
	    private List<String> modifiedFiles;
	    
	    /**
	     * 
	     */
	    @Parameter
	    private Properties replaceStrs;
	        
	/**
	 * jar���������ļ���web�ļ����¡��ֶ���ӵ�jar��
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
						
		
		try {
			createZipFileName();
		} catch (IOException e) {
			throw new MojoFailureException(e, "����zip���� ��������", "����zip���� ��������");
		}
		
		
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		pipReleaseVersionDir += File.separator+ df.format(new Date())+"-"+(System.currentTimeMillis()+"").substring(8);
		getLog().info("���ɷ���Ŀ¼��"+pipReleaseVersionDir); 
		File reldir = new File(pipReleaseVersionDir);
		reldir.mkdirs();
		
		
		
		getLog().info("����Ŀ¼��"+basedir.getPath());  
//
//		getLog().info("Դ����Ŀ¼��"+sourceDirectory.getPath());
//		getLog().info("����Դ��Ŀ¼��"+testSourceDirectory.getPath());
		
		getLog().info("=============================jar");  
		List<File> jarFiles = new ArrayList<File>();
		jarFiles.add(new File(this.jarDirectory.getPath()+File.separator+this.jarName+".jar"));
		findFile(jarFiles,this.basedir,"jar","lib");
		fileComparisonAndCopy("jar",jarFiles,null, this.jarPurposeDir);
		
		

		
		getLog().info("=============================�����ļ�");  
		getLog().info("��Դ�ļ�����·����"+this.resoucesDirectory.getPath());  
		File[] resouceFile = this.resoucesDirectory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				for(String goalType:resorurceFileType){
					if(name.endsWith(goalType)){
						return true;
					}
				}
				return false;
			}
		});
		List<File> resourceListFiles = new ArrayList<File>();
		for(File rescoureFile:resouceFile){
			resourceListFiles.add(rescoureFile);
		}
		fileComparisonAndCopy("resource",resourceListFiles,this.resoucesDirectory.getPath(), this.resourcePurposeDir);
		
		
		
		
		
		getLog().info("=============================web");  
		getLog().info("web����·����"+this.webDirectory);  
		List<File> webfiles = new ArrayList<File>();
		findFile(webfiles,new File(this.webDirectory),null,null);
		fileComparisonAndCopy("web",webfiles,this.webDirectory, this.webPurposeDir);
		
		
	

		//���ɲ�����
		com.kszit.stu_mavnePlugin.versionRelease.FileUtils.zipFiles(new File(pipReleaseVersionDir), 
				new File(pipReleaseVersionDir+File.separator+zipFileName));
		
		
		//�ύ������
		SSH2UploadFileUtil uploadfileUtil = new SSH2UploadFileUtil(
				uploadPath,
				uploadIp,
				uploadPort,
				uploadUName,
				uploadPwd);
		
		try {
			File f = new File(pipReleaseVersionDir+File.separator+zipFileName);
			byte[] filedata = new byte[(int) f.length()];
			
			InputStream fin = new FileInputStream(f);
			fin.read(filedata, 0, filedata.length);
			
			uploadfileUtil.upLoadFile(filedata,zipFileName);
		} catch (Exception e) {
			throw new MojoFailureException(e, "�ϴ��ļ���server��������", "�ϴ��ļ���server��������");
		}
		
		//�޸����ݿ�汾��
		IConnection con = new OracleConnection(
				this.dbip,
				this.dbport,
				this.dbName,
				this.dbusername,
				this.dbpwd);
		try{
			DbHandler dbhandler = new DbHandler(con.connection());
			
			String updatesql = this.updatesql.replace("#newversion#", this.zipFileVersion);
			dbhandler.update(updatesql);
			
			dbhandler.closeCon();
				
		}catch(Exception e){
			throw new MojoFailureException(e, "�޸����ݿ�汾�ŷ�������", "�޸����ݿ�汾�ŷ�������");
		}
		
		
	}
	
	private void fileComparisonAndCopy(String type,List<File> files,String originalRoot,String purposeRoot){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(File webFile:files){
			
			if(!isInclude(webFile)){
				continue;
			}
			
			String filePath = webFile.getPath();
			String purposePath = purposeRoot+File.separator+webFile.getName();
			
			if(originalRoot!=null){
				purposePath = filePath.replace(originalRoot, purposeRoot);
			}
			
			File purposeFile = new File(purposePath);
			if(!purposeFile.exists()){//�����ļ�
				File parentFile = purposeFile.getParentFile();
				if(!parentFile.exists()){
					parentFile.mkdirs();
				}
				try {
					getLog().info(type+"�����ļ���"+df.format(new Date(webFile.lastModified()))+"-->"+webFile.getPath());  
					FileUtils.copyFileToDirectory(webFile, parentFile);
					modifyFile(new File(purposeFile.getPath()));
					//�����ļ������Ŀ¼
					String PIPReleaseVersionFileStr = purposeFile.getPath().replace("D:\\PIP-release-version\\001", pipReleaseVersionDir);
					FileUtils.copyFile(purposeFile, new File(PIPReleaseVersionFileStr));
					getLog().info(type+"�����ļ���"+purposeFile.getPath()+"-->"+PIPReleaseVersionFileStr); 
					
					new File(purposeFile.getPath()).delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{//�滻�ļ�
				if(webFile.lastModified()>purposeFile.lastModified()){
					try {
						getLog().info(type+"�޸��ļ����£�"+df.format(new Date(webFile.lastModified()))
								+",��:"+df.format(new Date(purposeFile.lastModified()))
								+"-->"+webFile.getPath()); 
						File  tempFile = new File("d://"+purposeFile.getName());
						FileUtils.copyFile(webFile, tempFile);
						modifyFile(tempFile);
						//�����ļ������Ŀ¼
						String PIPReleaseVersionFileStr = purposeFile.getPath().replace("D:\\PIP-release-version\\001", pipReleaseVersionDir);
						FileUtils.copyFile(tempFile, new File(PIPReleaseVersionFileStr));
						getLog().info(type+"�����ļ���"+purposeFile.getPath()+"-->"+PIPReleaseVersionFileStr); 
					
						tempFile.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * �ļ��滻
	 * @param file
	 */
	private void modifyFile(File file){
		String fileName = file.getName();
		for(String compFileName:modifiedFiles){
			if(fileName.equals(compFileName)){
				for(Object yuanObj:replaceStrs.keySet()){
					String yuanStr = (String)yuanObj;
					String newStr = (String)replaceStrs.get(yuanObj);
					
					try {
						String content = org.apache.commons.io.FileUtils.readFileToString(file, "utf-8");
						if(content.contains(yuanStr)){
							content = content.replaceAll(yuanStr,newStr);
							
							org.apache.commons.io.FileUtils.forceDelete(file);
							org.apache.commons.io.FileUtils.writeStringToFile(file, content, "utf-8");
							
							getLog().info("�ַ����滻��"+file.getName()
									+","+yuanStr
									+"==>"+newStr); 
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					
				}
				
				
			}
		}
		
		
	}
	
	private void findFile(List<File> findFiles,File dir,String fileType,String dirStart){
		if(dir.isFile()){
			if(fileType!=null){
				if(dir.getName().endsWith(fileType)){
					findFiles.add(dir);
				}
			}else{
				findFiles.add(dir);
			}
		}else{
			for(File subFile:dir.listFiles()){
				if(dirStart!=null && subFile.isDirectory()){
					if(subFile.getName().startsWith(dirStart)){
						findFile(findFiles,subFile,fileType,dirStart);
					}
				}else{
					findFile(findFiles,subFile,fileType,dirStart);	
				}
			}
		}
	}
	
	private boolean isInclude(File f){
		String filePath = f.getPath();
		for(String exf:this.exFiles){
			if(filePath.endsWith(exf)){
				return false;
			}
		}
		return true;
	}
	
	
	private void createZipFileName() throws IOException{
		File vFile = new File(pipReleaseVersionDir+File.separator+"rVersion.txt");
		getLog().info("�汾��¼�ļ�:"+vFile.getPath());
		if(!vFile.exists()){
			vFile.createNewFile();
		}
//		getLog().info(vFile.getPath());
		DateFormat df2 = new SimpleDateFormat("yyyyMMdd");
		String cdate = df2.format(new Date());
		
		String smallV = "";
		String recordfV = org.apache.commons.io.FileUtils.readFileToString(vFile, "utf-8");
		if(recordfV==null || "".equals(recordfV)){
			smallV = "0001";
		}else{
			if(recordfV.startsWith(cdate)){
				String tem = ""+(Integer.parseInt(recordfV.replace(cdate, "").replace("0",""))+1);
				if(tem.length()==1){
					smallV = "000"+tem;
				}else if(tem.length()==2){
					smallV = "00"+tem;
				}else{
					smallV = "0"+tem;
				}
			}else{
				smallV = "0001";
			}
			
		}
		zipFileVersion = cdate+smallV;
		zipFileName = ""+cdate+smallV+".zip";
		//vFile.deleteOnExit();
		org.apache.commons.io.FileUtils.writeStringToFile(vFile, cdate+smallV, "utf-8");

		getLog().info("���������ƣ�"+zipFileName);
	}

}
