package com.mooc.house;

import com.mooc.house.biz.autoconfig.EnableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableHttpClient
@EnableAsync
@EnableAutoConfiguration
public class HouseApplication {

    public static void main(String[] args) {

        SpringApplication.run(HouseApplication.class, args);
    }
}
