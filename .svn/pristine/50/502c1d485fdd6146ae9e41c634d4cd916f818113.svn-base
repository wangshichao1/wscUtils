package com.wsc.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.wsc.utils.sftp.SFTPChannel;
/**
 * SFTP上传服务类
 * @author WSC
 */
@Service
public class SftpUpalodService {

	private SFTPChannel sftpChannel;
	public SftpUpalodService(){
		sftpChannel = new SFTPChannel();
	}
	
	/**
     * 上传文件
     * @param file
     * @param dst
     * @return
     */
    public boolean uploadFile(File srcFile)
    {
    	ChannelSftp channelSftp = sftpChannel.getChannel(60000);
        boolean res = sftpChannel.uploadFile(channelSftp, srcFile);
        sftpChannel.closeChannel();
        return res;
    }
    
    /**
     * 上传多个文件
     * @param file
     * @param dst
     * @return
     */
    public boolean uploadFileList(List<File> fileList)
    {
    	ChannelSftp channelSftp = sftpChannel.getChannel(60000);
        boolean res = sftpChannel.uploadFileList(channelSftp, fileList);
        sftpChannel.closeChannel();
        return res;
    }
}
