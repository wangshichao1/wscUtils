package com.wsc.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.wsc.thread.QueueDataToFileThread;
import com.wsc.thread.ReadDataToQueueThread;
import com.wsc.utils.ConfigParam;
import com.wsc.utils.FileUtil;
import com.wsc.vo.PageVo;
import com.wsc.vo.VgopSendNumFeedBack;

/**
 * �����ļ�������
 * 
 * @author WSC
 */
@Service
public class FeedBackFileService {
	@Resource
	private VgopFeedBackService feedBackService;
	private Logger log = Logger.getLogger(FeedBackFileService.class);
	// ���ݶ���
	private ConcurrentLinkedQueue<String> dataQueue;
	// ����ʧ�ܶ���
	private ConcurrentLinkedQueue<VgopSendNumFeedBack> failureQueue;
	//���������ļ�Ŀ¼
	private static final String configPath="/config.properties";
	//�̳߳ء�����2�����̡߳�
	private ThreadPoolExecutor threadPool;
	//��������2�ļ��߳�
	private QueueDataToFileThread dataToFileThread;
	//��������2�ļ��߳�-���н��
	private FutureTask<Map<String, Object>> futureTask;

	public FeedBackFileService(){
		dataQueue = new ConcurrentLinkedQueue<String>();
		failureQueue = new ConcurrentLinkedQueue<VgopSendNumFeedBack>();
	}
	
	/**
	 * ���������գ�Ĭ�ϻ�ȡǰһ�������������ļ�
	 * @return
	 */
	public Map<String, Object> createFile() {
		// ��ȡǰһ������
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date date = calendar.getTime();
		DateFormat df1 = new SimpleDateFormat("yyyy");
		DateFormat df2 = new SimpleDateFormat("MM");
		DateFormat df3 = new SimpleDateFormat("dd");
		int year = Integer.parseInt(df1.format(date));
		int month = Integer.parseInt(df2.format(date));
		int day = Integer.parseInt(df3.format(date));
		return createFile(year, month, day);
	}

	/**
	 * ���������մ��������ļ�
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public Map<String, Object> createFile(int year, int month, int day) {
		long startTimeMillis = System.currentTimeMillis();
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("target", "���ɡ�"+year+"-"+month+"-"+day+"����������");
		boolean dTQFlag = false;//���ݵ����гɹ���ʶ
		boolean isSuccess = false;
		String reason = "";
		/**
		 * �������ò���
		 */
		ConfigParam.init(configPath);
		/**
		 * ��ȡ�ܵļ�¼����������ÿ���̻߳�ȡ���������������
		 */
		VgopSendNumFeedBack feedBack = new VgopSendNumFeedBack();
		feedBack.setYear(year);
		feedBack.setMonth(month);
		feedBack.setDay(day);
		int allCount = feedBackService.countFeedBackNumByDate(feedBack);
		if(allCount == -1)
		{
			reason = "ͳ������ʧ��";
			resMap.put("isSuccess", isSuccess);
			resMap.put("reason", reason);
			return resMap;
		}else if(allCount == 0)
		{
			reason = "��"+year+"-"+month+"-"+day+"��������";
			log.info("��"+year+"-"+month+"-"+day+"��������;���ɿ������ļ�");
			resMap = createEmptyValFile(year, month, day);
			resMap.put("reason", reason);
			return resMap;
		}
		
		/**
		 * ��������д���ļ��߳�
		 */
		log.info("������To�ļ�����������д���ļ��߳�");
		dataToFileThread = new QueueDataToFileThread(dataQueue, getInfFilePath(year, month, day));
		futureTask = new FutureTask<Map<String,Object>>(dataToFileThread);
		Thread thread = new Thread(futureTask);
		thread.start();
		
		//ÿҳ��¼��С���൱���߳�Ҫ��ȡ��������
		int pageSize = ConfigParam.oneThreadNum;
		//��ҳ�����൱���ܵ���Ҫ���߳�����
		int totalPage = (allCount + pageSize - 1)/pageSize;
		
		/**
		 * ʹ���̳߳�
		 * ����һ���̳߳�
		 */
        threadPool = new ThreadPoolExecutor(ConfigParam.maxPoolSiz, ConfigParam.maxPoolSiz, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5),
                new ThreadPoolExecutor.CallerRunsPolicy());
        List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
        List<VgopSendNumFeedBack> sendNumFeedBacks = new ArrayList<VgopSendNumFeedBack>();
		for(int i=1;i<=totalPage;i++)
		{
			feedBack = new VgopSendNumFeedBack();
			feedBack.setYear(year);
			feedBack.setMonth(month);
			feedBack.setDay(day);
			PageVo pageVo = new PageVo(pageSize, i, allCount);
			feedBack.setPageVo(pageVo);
			sendNumFeedBacks.add(feedBack);
			ReadDataToQueueThread dataToQueueThread = new ReadDataToQueueThread(feedBackService,feedBack, dataQueue);
			Future<Boolean> future = threadPool.submit(dataToQueueThread);
			futures.add(future);
		}
		log.info("�Ѿ��������е����߳�"); 
		log.info("�̳߳���Ҫִ�е���������:"+threadPool.getTaskCount());
		log.info("�̳߳�����ɵ���������:"+threadPool.getCompletedTaskCount());
		/**
		 * �ر��̳߳�
		 */
        threadPool.shutdown();  
        log.info("�ر��̳߳�");
		//ʧ�����Դ���
        int count = 0;
        while(true){  
            if(threadPool.isTerminated()){  
                log.info("���е����̶߳������ˣ�"); 
                /**
                 * ѭ������Future�б���ȡ����ʧ�ܵ��̣߳������뵽ʧ�ܶ���
                 */
                for(int i=0;i<futures.size();i++)
                {
                	Future<Boolean> future = futures.get(i);
                	VgopSendNumFeedBack numFeedBack = sendNumFeedBacks.get(i);
                	try {
						boolean res = future.get();
						if(!res)
						{
							log.info("��DB���ݵ����С�������ʧ�ܵ����̣߳����뵽ʧ�ܶ�������ִ��!");
							failureQueue.add(numFeedBack);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						log.error("��DB���ݵ����С��������쳣�����̣߳����뵽ʧ�ܶ�������ִ��!"+e.getMessage());
						failureQueue.add(numFeedBack);
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						log.error("��DB���ݵ����С��������쳣�����̣߳����뵽ʧ�ܶ�������ִ��!"+e.getMessage());
						failureQueue.add(numFeedBack);
					}
                }
                if(failureQueue.isEmpty())
                {
                    log.info("���������߳����н�����־Ϊtrue");
                    dataToFileThread.setRunEnd(true);
                	log.info("��DB���ݵ����С������ɹ�");
                	dTQFlag = true;
                    break;  
                }
                else{
                	if(count < ConfigParam.failRepeatNum)
                	{
                		count++;
                		log.info("��DB���ݵ����С���ʧ�ܼ�¼�����е�"+count+"������");
                		sendNumFeedBacks.clear();
                		futures.clear();
                		threadPool = new ThreadPoolExecutor(ConfigParam.maxPoolSiz, ConfigParam.maxPoolSiz, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5),
                                new ThreadPoolExecutor.CallerRunsPolicy());
                    	while(!failureQueue.isEmpty())
                    	{
                    		VgopSendNumFeedBack numFeedBack = failureQueue.poll();
                    		if(numFeedBack != null)
                    		{
                    			sendNumFeedBacks.add(numFeedBack);
                    			ReadDataToQueueThread dataToQueueThread = new ReadDataToQueueThread(feedBackService,numFeedBack, dataQueue);
                    			Future<Boolean> future = threadPool.submit(dataToQueueThread);
                    			futures.add(future);
                    		}
                    	}
                		log.info("��DB���ݵ����С�ʧ�����ԡ����Ѿ��������е����߳�"); 
                		/**
                		 * �ر��̳߳�
                		 */
                        threadPool.shutdown();  
                        log.info("��DB���ݵ����С�ʧ�����ԡ����ر��̳߳�");
                	}else{
                		log.info("��DB���ݵ����С���ʧ�ܼ�¼���ѽ�����"+count+"�����ԣ�����ʧ��");
                		log.info("���������߳����н�����־Ϊtrue");
                		dataToFileThread.setRunEnd(true);
                        break; 
                	}
                }
            }  
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
        }
        log.info("����������To���С�ִ����ɣ�����");
        log.info("����������"+allCount);
        if(!dTQFlag)
        {
        	reason = "��ȡ���ݵ�����ʧ��";
        	if(futureTask != null && !futureTask.isDone())
        	{
        		futureTask.cancel(true);
        	}
        	//ɾ��Ŀ¼���ļ�
        	FileUtil.deleteFile(getInfFilePath(year, month, day));
        	resMap.put("isSuccess", isSuccess);
    		resMap.put("reason", reason);
        	return resMap;
        }
        /**
         * �ж�����д���ļ����
         */
        while(!futureTask.isDone())
        {
        	log.info("��������д���ļ���δ��ɣ������ȴ�������������");
        	try {
 				Thread.sleep(1000);
 			} catch (InterruptedException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}    
        }
        Map<String, Object> map = null;
        try {
			map = futureTask.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        log.info("��������д���ļ��߳����н�����");
        if(map == null)
        {
        	log.info("��������д���ļ�ʧ�ܣ�");
        	reason = "��������д���ļ�ʧ��";
        	//ɾ��Ŀ¼���ļ�
        	String valFilepath = getInfFilePath(year, month, day);
        	FileUtil.deleteFile(valFilepath);
        	resMap.put("isSuccess", isSuccess);
    		resMap.put("reason", reason);
        	return resMap;
        }
        boolean success = (Boolean) map.get("isSuccess");
        int rowCount = (Integer) map.get("rowCount");
        if(!success)
        {
        	log.info("��������д���ļ�ʧ�ܣ�");
        	reason = "��������д���ļ�ʧ��";
        	//ɾ��Ŀ¼���ļ�
        	String valFilepath = getInfFilePath(year, month, day);
        	FileUtil.deleteFile(valFilepath);
        	resMap.put("isSuccess", isSuccess);
    		resMap.put("reason", reason);
        	return resMap;
        }
        log.info("����д���ļ�������"+rowCount);
        if(rowCount != allCount)
        {
        	log.info("д���ļ���¼���������ݿ��ܼ�¼����һ�£��ܵ����ݴ�С����"+allCount+"��,д���ļ����ݴ�С����"+rowCount+"��");
        	reason = "д���ļ���¼���������ݿ��ܼ�¼����һ�£��ܵ����ݴ�С����"+allCount+"��,д���ļ����ݴ�С����"+rowCount+"��";
        	//ɾ��Ŀ¼���ļ�
        	String valFilepath = getInfFilePath(year, month, day);
        	FileUtil.deleteFile(valFilepath);
        	resMap.put("isSuccess", isSuccess);
    		resMap.put("reason", reason);
        	return resMap;
        }
        log.info("������To�ļ������������ļ��ɹ���");
        log.info("������To�ļ���׼������CHECK�ļ�������������");
        /**
         * ����check�ļ�
         */
        String valFilePath = getInfFilePath(year, month, day);
        File valFile = new File(valFilePath);
        if(valFile == null || !valFile.exists())
        {
        	resMap.put("isSuccess", false);
    		resMap.put("reason", "�����ļ�����ʧ��");
    		return resMap;
        }
        File checkFile = createCheckFile(valFile, allCount);
        if(checkFile == null || !checkFile.exists())
        {
        	log.info("������To�ļ�������CHECK�ļ�ʧ�ܡ�����������");
        	//ɾ��Ŀ¼���ļ�
        	String valFilepath = getInfFilePath(year, month, day);
        	FileUtil.deleteFile(valFilepath);
        	resMap.put("isSuccess", false);
    		resMap.put("reason", "CHECK�ļ�����ʧ��");
    		return resMap;
        }
        log.info("������To�ļ�������CHECK�ļ��ɹ�����");
        resMap.put("isSuccess", true);
        long endTimeMillis = System.currentTimeMillis();
        long runTimeMillis = endTimeMillis - startTimeMillis;
        long minutes = runTimeMillis/(1000*60);
        long seconds = (runTimeMillis%(1000*60))/1000;
		resMap.put("reason", "������To�ļ�����ʱ��"+minutes+"��"+seconds+"��");
		log.info("������To�ļ�����ʱ��"+minutes+"��"+seconds+"��");
		List<File> fileList = new ArrayList<File>();
		fileList.add(valFile);
		fileList.add(checkFile);
		resMap.put("FileList", fileList);
		return resMap;
	}
	
	/**
	 * ��ȡ���ֽӿ��ļ�ȫ·����(�����ļ���)
	 * @return
	 */
	public String getInfFilePath(int year,int month,int day)
	{
		String monthStr = "";
		if(month < 10)
		{
			monthStr = "0" + month;
		}else{
			monthStr = ""+month;
		}
		String dayStr = "";
		if(day < 10)
		{
			dayStr = "0" + day;
		}else{
			dayStr = ""+day;
		}
		String date = year + monthStr + dayStr;
		return ConfigParam.LocalFilePath+File.separator+ConfigParam.JF_INF_NAME+date+"000000"+ConfigParam.JF_INF_SUFFIX;
	}
	
	/**
	 * ����check�ļ�
	 * @param valFile
	 * @param allCount
	 * @return
	 */
	public File createCheckFile(File valFile,int allCount)
	{
		String valFileName = valFile.getName();
		String name = valFileName.substring(0, valFileName.indexOf("."));
		String checkFilePath = valFile.getParentFile().getAbsolutePath()+File.separator+name+ConfigParam.JF_CHECK_INF_SUFFIX;
		long length = valFile.length();
		String line = valFile.getName()+","+length+","+allCount;
		/**
		 * �����ļ�
		 */
		OutputStream out = null;
		BufferedWriter writer = null;
		try {
			out = new FileOutputStream(checkFilePath,false);
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(line);
			writer.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			//�ر���
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
		File file = new File(checkFilePath);
		return file;
	}
	
	/**
	 * ������������ݣ�ҲҪ�����յ��ļ�
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public Map<String, Object> createEmptyValFile(int year,int month,int day)
	{
		Map<String, Object> resMap = new HashMap<String, Object>();
		String valFilePath = getInfFilePath(year, month, day);
		File varfile = new File(valFilePath);
		try {
			varfile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File checkfile = createCheckFile(varfile, 0);
		if(varfile != null && varfile.exists() && checkfile != null && checkfile.exists())
		{
			resMap.put("isSuccess", true);
			List<File> fileList = new ArrayList<File>();
			fileList.add(varfile);
			fileList.add(checkfile);
			resMap.put("FileList", fileList);
			log.info("���ɿ��ļ��ɹ�...");
		}else{
			if(varfile != null)
			{
				FileUtil.deleteFile(varfile);
			}
			resMap.put("isSuccess", false);
			log.info("���ɿ��ļ�ʧ�ܣ�����");
		}
		return resMap;
	}

	@PreDestroy
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		if(threadPool != null)
		{
			threadPool.shutdownNow();
		}
		if(dataQueue != null)
		{
			dataQueue.clear();
		}
		if(failureQueue != null)
		{
			failureQueue.clear();
		}
		if(dataToFileThread != null)
		{
			dataToFileThread.setRunEnd(true);
		}
		if(futureTask != null && !futureTask.isDone())
		{
			futureTask.cancel(true);
		}
	}
	
	
}
