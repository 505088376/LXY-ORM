package com.lxy.sorm.core;
/**
 * 	����java�������ͺ����ݿ��������ͻ���ת��
 * @author ������
 *
 */
public interface TypeConvertor {
	/**
	 * 	�����ݿ���������ת��Ϊjava����������
	 * @param columnTyepe ���ݿ��ֶε���������
	 * @return	java����������
	 */
	public String databaseTypeToJavaType(String columnType);
	
	
	/**
	 * 	��java��������ת��Ϊ���ݿ���������
	 * @param javaDataType java��������
	 * @return	���ݿ���������
	 */
	public String javaTyepToDatabase(String javaDataType);
}
