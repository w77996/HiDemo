package com.w77996.es.controller;

import com.w77996.es.entity.User;
import com.w77996.es.entity.UserIndex1;
import com.w77996.es.service.EsAliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EsAliasController {

    @Autowired
    private EsAliasService esAliasService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping
    public String createIndex(){
        esAliasService.createIndex();
        return "ok";
    }

    @GetMapping("/template")
    public String createIndexByTemplate(){
        esAliasService.createIndexByTemplate();
        return "ok";
    }

    @GetMapping("/list")
    public String list(){
        esAliasService.list();
        return "ok";
    }

    @DeleteMapping
    public String deleteIndex(){
        esAliasService.deleteIndex();
        return "ok";
    }

    @GetMapping("/search")
    public List<User> search(){
        return esAliasService.searchData();
    }

    @GetMapping("/switchAlias")
    public String switchAlias(){
        esAliasService.switchAlias();
        return "ok";
    }

    @GetMapping("/create")
    public String create(){
        elasticsearchTemplate.createIndex(UserIndex1.class);
        return "ok";
    }



    public static void main(String[] args) {
        System.out.println(false && false);
    }
}
