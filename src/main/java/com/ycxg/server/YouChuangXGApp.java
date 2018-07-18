package com.ycxg.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Alan on 2016/4/12.
 */
@SpringBootApplication
@MapperScan("com.ycxg.server.mapper")
public class YouChuangXGApp {
    public static void main(String[] args) {
        SpringApplication.run(YouChuangXGApp.class, args);
    }
}
