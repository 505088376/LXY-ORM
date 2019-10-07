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
 * 	增删改查数据库的具体实现类
 * @author 李晓勇
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
		System.out.println("共"+count+"行受到影响");
	}
	
	/**
	 * 	传的是类对象  和  主键的值
	 * 	
	 */
	@Override
	public void delete(Class clazz, Object priKeyValue) {
		// Employees.class---> delete from employee where priKey = id;
		
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		String priKey = tableInfo.getOnlyPriKey().getName();
		String sql = "delete from " + tableInfo.getTname() + " where " + priKey + "=?";
		int count = executeDML(sql,new Object[] {priKeyValue});
		System.out.println("共"+count+"行受到影响");
	}
	
	/**
	 * 	传一个类   需要通过反射找到类对象 和  该类的主键值
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
//		update 表名 set uname=?,pwd=? where id=?
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
		System.out.println("共"+count+"行受到影响");
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
//				获取属性名称
				String fieldName = metaData.getColumnLabel(i);
//				获取类型名称
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
//					打印查询结果,并格式化输出
					Object fieldValue = rs.getObject(i);
					String format = fieldValue+"";
					format.trim();
					System.out.format("%-25s",format);
//					使用label是为了识别别名
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
			System.out.println("没有查到相关结果");
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
//				获取属性名称
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
//					打印查询结果,并格式化输出
					Object fieldValue = rs.getObject(i);
					String format = fieldValue+"";
					format.trim();
					System.out.format("%-25s",format);
//					通过反射将查询到的结果设置到java类中 并添加到list容器中返回
//					使用label是为了识别别名
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
			System.out.println("没有查到相关结果");
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
//		select * from 表名  where id = ?
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		String priKey = tableInfo.getOnlyPriKey().getName();
		String sql = "select * from " + tableInfo.getTname() + " where " + priKey + "=?";
		return queryUniqueRow(sql,clazz, new Object[]{id}); 
	}
	
}
