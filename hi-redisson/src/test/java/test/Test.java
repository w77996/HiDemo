package test;


import com.w77996.redisson.HiRedissonApplication;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName Test
 * @Description
 * @author wuhaihui
 * @date 2020/9/17 10:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HiRedissonApplication.class)
public class Test {



    @Autowired
    RedissonClient redissonClient;


    @org.junit.Test
    public void test(){
        redissonClient.getBucket("1").delete();
//         redissonClient.getDeque("1233",new JsonJacksonCodec()).add(1);

//        System.out.println(queue);
//        rdpRedisson.smembers("1:MT2005K35000545",new JsonJacksonCodec()).s;
//        System.out.println(JSON.toJavaObject(o,OnlineTerminal.class).getConnUrl());
//        System.out.println(JSON.toJSONString(o));
    }
}
