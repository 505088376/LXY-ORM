package com.lxy.po;

import java.util.*;
import java.sql.*;
public class Employees {

	private Double commission_pct;

	private Integer manager_id;

	private Integer department_id;

	private String job_id;

	private Integer employee_id;

	private String last_name;

	private String phone_number;

	private Double salary;

	private String first_name;

	private java.sql.Timestamp hiredate;

	private String email;

	public Double getCommission_pct(){
		return commission_pct;
	}

	public Integer getManager_id(){
		return manager_id;
	}

	public Integer getDepartment_id(){
		return department_id;
	}

	public String getJob_id(){
		return job_id;
	}

	public Integer getEmployee_id(){
		return employee_id;
	}

	public String getLast_name(){
		return last_name;
	}

	public String getPhone_number(){
		return phone_number;
	}

	public Double getSalary(){
		return salary;
	}

	public String getFirst_name(){
		return first_name;
	}

	public java.sql.Timestamp getHiredate(){
		return hiredate;
	}

	public String getEmail(){
		return email;
	}

	public void setCommission_pct(Double commission_pct){
		this.commission_pct = commission_pct;
	}

	public void setManager_id(Integer manager_id){
		this.manager_id = manager_id;
	}

	public void setDepartment_id(Integer department_id){
		this.department_id = department_id;
	}

	public void setJob_id(String job_id){
		this.job_id = job_id;
	}

	public void setEmployee_id(Integer employee_id){
		this.employee_id = employee_id;
	}

	public void setLast_name(String last_name){
		this.last_name = last_name;
	}

	public void setPhone_number(String phone_number){
		this.phone_number = phone_number;
	}

	public void setSalary(Double salary){
		this.salary = salary;
	}

	public void setFirst_name(String first_name){
		this.first_name = first_name;
	}

	public void setHiredate(java.sql.Timestamp hiredate){
		this.hiredate = hiredate;
	}

	public void setEmail(String email){
		this.email = email;
	}

}
