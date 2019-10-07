package com.lxy.sorm.core;
/**
 * 	负责java数据类型和数据库数据类型互相转化
 * @author 李晓勇
 *
 */
public interface TypeConvertor {
	/**
	 * 	将数据库数据类型转化为java的数据类型
	 * @param columnTyepe 数据库字段的数据类型
	 * @return	java的数据类型
	 */
	public String databaseTypeToJavaType(String columnType);
	
	
	/**
	 * 	将java数据类型转化为数据库数据类型
	 * @param javaDataType java数据类型
	 * @return	数据库数据类型
	 */
	public String javaTyepToDatabase(String javaDataType);
}
