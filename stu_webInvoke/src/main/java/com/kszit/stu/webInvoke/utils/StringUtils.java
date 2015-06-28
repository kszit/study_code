package com.kszit.stu.webInvoke.utils;

public class StringUtils {
	/**
	 * 修改字符串的第一个字母为小写
	 * @param s
	 * @return
	 */
	public static String firstCharToLower(String s){
		return org.apache.commons.lang.StringUtils.replaceOnce(s, s.charAt(0)+"", (s.charAt(0)+"").toLowerCase());
	}
}
