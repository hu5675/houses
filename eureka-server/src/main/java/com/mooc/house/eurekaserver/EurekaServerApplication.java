package com.mooc.house.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.LinkedList;
import java.util.concurrent.Callable;

@SpringBootApplication
@EnableEurekaServer
/**
 *
 */
public class EurekaServerApplication implements Callable {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

    @Override
    public Object call() throws Exception {
        return null;
    }
}
