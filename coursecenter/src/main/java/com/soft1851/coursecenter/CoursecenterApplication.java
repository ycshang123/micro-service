package com.soft1851.coursecenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;


@MapperScan(value = "com.soft1851.coursecenter.mapper")
@SpringBootApplication
public class CoursecenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursecenterApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
