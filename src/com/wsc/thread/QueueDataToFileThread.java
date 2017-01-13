package com.wsc.thread;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * �������е�����д�뵽�ļ���
 * @author WSC
 */
public class QueueDataToFileThread implements Callable<Map<String,Object>> {

	//���ݶ���
	private ConcurrentLinkedQueue<String> dataQueue;
	//�ļ�·��
	private String filePath;
	//�����߳����н�����ʶ
	private boolean isRunEnd = false;
	
	public QueueDataToFileThread(ConcurrentLinkedQueue<String> dataQueue,
			String filePath) {
		super();
		this.dataQueue = dataQueue;
		this.filePath = filePath;
	}


	/**
	 * ҵ���߼�
	 */
	public Map<String,Object> call() throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = new HashMap<String, Object>();
		int rowCount = 0;
		boolean isSuccess = true;
		/**
		 * ��ȡ�ļ���
		 */
		OutputStream out = null;
		BufferedWriter writer = null;
		out = new FileOutputStream(filePath,false);
		writer = new BufferedWriter(new OutputStreamWriter(out));
		while(true)
		{
			if(!dataQueue.isEmpty())
			{
				String line = dataQueue.poll();
				if(line == null)
				{
					continue;
				}
				/**
				 * ����д���ļ�
				 */
				try {
					writer.write(line);
					writer.newLine();
					writer.flush();
					rowCount++;//��¼����
				} catch (Exception e) {
					// TODO: handle exception
					isSuccess = false;
					break;
				}
			}else if(dataQueue.isEmpty() && isRunEnd)
			{
				/**
				 * ������ݶ���Ϊ�գ��������Ի�ȡ�����̶߳���ִ����ɣ����˳�ѭ��
				 */
				break;
			}
		}
		//�ر���
		if(writer != null)
		{
			writer.close();
		}
		if(out != null)
		{
			out.close();
		}
		
		resMap.put("rowCount", rowCount);
		resMap.put("isSuccess", isSuccess);
		return resMap;
	}

	public ConcurrentLinkedQueue<String> getDataQueue() {
		return dataQueue;
	}

	public void setDataQueue(ConcurrentLinkedQueue<String> dataQueue) {
		this.dataQueue = dataQueue;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public boolean isRunEnd() {
		return isRunEnd;
	}

	public void setRunEnd(boolean isRunEnd) {
		this.isRunEnd = isRunEnd;
	}

}
