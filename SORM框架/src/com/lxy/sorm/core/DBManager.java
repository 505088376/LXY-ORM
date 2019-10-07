package com.lxy.sorm.core;
/**
 * 	根据配置信息，维持连接对象的管理(增加连接池功能)
 * @author 李晓勇
 *
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.lxy.pool.DBConnPool;
import com.lxy.sorm.bean.Configuration;

public class DBManager {
	private static Configuration conf;
	private static DBConnPool pool;
	private 
	static Properties pros = null; // 	可以帮助读取和处理资源文件中信息
	static { //	在加载JDBCUtile类的时候调用一次
		pros = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream("db.properties");
			pros.load(is);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally {
			try {
				if(is!=null) is.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		conf = new Configuration();
		conf.setDriver(pros.getProperty("driver"));
		conf.setUrl(pros.getProperty("url"));
		conf.setUser(pros.getProperty("user"));
		conf.setPwd(pros.getProperty("pwd"));
		conf.setUsingDB(pros.getProperty("usingDB"));
		conf.setSrcPath(pros.getProperty("srcPath"));
		conf.setPoPackage(pros.getProperty("poPackage"));
		conf.setVoPackage(pros.getProperty("voPackage"));
		conf.setQueryClass(pros.getProperty("queryClass"));
		conf.setPoolSizeMin(Integer.parseInt(pros.getProperty("poolSizeMin")));
		conf.setPoolSizeMax(Integer.parseInt(pros.getProperty("poolSizeMax")));
	}
	
	public static Connection getConnection() {
		if(pool==null) {
			pool = new DBConnPool();
		}
		return pool.getConnetion();
	}
	
	public static Connection createConnection() {
		try {
			Class.forName(conf.getDriver());
			return DriverManager.getConnection(conf.getUrl(),conf.getUser(),conf.getPwd());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void close(ResultSet rs,PreparedStatement ps,Connection con) {
		try {
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			if(ps!=null) ps.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			if(con!=null) con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement ps,Connection con) {
		try {
			if(ps!=null) ps.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			if(con!=null) con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public static Configuration getConf() {
		return conf;
	}
}
