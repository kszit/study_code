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
 * 按照修改和添加文件发布补丁包，或发布到测试环境
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
	     * jar文件所在目录
	     */
	    @Parameter( defaultValue = "${project.build.directory}", required = true,readonly=true )
	    private File jarDirectory;
	    /**
	     * jar文件名称
	     */
	    @Parameter( defaultValue = "${project.build.finalName}", required = true,readonly=true )
	    private String jarName;
	    
	    @Parameter
	    private String jarPurposeDir;
	 
	    
	    /**
	     * 配置文档所在目录
	     */
	    @Parameter( defaultValue = "${project.build.outputDirectory}", required = true,readonly=true )
	    private File resoucesDirectory;
	    /**
	     * 资源文件类型
	     */
	    private String[] resorurceFileType = {".json",".properties",".xml",".bat"};
	    
	    @Parameter
	    private String resourcePurposeDir;

	    /**
	     * web资源所在目录
	     */
	    @Parameter
	    private String webDirectory;
	    /**
	     * web资源所在目录
	     */
	    @Parameter
	    private String webPurposeDir;
	    
	    /**
	     * 发布文件存放目录
	     */
	    @Parameter
	    private String pipReleaseVersionDir;
	    
	    
	    private String zipFileName;
	    
	    private String zipFileVersion;
	    
	    /**
	     * 上传路径
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
	     * 账号名
	     */
	    @Parameter
	    private String uploadUName;
	    /**
	     * 账号密码
	     */
	    @Parameter
	    private String uploadPwd;
	    
	    @Parameter
	    private List<String> exFiles;
	    
	    
	    
	    
	    
	    
		/**
		 * 数据库ip
		 */
		@Parameter
	    private String dbip;
	    
	    /**
		 * 数据库端口号
		 */
	    @Parameter
	    private String dbport;
	    /**
		 * 数据库名称
		 */
	    @Parameter
	    private String dbName;
	    /**
		 * 数据库用户名
		 */
	    @Parameter
	    private String dbusername;
	    /**
		 * 数据库密码
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
	 * jar包、配置文件、web文件夹下、手动添加的jar包
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
						
		
		try {
			createZipFileName();
		} catch (IOException e) {
			throw new MojoFailureException(e, "生成zip名称 发生错误", "生成zip名称 发生错误");
		}
		
		
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		pipReleaseVersionDir += File.separator+ df.format(new Date())+"-"+(System.currentTimeMillis()+"").substring(8);
		getLog().info("生成发布目录："+pipReleaseVersionDir); 
		File reldir = new File(pipReleaseVersionDir);
		reldir.mkdirs();
		
		
		
		getLog().info("基础目录："+basedir.getPath());  
//
//		getLog().info("源代码目录："+sourceDirectory.getPath());
//		getLog().info("测试源码目录："+testSourceDirectory.getPath());
		
		getLog().info("=============================jar");  
		List<File> jarFiles = new ArrayList<File>();
		jarFiles.add(new File(this.jarDirectory.getPath()+File.separator+this.jarName+".jar"));
		findFile(jarFiles,this.basedir,"jar","lib");
		fileComparisonAndCopy("jar",jarFiles,null, this.jarPurposeDir);
		
		

		
		getLog().info("=============================配置文件");  
		getLog().info("资源文件所在路径："+this.resoucesDirectory.getPath());  
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
		getLog().info("web所在路径："+this.webDirectory);  
		List<File> webfiles = new ArrayList<File>();
		findFile(webfiles,new File(this.webDirectory),null,null);
		fileComparisonAndCopy("web",webfiles,this.webDirectory, this.webPurposeDir);
		
		
	

		//生成补丁包
		com.kszit.stu_mavnePlugin.versionRelease.FileUtils.zipFiles(new File(pipReleaseVersionDir), 
				new File(pipReleaseVersionDir+File.separator+zipFileName));
		
		
		//提交补丁包
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
			throw new MojoFailureException(e, "上传文件到server发生错误", "上传文件到server发生错误");
		}
		
		//修改数据库版本号
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
			throw new MojoFailureException(e, "修改数据库版本号发生错误", "修改数据库版本号发生错误");
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
			if(!purposeFile.exists()){//新增文件
				File parentFile = purposeFile.getParentFile();
				if(!parentFile.exists()){
					parentFile.mkdirs();
				}
				try {
					getLog().info(type+"新增文件："+df.format(new Date(webFile.lastModified()))+"-->"+webFile.getPath());  
					FileUtils.copyFileToDirectory(webFile, parentFile);
					modifyFile(new File(purposeFile.getPath()));
					//拷贝文件到打包目录
					String PIPReleaseVersionFileStr = purposeFile.getPath().replace("D:\\PIP-release-version\\001", pipReleaseVersionDir);
					FileUtils.copyFile(purposeFile, new File(PIPReleaseVersionFileStr));
					getLog().info(type+"拷贝文件："+purposeFile.getPath()+"-->"+PIPReleaseVersionFileStr); 
					
					new File(purposeFile.getPath()).delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{//替换文件
				if(webFile.lastModified()>purposeFile.lastModified()){
					try {
						getLog().info(type+"修改文件：新："+df.format(new Date(webFile.lastModified()))
								+",旧:"+df.format(new Date(purposeFile.lastModified()))
								+"-->"+webFile.getPath()); 
						File  tempFile = new File("d://"+purposeFile.getName());
						FileUtils.copyFile(webFile, tempFile);
						modifyFile(tempFile);
						//拷贝文件到打包目录
						String PIPReleaseVersionFileStr = purposeFile.getPath().replace("D:\\PIP-release-version\\001", pipReleaseVersionDir);
						FileUtils.copyFile(tempFile, new File(PIPReleaseVersionFileStr));
						getLog().info(type+"拷贝文件："+purposeFile.getPath()+"-->"+PIPReleaseVersionFileStr); 
					
						tempFile.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 文件替换
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
							
							getLog().info("字符串替换："+file.getName()
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
		getLog().info("版本记录文件:"+vFile.getPath());
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

		getLog().info("补丁包名称："+zipFileName);
	}

}
