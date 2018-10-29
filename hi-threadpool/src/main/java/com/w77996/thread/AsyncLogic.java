package com.w77996.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author wangzhiyong
 * @date 2018/4/20
 */
@Component
public class AsyncLogic implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(AsyncLogic.class);

    private ThreadFactory threadFactory;

    private ExecutorService executorWithRejectPolicy;

    private ExecutorService executorWithCallerRunPolicy;

    public void async(Runnable command) {
        this.async(command, false);
    }

    public void asyncForce(Runnable command) {
        this.async(command, true);
    }

    private void initExecutors(int threads) {
        this.executorWithCallerRunPolicy = new ThreadPoolExecutor(threads, threads, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                this.threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
        this.executorWithRejectPolicy = new ThreadPoolExecutor(threads, threads, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                this.threadFactory,
                new ThreadPoolExecutor.DiscardPolicy());
    }

    private void async(Runnable command, boolean callerRun) {
        ExecutorService executorService = callerRun ? executorWithCallerRunPolicy : executorWithRejectPolicy;
        executorService.execute(command);
    }

    @Override
    public void destroy() throws Exception {
        executorWithCallerRunPolicy.shutdown();
        executorWithRejectPolicy.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.threadFactory = new ThreadFactoryBuilder().setNameFormat("AsyncLogic-%d").setDaemon(true).build();
        initExecutors(20);
    }
}
