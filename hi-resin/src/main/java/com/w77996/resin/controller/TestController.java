package com.w77996.resin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class TestController {


    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "ok";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
