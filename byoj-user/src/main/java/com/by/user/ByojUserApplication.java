package com.by.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lzh
 */
@SpringBootApplication
@ComponentScan("com.by")
@EnableDiscoveryClient
public class ByojUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ByojUserApplication.class, args);
    }

}
