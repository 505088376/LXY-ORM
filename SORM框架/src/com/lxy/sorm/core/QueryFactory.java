package com.lxy.sorm.core;
/**
 * 	��ѯ����  �������һ����ѯ��
 * @author ������
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
