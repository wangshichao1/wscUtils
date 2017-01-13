package com.wsc.utils.sftp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SftpInfo {
    public static String SFTP_REQ_HOST = "10.173.246.21";
    public static int SFTP_REQ_PORT = 22;
    public static String SFTP_REQ_USERNAME = "uipapp";
    public static String SFTP_REQ_PASSWORD = "uipapp789";
    public static final int SFTP_DEFAULT_PORT = 22;
    //远程主机目录
    public static String SFTP_REQ_LOC = "location";
    
    public static void init(String fileName) {
    	Properties props = new Properties();
		InputStream in = null;
		try {
			in = SftpInfo.class.getClassLoader().getResourceAsStream(fileName);
			props.load(in);
			SFTP_REQ_HOST = props.getProperty("SFTP_REQ_HOST");
	    	SFTP_REQ_PORT = Integer.parseInt(props.getProperty("SFTP_REQ_PORT"));
	    	SFTP_REQ_USERNAME = props.getProperty("SFTP_REQ_USERNAME");
	    	SFTP_REQ_PASSWORD = props.getProperty("SFTP_REQ_PASSWORD");
	    	SFTP_REQ_LOC = props.getProperty("SFTP_REQ_LOC");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in != null)
			{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void reInit(String path) {
		init(path);
	}
}