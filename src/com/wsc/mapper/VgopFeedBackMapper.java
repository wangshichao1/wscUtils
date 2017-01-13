package com.wsc.mapper;

import java.util.List;

import com.wsc.vo.VgopSendNumFeedBack;

public interface VgopFeedBackMapper {
	/**
	 * ����VgopSendNumFeedBack���������õ�����������ȡ �·������¼�б�
	 * �����з�ҳ����
	 * @return
	 * @throws Exception
	 */
	public List<VgopSendNumFeedBack> getFeedBackNumByDateByPage(VgopSendNumFeedBack vgopSendNumFeedBack) throws Exception;
	
	/**
	 * ����VgopSendNumFeedBack���������õ�����������ȡ �·������¼�б�
	 * @return
	 * @throws Exception
	 */
	public List<VgopSendNumFeedBack> getFeedBackNumByDate(VgopSendNumFeedBack vgopSendNumFeedBack) throws Exception;
	
	/**
	 * ����VgopSendNumFeedBack���������õ���������ͳ���ܵļ�¼��
	 * @param vgopSendNumFeedBack
	 * @return
	 * @throws Exception
	 */
	public int countFeedBackNumByDate(VgopSendNumFeedBack vgopSendNumFeedBack) throws Exception;
}
