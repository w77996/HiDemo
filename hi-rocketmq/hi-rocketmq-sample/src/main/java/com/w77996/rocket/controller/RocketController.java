package com.w77996.rocket.controller;

import com.w77996.rocket.service.RocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RocketController {

    @Autowired
    private RocketService rocketService;

    @GetMapping
    public String SyncProducer () throws Exception {
        rocketService.sendSyncProducer ();
        return "ok";
    }
}
