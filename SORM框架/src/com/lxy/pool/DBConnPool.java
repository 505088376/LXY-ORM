package com.lxy.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lxy.sorm.core.DBManager;

/**
 * 	连接池类
 * @author 李晓勇
 *
 */
public class DBConnPool {
	/**
	 * 	连接池对象
	 */
	private List<Connection> pool;
	/**
	 * 	最小连接数
	 */
	private static final int POOL_SIZE_MIN = DBManager.getConf().getPoolSizeMin();
	/**
	 * 	最大连接数
	 */
	private static final int POOL_SIZE_MAX = DBManager.getConf().getPoolSizeMax();
	
	public DBConnPool() {
		initPool();
	}
	
	/**
	 * 	初始化连接池
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
	 * 	从连接池取出连接，从最后一个取
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
	 * 	关闭连接
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
