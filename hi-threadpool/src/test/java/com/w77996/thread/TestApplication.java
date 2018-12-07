package com.w77996.thread;

import com.w77996.thread.asyn.AsyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description: 测试
 * @author: w77996
 * @create: 2018-12-05 16:37
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication {

    @Autowired
    AsyncService asyncService;

    @Test
    public void testAsync() {
        System.out.println("=====" + Thread.currentThread().getName() + "=========");
        asyncService.asyncMethod("Async");
    }



}
