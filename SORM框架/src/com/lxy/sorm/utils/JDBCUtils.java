package com.lxy.sorm.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 	封装常用JDBC操作
 * @author 李晓勇
 *
 */
public class JDBCUtils {
	/**
	 * 	给sql设置参数
	 * @param ps 预编译sql语句对象
	 * @param params 参数
	 */
	public static void handleParams(PreparedStatement ps, Object[] params) {
		if(params!=null) {
			for(int i=0;i<params.length;i++) {
				try {
					ps.setObject(i+1, params[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
