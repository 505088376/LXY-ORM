package com.lxy.sorm.bean;

import java.util.Map;

/**
 * 	封装一张表的信息
 * @author 李晓勇
 *
 */
public class TableInfo {
	/**
	 * 	表名
	 */
	private String tname;
	
	/**
	 * 	所有字段信息
	 */
	private Map<String,ColumnInfo> columns;
	
	/**
	 * 	唯一主键（目前我们只能处理表中有且只有一个主键的情况）
	 */
	private ColumnInfo onlyPriKey;

	public TableInfo() {
		super();
	}

	public TableInfo(String tname, Map<String, ColumnInfo> columns, ColumnInfo onlyPriKey) {
		super();
		this.tname = tname;
		this.columns = columns;
		this.onlyPriKey = onlyPriKey;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Map<String, ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, ColumnInfo> columns) {
		this.columns = columns;
	}

	public ColumnInfo getOnlyPriKey() {
		return onlyPriKey;
	}

	public void setOnlyPriKey(ColumnInfo onlyPriKey) {
		this.onlyPriKey = onlyPriKey;
	}
}
