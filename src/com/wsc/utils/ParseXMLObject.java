/*$$Id*/
/*
 *@(#)ParseXMLObject.java    2010-4-13
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

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;

/**
 * @title: 
 * @description: 
 * 
 * @author: yef
 */
public class ParseXMLObject {

	private String classPath = "";
	
	private Logger log = Logger.getLogger(ParseXMLObject.class);

	public ParseXMLObject(String classPath) {
		this.classPath = classPath;
	}

	/**
	 * 解析XML对象为List
	 * 
	 * @param XMLFilePath
	 * @param pModel
	 * @param startNode
	 * @return
	 * @throws Exception
	 */
	public final List getListFromXML(String XMLFilePath, Class pModel,
			String startNode) throws Exception {
		InputStream is = new FileInputStream(XMLFilePath);
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.push(this);
		String tmpName = pModel.getName();
		if (startNode != null && !startNode.equals(""))// 没有绝对路径
			startNode += "/";
		startNode += tmpName.substring(tmpName.lastIndexOf(".") + 1, tmpName
				.length());
		this.setDigesterAttr(digester, startNode, pModel);
		this.parseList = new ArrayList();
		digester.addSetNext(startNode, "setParseList");
		digester.parse(is);
		return this.parseList;
	}

	private List parseList = null;

	public final void setParseList(Object o) {
		this.parseList.add(o);
	}

	/**
	 * 递归方法 根据对象设置digester 属性
	 */
	public final void setDigesterAttr(Digester digester, String startNode,
			Class pModel) {
		log.debug("digester.addObjectCreate(\"" + startNode + "\","
				+ pModel.getName() + ".class);");
		digester.addObjectCreate(startNode, pModel);
		log.debug("digester.addSetProperties(\"" + startNode + "\");");
		digester.addSetProperties(startNode);
		Field[] fields = pModel.getDeclaredFields();
		Field fiel = null;
		String name = null;
		Class type = null;
		for (int i = 0; i < fields.length; i++) {
			fiel = fields[i];
			name = fiel.getName();
			type = fiel.getType();
			if (this.isBaseType(type)) { // 基本类型
				log.debug("digester.addBeanPropertySetter(\"" + startNode
						+ "/" + name + "\");");
				digester.addBeanPropertySetter(startNode + "/" + name);
			} else if (this.isCollectionType(type)) { // Collection类型
				try {
					Class c = Class.forName(this.classPath + "."
							+ name.substring(0, name.length() - 1));
					String methodName = "add"
							+ name.substring(0, 1).toUpperCase()
							+ name.substring(1, name.length() - 1);
					// 判断方法是否存在,不存在就不需要递归了
					Class[] tmpCA = { c };
					Method methoded = pModel.getDeclaredMethod(methodName,
							tmpCA);
					if (methoded != null) {
						this.setDigesterAttr(digester, startNode + "/"
								+ name.substring(0, name.length() - 1), c); // 递归
						log.debug("digester.addSetNext(\"" + startNode
								+ "/" + name.substring(0, name.length() - 1)
								+ "\", \"" + methodName + "\");");
						digester.addSetNext(startNode + "/"
								+ name.substring(0, name.length() - 1),
								methodName);
					}
				} catch (ClassNotFoundException ex) { // 不需要解析的对象或是命名错误
					log.error("无法解析对象[" + name + "]", ex);
				} catch (SecurityException ex1) {
					log.error("对象方法安全问题" + name + ".add"
							+ name.substring(0, 1).toUpperCase()
							+ name.substring(1, name.length() - 1)
							+ "", ex1);
				} catch (NoSuchMethodException ex1) {
					log.error("无法解析对象方法" + name + ".add"
							+ name.substring(0, 1).toUpperCase()
							+ name.substring(1, name.length() - 1), ex1);
				}
			} else { // 其他类型
				// 规则:只解析指定的类路径下的对象,其他为不用解析的对象(此处可以全部解析)
				if (type.getName().startsWith(this.classPath)) {
					String tmpname = type.getName();
					tmpname = tmpname.substring(tmpname.lastIndexOf(".") + 1,
							tmpname.length());
					this.setDigesterAttr(digester, startNode + "/" + tmpname,
							type); // 递归
					String methodName = "set"
							+ name.substring(0, 1).toUpperCase()
							+ name.substring(1, name.length());
					log.debug("digester.addSetNext(\"" + startNode + "/"
							+ name + "\", \"" + methodName + "\");");
					digester.addSetNext(startNode + "/" + name, methodName);
				}
			}
		}
	}

	/**
	 * 判断Class类型是否为基本数据类型
	 * 
	 * @param type
	 * @return
	 */
	public final boolean isBaseType(Class type) {
		if (type == java.lang.String.class)
			return true;
		if (type == float.class)
			return true;
		if (type == double.class)
			return true;
		if (type == int.class)
			return true;
		if (type == boolean.class)
			return true;
		if (type == java.util.Date.class)
			return true;
		return false;
	}

	/**
	 * 判断Class类型是否为collection类型
	 * 
	 * @param type
	 * @return
	 */
	public final boolean isCollectionType(Class type) {
		if (type == java.util.Collection.class)
			return true;
		if (type == java.util.Vector.class)
			return true;
		if (type == java.util.List.class)
			return true;
		if (type == java.util.ArrayList.class)
			return true;
		if (type == java.util.HashMap.class)
			return true;
		return false;
	}
}
