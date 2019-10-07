package com.lxy.po;

import java.util.*;
import java.sql.*;
public class Jobs {

	private Integer max_salary;

	private String job_id;

	private Integer min_salary;

	private String job_title;

	public Integer getMax_salary(){
		return max_salary;
	}

	public String getJob_id(){
		return job_id;
	}

	public Integer getMin_salary(){
		return min_salary;
	}

	public String getJob_title(){
		return job_title;
	}

	public void setMax_salary(Integer max_salary){
		this.max_salary = max_salary;
	}

	public void setJob_id(String job_id){
		this.job_id = job_id;
	}

	public void setMin_salary(Integer min_salary){
		this.min_salary = min_salary;
	}

	public void setJob_title(String job_title){
		this.job_title = job_title;
	}

}
