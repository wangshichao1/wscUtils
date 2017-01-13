package com.wsc.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wsc.vo.TaskLog;
/**
 * 业务处理服务类
 * 业务流程：
 * 1、从数据库获取数据并生成文件
 * 2、上传文件到数据库
 * 3、记录日志
 * @author WSC
 */
@Service
public class BusiProcessService {
	@Resource
	private FeedBackFileService backFileService;
	@Resource
	private SftpUpalodService sftpUpalodService;
	@Resource
	private TaskLogService logService;
	/**
	 * 业务处理方法
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void process()
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
			taskLog.setUpload_file_list("无");
			taskLog.setUpload_result("false");
			taskLog.setFailure_desc(desc);
			logService.saveLog(taskLog);
			return;
		}
		List<File> fileList = (List<File>) resMap.get("FileList");
		/**
		 * 上传文件
		 */
		boolean res = sftpUpalodService.uploadFileList(fileList);
		if(!res)
		{
			desc += ";上传文件失败！！";
		}
		String list = "";
		for(File file : fileList)
		{
			list += file.getName()+";";
		}
		taskLog.setUpload_file_list(list);
		taskLog.setUpload_result("true");
		taskLog.setFailure_desc(desc);
		logService.saveLog(taskLog);
	}
	
	/**
	 * 业务处理方法
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void process(int year,int month,int day)
	{
		Map<String, Object> resMap = backFileService.createFile(year,month,day);
		boolean isSuccess = (Boolean) resMap.get("isSuccess");
		String desc = (String) resMap.get("reason");
		if(desc == null || "".equals(desc))
		{
			desc = "无";
		}
		TaskLog taskLog = new TaskLog();
		if(!isSuccess)
		{
			taskLog.setUpload_file_list("");
			taskLog.setUpload_result("false");
			taskLog.setFailure_desc(desc);
			logService.saveLog(taskLog);
			return;
		}
		List<File> fileList = (List<File>) resMap.get("FileList");
		/**
		 * 上传文件
		 */
		boolean res = sftpUpalodService.uploadFileList(fileList);
		if(!res)
		{
			desc += ";上传文件失败！！";
		}
		String list = "";
		for(File file : fileList)
		{
			list += file.getName()+";";
		}
		taskLog.setUpload_file_list(list);
		taskLog.setUpload_result("true");
		taskLog.setFailure_desc(desc);
		logService.saveLog(taskLog);
	}
}
