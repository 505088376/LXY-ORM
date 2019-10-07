package com.lxy.sorm.bean;
/**
 * 	封装表中一个字段信息
 * @author 李晓勇
 *
 */
public class ColumnInfo {
	/**
	 * 	字段名称
	 */
	private String name;
	
	/**
	 * 	字段的数据类型
	 */
	private String dateType;
	
	/**
	 * 	字段键的类型（0：普通键，1：主键，2：外键）
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
