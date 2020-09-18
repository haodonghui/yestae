package com.yestae.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: yestae-sms
 * @description: 测试cloud调用dubbo的jar包 Feign接口
 * @author: zouco
 * @create: 2019-07-23 14:25
 **/
@FeignClient(value ="cloud-from-dubbo")
//@RequestMapping(value = "demoCloud")
public interface TestFeignCloudFromDubboApi {
    /**
     *
     * 功能描述: 测试cloud调用dubbo的jar包 Feign接口
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 18:08
     */
    @RequestMapping(value = "/cloudFromDubbo/testMysql", method = RequestMethod.POST, produces = "application/json")
    String testMysql(@RequestBody String p);

}
