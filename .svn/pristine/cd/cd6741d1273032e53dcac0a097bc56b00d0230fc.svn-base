package com.wsc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wsc.job.GenerateVgopNumFileJob;

/**
 * 生成数据文件
 * 
 * @author WSC
 */
@Controller
public class CreateFileController {
	@Resource
	private GenerateVgopNumFileJob fileJob;

	@RequestMapping("/data2file")
	public void data2File(int year, int month, int day,HttpServletResponse response) {
		// 读取数据并生成文件
		Map<String, Object> resMap = fileJob.process(year, month, day);
		resMap.remove("FileList");
		// 写回结果给JSP
		String jsonData = JSONObject.fromObject(resMap).toString();
		System.out.println(jsonData);
		response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(jsonData);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(writer != null)
			{
				writer.close();
			}
		}
	}
}
