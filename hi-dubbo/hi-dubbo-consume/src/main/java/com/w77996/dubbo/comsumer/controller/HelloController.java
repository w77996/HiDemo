package com.w77996.dubbo.comsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.w77996.dubbo.common.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hello")
@RestController
public class HelloController {

    @Reference(version = "${demo.service.version}")
    private HelloService helloService;

    @GetMapping
    public String sayHello(String name) {
        return helloService.sayHello(name);
    }

}
