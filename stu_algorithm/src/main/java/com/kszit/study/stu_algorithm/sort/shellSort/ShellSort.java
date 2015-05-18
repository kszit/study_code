package com.kszit.study.stu_algorithm.sort.shellSort;

import com.kszit.study.stu_algorithm.sort.AbstractSort;
import com.kszit.study.stu_algorithm.sort.Sort;

/**
 * Ï£¶ûÅÅÐò
 * @author hanxiaowei
 *
 */
public class ShellSort extends AbstractSort implements Sort{
	
	private int[] increateIndexs = {5,4,2,1};
	
	public ShellSort(Integer[] adata){
		super(adata);
		
	}
	
	
	public void sort(){
		int datalenth = data.length;
		for(int increateIndex:increateIndexs){
			for(int i=0;i<datalenth;i+=increateIndex){
				int start = i;
				int end = i+increateIndex;
				if(end>datalenth){
					end = datalenth;
				}
				
				insertSort(start,end);
			}
			
			
			
		}
		
		
		
	}
	
	private void insertSort(int startIndex,int endIndex){
		for(int i=startIndex+1;i<endIndex;i++){
			int cru = data[i];
			for(int j=i-1;j>=startIndex;j--){
				int pre = data[j];
				if(pre>cru){
					data[j+1] = pre;
					data[j] = cru;
				}
			}
		}
	}
}
