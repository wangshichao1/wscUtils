package com.wsc.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * VGOP下发号码反馈记录VO
 * @author WSC
 */
public class VgopSendNumFeedBack {
	//经分VGOP活动ID
	private String act_id;
	//短信下发号码
	private String serial_number;
	//活动下发时间
	private Date act_send_time;
	//短信下发时间
	private Date sms_send_time;
	//下发标识：1-未处理  2-已插入下发表
	private String touch_tag;
	//下发端口
	private String act_channel;
	//下发年份
	private int year;
	// 下发月份
	private int month;
	//下发日期
	private int day;
	//分页对象
	private PageVo pageVo;
	public VgopSendNumFeedBack() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAct_id() {
		return act_id;
	}
	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public Date getAct_send_time() {
		return act_send_time;
	}
	public void setAct_send_time(Date act_send_time) {
		this.act_send_time = act_send_time;
	}
	public String getTouch_tag() {
		return touch_tag;
	}
	public void setTouch_tag(String touch_tag) {
		this.touch_tag = touch_tag;
	}
	public String getAct_channel() {
		return act_channel;
	}
	public void setAct_channel(String act_channel) {
		this.act_channel = act_channel;
	}
	
	public Date getSms_send_time() {
		return sms_send_time;
	}
	public void setSms_send_time(Date sms_send_time) {
		this.sms_send_time = sms_send_time;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
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
	public PageVo getPageVo() {
		return pageVo;
	}
	public void setPageVo(PageVo pageVo) {
		this.pageVo = pageVo;
	}
	
	@Override
	public String toString() {
		return "VgopSendNumFeedBack [act_id=" + act_id + ", serial_number="
				+ serial_number + ", act_send_time=" + act_send_time
				+ ", sms_send_time=" + sms_send_time + ", touch_tag="
				+ touch_tag + ", act_channel=" + act_channel + ", year=" + year
				+ ", month=" + month + ", day=" + day + ", pageVo=" + pageVo
				+ "]";
	}
	
	public String getInfo()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return act_id+"|"+serial_number+"|"+dateFormat.format(sms_send_time)+"|"+touch_tag+"|"+act_channel;
	}
}
