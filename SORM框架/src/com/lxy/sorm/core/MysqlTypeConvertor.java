package com.lxy.sorm.core;
/**
 * 	字段类型转换类具体实现
 * @author 李晓勇
 *
 */
public class MysqlTypeConvertor implements TypeConvertor{

	@Override
	public String databaseTypeToJavaType(String columnType) {
		if("varchar".equalsIgnoreCase(columnType) ||
			"char".equalsIgnoreCase(columnType)) {
			return "String";
		}
		else if("int".equalsIgnoreCase(columnType) ||
				"tinyint".equalsIgnoreCase(columnType) ||
				"smallint".equalsIgnoreCase(columnType) ||
				"integer".equalsIgnoreCase(columnType)) {
			return "Integer";
		}
		else if("bigint".equalsIgnoreCase(columnType)) {
			return "Long";
		}
		else if("double".equalsIgnoreCase(columnType) ||
				"float".equalsIgnoreCase(columnType)) {
			return "Double";
		}
		else if("clob".equalsIgnoreCase(columnType)) {
			return "java.sql.Clob";
		}
		else if("blob".equalsIgnoreCase(columnType)) {
			return "java.sql.Blob";
		}
		else if("date".equalsIgnoreCase(columnType)) {
			return "java.sql.Date";
		}
		else if("time".equalsIgnoreCase(columnType)) {
			return "java.sql.Time";
		}
		else if("timestamp".equalsIgnoreCase(columnType) ||
				"datetime".equalsIgnoreCase(columnType)) {
			return "java.sql.Timestamp";
		}
		return null;
	}

	@Override
	public String javaTyepToDatabase(String javaDataType) {
		
		return null;
	}
	
}
