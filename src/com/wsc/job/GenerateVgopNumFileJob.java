package com.wsc.job;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wsc.service.FeedBackFileService;
import com.wsc.service.TaskLogService;
import com.wsc.vo.TaskLog;

/**
 * 生产VGOP下发号码文件-任务
 * @author WSC
 *
 */
@Service
public class GenerateVgopNumFileJob {
	
	@Resource
	private FeedBackFileService backFileService;
	@Resource
	private TaskLogService logService;
	
	/**
	 * 业务处理方法
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> process()
	{
		Map<String, Object> resMap = backFileService.createFile();
		boolean isSuccess = (Boolean) resMap.get("isSuccess");
		String desc = (String) resMap.get("reason");
		if(desc == null || "".equals(desc))
		{
			desc = "无";
		}
		TaskLog taskLog = new TaskLog();
		if(!isSuccess)
		{
			/**
			 * 生成文件失败，重试一次
			 */
			resMap = backFileService.createFile();
			isSuccess = (Boolean) resMap.get("isSuccess");
			desc = (String) resMap.get("reason");
			if(desc == null || "".equals(desc))
			{
				desc = "无";
			}
			if(!isSuccess)
			{
				taskLog.setUpload_file_list("无");
				taskLog.setUpload_result("false");
				taskLog.setFailure_desc(desc);
				logService.saveLog(taskLog);
				return resMap;
			}
		}
		//生成成功
		List<File> fileList = (List<File>) resMap.get("FileList");
		String list = "";
		if(fileList == null)
		{
			list = "无";
		}else{
			for(File file : fileList)
			{
				list += file.getName()+";";
			}
		}
		taskLog.setUpload_file_list(list);
		taskLog.setUpload_result("true");
		taskLog.setFailure_desc(desc);
		logService.saveLog(taskLog);
		resMap.put("FileNames", list);
		return resMap;
	}
	
	/**
	 * 业务处理方法
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> process(int year,int month,int day)
	{
		Map<String, Object> resMap = backFileService.createFile(year,month,day);
		String target = (String) resMap.get("target");
		boolean isSuccess = (Boolean) resMap.get("isSuccess");
		String desc = (String) resMap.get("reason");
		if(desc == null || "".equals(desc))
		{
			desc = "";
		}
		TaskLog taskLog = new TaskLog();
		if(!isSuccess)
		{
			/**
			 * 生成文件失败，重试一次
			 */
			//resMap = backFileService.createFile();
			isSuccess = (Boolean) resMap.get("isSuccess");
			desc = (String) resMap.get("reason");
			if(desc == null || "".equals(desc))
			{
				desc = "";
			}
			if(!isSuccess)
			{
				taskLog.setUpload_file_list("无");
				taskLog.setUpload_result("false");
				taskLog.setFailure_desc(target+"！"+desc);
				logService.saveLog(taskLog);
				return resMap;
			}
		}
		//生成成功
		List<File> fileList = (List<File>) resMap.get("FileList");
		String list = "";
		if(fileList == null)
		{
			list = "无";
		}else{
			for(File file : fileList)
			{
				list += file.getName()+";";
			}
		}
		taskLog.setUpload_file_list(list);
		taskLog.setUpload_result("true");
		taskLog.setFailure_desc(target+"！"+desc);
		logService.saveLog(taskLog);
		resMap.put("FileNames", list);
		return resMap;
	}
}
