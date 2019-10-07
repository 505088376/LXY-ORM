package com.lxy.sorm.bean;
/**
 * 	管理配置信息
 * @author 李晓勇
 *
 */
public class Configuration {
	/**
	 * 	驱动类
	 */
	private String driver;
	/**
	 *	jdbc的url
	 */
	private String url;
	/**
	 * 	数据库用户名
	 */
	private String user;
	/**
	 * 	数据库密码
	 */
	private String pwd;
	/**
	 * 	正在使用的数据库
	 */
	private String usingDB;
	/**
	 * 	项目源码路径
	 */
	private String srcPath;
	/**
	 * 	扫描生成java类包(po的意思是：Persistence Object 持久化对象)
	 */
	private String poPackage;
	/**
	 * 	查询生成的类文件以及该数据库下所有表字段的整合信息类包
	 */
	private String voPackage;
	/**
	 * 	数据库类
	 */
	private String queryClass;
	/**
	 * 	连接池最小值
	 */
	private int poolSizeMin;
	/**
	 * 	连接池最大值
	 */
	private int poolSizeMax;
	
	public Configuration() {
		super();
	}
	public Configuration(String driver, String url, String user, String pwd, String usingDB, String srcPath,
			String poPackage,String queryClass,int poolSizeMin, int poolSizeMax,String voPackage) {
		super();
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.usingDB = usingDB;
		this.srcPath = srcPath;
		this.poPackage = poPackage;
		this.queryClass = queryClass;
		this.poolSizeMin = poolSizeMin;
		this.poolSizeMax = poolSizeMax;
		this.voPackage = voPackage;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public String getVoPackage() {
		return voPackage;
	}
	public void setVoPackage(String voPackage) {
		this.voPackage = voPackage;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUsingDB() {
		return usingDB;
	}
	public void setUsingDB(String usingDB) {
		this.usingDB = usingDB;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public String getPoPackage() {
		return poPackage;
	}
	public void setPoPackage(String poPackage) {
		this.poPackage = poPackage;
	}
	public String getQueryClass() {
		return queryClass;
	}
	public void setQueryClass(String queryClass) {
		this.queryClass = queryClass;
	}
	public int getPoolSizeMin() {
		return poolSizeMin;
	}
	public void setPoolSizeMin(int poolSizeMin) {
		this.poolSizeMin = poolSizeMin;
	}
	public int getPoolSizeMax() {
		return poolSizeMax;
	}
	public void setPoolSizeMax(int poolSizeMax) {
		this.poolSizeMax = poolSizeMax;
	}
	
}
