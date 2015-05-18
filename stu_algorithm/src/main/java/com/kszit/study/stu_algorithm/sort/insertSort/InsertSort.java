package com.kszit.study.stu_algorithm.sort.insertSort;

import java.util.Collections;
import java.util.List;

import com.kszit.study.stu_algorithm.sort.AbstractSort;
import com.kszit.study.stu_algorithm.sort.Sort;

/**
 * ≤Â»Î≈≈–Ú
 * @author hanxiaowei
 *
 */
public class InsertSort extends AbstractSort implements Sort{
	
	public InsertSort(Integer[] adata){
		super(adata);
	}
	
	
	public void sort(){
		for(int i=1;i<data.length;i++){
			int cru = data[i];
			for(int j=i-1;j>=0;j--){
				int pre = data[j];
				if(pre>cru){
					data[j+1] = pre;
					data[j] = cru;
				}
			}
		}
	}
}
