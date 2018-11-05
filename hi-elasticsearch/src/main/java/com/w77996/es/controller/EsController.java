package com.w77996.es.controller;

import com.w77996.es.entity.News;
import com.w77996.es.service.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: w77996
 * @create: 2018-10-16 13:31
 **/
@RestController
@Slf4j
public class EsController {
    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/save")
    public void ok(){
        List<News> list = new ArrayList<>();
        for(int i = 1; i< 200; i++){
            News news = new News(i,"标题"+i,"内容"+i,"作者"+i);
            list.add(news);
        }
        newsRepository.saveAll(list);
        log.info("success");
    }

    @GetMapping("/list")
    public String okList(){

        Optional list = newsRepository.findById(1);

        return list.get().toString();
    }
}
