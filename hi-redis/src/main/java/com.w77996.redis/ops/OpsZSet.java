package com.w77996.redis.ops;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Set;

/**
 * @description:
 * @author: w77996
 * @create: 2018-10-22 10:09
 **/
@RestController
@Slf4j
public class OpsZSet {
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/add")
    public void add(){
        redisTemplate.opsForZSet().add("zSetValue","A",1);
        redisTemplate.opsForZSet().add("zSetValue","B",2);
        redisTemplate.opsForZSet().add("zSetValue","C",2);
        redisTemplate.opsForZSet().add("zSetValue","D",5);
        redisTemplate.opsForZSet().add("zSetValue","E",5);
        log.info("success");

    }

    @GetMapping("/range")
    public void range(){
        Set zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1);
        System.out.println("通过range(K key, long start, long end)方法获取指定区间的元素:" + zSetValue);
    }

    @GetMapping("/ops")
    public void ops() {

        redisTemplate.opsForZSet().add("zSetValue","A",1);
        redisTemplate.opsForZSet().add("zSetValue","B",2);
        redisTemplate.opsForZSet().add("zSetValue","C",3);
        redisTemplate.opsForZSet().add("zSetValue","D",4);
        redisTemplate.opsForZSet().add("zSetValue","D",5);
        log.info("success");

        Set zSetValue = redisTemplate.opsForZSet().range("zSetValue", 0, -1);
        System.out.println("通过range(K key, long start, long end)方法获取指定区间的元素:" + zSetValue);
//
//        long count = redisTemplate.opsForZSet().count("zSetValue", 0, -1);
//        System.out.println("通过count(K key, double min, double max)方法获取区间值的个数:" + count);
//
//        long index = redisTemplate.opsForZSet().rank("zSetValue", "B");
//        System.out.println("通过rank(K key, Object o)方法获取变量中元素的索引:" + index);
//
//        double score = redisTemplate.opsForZSet().score("zSetValue", "D");
//        System.out.println("通过score(K key, Object o)方法获取元素的分值:" + score);
//
//        long zCard = redisTemplate.opsForZSet().zCard("zSetValue");
//        System.out.println("通过zCard(K key)方法获取变量的长度:" + zCard);
//
//        double incrementScore = redisTemplate.opsForZSet().incrementScore("zSetValue", "B", 5);
//        System.out.println("通过incrementScore(K key, V value, double delta)方法修改变量中的元素的分值:" + incrementScore);
//        score = redisTemplate.opsForZSet().score("zSetValue", "B");
//        System.out.print(",修改后获取元素的分值:" + score);
//        zSetValue = redisTemplate.opsForZSet().range("zSetValue", 0, -1);
//        System.out.print("，修改后排序的元素:" + zSetValue);
//
//        index = redisTemplate.opsForZSet().rank("zSetValue","B");
//        System.out.println("通过rank(K key, Object o)方法获取变量中元素的索引:" + index);
//        zSetValue = redisTemplate.opsForZSet().reverseRange("zSetValue", 0, -1);
//        System.out.println("通过reverseRange(K key, long start, long end)方法倒序排列元素:" + zSetValue);
//
//        long reverseRank = redisTemplate.opsForZSet().reverseRank("zSetValue","B");
//        System.out.println("通过reverseRank(K key, Object o)获取倒序排列的索引值:" + reverseRank);
//
//        zSetValue = redisTemplate.opsForZSet().reverseRangeByScore("zSetValue", 1, 5);
//        System.out.println("通过reverseRangeByScore(K key, double min, double max)方法倒序排列指定分值区间元素:" + zSetValue);
//
//        zSetValue = redisTemplate.opsForZSet().reverseRangeByScore("zSetValue", 1, 5, 1, 2);
//        System.out.println("通过reverseRangeByScore(K key, double min, double max, long offset, long count)方法倒序排列从给定下标和给定长度分值区间元素:" + zSetValue);

//        long removeCount = redisTemplate.opsForZSet().removeRangeByScore("zSetValue",3,5);
//        zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1);
//        System.out.print("通过removeRangeByScore(K key, double min, double max)方法移除元素的个数:" + removeCount);
//        System.out.println(",移除后剩余的元素:" + zSetValue);
//
//
//        redisTemplate.opsForZSet().add("zSetValue","A",1);
//        redisTemplate.opsForZSet().add("zSetValue","B",2);
//        redisTemplate.opsForZSet().add("zSetValue","C",3);
//        redisTemplate.opsForZSet().add("zSetValue","D",4);
//        redisTemplate.opsForZSet().add("zSetValue","E",5);
//        log.info("success");
//
//        removeCount = redisTemplate.opsForZSet().removeRange("zSetValue",1,-1);
//        zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1);
//        System.out.print("通过removeRange(K key, long start, long end)方法移除元素的个数:" + removeCount);
//        System.out.println(",移除后剩余的元素:" + zSetValue);

//        Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().reverseRangeByScoreWithScores("zSetValue", 1, 5);
//        iterator = typedTupleSet.iterator();
//        while (iterator.hasNext()) {
//            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
//            Object value = typedTuple.getValue();
//            double score1 = typedTuple.getScore();
//            System.out.println("通过reverseRangeByScoreWithScores(K key, double min, double max)方法倒序排序获取RedisZSetCommands.Tuples的区间值:" + value + "---->" + score1);
//        }
        Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().reverseRangeWithScores("zSetValue",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = typedTupleSet.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            Object value = typedTuple.getValue();
            double score = typedTuple.getScore();
            System.out.println("通过rangeWithScores(K key, long start, long end)方法获取RedisZSetCommands.Tuples的区间值:" + value + "---->" + score );
        }
    }
}
