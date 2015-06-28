package com.kszit.stu.webInvoke.web.controle.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

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
	//跳过的类
	private String[] skipScanClassName = new String[]{"SynIDCardAPI","IDCordRead2"};
	private String pkgName = null;
	private Class<T> goalClass = null;
	private String[] jarNames = null;
	public FindClassExtendsSubClass(String apackageName,Class<T> agoalClass,String[] ajarNames){
		this.pkgName = apackageName.replace('.', '/') + "/";
		this.goalClass = agoalClass;
		this.jarNames = ajarNames;
		findClassAndJarPath();
	}
	
	private List<Class<T>> findClasses = new ArrayList<Class<T>>();
	
	public List<Class<T>> getClassSubCalss(boolean isContainSubPackage) { 
		getClassInPackage(this.pkgName,isContainSubPackage);
		return findClasses;
	}
	
	 private void getClassInPackage(String rPath,boolean isContainSubPackage) {  
	        
        try {  
            for (File classPath : classPathArray) {  
                if (!classPath.exists())  
                    continue;  
                if (classPath.isDirectory()) {  
                    File dir = new File(classPath, rPath);  
                    if (!dir.exists())  
                        continue;  
                    for (File file : dir.listFiles()) {  
                        if (file.isFile()) {  
                            String clsName = file.getName();  
                            if(clsName.endsWith(".class")){
                            	clsName = (rPath.replace("/", ".") + clsName).replace(".class",""); 
                                if(isSkip(clsName)){
                                	continue;
                                }
                                if(clsName.startsWith("com.bdcor.pip.client.util.idcord")){
                                	continue;
                                }
                                Class<T> c = (Class<T>) Class.forName(clsName);
                                if(isSubClass(c)){
                                	findClasses.add(c);
                                }
                            }
                        }else{
                        	if(isContainSubPackage){
                        		getClassInPackage(rPath+file.getName()+"/",isContainSubPackage);
                        	}
                        }
                    }  
                } else {
                	
                	if(!isIncludeJar(classPath.getName())){
                		continue;
                	}
//                	DatLogger.logSysStartDown(getClass(), "查找jar包："+classPath.getPath());
                    FileInputStream fis = new FileInputStream(classPath);  
                    JarInputStream jis = new JarInputStream(fis, false);  
                    JarEntry e = null;  
                    while ((e = jis.getNextJarEntry()) != null) {  
                        String eName = e.getName();  
                        
                        if (eName.startsWith(rPath) && !eName.endsWith("/")) {  
                        	if(!eName.endsWith(".class") || 
                        			eName.contains("PageOut")||
                        			eName.contains("VelocityOut")||
                        			eName.contains("HelloVelocity")){
                        		continue;
                        	}
//                        	DatLogger.logSysStartDown(getClass(), "找到类："+eName);
                        	Class c = Class.forName((eName.replace('/', '.')).replace(".class", ""));
                        	
                        	if(isSubClass(c)){
	//                        		DatLogger.logSysStartDown(getClass(), "添加类："+eName);
                        		findClasses.add(c);	
                        	}
                        }  
                        jis.closeEntry();  
                    }  
                    jis.close();
                }
            }  
        } catch (Exception e) { 
//            throw new RuntimeException(e);  
        }  
    }  
	
	/**
	 * 是否为子类
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
	
	private List<File> classPathArray = null;  
	/**
	 * 查找classpath目录
	 */
	private void findClassAndJarPath(){
		classPathArray = new ArrayList<File>();  
	    String delim = ":";  
	    if (System.getProperty("os.name").indexOf("Windows") != -1)  
	        delim = ";";  
	    for (String pro : CLASS_PATH_PROP) {  
	        String[] pathes = System.getProperty(pro).split(delim);  
	        for (String path : pathes) {  
//	        	DatLogger.logSysStartDown(getClass(), "classpth:"+path);
	        	classPathArray.add(new File(path));  
	        }  
	    }  
	}
	
	private boolean isSkip(String className){
		for(String name:skipScanClassName){
			if(className.contains(name)){
				return true;
			}
		}
		return false;
	}
	
	private boolean isIncludeJar(String jarName){
		for(String jar:jarNames){
//			DatLogger.logSysStartDown(getClass(), "找到jar包："+jarName+"。对别目标jar："+jar);
			if(jarName.startsWith(jar)){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] a) throws Exception{
		String rPath = "com.bdcor.pip.client".replace('.', '/') + "/";
		 FileInputStream fis = new FileInputStream("c://dat.jar");  
         JarInputStream jis = new JarInputStream(fis, false);  
         JarEntry e = null;  
         while ((e = jis.getNextJarEntry()) != null) {  
             String eName = e.getName(); 
             System.out.println(eName);
             if (eName.startsWith(rPath) && !eName.endsWith("/")) {  
             	Class c = Class.forName((eName.replace('/', '.')).replace(".class", ""));
             	System.out.println("找到目标："+eName);
             }  
             jis.closeEntry();  
         }  
         jis.close();
	}
}
