package com.lxy.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lxy.sorm.core.DBManager;

/**
 * 	���ӳ���
 * @author ������
 *
 */
public class DBConnPool {
	/**
	 * 	���ӳض���
	 */
	private List<Connection> pool;
	/**
	 * 	��С������
	 */
	private static final int POOL_SIZE_MIN = DBManager.getConf().getPoolSizeMin();
	/**
	 * 	���������
	 */
	private static final int POOL_SIZE_MAX = DBManager.getConf().getPoolSizeMax();
	
	public DBConnPool() {
		initPool();
	}
	
	/**
	 * 	��ʼ�����ӳ�
	 */
	public void initPool() {
		if(pool==null) {
			pool = new ArrayList<>();
		}else{
			while(pool.size()<DBConnPool.POOL_SIZE_MIN) {
				pool.add(DBManager.createConnection());
			}
		}
	}
	
	/**
	 * 	�����ӳ�ȡ�����ӣ������һ��ȡ
	 */
	public synchronized Connection getConnetion() {
		int lastIndex = pool.size()-1;
		if(lastIndex<0) {
			initPool();
			lastIndex = pool.size()-1;
		}
		Connection conn = pool.get(lastIndex);
		pool.remove(lastIndex);
		return conn;
	}
	
	/**
	 * 	�ر�����
	 */
	public synchronized void closeConnection(Connection conn) {
		if(pool.size()>DBConnPool.POOL_SIZE_MAX) {
			try {
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		pool.add(conn);
	}
}
