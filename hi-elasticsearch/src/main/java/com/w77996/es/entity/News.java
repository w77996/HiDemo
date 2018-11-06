package com.w77996.es.entity;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @description: 资讯
 * @author: w77996
 * @create: 2018-10-16 13:27
 **/
@Document(indexName = "news",type = "news",shards = 2, replicas = 1, refreshInterval = "-1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    private Integer id;

    private String title;

    private String content;

    private String authorName;

}
