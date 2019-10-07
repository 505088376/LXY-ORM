package com.lxy.sorm.core;

import java.util.List;

/**
 * 	Query接口：负责查询（对外提供服务的核心类）
 * 		传的核心参数是类对象
 * 		初始时候类属性的值都为null 在删除和添加时候  手动设置类属性的值 
 * 		这些方法的目的就是将这些你设置的类属性的值  转换为sql语句去数据库中执行
 * @author 李晓勇
 *
 */
public interface Query {
	/**
	 * 	直接执行一个DML语句
	 * @param sql	sql语句
	 * @param params	参数
	 * @return	执行sql语句后影响记录的行数
	 */
	public int executeDML(String sql, Object[] params);
	
	
	/**
	 * 	将对象存储到数据库中
	 * @param obj	要存储的对象
	 */
	public void insert(Object obj);
	
	
	/**
	 * 	删除clazz表示类对应的表中记录（指定主键值id的记录） 	
	 * @param clazz	跟表对应的类的Class对象
	 * @param priKeyValue	主键值
	 */
	public void delete(Class clazz, Object priKeyValue);  // delete from User where id=?
	
	
	/**
	 * 	删除对象在数据库中对应的记录（对象所在类对应到表，对象的主键的值对应到记录）
	 * @param obj 跟表对应的类的属性
	 */
	public void delete(Object obj);
	
	
	/**
	 * 	更新对象对应的记录，并且只更新指定的字段值
	 * @param obj	所要更新的对象
	 * @param fieldNames	更新的属性列表
	 * @return	执行sql语句后影响记录的行数
	 */
	public int update(Object obj, String[] fieldNames); // update User set uname=?,pwd=?
	
	
	/**
	 * 	查询返回多行记录，并将每行记录封装到clazz指定类的对象中
	 * @param sql	sql语句
	 * @param clazz	封装数据的javabean类的Class对象
	 * @param params	sql参数
	 * @return	查询到的结果 (该信息段所在类的对象)
	 */
	public List queryRows(String sql, Class clazz, Object[] params);
	
	
	/**
	 * 	查询打印多行记录，并将每行记录封装到clazz指定类的对象中
	 * 	并会根据查询结果生成一个SelectResult类 
	 * @param sql	sql语句
	 * @param params	sql参数
	 */
	public List queryRows(String sql, Object[] params);
	
	
	/**
	 * 	查询返回并打印一行记录，并将该行记录封装到clazz指定类的对象中
	 * @param sql	sql语句
	 * @param clazz	封装数据的javabean类的Class对象
	 * @param params	sql参数
	 * @return	查询到的结果 (该信息段所在类的对象)
	 */
	public Object queryUniqueRow(String sql, Class clazz, Object[] params);
	
	/**
	 * 	查询并打印一行记录
	 * 	并会根据查询结果生成一个SelectResult类
	 * @param sql 	sql语句
	 * @param params	sql参数
	 */
	public void queryUniqueRow(String sql,Object[] params);
	
	/**
	 * 	查询返回并打印一个值（一行一列）
	 * @param sql	sql语句
	 * @param params	sql参数
	 * @return	查询到的结果 (该信息段所在类的对象)
	 */
	public Object queryValue(String sql, Object[] params);
	
	
	/**
	 * 	查询返回并打印一个数字（一行一列）
	 * @param sql	sql语句
	 * @param params	sql参数
	 * @return	查询到的结果  (该信息段所在类的对象)
	 */
	public Number queryNumber(String sql, Object[] params);
	
	/**
	 * 	通过主键查询某行信息
	 * @param clazz
	 * @param id
	 * @return	该信息段所在类的对象
	 */
	public Object queryById(Class clazz, Object id);
	
}
