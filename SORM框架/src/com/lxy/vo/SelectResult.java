package com.lxy.vo;

import java.util.*;
import java.sql.*;
public class SelectResult {

	private Double commission_pct;
	private Integer department_id;
	private String department_name;
	private String last_name;
	private Double salary;
	private java.sql.Timestamp hiredate;
	private Integer location_id;
	private Integer manager_id;
	private String job_id;
	private Integer employee_id;
	private String phone_number;
	private String first_name;
	private String email;

	public Double getCommission_pct(){
		return commission_pct;
	}
	public Integer getDepartment_id(){
		return department_id;
	}
	public String getDepartment_name(){
		return department_name;
	}
	public String getLast_name(){
		return last_name;
	}
	public Double getSalary(){
		return salary;
	}
	public java.sql.Timestamp getHiredate(){
		return hiredate;
	}
	public Integer getLocation_id(){
		return location_id;
	}
	public Integer getManager_id(){
		return manager_id;
	}
	public String getJob_id(){
		return job_id;
	}
	public Integer getEmployee_id(){
		return employee_id;
	}
	public String getPhone_number(){
		return phone_number;
	}
	public String getFirst_name(){
		return first_name;
	}
	public String getEmail(){
		return email;
	}

	public void setCommission_pct(Double commission_pct){
		this.commission_pct = commission_pct;
	}
	public void setDepartment_id(Integer department_id){
		this.department_id = department_id;
	}
	public void setDepartment_name(String department_name){
		this.department_name = department_name;
	}
	public void setLast_name(String last_name){
		this.last_name = last_name;
	}
	public void setSalary(Double salary){
		this.salary = salary;
	}
	public void setHiredate(java.sql.Timestamp hiredate){
		this.hiredate = hiredate;
	}
	public void setLocation_id(Integer location_id){
		this.location_id = location_id;
	}
	public void setManager_id(Integer manager_id){
		this.manager_id = manager_id;
	}
	public void setJob_id(String job_id){
		this.job_id = job_id;
	}
	public void setEmployee_id(Integer employee_id){
		this.employee_id = employee_id;
	}
	public void setPhone_number(String phone_number){
		this.phone_number = phone_number;
	}
	public void setFirst_name(String first_name){
		this.first_name = first_name;
	}
	public void setEmail(String email){
		this.email = email;
	}

}
