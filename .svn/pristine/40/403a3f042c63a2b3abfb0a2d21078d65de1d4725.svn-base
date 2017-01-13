package com.wsc.utils;


/**
 * @title: 可配置参数类
 * @description:
 *
 * @author: yef
 */
public final class ParamDeploy {
	//JNDI名称
	public static String JndiName;
	//查询用户SQL
	public static String queryUserSql;
	//数据文件路径
	public static String dataFilePath;
	//已导入数据文件路径
	public static String movePath;
	//获取下发key语句
	public static String getSendKeySql;
	//插入KEY语句
	public static String insertKeySql;
	//查询KEY语句
	public static String queryKeySql;
	//更新KEY语句
	public static String updateKeySql;
	//更新KEY语句
	public static String deleteKeySql;
	//号码入库线程数
	public static int processThreadNum;
	
	//一次批量入库数量
    public static int oneBatchNum;
	//号码表名
    public static String ractNumTable;
    //号码入库SQL
  	public static String insertSql;
    //统计待下发数据SQL
    public static String countRactNumSql;
	//删除号码
    public static String delRactNumSql;
	//批量下发SQL
    public static String insertBatchSql;
	//单条下发SQL
    public static String insertSingleSql;
    //下发日志记录
    public static String recordLogSql;
    //是否允许10086端口下发
    public static boolean is10086Send;
    
    //查询用户未发送短信
    public static String queryWaitSendSmsSql;
    //查询用户已发送短信
    public static String querySendSmsSql;
    
    //增加群发屏蔽号码
    public static String insertShieldNumSql;
    //查询群发屏蔽号码
    public static String queryShieldNumSql;

	public static void init(String filePath) {
		JndiName = ReadEnv.readValue(filePath, "JndiName");
		queryUserSql = ReadEnv.readValue(filePath, "queryUserSql");
		dataFilePath = ReadEnv.readValue(filePath, "dataFilePath");
		movePath = ReadEnv.readValue(filePath, "movePath");
		
		getSendKeySql = ReadEnv.readValue(filePath, "getSendKeySql");
		insertKeySql = ReadEnv.readValue(filePath, "insertKeySql");
		queryKeySql = ReadEnv.readValue(filePath, "queryKeySql");
		updateKeySql = ReadEnv.readValue(filePath, "updateKeySql");
		deleteKeySql = ReadEnv.readValue(filePath, "deleteKeySql");
		processThreadNum = ReadEnv.readIntValue(filePath, "processThreadNum");
		
		insertSql = ReadEnv.readValue(filePath, "insertSql");
		countRactNumSql = ReadEnv.readValue(filePath, "countRactNumSql");
		oneBatchNum = ReadEnv.readIntValue(filePath, "oneBatchNum");
		ractNumTable = ReadEnv.readValue(filePath, "ractNumTable");
		delRactNumSql = ReadEnv.readValue(filePath, "delRactNumSql");
		insertBatchSql = ReadEnv.readValue(filePath, "insertBatchSql");
		insertSingleSql = ReadEnv.readValue(filePath, "insertSingleSql");
		
		recordLogSql = ReadEnv.readValue(filePath, "recordLogSql");
		is10086Send = Boolean.parseBoolean(ReadEnv.readValue(filePath, "is10086Send"));
		
		queryWaitSendSmsSql = ReadEnv.readValue(filePath, "queryWaitSendSmsSql");
		querySendSmsSql = ReadEnv.readValue(filePath, "querySendSmsSql");
		insertShieldNumSql = ReadEnv.readValue(filePath, "insertShieldNumSql");
		queryShieldNumSql = ReadEnv.readValue(filePath, "queryShieldNumSql");
	}

	public static void reInit(String path) {
		init(path);
	}

}
