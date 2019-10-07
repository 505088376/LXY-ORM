package com.lxy.sorm.bean;
/**
 * 	封装了java属性和get、set方法源码
 * @author 李晓勇
 *
 */
public class JavaFieldGetSet {
	/**
	 * 	属性源码信息   如：private String name;
	 */
	private String fieldInfo;
	/**
	 * 	get方法源码信息   如：public String getName(){}
	 */
	private String getInfo;
	/**
	 * 	set方法源码信息   如：public void setName(String name){this.name = name}
	 */
	private String setInfo;
	
	public JavaFieldGetSet() {
		super();
	}

	public JavaFieldGetSet(String fieldInfo, String getInfo, String setInfo) {
		super();
		this.fieldInfo = fieldInfo;
		this.getInfo = getInfo;
		this.setInfo = setInfo;
	}

	public String getFiledInfo() {
		return fieldInfo;
	}

	public void setFiledInfo(String filedInfo) {
		this.fieldInfo = filedInfo;
	}

	public String getGetInfo() {
		return getInfo;
	}

	public void setGetInfo(String getInfo) {
		this.getInfo = getInfo;
	}

	public String getSetInfo() {
		return setInfo;
	}

	public void setSetInfo(String setInfo) {
		this.setInfo = setInfo;
	}
	
	@Override
	public String toString() {
		System.out.println(fieldInfo);
		System.out.println(getInfo);
		System.out.println(setInfo);
		return "";
	}
}
