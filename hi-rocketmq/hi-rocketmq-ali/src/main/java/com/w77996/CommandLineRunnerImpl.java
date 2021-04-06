package com.w77996;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName CommandLineRunnerImpl
 * @Description
 * @author wuhaihui
 * @date 2020/12/25 11:27
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    private RocketmqProducerService rocketmqProducerService;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("发送消息");
//        Thread.sleep(5000);
        rocketmqProducerService.sendMsg("whh_test");
    }
}
