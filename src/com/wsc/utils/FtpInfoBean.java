package com.wsc.utils;
/**
 * @author:WSC
 * @createTime:2013年11月15日下午4:14:30
 * @version:1.0
 * @Description	: 用于存放ftp服务器ip地址、端口、登陆用户名和密码的javaBean
 */
public class FtpInfoBean {
	private String ip;
	private int port;
	private String user;
	private String password;
	private String encode;
	private String localFilePath;
	private final String remoteFilePath = "/device";
	
	public FtpInfoBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FtpInfoBean(String ip, int port, String user, String password,
			String encode) {
		super();
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
		this.encode = encode;
	}

	public FtpInfoBean(String ip, int port, String user, String password,
			String encode, String localFilePath) {
		super();
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
		this.encode = encode;
		this.localFilePath = localFilePath;
	}

	public String getRemoteFilePath() {
		return remoteFilePath;
	}

	public String getLocalFilePath() {
		return localFilePath;
	}

	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
	
}
