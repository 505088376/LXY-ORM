package com.lxy.sorm.core;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lxy.sorm.bean.TableInfo;
import com.lxy.sorm.utils.JDBCUtils;
import com.lxy.sorm.utils.JavaFileUtils;
import com.lxy.sorm.utils.ReflectUtils;
/**
 * 	��ɾ�Ĳ����ݿ�ľ���ʵ����
 * @author ������
 *
 */
public class MySqlQuery implements Query{

	@Override
	public int executeDML(String sql, Object[] params) {
		Connection conn = DBManager.getConnection();
		PreparedStatement ps = null;
		int count = 0;
		try {
			ps = conn.prepareStatement(sql);
			JDBCUtils.handleParams(ps, params);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.close(ps, conn);
		}
		return count;
	}

	@Override
	public void insert(Object obj) {
//		insert into employees(employee_id,Last_name) values(?,?)
		Class clazz = obj.getClass();
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		String tName = tableInfo.getTname();
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		int countNotNullField = 0;
		sql.append("insert into "+tName+"(");
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			Object fieldValue = ReflectUtils.invokeGet(field.getName(), obj);
			if(fieldValue!=null) {
				sql.append(field.getName()+",");
				params.add(fieldValue);
				countNotNullField++;
			}
		}
		sql.setCharAt(sql.length()-1, ')');
		sql.append(" values(");
		for(int i=0;i<countNotNullField;i++) {
			sql.append("?,");
		}
		sql.setCharAt(sql.length()-1, ')');
		int count = executeDML(sql.toString(),params.toArray());
		System.out.println("��"+count+"���ܵ�Ӱ��");
	}
	
	/**
	 * 	�����������  ��  ������ֵ
	 * 	
	 */
	@Override
	public void delete(Class clazz, Object priKeyValue) {
		// Employees.class---> delete from employee where priKey = id;
		
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		String priKey = tableInfo.getOnlyPriKey().getName();
		String sql = "delete from " + tableInfo.getTname() + " where " + priKey + "=?";
		int count = executeDML(sql,new Object[] {priKeyValue});
		System.out.println("��"+count+"���ܵ�Ӱ��");
	}
	
	/**
	 * 	��һ����   ��Ҫͨ�������ҵ������ ��  ���������ֵ
	 */
	@Override
	public void delete(Object obj) {
		
		Class clazz = obj.getClass();
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		String priKey = tableInfo.getOnlyPriKey().getName();
	
		Object priKeyValue = ReflectUtils.invokeGet(priKey, obj);
		delete(clazz,priKeyValue);
		
	}

	@Override
	public int update(Object obj, String[] fieldNames) {
//		update ���� set uname=?,pwd=? where id=?
		Class clazz = obj.getClass();
		List<Object> params = new ArrayList<>();
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		String tName = tableInfo.getTname();
		String priKey = tableInfo.getOnlyPriKey().getName();
		StringBuilder sql = new StringBuilder();
		sql.append("update "+tName+" set ");
		for(String fName : fieldNames) {
			sql.append(fName+"=?,");
			params.add(ReflectUtils.invokeGet(fName, obj));
		}
		sql.setCharAt(sql.length()-1, ' ');
		sql.append("where "+priKey+"=?" );
		params.add(ReflectUtils.invokeGet(priKey, obj));
		int count = executeDML(sql.toString(), params.toArray());
		System.out.println("��"+count+"���ܵ�Ӱ��");
		return count;
	}

	@Override
	public List queryRows(String sql, Object[] params) {
		Connection conn = DBManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = null;
		HashMap<String,String> columnInfo = new HashMap<>();
		StringBuilder fieldInfo = new StringBuilder();
		StringBuilder getInfo = new StringBuilder();
		StringBuilder setInfo = new StringBuilder();
		try {
			ps = conn.prepareStatement(sql);
			JDBCUtils.handleParams(ps, params);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			for(int i=1;i<=metaData.getColumnCount();i++) {
//				��ȡ��������
				String fieldName = metaData.getColumnLabel(i);
//				��ȡ��������
				String typeName = metaData.getColumnTypeName(i);
				System.out.printf("%-25s",fieldName);
				
				columnInfo.put(fieldName, typeName);
			}
			System.out.println();
			for(String key : columnInfo.keySet()) {
				fieldInfo.append(JavaFileUtils.createFileGetSet(key,columnInfo.get(key)).getFiledInfo());
				getInfo.append(JavaFileUtils.createFileGetSet(key,columnInfo.get(key)).getGetInfo());
				setInfo.append(JavaFileUtils.createFileGetSet(key,columnInfo.get(key)).getSetInfo());
			}
			List<StringBuilder> infoClass = new ArrayList<>();
			infoClass.add(fieldInfo);
			infoClass.add(getInfo);
			infoClass.add(setInfo);
			Class clazz = JavaFileUtils.createJavaPOFile(infoClass,"SelectResult");
			while(rs.next()) {
				if(list==null) {
					list = new ArrayList<>();
				}
				Object obj = clazz.getDeclaredConstructor().newInstance();
				for(int i=1;i<=metaData.getColumnCount();i++) {
//					��ӡ��ѯ���,����ʽ�����
					Object fieldValue = rs.getObject(i);
					String format = fieldValue+"";
					format.trim();
					System.out.format("%-25s",format);
//					ʹ��label��Ϊ��ʶ�����
					String fieldName = metaData.getColumnLabel(i);
					if(fieldValue!=null) {
						ReflectUtils.invokeSet(fieldName, obj, fieldValue);
					}
				}
				list.add(obj);
				System.out.println();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(rs, ps, conn);
		}
		if(list==null) {
			System.out.println("û�в鵽��ؽ��");
		}
		return list;
	}
	
	public void queryUniqueRow(String sql,Object[] params) {
		queryRows(sql,params);
	}
	
	@Override
	public List queryRows(String sql,Class clazz,Object[] params) {
		Connection conn = DBManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> list = null;
		try {
			ps = conn.prepareStatement(sql);
			JDBCUtils.handleParams(ps, params);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			for(int i=1;i<=metaData.getColumnCount();i++) {
//				��ȡ��������
				String fieldName = metaData.getColumnLabel(i);
				System.out.printf("%-25s",fieldName);
			}
			System.out.println();
			while(rs.next()) {
				if(list==null) {
					list = new ArrayList<>();
				}
				Object obj = clazz.getDeclaredConstructor().newInstance();
				for(int i=1;i<=metaData.getColumnCount();i++) {
//					��ӡ��ѯ���,����ʽ�����
					Object fieldValue = rs.getObject(i);
					String format = fieldValue+"";
					format.trim();
					System.out.format("%-25s",format);
//					ͨ�����佫��ѯ���Ľ�����õ�java���� ����ӵ�list�����з���
//					ʹ��label��Ϊ��ʶ�����
					String fieldName = metaData.getColumnLabel(i);
					if(fieldValue!=null) {
						ReflectUtils.invokeSet(fieldName, obj, fieldValue);
					}
				}
				list.add(obj);
				System.out.println();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(rs, ps, conn);
		}
		if(list==null) {
			System.out.println("û�в鵽��ؽ��");
		}
		return list;
	}

	@Override
	public Object queryUniqueRow(String sql, Class clazz, Object[] params) {
		List list = queryRows(sql,clazz,params);
		return (list == null || list.size()==0) ?  null : list.get(0);
	}

	@Override
	public Object queryValue(String sql, Object[] params) {
		Connection conn = DBManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object obj = null;
		try {
			ps = conn.prepareStatement(sql);
			JDBCUtils.handleParams(ps, params);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			for(int i=1;i<=metaData.getColumnCount();i++) {
				
				String fieldName = metaData.getColumnLabel(i);
				System.out.printf("%-20s",fieldName);
			}
			System.out.println();
			while(rs.next()) {
				obj = rs.getObject(1);
				System.out.println(obj);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Number queryNumber(String sql, Object[] params) {
		
		return (Number)queryValue(sql, params);
	}
	
	@Override
	public Object queryById(Class clazz, Object id) {
//		select * from ����  where id = ?
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		String priKey = tableInfo.getOnlyPriKey().getName();
		String sql = "select * from " + tableInfo.getTname() + " where " + priKey + "=?";
		return queryUniqueRow(sql,clazz, new Object[]{id}); 
	}
	
}
