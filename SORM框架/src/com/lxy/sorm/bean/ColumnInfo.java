package com.lxy.sorm.bean;
/**
 * 	��װ����һ���ֶ���Ϣ
 * @author ������
 *
 */
public class ColumnInfo {
	/**
	 * 	�ֶ�����
	 */
	private String name;
	
	/**
	 * 	�ֶε���������
	 */
	private String dateType;
	
	/**
	 * 	�ֶμ������ͣ�0����ͨ����1��������2�������
	 */
	private int keyType;

	public ColumnInfo() {
		super();
	}

	public ColumnInfo(String name, String dateType, int keyType) {
		super();
		this.name = name;
		this.dateType = dateType;
		this.keyType = keyType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public int getKeyType() {
		return keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}
}
