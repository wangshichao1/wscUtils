package com.wsc.utils.sftp;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPChannel {
    private Session session = null;
    private Channel channel = null;

    private static final Logger LOG = Logger.getLogger(SFTPChannel.class);
    private static String sftpInfoFile = "/config.properties";
    
    public SFTPChannel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * 获取sftp通道
     * @param timeout
     * @return
     * @throws JSchException
     */
    public ChannelSftp getChannel(int timeout){
    	/**
    	 * 加载Sftp主机信息
    	 */
    	SftpInfo.init(sftpInfoFile);
        String ftpHost = SftpInfo.SFTP_REQ_HOST;
        int port = SftpInfo.SFTP_REQ_PORT;
        String ftpUserName = SftpInfo.SFTP_REQ_USERNAME;
        String ftpPassword = SftpInfo.SFTP_REQ_PASSWORD;

        int ftpPort = SftpInfo.SFTP_DEFAULT_PORT;
        if (port != 0) {
            ftpPort = port;
        }

        JSch jsch = new JSch(); // 创建JSch对象
        try {
			session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
			// 根据用户名，主机ip，端口获取一个Session对象
	        LOG.info("Session created.");
	        if (ftpPassword != null) {
	            session.setPassword(ftpPassword); // 设置密码
	        }
	        Properties config = new Properties();
	        config.put("StrictHostKeyChecking", "no");
	        session.setConfig(config); // 为Session对象设置properties
	        session.setTimeout(timeout); // 设置timeout时间
	        session.connect(); // 通过Session建立链接
	        LOG.info("Session connected.");

	        LOG.info("Opening Channel.");
	        channel = session.openChannel("sftp"); // 打开SFTP通道
	        channel.connect(); // 建立SFTP通道的连接
	        LOG.info("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName
	                + ", returning: " + channel);
	        return (ChannelSftp) channel;
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
    }
    
    /**
     * 上传文件
     * @param file
     * @param dst
     * @return
     */
    public boolean uploadFile(ChannelSftp channelSftp,File srcFile,String dst)
    {
		try {
			/*long fileSize = srcFile.length();
			FileProgressMonitor fileProgressMonitor = new FileProgressMonitor(fileSize);
			channelSftp.put(srcFile.getAbsolutePath(),dst, fileProgressMonitor,ChannelSftp.OVERWRITE);*/
			channelSftp.put(srcFile.getAbsolutePath(),dst,ChannelSftp.OVERWRITE);
	        return true;
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    }
    
    /**
     * 上传多个文件
     * @param file
     * @param dst
     * @return
     */
    public boolean uploadFileList(ChannelSftp channelSftp,List<File> fileList,String dst)
    {
		try {
			for(File srcFile : fileList)
			{
				/**
				 * 进度监控
				 */
				/*
				long fileSize = srcFile.length();
				FileProgressMonitor fileProgressMonitor = new FileProgressMonitor(fileSize);
				channelSftp.put(srcFile.getAbsolutePath(),dst, fileProgressMonitor,ChannelSftp.OVERWRITE);*/
				channelSftp.put(srcFile.getAbsolutePath(),dst,ChannelSftp.OVERWRITE);
			}
	        return true;
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    }
    
    /**
     * 上传多个文件
     * @param file
     * @param dst
     * @return
     */
    public boolean uploadFileList(ChannelSftp channelSftp,List<File> fileList)
    {
    	String dst = SftpInfo.SFTP_REQ_LOC;
		return uploadFileList(channelSftp, fileList, dst);
    }
    
    /**
     * 上传文件
     * @param file
     * @param dst
     * @return
     */
    public boolean uploadFile(ChannelSftp channelSftp,String srcFilePath,String dst)
    {
    	File srcFile = new File(srcFilePath);
    	return uploadFile(channelSftp, srcFile, dst);
    }
    
    /**
     * 上传文件
     * @param file
     * @param dst
     * @return
     */
    public boolean uploadFile(ChannelSftp channelSftp,File srcFile)
    {
    	String dst = SftpInfo.SFTP_REQ_LOC;
    	return uploadFile(channelSftp, srcFile, dst);		
    }
    
    /**
     * 关闭sftp通道
     * @throws Exception
     */
    public void closeChannel(){
        if (channel != null) {
        	try {
        		channel.disconnect();
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
        if (session != null) {
        	try {
        		session.disconnect();
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
    }
}