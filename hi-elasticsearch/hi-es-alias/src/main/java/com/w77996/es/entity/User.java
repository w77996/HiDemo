package com.w77996.es.entity;

import com.w77996.es.constance.Constant;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Integer id;

    private String username;

}
