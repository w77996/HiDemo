package com.w77996.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.w77996.dubbo.common.service.HelloService;

@Service(version = "${demo.service.version}")
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "say hello" + name;
    }
}
