package com.w77996.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author w77996
 */
@Slf4j
public class CanalMessageHandler {


    /**完成的消息处理数*/
    private static final AtomicLong totalFinishCount = new AtomicLong(0L);
    /**累计完成消息处理的毫秒数*/
    private static final AtomicLong totalMillisCount = new AtomicLong(0L);
    /**上次的时间*/
    private static final AtomicLong lastTime = new AtomicLong(0L);
    /**速度统计间隔，5秒*/
    private static final long STAT_CYCLE_INTERVAL_MILLISSECONDS_5S=1000*5;
    /**
     * binlog format
     */
    private static String row_format = "binlog[{0}:{1}], name[{2}.{3}], id:{4}, eventType:{5}, executeTime:{6}, delay:{7}ms";


    private static final Map<String, AbstractCanalEntryHandler> entryHandlers = new HashMap<String, AbstractCanalEntryHandler>();

    /**
     * 多表处理器
     */
    private static final Pattern tableNamePattern = Pattern.compile("(.*_)([0-9]+)$");

    private static final Map<String, AbstractCanalEntryHandler> prefixEntryHandlers = new HashMap<String, AbstractCanalEntryHandler>();

    public CanalMessageHandler addEntryHandler(AbstractCanalEntryHandler entryHandler) {
        if(entryHandler.isMultiTable()){
            prefixEntryHandlers.put(entryHandler.getTableName(), entryHandler);
        }else{
            entryHandlers.put(entryHandler.getTableName(), entryHandler);
        }
        return this;
    }

    public void handler(Message message) throws Exception {
        List<CanalEntry.Entry> list = message.getEntries();
        for (CanalEntry.Entry entry : list) {
            /**比较数据库是否在指定书库中*/
//            if(!schemaContains(entry.getHeader().getSchemaName())) {
//                continue;
//            }
            long executeTime = entry.getHeader().getExecuteTime();
            long delayTime = System.currentTimeMillis() - executeTime;

            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                //事务开始事件处理
                if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN) {
                    CanalEntry.TransactionBegin begin = null;
                    try {
                        begin = CanalEntry.TransactionBegin.parseFrom(entry.getStoreValue());
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                    }
                    // 打印事务头信息，执行的线程id，事务耗时
                    log.debug(" BEGIN ----> Thread id: " + begin.getThreadId());
                } else if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                    CanalEntry.TransactionEnd end = null;
                    try {
                        end = CanalEntry.TransactionEnd.parseFrom(entry.getStoreValue());
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                    }
                    // 打印事务提交信息，事务id
                    log.debug(" END ----> transaction id: " + end.getTransactionId());
                }

                continue;
            }

            String simpleTableName = entry.getHeader().getTableName();
            // 获取单表处理器
            AbstractCanalEntryHandler entryHandler = entryHandlers.get(simpleTableName);
            // 根据前缀获取多表处理器
            if(entryHandler==null){
                Matcher matcher = tableNamePattern.matcher(simpleTableName);
                if(matcher.find()){
                    entryHandler = prefixEntryHandlers.get(matcher.group(1));
                }
            }
            if (entry.getEntryType() == CanalEntry.EntryType.ROWDATA && entryHandler != null) {
                CanalEntry.RowChange rowChange = null;
                try {
                    rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                } catch (Exception e) {
                    throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                }

                CanalEntry.EventType eventType = rowChange.getEventType();
                for (CanalEntry.RowData row : rowChange.getRowDatasList()) {
                    if(eventType != CanalEntry.EventType.DELETE && eventType != CanalEntry.EventType.UPDATE && eventType != CanalEntry.EventType.INSERT) {
                        continue;
                    }
                    String id = "0";
                    long start = System.currentTimeMillis();
                    String tableName = entry.getHeader().getSchemaName()+"."+entry.getHeader().getTableName();
                    if(eventType == CanalEntry.EventType.DELETE) {
                        id = entryHandler.getBeforeId(row);
                        entryHandler.deleteEntry(tableName, id, row);
                    } else if(eventType == CanalEntry.EventType.UPDATE) {
                        id = entryHandler.getAfterId(row);
                        entryHandler.updateEntry(tableName, id, row);
                    } else if(eventType == CanalEntry.EventType.INSERT) {
                        id = entryHandler.getAfterId(row);
                        entryHandler.insertEntry(tableName, id, row);
                    }
                    //在Canal执行的消息延迟超过1s或者Canal处理单条消息的时间超过10ms则输出告警日志
                    long msgHandleMillis = System.currentTimeMillis()-start;
                    boolean delayOrHandleLongTime = delayTime >= 1000 || msgHandleMillis > 10;
                    //以5秒为一个周期，统计Canal消息处理速度
                    long totalMsgCount = totalFinishCount.incrementAndGet();
                    long totalMsgTime = totalMillisCount.addAndGet(msgHandleMillis);
                    long intervalMillis = System.currentTimeMillis()-lastTime.get();
                    boolean logSpeed = intervalMillis >= STAT_CYCLE_INTERVAL_MILLISSECONDS_5S;
                    if(delayOrHandleLongTime || logSpeed){
                        log.warn(MessageFormat.format(row_format, entry.getHeader().getLogfileName(),
                                String.valueOf(entry.getHeader().getLogfileOffset()), entry.getHeader().getSchemaName(),
                                entry.getHeader().getTableName(), id==null ? "no_primary_key" : id, eventType,
                                String.valueOf(entry.getHeader().getExecuteTime()), String.valueOf(delayTime)) + ";handleTime=" + msgHandleMillis);
                    }
                    if(logSpeed){
                        log.info("YYtingMessageHandleSpeed=" + ( totalMsgCount / (intervalMillis /1000) ) +"tps,avgTime="  + ( totalMsgTime / totalMsgCount));
                        totalFinishCount.set(0);
                        totalMillisCount.set(0L);
                        lastTime.set(System.currentTimeMillis());
                    }
                }
            }

        }
    }
}
