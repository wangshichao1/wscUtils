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
 * �����ݿ��ȡ���ݵ�����
 * @author WSC
 *
 */
public class ReadDataToQueueThread implements Callable<Boolean> {
	private VgopFeedBackService feedBackService;
	private Logger log = Logger.getLogger(ReadDataToQueueThread.class);
	private VgopSendNumFeedBack sendNumFeedBack;
	//���ݶ���
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
	 * ҵ���߼�
	 */
	public Boolean call() throws Exception {
		/**
		 * 1����ȡ���ݶ��󼯺�
		 */
		List<VgopSendNumFeedBack> feedBacks = feedBackService.getFeedBackNumByDateByPage(sendNumFeedBack);
		if(feedBacks == null || feedBacks.size() == 0)
		{
			/**
			 * ��ѯ����ʧ�ܣ����ؽ��
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
		//�����ӵ�����ʧ�ܣ����������
		while(!res)
		{
			if(count < ConfigParam.failRepeatNum)
			{
				count++;
				res = dataQueue.addAll(tempList);
			}else{
				/**
				 * ���Ժ���Ȼʧ�ܣ����ؽ��
				 */
				return false;
			}
		}
		log.info("��DB���ݵ����С����߳�("+Thread.currentThread().getName()+")���ݳɹ��ŵ�������");
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
