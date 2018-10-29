package com.w77996.netty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: w77996
 * @create: 2018-10-15 13:34
 **/
@SpringBootApplication
public class NettyApplication  implements  CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }


    @Override
    public void run(String... strings) {
    }
}
