package com.lxy.po;

import java.util.*;
import java.sql.*;
public class Locations {

	private String street_address;

	private String city;

	private String state_province;

	private String postal_code;

	private Integer location_id;

	private String country_id;

	public String getStreet_address(){
		return street_address;
	}

	public String getCity(){
		return city;
	}

	public String getState_province(){
		return state_province;
	}

	public String getPostal_code(){
		return postal_code;
	}

	public Integer getLocation_id(){
		return location_id;
	}

	public String getCountry_id(){
		return country_id;
	}

	public void setStreet_address(String street_address){
		this.street_address = street_address;
	}

	public void setCity(String city){
		this.city = city;
	}

	public void setState_province(String state_province){
		this.state_province = state_province;
	}

	public void setPostal_code(String postal_code){
		this.postal_code = postal_code;
	}

	public void setLocation_id(Integer location_id){
		this.location_id = location_id;
	}

	public void setCountry_id(String country_id){
		this.country_id = country_id;
	}

}
