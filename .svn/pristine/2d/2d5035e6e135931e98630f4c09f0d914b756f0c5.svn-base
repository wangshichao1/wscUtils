package com.wsc.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.TimeZone;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * 
 * @author wsc 用于上传文件到ftp服务器或是从ftp服务器下载文件
 */
public class FtpClientImpl {

	private Logger log = Logger.getLogger(FtpClientImpl.class);
	private FTPClient ftpClient;
	private FtpInfoBean ftpInfoBean;

	public FtpClientImpl(FtpInfoBean ftpInfoBean) {
		this.ftpInfoBean = ftpInfoBean;
		this.ftpClient = new FTPClient();
	}

	public FtpInfoBean getFtpInfoBean() {
		return ftpInfoBean;
	}

	public void setFtpInfoBean(FtpInfoBean ftpInfoBean) {
		this.ftpInfoBean = ftpInfoBean;
	}

	/**
	 * @return 判断是否登入成功
	 * @throws IOException
	 * @throws SocketException
	 * */
	public boolean ftpLogin() {
		log.info("ftpLogin start");
		boolean isLogin = false;
		FTPClientConfig ftpClientConfig = new FTPClientConfig();
		ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
		this.ftpClient.setControlEncoding(ftpInfoBean.getEncode());
		this.ftpClient.configure(ftpClientConfig);
		try {
			String ip = ftpInfoBean.getIp();
			int port = ftpInfoBean.getPort();
			String user = ftpInfoBean.getUser();
			String password = ftpInfoBean.getPassword();
			log.info("FTP服务器信息：IP：[" + ip + "] PORT:[" + port + "] USER:["
					+ user + "]");
			log.info("连接FTP服务器");
			if (port > 0) {
				this.ftpClient.connect(ip, port);
			} else {
				this.ftpClient.connect(ip);
			}
			// FTP服务器连接回答
			int reply = this.ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				this.ftpClient.disconnect();
				return isLogin;
			} else {
				log.info("登陆FTP服务器");
				boolean login = this.ftpClient.login(user, password);
				if (login) {
					// 设置传输协议
					this.ftpClient.enterLocalPassiveMode();
					this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
					log.info("登陆FTP服务器成功");
					isLogin = true;
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			log.info("登陆FTP服务器失败");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("登陆FTP服务器失败");
			e.printStackTrace();
		}

		this.ftpClient.setBufferSize(1024 * 2);
		this.ftpClient.setDataTimeout(60 * 1000);
		return isLogin;
	}

	/**
	 * 退出关闭服务器链接
	 * 
	 * @throws IOException
	 */
	public void ftpLogOut() {
		log.info("注销登陆开始......");
		try {
			if (null != this.ftpClient && this.ftpClient.isConnected()) {
				this.ftpClient.logout();
				this.ftpClient.disconnect();// 关闭FTP服务器的连接
				log.info("注销登陆成功！");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("注销登陆失败！");
		}
	}

	/***
	 * 上传Ftp文件
	 * 
	 * @param localFile
	 *            当地文件
	 * @param romotUpLoadePath上传服务器路径
	 *            - 应该以/结束
	 * @throws IOException
	 * */
	public boolean uploadFile(File localFile, String remotePath) {
		log.info("uploadFile start");
		log.info("文件名：[" + localFile.getName() + "] 文件要保存在FTP的路径：["
				+ remotePath + "]");
		BufferedInputStream inStream = null;
		boolean success = false;
		try {
			this.ftpClient.makeDirectory(remotePath);
			this.ftpClient.changeWorkingDirectory(remotePath);
			inStream = new BufferedInputStream(new FileInputStream(localFile));
			log.info("开始上传.....");
			success = this.ftpClient.storeFile(localFile.getName(), inStream);
			if (success == true) {
				log.info("上传成功");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			log.info("上传失败");
			e1.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		log.info("uploadFile end");
		return success;
	}

	/***
	 * 下载文件
	 * 
	 * @param remoteFileName
	 *            待下载文件名称
	 * @param localDires
	 *            下载到当地那个路径下
	 * @param remoteDownLoadPath
	 *            remoteFileName所在的路径
	 * @throws IOException
	 * */
	public boolean downloadFile(String fileName, String localDires,
			String remoteDownLoadPath) {
		log.info("downloadFile start");
		log.info("下载文件名：[" + fileName + "] 文件在FTP的路径：[" + remoteDownLoadPath
				+ "] 文件要保存的路径：[" + localDires + "]");
		// 组装目的文件路径
		String strFilePath = null;
		if (localDires.endsWith(File.separator)) {
			strFilePath = localDires + fileName;
		} else {
			strFilePath = localDires + File.separator + fileName;
		}
		// 如果文件路径不存在，则创建该路径
		File file = new File(strFilePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		BufferedOutputStream outStream = null;
		boolean success = false;
		try {
			this.ftpClient.changeWorkingDirectory(remoteDownLoadPath);
			outStream = new BufferedOutputStream(new FileOutputStream(
					strFilePath));
			log.info("开始下载文件....");
			success = this.ftpClient.retrieveFile(fileName, outStream);
			if (success) {
				log.info(fileName + "成功下载到" + strFilePath);
				return success;
			}else{
				log.info(fileName + "下载失败");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (null != outStream) {
				try {
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		log.info("downloadFile end");
		return success;
	}

	/***
	 * 下载目录下所有文件
	 * 
	 * @param remoteFileName
	 *            待下载文件名称
	 * @param localDires
	 *            下载到当地那个路径下
	 * @param remoteDownLoadPath
	 *            remoteFileName所在的路径
	 * @throws IOException
	 * */
	public boolean downAllFiles(String remoteDownLoadPath) {
		log.info("downAllFiles start......");
		log.info("FTP文件夹的路径：[" + remoteDownLoadPath + "] 文件要保存的路径：["
				+ ftpInfoBean.getLocalFilePath() + "]");
		FTPFile[] ftpFileArr = null;
		try {
			ftpFileArr = ftpClient.listFiles(remoteDownLoadPath);
		} catch (IOException e) {
			log.error("下载文件失败：" + e);
			return false;
		}
		if (ftpFileArr.length == 0) {
			return false;
		}
		for (FTPFile ftpFile : ftpFileArr) {
			String fileName = ftpFile.getName();
			downloadFile(fileName, ftpInfoBean.getLocalFilePath(),
					remoteDownLoadPath);
		}
		return true;
	}

	/**
	 * 转移到FTP服务器工作目录
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean changeDirectory(String path) throws IOException {
		return ftpClient.changeWorkingDirectory(path);
	}

	/**
	 * 在服务器上创建目录
	 * 
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
	public boolean createDirectory(String pathName) throws IOException {
		return ftpClient.makeDirectory(pathName);
	}

	/**
	 * 在服务器上删除目录
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean removeDirectory(String path) throws IOException {
		return ftpClient.removeDirectory(path);
	}

	/**
	 * 检查目录在服务器上是否存在 true：存在 false：不存在
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean existDirectory(String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		for (FTPFile ftpFile : ftpFileArr) {
			if (ftpFile.isDirectory()
					&& ftpFile.getName().equalsIgnoreCase(path)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 返回ftp文件目录
	 */
	public FTPFile[] getFileList(String path) {
		FTPFile[] ftpFiles = null;
		try {
			ftpFiles = ftpClient.listFiles(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ftpFiles;
	}

	/**
	 * 删除服务器上的文件
	 * 
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(String path,String pathName) throws IOException {
		ftpClient.changeWorkingDirectory(path);
		return ftpClient.deleteFile(pathName);
	}

	/**
	 * 上传文件到ftp服务器 在进行上传和下载文件的时候，设置文件的类型最好是：
	 * ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE) localFilePath:本地文件路径和名称
	 * remoteFileName:服务器文件名称
	 */
	public boolean uploadFile(String localFilePath, String remoteFileName)
			throws IOException {
		boolean flag = false;
		InputStream iStream = null;
		try {
			iStream = new FileInputStream(localFilePath);
			flag = ftpClient.storeFile(remoteFileName, iStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	/**
	 * 上传文件到ftp服务器，上传新的文件名称和原名称一样
	 * 
	 * @param fileName
	 *            ：文件名称
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(String fileName) throws IOException {
		return uploadFile(fileName, fileName);
	}

	/**
	 * 上传文件到ftp服务器
	 * 
	 * @param iStream
	 *            输入流
	 * @param newName
	 *            新文件名称
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(InputStream iStream, String newName)
			throws IOException {
		boolean flag = false;
		try {
			flag = ftpClient.storeFile(newName, iStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	/**
	 * 移动文件到新目录
	 * 
	 * @param fileName
	 * @param fromPath
	 * @param toPath
	 * @return
	 */
	public boolean removeFtpFile(String fileName, String fromPath, String toPath) {
		boolean flag = false;
		log.info("开始移动文件：" + fileName + ",从目录：[" + fromPath + "]移动到[" + toPath
				+ "]");
		ByteArrayInputStream in = null;
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		try {
			String sourFilePath = fromPath + File.separator + fileName;
			ftpClient.makeDirectory(toPath);
			// 变更工作路径
			ftpClient.changeWorkingDirectory(fromPath);
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.retrieveFile(sourFilePath, fos);
			in = new ByteArrayInputStream(fos.toByteArray());
			if (in != null) {
				ftpClient.changeWorkingDirectory(toPath);
				flag = ftpClient.storeFile(fileName, in);
			}
			if (flag) {
				log.info("复制文件[" + fileName + "]成功");
				// 删除文件
				boolean isDel = deleteFile(fromPath,fileName);
				if(isDel)
				{
					log.info("从源目录移除文件[" + fileName + "]成功");
				}
				else{
					log.info("从源目录移除文件[" + fileName + "]失败");
				}
			} else {
				log.error("移动文件[" + fileName + "]失败");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("移动文件[" + fileName + "]失败" + e);
		} finally {
			// 关闭流
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
}
