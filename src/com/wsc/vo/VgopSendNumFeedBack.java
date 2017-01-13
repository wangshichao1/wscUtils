package com.wsc.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * VGOP�·����뷴����¼VO
 * @author WSC
 */
public class VgopSendNumFeedBack {
	//����VGOP�ID
	private String act_id;
	//�����·�����
	private String serial_number;
	//��·�ʱ��
	private Date act_send_time;
	//�����·�ʱ��
	private Date sms_send_time;
	//�·���ʶ��1-δ����  2-�Ѳ����·���
	private String touch_tag;
	//�·��˿�
	private String act_channel;
	//�·����
	private int year;
	// �·��·�
	private int month;
	//�·�����
	private int day;
	//��ҳ����
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
