package com.w77996.redisson;

import cn.hutool.core.date.DateUtil;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName StartListener
 * @Description
 * @author wuhaihui
 * @date 2020/9/23 19:24
 */
@Component
public class StartListener implements CommandLineRunner {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> take()).start();
//        RBlockingQueue<String> blockingFairQueue = redissonClient.getBlockingQueue("delay_queue");
//
//        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
//
//        for (int i = 0; i < 10; i++) {
//            Thread.sleep(1000);
//            System.out.println("放入队列" + i);
//            delayedQueue.offer(i + " " + DateUtil.now(), 10, TimeUnit.SECONDS);
//        }
//        delayedQueue.destroy();
//        delayedQueue.offer( DateUtil.now(), 10, TimeUnit.SECONDS);
//        new Thread(() -> take()).start();
    }

    private void take(){
        RBlockingQueue<String> blockingFairQueue = redissonClient.getBlockingQueue("delay_queue");
        System.out.println("tack");
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        while (true) {
            String callCdr = null;
            try {
                callCdr = blockingFairQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("订单取消时间：" +DateUtil.now() + "==订单生成时间" + callCdr);
        }
    }
}
