package com.lxy.sorm.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 	��װ����JDBC����
 * @author ������
 *
 */
public class JDBCUtils {
	/**
	 * 	��sql���ò���
	 * @param ps Ԥ����sql������
	 * @param params ����
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
