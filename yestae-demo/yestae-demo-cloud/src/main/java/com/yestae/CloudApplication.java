package com.yestae;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @program: yestae-demo
 * @description:
 * @author: zouco
 * @create: 2019-07-29 17:40
 **/
@SpringBootApplication(scanBasePackages = {"com.yestae","com.yestae.framework"})
//@ComponentScan("com.yestae")
@MapperScan({"com.yestae.dao"})
@EnableEurekaClient
public class CloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudApplication.class);
    }
}
