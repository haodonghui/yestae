package com.yestae.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: yestae-sms
 * @description: 测试cloud-demo Feign接口
 * @author: zouco
 * @create: 2019-07-23 14:25
 **/
@FeignClient(value ="demo-cloud")
//@RequestMapping(value = "demoCloud")
public interface TestFeignCloudApi {
    /**
     *
     * 功能描述: 测试cloud-demo Feign接口
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 18:06
     */
    @RequestMapping(value = "/demoCloud/testMysql", method = RequestMethod.POST, produces = "application/json")
    String testMysql(@RequestBody String p);
    //@RequestMapping(value = "testMongo", method = RequestMethod.POST, produces = "application/json")
    //String testMongo(@RequestParam Integer id, @RequestParam String name);
    //@RequestMapping(value = "testUser" ,method = RequestMethod.POST,produces ="application/json")
    //String testUser(@RequestBody User user);
    //@RequestMapping(value = "testRedis" ,method = RequestMethod.POST,produces ="application/json")
    //String testRedis(String p);
}
