package com.lxy.sorm.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lxy.sorm.bean.ColumnInfo;
import com.lxy.sorm.bean.TableInfo;
import com.lxy.sorm.utils.JavaFileUtils;
import com.lxy.sorm.utils.StringUtils;

/**
 * 	负责获取管理数据库所有表结构和类结构的关系，并可以根据表结构生成类结构
 * @author 李晓勇
 *
 */
@SuppressWarnings("all")
public class TableContext {
	/**
	 * 	表名为key，表信息对象为value
	 */
	public static Map<String,TableInfo> tables = new HashMap<>();
	/**
	 * 	将po的class对象和表信息对象关联起来，方便重用
	 */
	public static Map<Class,TableInfo> poClassTableMap = new HashMap<>();
	
	private TableContext() {
		
	}
	
	static { //		初始化获得表的信息
		try {
//			连接数据库
			Connection conn = DBManager.getConnection();
//			获取数据库的整体信息
			DatabaseMetaData dbmd = conn.getMetaData();
			/**
			 * 	第一个参数表示数据库名称
			 * 	第二个参数可理解为数据库登陆名  %表示0个或多个字符
			 * 	第三个参数表示表的名称
			 * 	第四个参数一般就用这个
			 */
			ResultSet tableRet = dbmd.getTables("myemployees", "%", "%", new String[] {"TABLE"});
			while(tableRet.next()) {
//				获取该数据库下所有表的名称（TABLE_NAME格式就是这么写的）
				String tableName = (String) tableRet.getObject("TABLE_NAME");
//				初始化表信息（TableInfo）  
				TableInfo ti = new TableInfo(tableName,new HashMap<String,ColumnInfo>(),new ColumnInfo());
//				将表名和对应的表信息添加到tables中 （注意：这个时候只是初始化表的信息  表的信息还没有添加进去）
				tables.put(tableName, ti);
//				获取当前表的所有字段（参数与上面类似）
				ResultSet tableInfo = dbmd.getColumns(null, "%", tableName, "%");
//				将表的信息添加到tables中去，这个循环添加了表的字段名和字段类型信息
				while(tableInfo.next()) {
					ColumnInfo ci = new ColumnInfo(tableInfo.getString("COLUMN_NAME"),tableInfo.getString("TYPE_NAME"),0); 
					tables.get(tableName).getColumns().put(tableInfo.getString("COLUMN_NAME"), ci);
				}
//				获取当前表的主键
				ResultSet priKey = dbmd.getPrimaryKeys("myemployees", "%", tableName);
//				讲主键添加到tables中
				while(priKey.next()) {
					String key = priKey.getString("COLUMN_NAME");
					if(tables.get(tableName).getColumns().containsKey(key)) {
						tables.get(tableName).getColumns().get(key).setKeyType(1);
						tables.get(tableName).setOnlyPriKey(tables.get(tableName).getColumns().get(key));
					}
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
//		更新类结构
		updateJavaPOFile();
//		加载po包下的类
//		loadPO();
	}
	
	/**
	 * 	根据表结构，更新配置po包下面的java类
	 * 	实现了从表结构到类结构的转换
	 */
	public static void updateJavaPOFile() {
		Map<String,String> columnInfo = new HashMap<>();
		Map<String,TableInfo> map = TableContext.tables;
		StringBuilder fieldInfo = new StringBuilder();
		StringBuilder getInfo = new StringBuilder();
		StringBuilder setInfo = new StringBuilder();
		for(TableInfo tableInfo : map.values()) {
			Class clazz = JavaFileUtils.createJavaPOFile(tableInfo);
			for(ColumnInfo c : tableInfo.getColumns().values()) {
				String fieldName = c.getName();
				String typeName = c.getDateType();
				columnInfo.put(fieldName, typeName);
			}
			poClassTableMap.put(clazz, tableInfo);
		}
		for(String key : columnInfo.keySet()) {
			fieldInfo.append(JavaFileUtils.createFileGetSet(key,columnInfo.get(key)).getFiledInfo());
			getInfo.append(JavaFileUtils.createFileGetSet(key,columnInfo.get(key)).getGetInfo());
			setInfo.append(JavaFileUtils.createFileGetSet(key,columnInfo.get(key)).getSetInfo());
		}
		List<StringBuilder> infoClass = new ArrayList<>();
		infoClass.add(fieldInfo);
		infoClass.add(getInfo);
		infoClass.add(setInfo);
		Class clazz = JavaFileUtils.createJavaPOFile(infoClass,"All");
	}
	
//	public static void loadPO() {
//		try {
//			for(String key : tables.keySet()){
//				Class clazz = Class.forName(DBManager.getConf().getPoPackage()+"."
//											+StringUtils.initialUpper(key));
//				
//				poClassTableMap.put(clazz, tables.get(key));
//			}
//		} catch (ClassNotFoundException e) {
//
//			e.printStackTrace();
//		}
//	}
}
