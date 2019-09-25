package com.w77996.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class AbstractConsumer {


    /**完成的消息处理数*/
    protected final AtomicLong totalConsumerCount = new AtomicLong(0L);
    /**累计完成消息处理的毫秒数*/
    protected final AtomicLong totalMillisCount = new AtomicLong(0L);
    /**上次的时间*/
    protected final AtomicLong lastTime = new AtomicLong(0L);
    /**速度统计间隔，5秒*/
    protected final long STAT_CYCLE_INTERVAL_MILLSECONDS_5S=1000*5;

    protected final long COMMIT_INTERVAL_MILLSECONDS=1000*5;

    //protected ConsumerConnector consumer;

//    private final String zkConnect;
//    protected final String topicName;
//    private final String groupId;
//    private static int pauseTime;
//    private static long pauseStartTime;
}
