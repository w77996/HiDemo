package com;

import com.w77996.redis.RedisApplication;
import com.w77996.redis.core.BaseRedisDaoInter;
import com.w77996.redis.properties.RedisClusterProperties;
import javafx.application.Application;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 测试
 * @author: w77996
 * @create: 2018-12-05 16:37
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
//@ContextConfiguration(classes = {RedisCluster.class})
@EnableConfigurationProperties
public class TestApplication {

//    @Resource
//    private BaseRedisDaoInter redisDaoInter;

//    @Autowired
//    private JedisCluster jedisCluster;
//    @Test
//    public void testAsync() {
//        System.out.println("=====" + Thread.currentThread().getName() + "=========");
//        System.out.println("=====" + redisDaoInter.get("test") + "=========");
//        System.out.println("=====" + redisDaoInter.get("new") + "=========");
//    }

    @Autowired
    private RedisTemplate redisTemplate;

    String cityGeoKey = "cityGeo ";

    /**
     * 添加坐标
     */
    @Test
    public void testAdd(){
        Long addedNum = redisTemplate.opsForGeo()
                .add(cityGeoKey,new Point(116.405285,39.904989),"北京");
        redisTemplate.opsForGeo()
                .add(cityGeoKey,new Point(121.472644,31.231706),"上海");
         redisTemplate.opsForGeo()
                .add(cityGeoKey,new Point(114.371087,22.528578),"深圳");
        System.out.println(addedNum);
    }

    /**
     * 获取坐标
     */
    @Test
    public void testGeoGet(){
        List<Point> points = redisTemplate.opsForGeo().position(cityGeoKey,"北京","上海","深圳");
        System.out.println(points);
    }

    /**
     * 两个地区之前的距离
     */
    @Test
    public void testDist(){
        Distance distance = redisTemplate.opsForGeo()
                .distance(cityGeoKey,"北京","上海", RedisGeoCommands.DistanceUnit.KILOMETERS);
        System.out.println(distance);
    }

    /**
     * 经纬度附近的地区
     */
    @Test
    public void testNearByXY(){
        //longitude,latitude
        Circle circle = new Circle(116.405285,39.904989, Metrics.KILOMETERS.getMultiplier());
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo()
                .radius(cityGeoKey,circle,args);
        System.out.println(results);
    }
    /**
     * 北京1500km范围内的数据
     */
    @Test
    public void testNearByPlace(){
        Distance distance = new Distance(1500, Metrics.KILOMETERS);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<String>>  results = redisTemplate.opsForGeo()
                .radius(cityGeoKey,"北京",distance,args);
        System.out.println(results);
    }
    /**
     * hash
     */
    @Test
    public void testGeoHash(){
        List<String> results = redisTemplate.opsForGeo()
                .hash(cityGeoKey,"北京","上海","深圳");
        System.out.println(results);
    }


    /**
     * testHyperLogLog
     */
    @Test
    public void testHyperLogLog(){
        //home页面userId为1的用户访问
        long results = redisTemplate.opsForHyperLogLog().add("home",1);
        System.out.println(results);
        //计算home页面的uv
        long results2 = redisTemplate.opsForHyperLogLog().size("home");
        System.out.println(results2);
    }


    /**
     * testHyperLogLog
     */
    @Test
    public void testSet(){
        //home页面userId为1的用户访问
        long results = redisTemplate.opsForSet().add("PRIZE_RESULT_IS_INIT",1);
        System.out.println(results);
        //计算home页面的uv
        Set<String> results2 = redisTemplate.opsForSet().members("PRIZE_RESULT_IS_INIT");
        System.out.println(Arrays.asList(results2));
    }

    @Test
    public void testZSet(){
        String Z_HAND_PRINT = "Z_HAND_PRINT";
        String Z_PRINT = "Z_PRINT";
        String S_PRE_PRINT = "S_PRE_PRINT";
        long nowTime = System.currentTimeMillis();
        String orderNo = "658423424298523492";
        Boolean add = redisTemplate.opsForZSet().add(Z_HAND_PRINT, orderNo, nowTime);
        System.out.println(add);
        Long remove = redisTemplate.opsForSet().remove(S_PRE_PRINT, orderNo);
        System.out.println(remove);
        //出票补偿queue
        Boolean add1 = redisTemplate.opsForZSet().add(Z_PRINT, orderNo, nowTime);
        System.out.println(add1);

    }



}
