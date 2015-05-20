package com.kszit.stu_mavnePlugin.versionRelease;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;


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
	    
	    
	    @Parameter
	    private List<String> exFiles;
	        
	/**
	 * jar包、配置文件、web文件夹下、手动添加的jar包
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {

		
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
			if(!purposeFile.exists()){
				File parentFile = purposeFile.getParentFile();
				if(!parentFile.exists()){
					parentFile.mkdirs();
				}
				try {
					FileUtils.copyFileToDirectory(webFile, parentFile);
					getLog().info(type+"新增文件："+df.format(new Date(webFile.lastModified()))+"-->"+webFile.getPath());  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				if(webFile.lastModified()>purposeFile.lastModified()){
					try {
						getLog().info(type+"修改文件：原："+df.format(new Date(webFile.lastModified()))
								+",新:"+df.format(new Date(purposeFile.lastModified()))
								+"-->"+webFile.getPath()); 
						FileUtils.copyFile(webFile, purposeFile);
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
	

}
