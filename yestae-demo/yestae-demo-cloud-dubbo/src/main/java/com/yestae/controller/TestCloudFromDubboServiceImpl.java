package com.yestae.controller;


import com.alibaba.fastjson.JSONObject;
import com.yestae.entity.User;
import com.yestae.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cloudFromDubbo")
public class TestCloudFromDubboServiceImpl {

    @Autowired
    private UserService userService;

    /**
     *
     * 功能描述: 新建模块调用dubbo服务jar包，转cloud
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 18:16
     */
    @RequestMapping(value = "testMysql", method = RequestMethod.POST, produces = "application/json")
    public String testMysql(@RequestBody String p) {
        String s = "cloudFromDubbo-testMysql ("+p+"):";
        long l = Long.parseLong(p);
        User one = userService.getOne(l);
        String jsonString =s+ JSONObject.toJSONString(one);

        System.out.println("userMapper.getOne" + jsonString);

        return jsonString;
    }

}