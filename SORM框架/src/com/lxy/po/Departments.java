package com.lxy.po;

import java.util.*;
import java.sql.*;
public class Departments {

	private Integer department_id;

	private Integer manager_id;

	private String department_name;

	private Integer location_id;

	public Integer getDepartment_id(){
		return department_id;
	}

	public Integer getManager_id(){
		return manager_id;
	}

	public String getDepartment_name(){
		return department_name;
	}

	public Integer getLocation_id(){
		return location_id;
	}

	public void setDepartment_id(Integer department_id){
		this.department_id = department_id;
	}

	public void setManager_id(Integer manager_id){
		this.manager_id = manager_id;
	}

	public void setDepartment_name(String department_name){
		this.department_name = department_name;
	}

	public void setLocation_id(Integer location_id){
		this.location_id = location_id;
	}

}
