package com.w77996.redis.lock;

import com.w77996.redis.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.concurrent.Callable;

/**
 * @description: 分布式所
 * @author: w77996
 * @create: 2018-10-16 09:17
 **/
@Component
@Slf4j
public class LockLogic<T> {

//    @Autowired
//    private Jedis redisTemplate;
//
//    public boolean tryLock(String key, int holdSeconds) {
//        /**
//         * 存储数据到缓存中，并制定过期时间和当Key存在时是否覆盖。
//         * @param key
//         * @param value
//         * @param nxxx nxxx的值只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
//         * @param expx expx的值只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒。
//         * @param time 过期时间，单位是expx所代表的单位。
//         * @return
//         */
//        String code = redisTemplate.set(key, "Y", "NX", "EX", holdSeconds);
//        return "OK".equals(code);
//    }
//
//    public String runWithLock(String key, int holdSeconds, boolean keep, Callable<T> callable) throws RuntimeException {
//        if (!tryLock(key, holdSeconds)) {
//            return "error";
//        }
//        try {
//            T result = callable.call();
//            return "success";
//        } catch (Exception e) {
//            log.error("run exception!", e);
//            if (e instanceof RuntimeException) {
//                throw (RuntimeException) e;
//            }
//            throw new RuntimeException(e);
//        } finally {
//            if (!keep) {
//                redisTemplate.del(key);
//            }
//        }
//    }
//
//    public <T> Result<T> runWithLockKeep(String key, int holdSeconds, Callable<T> callable) throws RuntimeException {
//        return runWithLock(key, holdSeconds, true, callable);
//    }
//
//    public <T> Result<T> runWithLockNoKeep(String key, int holdSeconds, Callable<T> callable) throws RuntimeException {
//        return runWithLock(key, holdSeconds, false, callable);
//    }
}
