package com.w77996.es.controller;

import com.w77996.es.service.EsAliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EsAliasController {

    @Autowired
    private EsAliasService esAliasService;

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


    public static void main(String[] args) {
        System.out.println(false && false);
    }
}
