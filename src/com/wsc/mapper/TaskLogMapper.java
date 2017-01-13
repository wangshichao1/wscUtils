package com.wsc.mapper;

import com.wsc.vo.TaskLog;

public interface TaskLogMapper {
	/**
	 * ��¼��־
	 * @param logVo
	 * @return
	 * @throws Exception
	 */
	public int saveLog(TaskLog logVo) throws Exception;
	/**
	 * �޸���־
	 * @param logVo
	 * @return
	 * @throws Exception
	 */
	public int updateLog(TaskLog logVo) throws Exception;
	/**
	 * ɾ����־
	 * @param logVo
	 * @return
	 * @throws Exception
	 */
	public int deleteLog(TaskLog logVo) throws Exception;
}
