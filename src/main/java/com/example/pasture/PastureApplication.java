package com.example.pasture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan("com.example.pasture.mapper")
public class PastureApplication{

    public static void main(String[] args) {
        SpringApplication.run(PastureApplication.class, args);
    }

}
