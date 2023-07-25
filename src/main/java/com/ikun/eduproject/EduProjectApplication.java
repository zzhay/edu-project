package com.ikun.eduproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ikun.eduproject.dao")
public class EduProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduProjectApplication.class, args);
    }

}
