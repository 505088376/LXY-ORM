package com.lxy.sorm.core;
/**
 * 	查询工厂  负责产生一个查询类
 * @author 李晓勇
 *
 */
public class QueryFactory {
	
	private static QueryFactory instance = new QueryFactory();
	private static Class clazz;
	static {
		try {
			clazz = Class.forName(DBManager.getConf().getQueryClass());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private QueryFactory() {
	}
	
	public static Query createQuery() {
		try {
			return (Query) clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
