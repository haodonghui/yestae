package com.yestae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: yestae-demo
 * @description:
 * @author: zouco
 * @create: 2019-08-13 16:24
 **/
@SpringBootApplication
//@EnableEurekaClient
//@EnableFeignClients
public class ParentCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParentCloudApplication.class);
        System.out.println("启动成功！");
    }
}
