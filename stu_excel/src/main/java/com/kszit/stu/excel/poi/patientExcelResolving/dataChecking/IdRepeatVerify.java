package com.kszit.stu.excel.poi.patientExcelResolving.dataChecking;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.tools.ant.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 证件号重复验证
 * @author Administrator
 *
 */
public class IdRepeatVerify {
	private Logger log = LoggerFactory.getLogger(IdRepeatVerify.class);
	private Set<String> tempIds = new HashSet<String>();
	
	
	
	private String filePath = FileUtils.getUserDirectoryPath();
	private String fileName = "idRepoet.txt";
	
	private static IdRepeatVerify idRepeatVerify = null;
	public static synchronized IdRepeatVerify instance(){
		if(idRepeatVerify==null){
			idRepeatVerify = new IdRepeatVerify();
		}
		return idRepeatVerify;
	}
	
	
	
	private IdRepeatVerify(){
		File fileP = new File(filePath);
		if(!fileP.exists()){
			fileP.mkdirs();
		}

		File file = new File(fileP,fileName);

		log.info("加载未导入到数据库中的id。缓存文件："+file.getPath());
		if(!file.exists()){
			
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			tempIds.addAll(FileUtils.readLines(file));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String s:tempIds){
			log.info("加载到的缓存："+s);
		}
		
	}
	
	/**
	 * 是否有重复的数据，存在 返回true，
	 * 不存在 添加，返回false;
	 * @param id
	 * @return
	 */
	public boolean repeatId(String id){
		if(tempIds.contains(id)){
			return true;
		}else{
			tempIds.add(id);
		}
		return false;
	}
	
	/**
	 * 删除id
	 * @param id
	 */
	public void deleteId(String id){
		tempIds.remove(id);
	}

	/**
	 * 保存
	 */
	public void save(){
		try {
			File saveFile = new File(filePath+fileName);
			FileUtils.write(saveFile, "");
			FileUtils.writeLines(saveFile, this.tempIds);
			log.info("保存缓存id到文件。"+saveFile.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		save();
	}
	
	
	
}
