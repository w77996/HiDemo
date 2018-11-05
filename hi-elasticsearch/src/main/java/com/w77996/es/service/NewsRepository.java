package com.w77996.es.service;

import com.w77996.es.entity.News;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: w77996
 * @create: 2018-10-16 13:30
 **/
@Component
public interface NewsRepository extends ElasticsearchRepository<News,Integer> {


}
