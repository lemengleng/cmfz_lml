package com.lml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.lml.dao")
public class CmfzApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(CmfzApplication.class,args);
    }
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(CmfzApplication.class);
    }
}
