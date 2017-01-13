package com.wsc.mapper;

import java.util.List;

import com.wsc.vo.VgopSendNumFeedBack;

public interface VgopFeedBackMapper {
	/**
	 * 根据VgopSendNumFeedBack对象中设置的年月日来获取 下发号码记录列表
	 * 并进行分页处理
	 * @return
	 * @throws Exception
	 */
	public List<VgopSendNumFeedBack> getFeedBackNumByDateByPage(VgopSendNumFeedBack vgopSendNumFeedBack) throws Exception;
	
	/**
	 * 根据VgopSendNumFeedBack对象中设置的年月日来获取 下发号码记录列表
	 * @return
	 * @throws Exception
	 */
	public List<VgopSendNumFeedBack> getFeedBackNumByDate(VgopSendNumFeedBack vgopSendNumFeedBack) throws Exception;
	
	/**
	 * 根据VgopSendNumFeedBack对象中设置的年月日来统计总的记录数
	 * @param vgopSendNumFeedBack
	 * @return
	 * @throws Exception
	 */
	public int countFeedBackNumByDate(VgopSendNumFeedBack vgopSendNumFeedBack) throws Exception;
}
