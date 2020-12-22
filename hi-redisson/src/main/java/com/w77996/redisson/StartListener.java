package com.w77996.redisson;

import cn.hutool.core.date.DateUtil;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.client.protocol.decoder.CodecDecoder;
import org.redisson.codec.FstCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
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
//        redissonClient.getScoredSortedSet("zp", new JsonJacksonCodec()).add(Double.valueOf(System.currentTimeMillis()), "123");
//        Set<Map.Entry<Object, Object>> entries = redissonClient.getMap("1", new JsonJacksonCodec()).entrySet();

//        redissonClient.getScoredSortedSet("zp",new JsonJacksonCodec()).("123",1);
//        new Thread(() -> take()).start();
//        RBlockingQueue<String> blockingFairQueue = redissonClient.getBlockingQueue("delay_queue",new JsonJacksonCodec());
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
//        RMap<Object, Object> map = redissonClient.getMap("KEY_H_PROVIDER_ONLINE_COUNT_PREFIX:2020-10-26",new JsonJacksonCodec());
//        RMap<Object, Object> map = redissonClient.getMap("1",new JsonJacksonCodec());

//        redissonClient.getMap("KEY_H_PROVIDER_ONLINE_COUNT_PREFIX:2020-10-29", new JsonJacksonCodec()).put("659240447069126656",2);
//        RMap<Object, Object> map  = redissonClient.getMap("KEY_H_PROVIDER_ONLINE_COUNT_PREFIX:2020-10-27", new JsonJacksonCodec());
//        RMap<Long, Integer> map1  = redissonClient.getMap("KEY_H_PROVIDER_ONLINE_COUNT_PREFIX:2020-10-26", new JsonJacksonCodec());
//        RMap<Long, Integer> map2  = redissonClient.getMap("KEY_H_PROVIDER_ONLINE_COUNT_PREFIX:2020-10-31", new SerializationCodec());
//         redissonClient.getMap("KEY_H_PROVIDER_ONLINE_COUNT_PREFIX:2020-11-31", new JsonJacksonCodec()).put("1","1");
//        System.out.println(map.get(659240447069126656L));
//        System.out.println(map1.get(659240447069126656L));
//        System.out.println(map2.get(Long.parseLong("659240447069126656")));
//        Object o = redissonClient.getBucket("KEY_H_PROVIDER_ONLINE_COUNT_PREFIX:659240447069126656:2020-10-31", new JsonJacksonCodec()).get();
        String Z_HAND_PRINT = "Z_HAND_PRINT";
        String Z_PRINT = "Z_PRINT";
        String S_PRE_PRINT = "S_PRE_PRINT";
        long nowTime = System.currentTimeMillis();
        String orderNo = "658423424298523492";
        Double o = redissonClient.getScoredSortedSet(Z_HAND_PRINT,new JsonJacksonCodec()).getScore(orderNo);
        System.out.println(o);
    }

    private void take(){
        RBlockingQueue<String> blockingFairQueue = redissonClient.getBlockingQueue("delay_queue",new JsonJacksonCodec());
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
