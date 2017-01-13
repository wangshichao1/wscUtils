package com.wsc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * ���ò�������
 * @author WSC
 */
public class ConfigParam {
	//�̳߳�����߳���
    public static int maxPoolSiz = 20;
    //һ���߳�һ�ζ�ȡ������
    public static int oneThreadNum = 5000;
    //ʧ�����Դ���
    public static int failRepeatNum = 3;
    //�����ļ�Ŀ¼
    public static String LocalFilePath;
    //���ֽӿ�����
    public static String JF_INF_NAME;
    //���ֽӿں�׺��
    public static String JF_INF_SUFFIX;
    //���ֽӿ��ļ�check�ļ���׺��
    public static String JF_CHECK_INF_SUFFIX;
    //�ӿ��ļ������·�
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