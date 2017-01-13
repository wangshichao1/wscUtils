package com.wsc.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * �ļ�������
 * 
 * @author WSC
 */
public class FileUtil {
	/**
	 * ��ȡTXT�����ļ�
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<List<Object>> readTxt(String filePath) {
		List<List<Object>> dataList = new ArrayList<List<Object>>();
		InputStream is = null;
		BufferedReader reader = null;
		try {
			File file = new File(filePath);
			if (!file.exists())
				return null;
			is = new FileInputStream(filePath);
			String line; // ��������ÿ�ж�ȡ������
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			line = reader.readLine(); // ��ȡ��һ��
			while (line != null) { // ��� line Ϊ��˵��������
				List<Object> list = new ArrayList<Object>();
				list.add(line);
				dataList.add(list); // ��������������ӵ� buffer ��
				line = reader.readLine(); // ��ȡ��һ��
			}
			reader.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(reader != null)
			{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//ȥ���ظ�����
		@SuppressWarnings("rawtypes")
		List<List<Object>> dataList1 = new ArrayList<List<Object>>(new HashSet(dataList));
		return dataList1;
	}
	
	/**
	 * ��ȡTXT�����ļ�
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<List<Object>> readTxt(String filePath,String sendKey) {
		List<List<Object>> dataList = new ArrayList<List<Object>>();
		InputStream is = null;
		BufferedReader reader = null;
		try {
			File file = new File(filePath);
			if (!file.exists())
				return null;
			is = new FileInputStream(filePath);
			String line; // ��������ÿ�ж�ȡ������
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			line = reader.readLine(); // ��ȡ��һ��
			while (line != null) { // ��� line Ϊ��˵��������
				//��ȥ�ո�
				line = line.replaceAll(" ", "");
				if ("".equals(line.trim()) || !line.matches("[0-9]{11}")) {
					line = reader.readLine();
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(line);
				list.add(sendKey);
				dataList.add(list); // ��������������ӵ� buffer ��
				line = reader.readLine(); // ��ȡ��һ��
			}
			reader.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(reader != null)
			{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//ȥ���ظ�����
		@SuppressWarnings("rawtypes")
		List<List<Object>> dataList1 = new ArrayList<List<Object>>(new HashSet(dataList));
		return dataList1;
	}

	/**
	 * ��ȡTXT�����ļ�
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean readNumFromTxt(String filePath, List<List<Object>> numList) {
		InputStream is = null;
		BufferedReader reader = null;
		try {
			File file = new File(filePath);
			if (!file.exists())
				return false;
			//�ж��Ƿ�TXT��ʽ
			if(!file.getName().toUpperCase().endsWith(".TXT"))
			{
				numList = null;
				return false;
			}
			is = new FileInputStream(filePath);
			String line; // ��������ÿ�ж�ȡ������
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			line = reader.readLine(); // ��ȡ��һ��
			while (line != null) { // ��� line Ϊ��˵��������
				//��ȥ�ո�
				line = line.replaceAll(" ", "");
				if ("".equals(line.trim()) || !line.matches("[0-9]{11}")) {
					line = reader.readLine();
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(line);
				numList.add(list);
				line = reader.readLine(); // ��ȡ��һ��
			}
			reader.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			if(reader != null)
			{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//ȥ���ظ�����
		@SuppressWarnings("rawtypes")
		List<List<Object>> dataList = new ArrayList<List<Object>>(new HashSet(numList));
		numList = dataList;
		return true;
	}
	
	/**
	 * ��ȡTXT��������
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static Map<String,Object> countDataNum(String filePath) {
		List<List<Object>> dataList = new ArrayList<List<Object>>();
		Map<String,Object> map = new HashMap<String, Object>();
		InputStream is = null;
		BufferedReader reader = null;
		try {
			File file = new File(filePath);
			if (!file.exists())
			{
				map.put("isValid", false);
				return map;
			}
			//�ж��Ƿ�TXT��ʽ
			if(!file.getName().toUpperCase().endsWith(".TXT"))
			{
				map.put("isValid", false);
				return map;
			}
			is = new FileInputStream(filePath);
			String line; // ��������ÿ�ж�ȡ������
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			line = reader.readLine(); // ��ȡ��һ��
			while (line != null) { // ��� line Ϊ��˵��������
				//��ȥ�ո�
				line = line.replaceAll(" ", "");
				if("".equals(line.trim()) || !line.matches("[0-9]{11}"))
				{
					line = reader.readLine(); 
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(line);
				dataList.add(list);
				line = reader.readLine(); // ��ȡ��һ��
				
			}
			reader.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			map.put("isValid", false);
			return map;
		}finally{
			if(reader != null)
			{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//ȥ���ظ�����
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<List<Object>> dataList1 = new ArrayList<List<Object>>(new HashSet(dataList));
		map.put("isValid", true);
		map.put("size", dataList1.size());
		dataList1 = null;
		return map;
	}

	/**
	 * ��ȡĳ��Ŀ¼�����е��ļ���Ϣ���ϣ�
	 * 
	 * @return
	 */
	public static List<Map<String, String>> getAllFile(String mulu) {
		File file = new File(mulu);
		if (!file.exists()) {
			return new ArrayList<Map<String, String>>();
		}
		File[] files = file.listFiles();
		List<Map<String, String>> allFileInfo = new ArrayList<Map<String, String>>();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			String fileSize = formetFileSize(getFileSize(files[i]));
			long modify = files[i].lastModified();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String lastModify = format.format(new Date(modify));
			Map<String, String> map = new HashMap<String, String>();
			map.put("fileName", fileName);
			map.put("fileSize", fileSize);
			map.put("lastModify", lastModify);
			allFileInfo.add(map);
		}
		return allFileInfo;
	}
	
	/**
	 * ��ȡĳ��Ŀ¼�����е��ļ���Ϣ���ϣ�
	 * 
	 * @return
	 */
	public static List<String> getAllFileName(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return new ArrayList<String>();
		}
		File[] files = file.listFiles();
		List<String> allFileInfo = new ArrayList<String>();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			allFileInfo.add(fileName);
		}
		return allFileInfo;
	}
	
	/**
	 * �ж�Ŀ¼���ļ��Ƿ��Ѿ�����
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static boolean isExists(String path,String fileName)
	{
		String filePath = path + File.separator + fileName;
		File file = new File(filePath);
		return file.exists();
	}
	/**
	 * ��ȡĳ��Ŀ¼�����е��ļ���Ϣ���ϣ�
	 * 
	 * @return
	 */
	public static Map<String, Object> getFileInfo(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String fileName = file.getName();
		map.put("fileName", fileName);
		map.put("fileSize", formetFileSize(getFileSize(file)));
		List<List<Object>> numList = new ArrayList<List<Object>>();
		boolean isValid = readNumFromTxt(path, numList);
		map.put("isValid", isValid);
		if(isValid)
		{
			map.put("dataSize", numList.size());
		}else{
			map.put("dataSize", 0);
		}
		return map;
	}

	/**
	 * ɾ���ļ����ۺϲ���( ����·��ɾ��ָ����Ŀ¼���ļ������۴������)
	 * 
	 * @param sPath
	 *            Ҫɾ����Ŀ¼���ļ�
	 * @return ɾ���ɹ����� true�����򷵻� false��
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// �ж�Ŀ¼���ļ��Ƿ����
		if (!file.exists()) { // �����ڷ��� false
			return flag;
		} else {
			// �ж��Ƿ�Ϊ�ļ�
			if (file.isFile()) { // Ϊ�ļ�ʱ����ɾ���ļ�����
				return deleteFile(file);
			} else { // ΪĿ¼ʱ����ɾ��Ŀ¼����
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * ɾ�������ļ�
	 * 
	 * @param sPath
	 *            ��ɾ���ļ����ļ���
	 * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
	 */
	public static boolean deleteFile(File file) {
		boolean flag = false;
		// ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
		if (file.isFile() && file.exists()) {
			flag = file.delete();
		}
		return flag;
	}
	
	public static boolean deleteFile(String filePath) {
		// TODO Auto-generated method stub
		File file = new File(filePath);
		boolean res = false;
		if(file != null)
		{
			res = deleteFile(file);
		}
		return res;
	}

	/**
	 * ɾ��Ŀ¼�µ��ļ�
	 * 
	 * @param sPath
	 *            ��ɾ��Ŀ¼���ļ�·��
	 * @return Ŀ¼ɾ���ɹ�����true�����򷵻�false
	 */
	public static boolean deleteDirectory(String sPath) {
		// ���sPath�����ļ��ָ�����β���Զ�����ļ��ָ���
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// ɾ���ļ����µ������ļ�(������Ŀ¼)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// ɾ�����ļ�
			if (files[i].isFile()) {
				flag = deleteFile(files[i]);
				if (!flag)
					break;
			} // ɾ����Ŀ¼
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		return flag;
	}

	/**
	 * �ƶ��ļ�
	 * 
	 * @param oldPath
	 * @param newPath
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static boolean moveFile(String oldPath, String newPath,
			String fileName){
		try {
			if (copyFile(oldPath, newPath, fileName)) {
				boolean result = deleteFile(new File(oldPath + File.separator + fileName));
				return result;
			} else {
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param oldPath
	 * @param newPath
	 * @param newFileName
	 * @return
	 * @throws IOException
	 */
	public static boolean copyFile(String oldPath, String newPath,
			String fileName) throws IOException {
		boolean flag = false;
		if (oldPath == null || newPath == null || newPath.equals("")
				|| oldPath.equals("")) {
			return flag;
		}
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int byteread = 0;
			File file = null;
			file = new File(newPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(oldPath + File.separator + fileName);
			if (file.exists()) { // �ļ�����ʱ
				inStream = new FileInputStream(file); // ����ԭ�ļ�
				fs = new FileOutputStream(newPath + File.separator + fileName);
				byte[] buffer = new byte[1024 * 8];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				flag = true;
			}
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fs != null) {
					fs.close();
				}
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return flag;
	}

	/**
	 * ��ȡ�ļ���С
	 * 
	 * @param filePath
	 * @return
	 */
	public static long getFileSize(File f) {// ȡ���ļ���С
		long s = 0;
		FileInputStream fis = null;
		try {
			if (f.exists()) {
				fis = new FileInputStream(f);
				s = fis.available();
			} else {
				return 0;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}finally{
			if(fis != null)
			{
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return s;
	}

	// �ݹ�
	public static long getAllFileSize(File f)// ȡ���ļ��д�С
	{
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	public static String formetFileSize(long size) {// ת���ļ���С
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (size < 1024) {
			fileSizeString = df.format((double) size) + "B";
		} else if (size < 1048576) {
			fileSizeString = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			fileSizeString = df.format((double) size / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) size / 1073741824) + "G";
		}
		return fileSizeString;
	}
	
	/**
	 * ��һ���ļ�׷�ӵ��ļ�ĩβ
	 * @param filePath
	 * @param line
	 */
	public static void appendLineToFile(String filePath,String line)
	{
		OutputStream out = null;
		BufferedWriter writer = null;
		try {
			File file = new File(filePath);
			if (!file.exists())
				return;
			out = new FileOutputStream(filePath,true);
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.newLine();
			writer.write(line);
			writer.flush();
			writer.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(writer != null)
			{
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
