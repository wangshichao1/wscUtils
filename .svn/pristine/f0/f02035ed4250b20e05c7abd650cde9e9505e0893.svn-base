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
 * 反馈文件服务类
 * 
 * @author WSC
 */
@Service
public class FeedBackFileService {
	@Resource
	private VgopFeedBackService feedBackService;
	private Logger log = Logger.getLogger(FeedBackFileService.class);
	// 数据队列
	private ConcurrentLinkedQueue<String> dataQueue;
	// 处理失败队列
	private ConcurrentLinkedQueue<VgopSendNumFeedBack> failureQueue;
	//参数配置文件目录
	private static final String configPath="/config.properties";
	//线程池【数据2队列线程】
	private ThreadPoolExecutor threadPool;
	//队列数据2文件线程
	private QueueDataToFileThread dataToFileThread;
	//队列数据2文件线程-运行结果
	private FutureTask<Map<String, Object>> futureTask;

	public FeedBackFileService(){
		dataQueue = new ConcurrentLinkedQueue<String>();
		failureQueue = new ConcurrentLinkedQueue<VgopSendNumFeedBack>();
	}
	
	/**
	 * 不传年月日，默认获取前一天数据来创建文件
	 * @return
	 */
	public Map<String, Object> createFile() {
		// 获取前一天日期
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
	 * 根据年月日创建数据文件
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public Map<String, Object> createFile(int year, int month, int day) {
		long startTimeMillis = System.currentTimeMillis();
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("target", "生成【"+year+"-"+month+"-"+day+"】日期数据");
		boolean dTQFlag = false;//数据到队列成功标识
		boolean isSuccess = false;
		String reason = "";
		/**
		 * 加载配置参数
		 */
		ConfigParam.init(configPath);
		/**
		 * 获取总的记录数，并根据每个线程获取数量进行任务分配
		 */
		VgopSendNumFeedBack feedBack = new VgopSendNumFeedBack();
		feedBack.setYear(year);
		feedBack.setMonth(month);
		feedBack.setDay(day);
		int allCount = feedBackService.countFeedBackNumByDate(feedBack);
		if(allCount == -1)
		{
			reason = "统计总数失败";
			resMap.put("isSuccess", isSuccess);
			resMap.put("reason", reason);
			return resMap;
		}else if(allCount == 0)
		{
			reason = "【"+year+"-"+month+"-"+day+"】无数据";
			log.info("【"+year+"-"+month+"-"+day+"】无数据;生成空数据文件");
			resMap = createEmptyValFile(year, month, day);
			resMap.put("reason", reason);
			return resMap;
		}
		
		/**
		 * 开启数据写入文件线程
		 */
		log.info("【数据To文件】开启数据写入文件线程");
		dataToFileThread = new QueueDataToFileThread(dataQueue, getInfFilePath(year, month, day));
		futureTask = new FutureTask<Map<String,Object>>(dataToFileThread);
		Thread thread = new Thread(futureTask);
		thread.start();
		
		//每页记录大小，相当于线程要获取的数据量
		int pageSize = ConfigParam.oneThreadNum;
		//总页数，相当于总的需要的线程数量
		int totalPage = (allCount + pageSize - 1)/pageSize;
		
		/**
		 * 使用线程池
		 * 构造一个线程池
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
		log.info("已经开启所有的子线程"); 
		log.info("线程池需要执行的任务数量:"+threadPool.getTaskCount());
		log.info("线程池已完成的任务数量:"+threadPool.getCompletedTaskCount());
		/**
		 * 关闭线程池
		 */
        threadPool.shutdown();  
        log.info("关闭线程池");
		//失败重试次数
        int count = 0;
        while(true){  
            if(threadPool.isTerminated()){  
                log.info("所有的子线程都结束了！"); 
                /**
                 * 循环遍历Future列表，获取运行失败的线程，并加入到失败队列
                 */
                for(int i=0;i<futures.size();i++)
                {
                	Future<Boolean> future = futures.get(i);
                	VgopSendNumFeedBack numFeedBack = sendNumFeedBacks.get(i);
                	try {
						boolean res = future.get();
						if(!res)
						{
							log.info("【DB数据到队列】有运行失败的子线程，加入到失败队列重新执行!");
							failureQueue.add(numFeedBack);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						log.error("【DB数据到队列】有运行异常的子线程，加入到失败队列重新执行!"+e.getMessage());
						failureQueue.add(numFeedBack);
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						log.error("【DB数据到队列】有运行异常的子线程，加入到失败队列重新执行!"+e.getMessage());
						failureQueue.add(numFeedBack);
					}
                }
                if(failureQueue.isEmpty())
                {
                    log.info("设置批量线程运行结束标志为true");
                    dataToFileThread.setRunEnd(true);
                	log.info("【DB数据到队列】操作成功");
                	dTQFlag = true;
                    break;  
                }
                else{
                	if(count < ConfigParam.failRepeatNum)
                	{
                		count++;
                		log.info("【DB数据到队列】有失败记录，进行第"+count+"次重试");
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
                		log.info("【DB数据到队列【失败重试】】已经开启所有的子线程"); 
                		/**
                		 * 关闭线程池
                		 */
                        threadPool.shutdown();  
                        log.info("【DB数据到队列【失败重试】】关闭线程池");
                	}else{
                		log.info("【DB数据到队列】有失败记录，已进行了"+count+"次重试；处理失败");
                		log.info("设置批量线程运行结束标志为true");
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
        log.info("批量【数据To队列】执行完成！！！");
        log.info("数据总量："+allCount);
        if(!dTQFlag)
        {
        	reason = "读取数据到队列失败";
        	if(futureTask != null && !futureTask.isDone())
        	{
        		futureTask.cancel(true);
        	}
        	//删除目录下文件
        	FileUtil.deleteFile(getInfFilePath(year, month, day));
        	resMap.put("isSuccess", isSuccess);
    		resMap.put("reason", reason);
        	return resMap;
        }
        /**
         * 判断数据写入文件结果
         */
        while(!futureTask.isDone())
        {
        	log.info("队列数据写入文件还未完成，继续等待······");
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
        log.info("队列数据写入文件线程运行结束！");
        if(map == null)
        {
        	log.info("队列数据写入文件失败！");
        	reason = "队列数据写入文件失败";
        	//删除目录下文件
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
        	log.info("队列数据写入文件失败！");
        	reason = "队列数据写入文件失败";
        	//删除目录下文件
        	String valFilepath = getInfFilePath(year, month, day);
        	FileUtil.deleteFile(valFilepath);
        	resMap.put("isSuccess", isSuccess);
    		resMap.put("reason", reason);
        	return resMap;
        }
        log.info("队列写入文件数量："+rowCount);
        if(rowCount != allCount)
        {
        	log.info("写入文件记录数量跟数据库总记录数不一致！总的数据大小：【"+allCount+"】,写入文件数据大小：【"+rowCount+"】");
        	reason = "写入文件记录数量跟数据库总记录数不一致！总的数据大小：【"+allCount+"】,写入文件数据大小：【"+rowCount+"】";
        	//删除目录下文件
        	String valFilepath = getInfFilePath(year, month, day);
        	FileUtil.deleteFile(valFilepath);
        	resMap.put("isSuccess", isSuccess);
    		resMap.put("reason", reason);
        	return resMap;
        }
        log.info("【数据To文件】生成数据文件成功！");
        log.info("【数据To文件】准备生成CHECK文件······");
        /**
         * 生成check文件
         */
        String valFilePath = getInfFilePath(year, month, day);
        File valFile = new File(valFilePath);
        if(valFile == null || !valFile.exists())
        {
        	resMap.put("isSuccess", false);
    		resMap.put("reason", "数据文件生成失败");
    		return resMap;
        }
        File checkFile = createCheckFile(valFile, allCount);
        if(checkFile == null || !checkFile.exists())
        {
        	log.info("【数据To文件】生成CHECK文件失败······");
        	//删除目录下文件
        	String valFilepath = getInfFilePath(year, month, day);
        	FileUtil.deleteFile(valFilepath);
        	resMap.put("isSuccess", false);
    		resMap.put("reason", "CHECK文件生成失败");
    		return resMap;
        }
        log.info("【数据To文件】生成CHECK文件成功！！");
        resMap.put("isSuccess", true);
        long endTimeMillis = System.currentTimeMillis();
        long runTimeMillis = endTimeMillis - startTimeMillis;
        long minutes = runTimeMillis/(1000*60);
        long seconds = (runTimeMillis%(1000*60))/1000;
		resMap.put("reason", "【数据To文件】耗时："+minutes+"分"+seconds+"秒");
		log.info("【数据To文件】耗时："+minutes+"分"+seconds+"秒");
		List<File> fileList = new ArrayList<File>();
		fileList.add(valFile);
		fileList.add(checkFile);
		resMap.put("FileList", fileList);
		return resMap;
	}
	
	/**
	 * 获取经分接口文件全路径名(包括文件名)
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
	 * 创建check文件
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
		 * 生成文件
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
			//关闭流
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
	 * 如果当日无数据，也要创建空的文件
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
			log.info("生成空文件成功...");
		}else{
			if(varfile != null)
			{
				FileUtil.deleteFile(varfile);
			}
			resMap.put("isSuccess", false);
			log.info("生成空文件失败！！！");
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
