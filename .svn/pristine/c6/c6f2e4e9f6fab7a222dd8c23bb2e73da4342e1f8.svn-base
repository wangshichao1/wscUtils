package com.wsc.vo;
/**
 * 分页对象
 * @author WSC
 */
public class PageVo {
	//每页大小
	private int pageSize;
	//当前页码
	private int nowPage;
	//总共页数
	private int totalPage;
	//总记录数
	private int allCount;
	//开始坐标
	private int startIndex;
	//结束坐标
	private int endIndex;
	
	public PageVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageVo(int pageSize, int nowPage, int allCount) {
		super();
		this.pageSize = pageSize;
		this.nowPage = nowPage;
		this.allCount = allCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	/**
	 * 计算总共页数
	 * @return
	 */
	public int getTotalPage() {
		totalPage = (allCount + pageSize - 1)/pageSize;
		return totalPage;
	}
	
	/**
	 * 计算开始下标
	 */
	public int getStartIndex()
	{
		startIndex = (nowPage - 1)*pageSize + 1;
		return startIndex;
	}
	/**
	 * 计算结束下标
	 */
	public int getEndIndex()
	{
		endIndex = nowPage*pageSize;
		if(endIndex >= allCount)
		{
			endIndex = allCount;
		}
		return endIndex;
	}
	@Override
	public String toString() {
		return "PageVo [pageSize=" + pageSize + ", nowPage=" + nowPage
				+ ", totalPage=" + totalPage + ", allCount=" + allCount
				+ ", startIndex=" + startIndex + ", endIndex=" + endIndex + "]";
	}
	
}

