package com.wsc.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsc.mapper.VgopFeedBackMapper;
import com.wsc.vo.VgopSendNumFeedBack;
@Service
public class VgopFeedBackService {
	private Logger log = Logger.getLogger(VgopFeedBackService.class);
	@Autowired
	private SqlSessionFactory sessionFactory;
	/**
	 * 根据VgopSendNumFeedBack对象中设置的年月日来获取 下发号码记录列表
	 * @return
	 */
	public List<VgopSendNumFeedBack> getFeedBackNumByDate()
	{
		/**
		 * 获取前一天日期
		 */
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1); 
		Date date = calendar.getTime();
		DateFormat df1 = new SimpleDateFormat("yyyy");
		DateFormat df2 = new SimpleDateFormat("MM");
		DateFormat df3 = new SimpleDateFormat("dd");
		int year = Integer.parseInt(df1.format(date));
		int month = Integer.parseInt(df2.format(date));
		int day = Integer.parseInt(df3.format(date));
		return getFeedBackNumByDate(year, month, day);
	}
	/**
	 * 根据VgopSendNumFeedBack对象中设置的年月日来获取 下发号码记录列表
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public List<VgopSendNumFeedBack> getFeedBackNumByDate(int year,int month,int day)
	{
		log.info("开始查询VGOP下发号码列表·····");
		List<VgopSendNumFeedBack> sendNumFeedBacks = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = sessionFactory.openSession();
			VgopFeedBackMapper feedBackMapper = sqlSession.getMapper(VgopFeedBackMapper.class);
			VgopSendNumFeedBack vgopSendNumFeedBack = new VgopSendNumFeedBack();
			vgopSendNumFeedBack.setYear(year);
			vgopSendNumFeedBack.setMonth(month);
			vgopSendNumFeedBack.setDay(day);
			log.info("查询条件："+vgopSendNumFeedBack.toString());
			sendNumFeedBacks = feedBackMapper.getFeedBackNumByDate(vgopSendNumFeedBack);
			log.info("查询成功，共"+sendNumFeedBacks.size()+"条数据！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession != null)
			{
				sqlSession.close();
			}
		}
		return sendNumFeedBacks;
	}
	
	/**
	 * 根据VgopSendNumFeedBack对象中设置的年月日来获取 下发号码记录列表
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public int countFeedBackNumByDate(VgopSendNumFeedBack vgopSendNumFeedBack)
	{
		log.info("开始统计VGOP下发号码记录数·····");
		int allCount = -1;
		SqlSession sqlSession = null;
		try {
			sqlSession = sessionFactory.openSession();
			VgopFeedBackMapper feedBackMapper = sqlSession.getMapper(VgopFeedBackMapper.class);
			log.info("查询条件："+vgopSendNumFeedBack.toString());
			allCount = feedBackMapper.countFeedBackNumByDate(vgopSendNumFeedBack);
			log.info("统计成功，共"+allCount+"条数据！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession != null)
			{
				sqlSession.close();
			}
		}
		return allCount;
	}
	
	/**
	 * 根据VgopSendNumFeedBack对象中设置的年月日来获取 下发号码记录列表
	 * 并进行分页处理
	 * @param vgopSendNumFeedBack
	 * @return
	 */
	public List<VgopSendNumFeedBack> getFeedBackNumByDateByPage(VgopSendNumFeedBack vgopSendNumFeedBack)
	{
		log.info("开始查询VGOP下发号码列表·····");
		List<VgopSendNumFeedBack> sendNumFeedBacks = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = sessionFactory.openSession();
			VgopFeedBackMapper feedBackMapper = sqlSession.getMapper(VgopFeedBackMapper.class);
			log.info("查询条件："+vgopSendNumFeedBack.toString());
			sendNumFeedBacks = feedBackMapper.getFeedBackNumByDateByPage(vgopSendNumFeedBack);
			log.info("查询成功，共"+sendNumFeedBacks.size()+"条数据！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession != null)
			{
				sqlSession.close();
			}
		}
		return sendNumFeedBacks;
	}
}
