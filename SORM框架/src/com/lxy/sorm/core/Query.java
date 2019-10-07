package com.lxy.sorm.core;

import java.util.List;

/**
 * 	Query�ӿڣ������ѯ�������ṩ����ĺ����ࣩ
 * 		���ĺ��Ĳ����������
 * 		��ʼʱ�������Ե�ֵ��Ϊnull ��ɾ�������ʱ��  �ֶ����������Ե�ֵ 
 * 		��Щ������Ŀ�ľ��ǽ���Щ�����õ������Ե�ֵ  ת��Ϊsql���ȥ���ݿ���ִ��
 * @author ������
 *
 */
public interface Query {
	/**
	 * 	ֱ��ִ��һ��DML���
	 * @param sql	sql���
	 * @param params	����
	 * @return	ִ��sql����Ӱ���¼������
	 */
	public int executeDML(String sql, Object[] params);
	
	
	/**
	 * 	������洢�����ݿ���
	 * @param obj	Ҫ�洢�Ķ���
	 */
	public void insert(Object obj);
	
	
	/**
	 * 	ɾ��clazz��ʾ���Ӧ�ı��м�¼��ָ������ֵid�ļ�¼�� 	
	 * @param clazz	�����Ӧ�����Class����
	 * @param priKeyValue	����ֵ
	 */
	public void delete(Class clazz, Object priKeyValue);  // delete from User where id=?
	
	
	/**
	 * 	ɾ�����������ݿ��ж�Ӧ�ļ�¼�������������Ӧ���������������ֵ��Ӧ����¼��
	 * @param obj �����Ӧ���������
	 */
	public void delete(Object obj);
	
	
	/**
	 * 	���¶����Ӧ�ļ�¼������ֻ����ָ�����ֶ�ֵ
	 * @param obj	��Ҫ���µĶ���
	 * @param fieldNames	���µ������б�
	 * @return	ִ��sql����Ӱ���¼������
	 */
	public int update(Object obj, String[] fieldNames); // update User set uname=?,pwd=?
	
	
	/**
	 * 	��ѯ���ض��м�¼������ÿ�м�¼��װ��clazzָ����Ķ�����
	 * @param sql	sql���
	 * @param clazz	��װ���ݵ�javabean���Class����
	 * @param params	sql����
	 * @return	��ѯ���Ľ�� (����Ϣ��������Ķ���)
	 */
	public List queryRows(String sql, Class clazz, Object[] params);
	
	
	/**
	 * 	��ѯ��ӡ���м�¼������ÿ�м�¼��װ��clazzָ����Ķ�����
	 * 	������ݲ�ѯ�������һ��SelectResult�� 
	 * @param sql	sql���
	 * @param params	sql����
	 */
	public List queryRows(String sql, Object[] params);
	
	
	/**
	 * 	��ѯ���ز���ӡһ�м�¼���������м�¼��װ��clazzָ����Ķ�����
	 * @param sql	sql���
	 * @param clazz	��װ���ݵ�javabean���Class����
	 * @param params	sql����
	 * @return	��ѯ���Ľ�� (����Ϣ��������Ķ���)
	 */
	public Object queryUniqueRow(String sql, Class clazz, Object[] params);
	
	/**
	 * 	��ѯ����ӡһ�м�¼
	 * 	������ݲ�ѯ�������һ��SelectResult��
	 * @param sql 	sql���
	 * @param params	sql����
	 */
	public void queryUniqueRow(String sql,Object[] params);
	
	/**
	 * 	��ѯ���ز���ӡһ��ֵ��һ��һ�У�
	 * @param sql	sql���
	 * @param params	sql����
	 * @return	��ѯ���Ľ�� (����Ϣ��������Ķ���)
	 */
	public Object queryValue(String sql, Object[] params);
	
	
	/**
	 * 	��ѯ���ز���ӡһ�����֣�һ��һ�У�
	 * @param sql	sql���
	 * @param params	sql����
	 * @return	��ѯ���Ľ��  (����Ϣ��������Ķ���)
	 */
	public Number queryNumber(String sql, Object[] params);
	
	/**
	 * 	ͨ��������ѯĳ����Ϣ
	 * @param clazz
	 * @param id
	 * @return	����Ϣ��������Ķ���
	 */
	public Object queryById(Class clazz, Object id);
	
}
