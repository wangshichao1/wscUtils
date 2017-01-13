/*$$Id*/
/*
 *@(#)ReadEnv.java    2010-4-12
 *
 *Copyright (c) 2010-2012 Linkage.
 *All Rights Reserved.
 *
 *This software is the confidential and proprietary information of Linkage. 
 *("Confidential Information").  You shall not disclose such Confidential 
 *Information and shall use it only in accordance with the terms of the 
 *license agreement you entered into with Linkage. 
 */

package com.wsc.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @title: 参数文件读取
 * @description:
 * 
 * @author: yef
 */
public final class ReadEnv {
	private static Logger log = Logger.getLogger(ReadEnv.class);

	/**
	 * 读取指定配置文件<code>filePath</code>中指定<code>key</code>的配置值
	 * 
	 * @param filePath
	 *            配置文件路径
	 * @param key
	 * @return
	 */
	public final static String readValue(String filePath, String key) {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			log.error("paramdeploy file read error", e);
			return null;
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
	
	/**
	 * 读取指定配置文件<code>filePath</code>中指定<code>key</code>的配置值
	 * 
	 * @param filePath
	 *            配置文件路径
	 * @param key
	 * @return
	 */
	public final static String readValue(String charset,String filePath, String key) {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(filePath);
			BufferedReader bf = new BufferedReader(new  InputStreamReader(in, charset));
			props.load(bf);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			log.error("paramdeploy file read error", e);
			return null;
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

	/**
	 * 读取配置文件指定键值的值
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 */
	public final static Integer readIntValue(String filePath, String key) {
		return Integer.parseInt(readValue(filePath, key));
	}
	
	/**
	 * 将键值对写入properties文件
	 * 
	 * @param filePath
	 *            配置文件路径
	 * @param dataMap
	 * @return
	 */
	public final static boolean writeValue(String filePath, Map<String, String> dataMap,String comments) {
		Properties props = new Properties();
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(
					filePath));
			Set<String> keySet = dataMap.keySet();
			Iterator<String> iterator = keySet.iterator();
			while(iterator.hasNext())
			{
				String key = iterator.next();
				String value = dataMap.get(key);
				props.setProperty(key, value);
			}
			props.store(out, comments);
			return true;
		} catch (Exception e) {
			log.error("paramdeploy file read error", e);
			return false;
		}
		finally{
			if(out != null)
			{
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
