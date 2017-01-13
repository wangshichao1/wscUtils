package com.wsc.utils.sftp;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.jcraft.jsch.SftpProgressMonitor;

public class FileProgressMonitor extends TimerTask implements SftpProgressMonitor {
    private Logger log = Logger.getLogger(FileProgressMonitor.class);
    
    private long progressInterval = 5 * 1000; // Ĭ�ϼ��ʱ��Ϊ5��
    
    private boolean isEnd = false; // ��¼�����Ƿ����
    
    private long transfered; // ��¼�Ѵ���������ܴ�С
    
    private long fileSize; // ��¼�ļ��ܴ�С
    
    private Timer timer; // ��ʱ������
    
    private boolean isScheduled = false; // ��¼�Ƿ�������timer��ʱ��
    
    public FileProgressMonitor(long fileSize) {
        this.fileSize = fileSize;
    }
    
    @Override
    public void run() {
        if (!isEnd()) { // �жϴ����Ƿ��ѽ���
            log.info("Transfering is in progress.");
            long transfered = getTransfered();
            if (transfered != fileSize) { // �жϵ�ǰ�Ѵ������ݴ�С�Ƿ�����ļ��ܴ�С
                log.info("Current transfered: " + transfered + " bytes");
                sendProgressMessage(transfered);
            } else {
                log.info("File transfering is done.");
                setEnd(true); // �����ǰ�Ѵ������ݴ�С�����ļ��ܴ�С��˵������ɣ�����end
            }
        } else {
            log.info("Transfering done. Cancel timer.");
            stop(); // ������������ֹͣtimer��ʱ��
            return;
        }
    }
    
    public void stop() {
        log.info("Try to stop progress monitor.");
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
            isScheduled = false;
        }
        log.info("Progress monitor stoped.");
    }
    
    public void start() {
        log.info("Try to start progress monitor.");
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(this, 1000, progressInterval);
        isScheduled = true;
        log.info("Progress monitor started.");
    }
    
    /**
     * ��ӡprogress��Ϣ
     * @param transfered
     */
    private void sendProgressMessage(long transfered) {
        if (fileSize != 0) {
            double d = ((double)transfered * 100)/(double)fileSize;
            DecimalFormat df = new DecimalFormat( "#.##"); 
            log.info("Sending progress message: " + df.format(d) + "%");
        } else {
            log.info("Sending progress message: " + transfered);
        }
    }

    /**
     * ʵ����SftpProgressMonitor�ӿڵ�count����
     */
    public boolean count(long count) {
        if (isEnd()) return false;
        if (!isScheduled) {
            start();
        }
        add(count);
        return true;
    }

    /**
     * ʵ����SftpProgressMonitor�ӿڵ�end����
     */
    public void end() {
        setEnd(true);
        log.info("transfering end.");
    }
    
    private synchronized void add(long count) {
        transfered = transfered + count;
    }
    
    private synchronized long getTransfered() {
        return transfered;
    }
    
    public synchronized void setTransfered(long transfered) {
        this.transfered = transfered;
    }
    
    private synchronized void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }
    
    private synchronized boolean isEnd() {
        return isEnd;
    }

    public void init(int op, String src, String dest, long max) {
        // Not used for putting InputStream
    }
}