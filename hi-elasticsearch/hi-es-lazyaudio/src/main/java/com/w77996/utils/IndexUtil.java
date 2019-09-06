package com.w77996.utils;

import com.w77996.es.IIndex;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class IndexUtil {

    /**
     * 日志目录
     */
    private final String logPath;

    public IndexContext(IIndex index, MessageConsumer messageConsumer){
        this.logger = LoggerFactory.getLogger("SEARCH-"+IndexContext.class.getSimpleName()+"$"+index.getIndexName());
        this.index = index;
        this.logPath = "";
        this.onlineIndex = new OnlineIndex(this);
        this.rebuildIndex = new RebuildIndex(this);
        this.messageConsumer = messageConsumer;
        aOnline = new AtomicBoolean(true);
    }
}
