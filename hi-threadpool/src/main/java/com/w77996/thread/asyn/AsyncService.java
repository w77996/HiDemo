package com.w77996.thread.asyn;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @description: 异步service
 * @author: w77996
 * @create: 2018-12-05 16:34
 **/
@Service
public class AsyncService {

    @Async
    public void asyncMethod(String arg) {
        System.out.println("arg:" + arg);
        System.out.println("=====" + Thread.currentThread().getName() + "=========");
    }


}
