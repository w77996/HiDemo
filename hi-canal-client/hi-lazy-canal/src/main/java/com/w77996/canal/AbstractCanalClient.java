package com.w77996.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * @author w77996
 */
@Slf4j
public class AbstractCanalClient {

    protected String destination;
    protected CanalConnector connector;
    protected CanalMessageHandler messageHandler;
    protected volatile boolean running = false;
    protected Thread.UncaughtExceptionHandler handler = (t, e) -> log.error("parse events has an error", e);
    protected Thread thread = null;

    public AbstractCanalClient(String destination, CanalConnector connector){
        this.destination = destination;
        this.connector = connector;
    }

    public AbstractCanalClient(String destination){
        this(destination, null);
    }

    /**
     * 异步线程启动canal client
     */
    protected void start() {
        Assert.notNull(connector, "connector is null");
        Assert.notNull(messageHandler, "message handler is empty");
        thread = new Thread(() -> process());
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
        running = true;
    }

    /**
     * 处理canal消息，不断获取未ack的消息
     */
    protected void process() {
        int batchSize = 5 * 1024;
        while (running) {
            try {
                log.info("======canal start============");

                connector.connect();
                connector.subscribe();
                while (running) {
                    // 获取指定数量的数据
                    Message message = connector.getWithoutAck(batchSize);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        //当没有获取到更新记录时，暂停100ms
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                    } else {
                        // 进行不停的尝试，直到业务处理成功
                        int sleepTime = 200;
                        while(true){
                            long batchStartTime = System.currentTimeMillis();
                            try {
                                messageHandler.handler(message);
                                long time = System.currentTimeMillis()-batchStartTime;
                                log.info("message handler entriy size="+size+" time="+time);
                                break;
                            } catch(Exception e) {
                                log.error("message handler error! entriy size="+size+" time="+(System.currentTimeMillis()-batchStartTime), e);
                                Thread.sleep(sleepTime);
                                sleepTime = sleepTime+200;
                                sleepTime = sleepTime>60000 ? 60000 : sleepTime;
                                // 处理失败, 回滚数据  （无限重试情况下不需要回滚）
                                // connector.rollback(batchId);
                            }
                        }
                    }
                    // 提交确认
                    if(batchId!=-1){
                        connector.ack(batchId);
                    }
                }
            } catch (Exception e) {
                log.error("process error!", e);
            } finally {
                connector.disconnect();
            }
        }
    }

    public void setConnector(CanalConnector connector) {
        this.connector = connector;
    }

    public void setMessageHandler(CanalMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

}
