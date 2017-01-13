package com.wsc.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.wsc.service.VgopFeedBackService;
import com.wsc.utils.ConfigParam;
import com.wsc.vo.VgopSendNumFeedBack;
/**
 * 从数据库读取数据到队列
 * @author WSC
 *
 */
public class ReadDataToQueueThread implements Callable<Boolean> {
	private VgopFeedBackService feedBackService;
	private Logger log = Logger.getLogger(ReadDataToQueueThread.class);
	private VgopSendNumFeedBack sendNumFeedBack;
	//数据队列
	private ConcurrentLinkedQueue<String> dataQueue;
	
	public ReadDataToQueueThread(){
	}
	
	public ReadDataToQueueThread(VgopFeedBackService feedBackService,VgopSendNumFeedBack sendNumFeedBack,
			ConcurrentLinkedQueue<String> dataQueue) {
		super();
		this.feedBackService = feedBackService;
		this.sendNumFeedBack = sendNumFeedBack;
		this.dataQueue = dataQueue;
	}

	/**
	 * 业务逻辑
	 */
	public Boolean call() throws Exception {
		/**
		 * 1、获取数据对象集合
		 */
		List<VgopSendNumFeedBack> feedBacks = feedBackService.getFeedBackNumByDateByPage(sendNumFeedBack);
		if(feedBacks == null || feedBacks.size() == 0)
		{
			/**
			 * 查询数据失败，返回结果
			 */
			return false;
		}
		List<String> tempList = new ArrayList<String>();
		for(VgopSendNumFeedBack numfeedBack:feedBacks)
		{
			String info = numfeedBack.getInfo();
			tempList.add(info);
		}
		boolean res = dataQueue.addAll(tempList);
		int count = 0;
		//如果添加到队列失败，则进行重试
		while(!res)
		{
			if(count < ConfigParam.failRepeatNum)
			{
				count++;
				res = dataQueue.addAll(tempList);
			}else{
				/**
				 * 重试后依然失败，返回结果
				 */
				return false;
			}
		}
		log.info("【DB数据到队列】子线程("+Thread.currentThread().getName()+")数据成功放到队列中");
		return true;
	}

	
	public VgopFeedBackService getFeedBackService() {
		return feedBackService;
	}

	public void setFeedBackService(VgopFeedBackService feedBackService) {
		this.feedBackService = feedBackService;
	}

	public VgopSendNumFeedBack getSendNumFeedBack() {
		return sendNumFeedBack;
	}

	public void setSendNumFeedBack(VgopSendNumFeedBack sendNumFeedBack) {
		this.sendNumFeedBack = sendNumFeedBack;
	}

	public ConcurrentLinkedQueue<String> getDataQueue() {
		return dataQueue;
	}

	public void setDataQueue(ConcurrentLinkedQueue<String> dataQueue) {
		this.dataQueue = dataQueue;
	}

}
