package com.wsc.job;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.wsc.utils.ConfigParam;
import com.wsc.utils.FileUtil;

/**
 * 删除3个月前数据文件
 * @author WSC
 */
@Service
public class Delete3MonthsAgoFileJob {
	private Logger log = Logger.getLogger(Delete3MonthsAgoFileJob.class);
	//参数配置文件目录
	private static final String configPath="/config.properties";
	
	public void deleteFile()
	{
		log.info("【文件扫描删除任务】扫描3个月前的文件进行删除······");
		/**
		 * 加载配置参数
		 */
		ConfigParam.init(configPath);
		String path = ConfigParam.LocalFilePath;
		/**
		 * 获取子文件列表
		 */
		File dictory = new File(path);
		File[] fileList = dictory.listFiles();
		for(File file : fileList)
		{
			if(file.isFile())
			{
				checkAndDelete(file);
			}
		}
		log.info("【文件扫描删除任务】执行结束！！！");
	}

	private void checkAndDelete(File file) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -ConfigParam.data_save_month);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, maxDay);
		Date date = calendar.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		int dateInt = Integer.parseInt(dateFormat.format(date));
		String fileName = file.getName();
		int fileDateInt = Integer.parseInt(fileName.substring(ConfigParam.JF_INF_NAME.length(), ConfigParam.JF_INF_NAME.length()+8));
		if(fileDateInt <= dateInt)
		{
			log.info("【文件扫描删除任务】删除文件："+file.getName());
			FileUtil.deleteFile(file);
		}
	}

}
