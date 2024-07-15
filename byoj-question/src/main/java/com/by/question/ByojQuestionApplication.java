package com.by.question;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lzh
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.by")
public class ByojQuestionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ByojQuestionApplication.class, args);
    }

}
