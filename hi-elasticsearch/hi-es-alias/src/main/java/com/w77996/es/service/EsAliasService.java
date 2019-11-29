package com.w77996.es.service;

import com.w77996.es.entity.User;

import java.util.List;

public interface EsAliasService {

    void createIndex();

    boolean createIndexByTemplate();

    void deleteIndex();

    boolean rebuildIndex();

    void addData(String type);

    List<User> list();

    List<User> searchData();

    void switchAlias();
}
