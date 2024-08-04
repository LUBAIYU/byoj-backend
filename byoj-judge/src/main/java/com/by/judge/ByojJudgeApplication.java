package com.by.judge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lzh
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ByojJudgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ByojJudgeApplication.class, args);
    }

}
