package com.w77996.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.w77996.common.util.ContextUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author w77996
 */
@Slf4j
public class HiCanalClient extends AbstractCanalClient {

    private static String zkServers = "119.23.61.10:8017";

    public HiCanalClient(String destination) {
        super(destination);
    }

    public HiCanalClient(String destination, CanalConnector connector) {
        super(destination, connector);
    }

    public void init() {
        log.info(" -------------- start YYtingCanalClient ------------ ");

        //添加message handler
        messageHandler = new CanalMessageHandler();

        // 添加表处理器，包括多表和单表
        log.info("do register YYtingEntryHandler      ------------ ");
        List<AbstractCanalEntryHandler> handlerList = ContextUtils.getBeans(AbstractCanalEntryHandler.class);
        for(AbstractCanalEntryHandler handler : handlerList){
            messageHandler.addEntryHandler(handler);
            log.info("Register AbstractCanalEntryHandler: isMultiTable=" + handler.isMultiTable() + "\t tableName=" + handler.getTableName() + "\t primaryKey=" + (handler.getPrimaryKey()==null ? "no_simple_primary_key" : handler.getPrimaryKey()) + "\t handlerClass=" + handler.getClass());
        }
        log.info("AbstractCanalEntryHandler register finish   ------------ ");

//        // 添加表处理器，包括多表和单表
//        log.info("do register IBatchCommit      ------------ ");
//        List<IBatchCommit> batchCommits = ContextUtils.getBeans(IBatchCommit.class);
//        for(IBatchCommit handler : batchCommits){
//            messageHandler.addBatchCommit(handler);
//            log.info("Register IBatchCommit: handlerClass=" + handler.getClass());
//        }
//        log.info("IBatchCommit register finish   ------------ ");

        log.info("canal info, zkServers=" + zkServers + " destination=" + destination);
        CanalConnector connector = CanalConnectors.newClusterConnector(zkServers, destination, "", "");
        this.setMessageHandler(messageHandler);
        this.setConnector(connector);
        super.start();
        log.info(" -------------- YYtingCanalClient runing ------------ ");
    }
}
