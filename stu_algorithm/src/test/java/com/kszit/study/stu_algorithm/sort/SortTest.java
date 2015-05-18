package com.kszit.study.stu_algorithm.sort;

import org.junit.Before;
import org.junit.Test;

import com.kszit.study.stu_algorithm.sort.insertSort.InsertSort;
import com.kszit.study.stu_algorithm.sort.shellSort.ShellSort;

public class SortTest {

	Integer[] correctResult = new Integer[]{1,2,3,4,5,6,7,8,9};
	
	Integer[] data;
	@Before
	public void initData(){
		data = new Integer[]{6,3,5,7,1,8,4,2,9};
	}
	
	@Test
	public void insertSortTest(){
		InsertSort sort = new InsertSort(data);
		sort.sort();
		
		org.junit.Assert.assertArrayEquals(correctResult, sort.resultData());
		
	}
	
	@Test
	public void shellSortTest(){
		ShellSort sort = new ShellSort(data);
		sort.sort();
		
		org.junit.Assert.assertArrayEquals(correctResult, sort.resultData());
		
	}
}
