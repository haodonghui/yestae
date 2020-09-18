package com.yestae.api;


import com.yestae.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: yestae-sms
 * @description: 测试dubbo服务
 * @author: zouco
 * @create: 2019-07-23 14:25
 **/
public interface TestDubboService {
     String testMysql(String p);
    //@RequestMapping(value = "testMongo", method = RequestMethod.POST, produces = "application/json")
    //String testMongo(@RequestParam Integer id, @RequestParam String name);
    //@RequestMapping(value = "testUser" ,method = RequestMethod.POST,produces ="application/json")
    //String testUser(@RequestBody User user);
    //@RequestMapping(value = "testRedis" ,method = RequestMethod.POST,produces ="application/json")
    //String testRedis(String p);
}
