package com.w77996.cros;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: w77996
 * @create: 2018-10-20 11:07
 **/
@RestController
public class CROSController {

    @GetMapping("/get")
    public String get(){
        return "可以的";
    }
}
