package com.lxy.sorm.utils;
/**
 * 	封装常用字符串操作
 * @author 李晓勇
 *
 */
public class StringUtils {
	public static String initialUpper(String str) {
		return str.toUpperCase().substring(0,1)+str.substring(1);
	}
}
