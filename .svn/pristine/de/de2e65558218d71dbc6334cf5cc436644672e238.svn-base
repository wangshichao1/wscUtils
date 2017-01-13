package com.wsc.service;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.wsc.mapper.TaskLogMapper;
import com.wsc.vo.TaskLog;
@Service
public class TaskLogService {
	@Resource
	private SqlSessionFactory sessionFactory;
	
	public boolean saveLog(TaskLog logVo){
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		try {
			sqlSession = sessionFactory.openSession();
			TaskLogMapper taskLogMapper = sqlSession.getMapper(TaskLogMapper.class);
			int res = taskLogMapper.saveLog(logVo);
			if(res == 1)
			{
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession != null)
			{
				sqlSession.close();
			}
		}
		return false;
	}
}
