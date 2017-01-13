package com.wsc.vo;

import java.util.Date;

/**
 * 定时任务日志对象
 * @author WSC
 */
public class TaskLog {

	private long task_id;
	private String task_name;
	private Date run_time;
	private String upload_file_list;
	private String upload_result;
	private String failure_desc;
	private int month;
	private int day;
	public TaskLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getTask_id() {
		return task_id;
	}
	public void setTask_id(long task_id) {
		this.task_id = task_id;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public Date getRun_time() {
		return run_time;
	}
	public void setRun_time(Date run_time) {
		this.run_time = run_time;
	}
	public String getUpload_file_list() {
		return upload_file_list;
	}
	public void setUpload_file_list(String upload_file_list) {
		this.upload_file_list = upload_file_list;
	}
	public String getUpload_result() {
		return upload_result;
	}
	public void setUpload_result(String upload_result) {
		this.upload_result = upload_result;
	}
	public String getFailure_desc() {
		return failure_desc;
	}
	public void setFailure_desc(String failure_desc) {
		this.failure_desc = failure_desc;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	@Override
	public String toString() {
		return "TaskLogVo [task_id=" + task_id + ", task_name=" + task_name
				+ ", run_time=" + run_time + ", upload_file_list="
				+ upload_file_list + ", upload_result=" + upload_result
				+ ", failure_desc=" + failure_desc + ", month=" + month
				+ ", day=" + day + "]";
	}
	
}
