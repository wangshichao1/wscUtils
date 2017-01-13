package com.wsc.utils;


/**
 * @title: �����ò�����
 * @description:
 *
 * @author: yef
 */
public final class ParamDeploy {
	//JNDI����
	public static String JndiName;
	//��ѯ�û�SQL
	public static String queryUserSql;
	//�����ļ�·��
	public static String dataFilePath;
	//�ѵ��������ļ�·��
	public static String movePath;
	//��ȡ�·�key���
	public static String getSendKeySql;
	//����KEY���
	public static String insertKeySql;
	//��ѯKEY���
	public static String queryKeySql;
	//����KEY���
	public static String updateKeySql;
	//����KEY���
	public static String deleteKeySql;
	//��������߳���
	public static int processThreadNum;
	
	//һ�������������
    public static int oneBatchNum;
	//�������
    public static String ractNumTable;
    //�������SQL
  	public static String insertSql;
    //ͳ�ƴ��·�����SQL
    public static String countRactNumSql;
	//ɾ������
    public static String delRactNumSql;
	//�����·�SQL
    public static String insertBatchSql;
	//�����·�SQL
    public static String insertSingleSql;
    //�·���־��¼
    public static String recordLogSql;
    //�Ƿ�����10086�˿��·�
    public static boolean is10086Send;
    
    //��ѯ�û�δ���Ͷ���
    public static String queryWaitSendSmsSql;
    //��ѯ�û��ѷ��Ͷ���
    public static String querySendSmsSql;
    
    //����Ⱥ�����κ���
    public static String insertShieldNumSql;
    //��ѯȺ�����κ���
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
