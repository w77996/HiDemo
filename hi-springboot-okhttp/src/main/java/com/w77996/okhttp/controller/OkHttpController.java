package com.w77996.okhttp.controller;

import com.w77996.okhttp.util.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 请求测试
 * @author: w77996
 * @create: 2018-10-15 12:43
 **/
@RestController
@Slf4j
public class OkHttpController {

    @GetMapping("/")
    public String ok(){
        String body = OkHttpUtil.get("https://www.baidu.com",null);
        log.info(body);
        return "ok";
    }

}
