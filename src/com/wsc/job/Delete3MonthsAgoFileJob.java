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
 * ɾ��3����ǰ�����ļ�
 * @author WSC
 */
@Service
public class Delete3MonthsAgoFileJob {
	private Logger log = Logger.getLogger(Delete3MonthsAgoFileJob.class);
	//���������ļ�Ŀ¼
	private static final String configPath="/config.properties";
	
	public void deleteFile()
	{
		log.info("���ļ�ɨ��ɾ������ɨ��3����ǰ���ļ�����ɾ��������������");
		/**
		 * �������ò���
		 */
		ConfigParam.init(configPath);
		String path = ConfigParam.LocalFilePath;
		/**
		 * ��ȡ���ļ��б�
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
		log.info("���ļ�ɨ��ɾ������ִ�н���������");
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
			log.info("���ļ�ɨ��ɾ������ɾ���ļ���"+file.getName());
			FileUtil.deleteFile(file);
		}
	}

}
