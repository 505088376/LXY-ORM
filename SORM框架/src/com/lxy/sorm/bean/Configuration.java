package com.lxy.sorm.bean;
/**
 * 	����������Ϣ
 * @author ������
 *
 */
public class Configuration {
	/**
	 * 	������
	 */
	private String driver;
	/**
	 *	jdbc��url
	 */
	private String url;
	/**
	 * 	���ݿ��û���
	 */
	private String user;
	/**
	 * 	���ݿ�����
	 */
	private String pwd;
	/**
	 * 	����ʹ�õ����ݿ�
	 */
	private String usingDB;
	/**
	 * 	��ĿԴ��·��
	 */
	private String srcPath;
	/**
	 * 	ɨ������java���(po����˼�ǣ�Persistence Object �־û�����)
	 */
	private String poPackage;
	/**
	 * 	��ѯ���ɵ����ļ��Լ������ݿ������б��ֶε�������Ϣ���
	 */
	private String voPackage;
	/**
	 * 	���ݿ���
	 */
	private String queryClass;
	/**
	 * 	���ӳ���Сֵ
	 */
	private int poolSizeMin;
	/**
	 * 	���ӳ����ֵ
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
