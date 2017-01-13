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
 * 将队列中的数据写入到文件中
 * @author WSC
 */
public class QueueDataToFileThread implements Callable<Map<String,Object>> {

	//数据队列
	private ConcurrentLinkedQueue<String> dataQueue;
	//文件路径
	private String filePath;
	//批量线程运行结束标识
	private boolean isRunEnd = false;
	
	public QueueDataToFileThread(ConcurrentLinkedQueue<String> dataQueue,
			String filePath) {
		super();
		this.dataQueue = dataQueue;
		this.filePath = filePath;
	}


	/**
	 * 业务逻辑
	 */
	public Map<String,Object> call() throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = new HashMap<String, Object>();
		int rowCount = 0;
		boolean isSuccess = true;
		/**
		 * 获取文件流
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
				 * 数据写入文件
				 */
				try {
					writer.write(line);
					writer.newLine();
					writer.flush();
					rowCount++;//记录行数
				} catch (Exception e) {
					// TODO: handle exception
					isSuccess = false;
					break;
				}
			}else if(dataQueue.isEmpty() && isRunEnd)
			{
				/**
				 * 如果数据队列为空，并且所以获取数据线程都已执行完成，则退出循环
				 */
				break;
			}
		}
		//关闭流
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
