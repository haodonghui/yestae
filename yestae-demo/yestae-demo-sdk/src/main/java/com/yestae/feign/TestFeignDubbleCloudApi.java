package com.yestae.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: yestae-sms
 * @description: 测试dubbo转cloud Feign接口
 * @author: zouco
 * @create: 2019-07-23 14:25
 **/
@FeignClient(value ="duboo-cloud-service")
//@RequestMapping(value = "providerCloud")
public interface TestFeignDubbleCloudApi {
    /**
     *
     * 功能描述: 测试直接dubbo服务增加controller注解转cloud  Feign接口
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 18:10
     */
    @RequestMapping(value = "dubbo/testMysql", method = RequestMethod.POST, produces = "application/json")
    String testDubbleMysql(String p);
    /**
     *
     * 功能描述: 测试直接dubbo服务增加controller层转cloud  Feign接口
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 18:11
     */
    @RequestMapping(value = "dubboCloud/testMysql", method = RequestMethod.POST, produces = "application/json")
    String testCloudMysql(String p);

}
