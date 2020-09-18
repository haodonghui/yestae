package com.yestae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: yestae-demo
 * @description:
 * @author: zouco
 * @create: 2019-07-30 15:55
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class CloudFromDubboApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudFromDubboApplication.class);
    }
}
