package com.yupi.yuzan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.yupi.yuzan.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class YuzanApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuzanApplication.class, args);
    }

}
