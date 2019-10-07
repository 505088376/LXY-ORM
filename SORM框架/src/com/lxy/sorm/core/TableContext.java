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
 * 	�����ȡ�������ݿ����б�ṹ����ṹ�Ĺ�ϵ�������Ը��ݱ�ṹ������ṹ
 * @author ������
 *
 */
@SuppressWarnings("all")
public class TableContext {
	/**
	 * 	����Ϊkey������Ϣ����Ϊvalue
	 */
	public static Map<String,TableInfo> tables = new HashMap<>();
	/**
	 * 	��po��class����ͱ���Ϣ���������������������
	 */
	public static Map<Class,TableInfo> poClassTableMap = new HashMap<>();
	
	private TableContext() {
		
	}
	
	static { //		��ʼ����ñ����Ϣ
		try {
//			�������ݿ�
			Connection conn = DBManager.getConnection();
//			��ȡ���ݿ��������Ϣ
			DatabaseMetaData dbmd = conn.getMetaData();
			/**
			 * 	��һ��������ʾ���ݿ�����
			 * 	�ڶ������������Ϊ���ݿ��½��  %��ʾ0�������ַ�
			 * 	������������ʾ�������
			 * 	���ĸ�����һ��������
			 */
			ResultSet tableRet = dbmd.getTables("myemployees", "%", "%", new String[] {"TABLE"});
			while(tableRet.next()) {
//				��ȡ�����ݿ������б�����ƣ�TABLE_NAME��ʽ������ôд�ģ�
				String tableName = (String) tableRet.getObject("TABLE_NAME");
//				��ʼ������Ϣ��TableInfo��  
				TableInfo ti = new TableInfo(tableName,new HashMap<String,ColumnInfo>(),new ColumnInfo());
//				�������Ͷ�Ӧ�ı���Ϣ��ӵ�tables�� ��ע�⣺���ʱ��ֻ�ǳ�ʼ�������Ϣ  �����Ϣ��û����ӽ�ȥ��
				tables.put(tableName, ti);
//				��ȡ��ǰ��������ֶΣ��������������ƣ�
				ResultSet tableInfo = dbmd.getColumns(null, "%", tableName, "%");
//				�������Ϣ��ӵ�tables��ȥ�����ѭ������˱���ֶ������ֶ�������Ϣ
				while(tableInfo.next()) {
					ColumnInfo ci = new ColumnInfo(tableInfo.getString("COLUMN_NAME"),tableInfo.getString("TYPE_NAME"),0); 
					tables.get(tableName).getColumns().put(tableInfo.getString("COLUMN_NAME"), ci);
				}
//				��ȡ��ǰ�������
				ResultSet priKey = dbmd.getPrimaryKeys("myemployees", "%", tableName);
//				��������ӵ�tables��
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
//		������ṹ
		updateJavaPOFile();
//		����po���µ���
//		loadPO();
	}
	
	/**
	 * 	���ݱ�ṹ����������po�������java��
	 * 	ʵ���˴ӱ�ṹ����ṹ��ת��
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
