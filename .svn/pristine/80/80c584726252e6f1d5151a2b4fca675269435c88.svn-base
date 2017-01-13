package com.wsc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 配置参数对象
 * @author WSC
 */
public class ConfigParam {
	//线程池最大线程数
    public static int maxPoolSiz = 20;
    //一个线程一次读取的数量
    public static int oneThreadNum = 5000;
    //失败重试次数
    public static int failRepeatNum = 3;
    //生成文件目录
    public static String LocalFilePath;
    //经分接口名称
    public static String JF_INF_NAME;
    //经分接口后缀名
    public static String JF_INF_SUFFIX;
    //经分接口文件check文件后缀名
    public static String JF_CHECK_INF_SUFFIX;
    //接口文件保存月份
    public static int data_save_month;
    
    
    public static void init(String fileName) {
    	Properties props = new Properties();
		InputStream in = null;
		try {
			in = ConfigParam.class.getClassLoader().getResourceAsStream(fileName);
			props.load(in);
			maxPoolSiz = Integer.parseInt(props.getProperty("maxPoolSiz"));
			oneThreadNum = Integer.parseInt(props.getProperty("oneThreadNum"));
			failRepeatNum = Integer.parseInt(props.getProperty("failRepeatNum"));
			LocalFilePath = props.getProperty("LocalFilePath");
			JF_INF_NAME = props.getProperty("JF_INF_NAME");
			JF_INF_SUFFIX = props.getProperty("JF_INF_SUFFIX");
			JF_CHECK_INF_SUFFIX = props.getProperty("JF_CHECK_INF_SUFFIX");
			data_save_month = Integer.parseInt(props.getProperty("DATA_SAVE_MONTH"));
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