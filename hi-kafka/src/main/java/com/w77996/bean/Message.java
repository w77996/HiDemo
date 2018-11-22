package com.w77996.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 消息
 * @author: w77996
 * @create: 2018-11-05 12:49
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Long id;    //id

    private String msg; //消息

    private Date sendTime;  //时间戳

}

