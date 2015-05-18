package com.kszit.study.stu_algorithm.sort;

import java.util.List;

/**
 * ≤Â»Î≈≈–Ú
 * @author hanxiaowei
 *
 */
public abstract class AbstractSort implements Sort{
	
	protected Integer[] data;
	public AbstractSort(Integer[] adata){
		this.data = adata;
	}
	
	public abstract void sort();
	
	public Integer[] resultData(){
		return data;
	}
}
