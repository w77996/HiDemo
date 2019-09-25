package com.w77996.index;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class OnlineIndex {

    private static final int MAX_RETRY_COUNT = 3;

    /**
     * 是否记录change log
     */
    private final AtomicBoolean doChangeLog;

    /**
     * change log 中的日子行数
     */
    private final AtomicInteger writeChangeLogCount;

    /**
     * 索引上下文
     */
    private final IndexContext indexContext;

    /**
     * change log writer
     */
    private PrintWriter changeLogWriter;

    public OnlineIndex(IndexContext indexContext){
        this.indexContext = indexContext;
        this.writeChangeLogCount = new AtomicInteger(0);
        this.doChangeLog = new AtomicBoolean(false);

        log.info("构建 OnlineIndex 进行 "+indexContext.getIndex().getIndexName()+" 索引业务，"+"负责处理"+indexContext.getIndex().getTableNames()+"表的数据");
    }

    /**
     * 是否在做变更日志
     * @return
     */
    public boolean isChangeLogDoing(){
        return doChangeLog.get();
    }

    /**
     * 记录数据变更日志
     * @return 是否执行成功， false：change log日志在调用前已经被开始
     * @throws IOException
     */
    public void startChangeLog() {
        // 快速失败
        if(doChangeLog.get()){
            log.error("错误的调用时机，change log 已经开启");
//            throw new IndexException("change log 已经开启");
            return;
        }

        // 日志状态切换
        synchronized (this) {
            if(doChangeLog.get()){
                log.error("错误的调用时机，change log 已经开启");
//                throw new IndexException("change log 已经开启");
                return;
            }

            log.info("开始执行 change log ...");

            closeChangeLogWriter();

            File logFile = new File(indexContext.getChangeLogFilePath());
            log.info("删除 change log， file:"+logFile.getAbsolutePath());
            if(logFile.exists()){
                logFile.delete();
            }

            // logFile.createNewFile();

            try {
                // TODO 字符集设置
                changeLogWriter = new PrintWriter(new FileWriter(logFile, false), true);
            } catch (IOException e) {
                log.error("打开 change log 输出流失败， file:"+logFile.getAbsolutePath(), e);
//                throw new IndexException("打开 change log 输出流失败");
                return;
            }

            doChangeLog.set(true);
            writeChangeLogCount.set(0);

            log.info(" ------  change log 被打开，将开始记录变更    ------  ");
        }
    }

    /**
     * 停止数据变更日志
     * @return 是否执行成功， false：change log日志在调用之前已经被停止
     */
    public void stopChangeLog() {
        // 快速失败
        if(!doChangeLog.get()){
            log.error("错误的调用时机，change log 已经关闭");
//            throw new IndexException("change log 已经关闭");
            return;
        }

        // 日志状态切换
        synchronized (this) {
            if(!doChangeLog.get()){
                log.error("错误的调用时机，change log 已经关闭");
//                throw new IndexException("change log 已经关闭");
                return;
            }

            log.info("开始关闭 change log ...");

            doChangeLog.set(false);
            closeChangeLogWriter();

            log.info(" ------  change log 被关闭，变更不再记录    ------  ");
        }
    }

    /**
     * 安全关闭 change log
     */
    private synchronized void closeChangeLogWriter(){
        if(changeLogWriter!=null){
            changeLogWriter.flush();
            changeLogWriter.close();
            changeLogWriter = null;
            log.info("关闭 changeLogWriter");
        }
    }

    /**
     * 获取当前记录的变更日志行数
     * @return
     */
    public int getWriteChangeLogCount() {
        return writeChangeLogCount.get();
    }


}
