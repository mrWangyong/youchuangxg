package com.ycxg.server;

import com.ycxg.server.WebsocketH5.MyHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Alan on 2016/4/12.
 */
@SpringBootApplication
@MapperScan("com.ycxg.server.mapper")
public class YouChuangXGApp {
    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(YouChuangXGApp.class, args);
        MyHandler.setApplicationContext(applicationContext);
    }
}
