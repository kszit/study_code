package com.kszit.stu.webInvoke.web.controle.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 查找子类。
 * <ol>
 * 		<li>class文件夹中查找 class文件</li>
 * 		<li>jar保重查找(功能未测试)</li>
 * </ol>
 * @author Administrator
 *
 */
public class FindClassExtendsSubClass<T> {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	//扫描路径
	private String pkgName = null;
	
	//查找类目标（其子类）
	private Class<T> goalClass = null;
	
	public FindClassExtendsSubClass(String apackageName,Class<T> agoalClass){
		this.pkgName = apackageName.replace('.', '/') + "/";
		this.goalClass = agoalClass;
		findClassAndJarPath();
	}
	
	private List<Class<T>> findClasses = new ArrayList<Class<T>>();
	
	/**
	 * 获取查找到的类
	 * @param isContainSubPackage  是否包括扫描路径的子路径
	 * @return
	 */
	public List<Class<T>> getClassSubCalss(boolean isContainSubPackage) { 
		getClassInPackage(this.pkgName,isContainSubPackage);
		return findClasses;
	}
	
	
	
	 private void getClassInPackage(String rPath,boolean isContainSubPackage) {  
		log.debug("找到路径："+rPath);
        try {  
            for (File classPath : classPathArray) {  
                if (!classPath.exists()){
                	continue; 
                }
                     
                if (classPath.isDirectory()) {  
                	
                	//查找目录下是否存在要查找的包路径
                    File dir = new File(classPath, rPath);  
                    if (!dir.exists()){  
                        continue;  
                    }
                    
                    //遍历文件夹下的文件夹或文件
                    for (File file : dir.listFiles()) {  
                    	if (file.isFile()) {  
                            log.debug("找到类："+file.getPath());
                    		String clsName = file.getName();  
                            if(clsName.endsWith(".class")){
                            	clsName = (rPath.replace("/", ".") + clsName).replace(".class",""); 
                                Class<T> c = (Class<T>) Class.forName(clsName);
                                if(isSubClass(c)){
                                	findClasses.add(c);
                                }
                            }
                        }else{
                        	if(isContainSubPackage){
                        		//递归查找子目录
                        		getClassInPackage(rPath+file.getName()+"/",isContainSubPackage);
                        	}
                        }
                    }  
                }else{
//                	DatLogger.logSysStartDown(getClass(), "查找jar包："+classPath.getPath());
                	//jar包处理
                    FileInputStream fis = new FileInputStream(classPath);  
                    JarInputStream jis = new JarInputStream(fis, false);  
                    JarEntry e = null;  
                    while ((e = jis.getNextJarEntry()) != null) {  
                        String eName = e.getName();  
                        
                        if (eName.startsWith(rPath) && !eName.endsWith("/")) {  
                        	if(!eName.endsWith(".class")){//跳过非class文件
                        		continue;
                        	}
                        	
                        	Class c = Class.forName((eName.replace('/', '.')).replace(".class", ""));
                        	if(isSubClass(c)){
                        		findClasses.add(c);	
                        	}
                        }  
                        jis.closeEntry();  
                    }  
                    jis.close();
                }
            }  
        } catch (Exception e) { 
        	
        }  
    }  
	
	/**
	 * 是否为子类(是否为目标类)
	 * @param c
	 * @return
	 */
	public boolean isSubClass(Class<T> c){
		if(goalClass==c){
			return false;
		}
		return goalClass.isAssignableFrom(c);
	}
	
	
	private static String[] CLASS_PATH_PROP = { "java.class.path",  
        "java.ext.dirs", "sun.boot.class.path" };  
	
	/** 待查找的目录或jar文件 */
	private List<File> classPathArray = null;  
	
	/**
	 * 查找classpath目录
	 */
	private void findClassAndJarPath(){
		classPathArray = new ArrayList<File>();  
	    String delim = ":";
	    
	    if (System.getProperty("os.name").indexOf("Windows") != -1){
	    	delim = ";";
	    }
	          
	    for (String pro : CLASS_PATH_PROP) {  
	        String[] pathes = System.getProperty(pro).split(delim);  
	        for (String path : pathes) {  
	        	log.debug(pro+":查找到类路径或者jar包："+path);
//	        	System.out.println(pro+":查找到类路径或者jar包："+path);
	        	classPathArray.add(new File(path));  
	        }  
	    }  
	}
	


	
	public static void main(String[] a) throws Exception{
		FindClassExtendsSubClass subClass = new FindClassExtendsSubClass("",null);
	}
}
