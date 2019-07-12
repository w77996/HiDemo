package com.w77996.es;

import com.w77996.es.service.EsAliasService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsAliasTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class EsAliasTest {

    @Autowired
    EsAliasService esAliasService;

    @Test
    public void createIndex(){
        esAliasService.createIndex();
    }
}
