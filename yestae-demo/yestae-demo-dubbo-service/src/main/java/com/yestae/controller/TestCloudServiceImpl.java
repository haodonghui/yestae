package com.yestae.controller;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.yestae.entity.User;
import com.yestae.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * 功能描述:  dubbo 增加controller层,转cloud
 *
 * @param:
 * @return:
 * @auther: zouco
 * @date: 2019/7/30 19:41
 */
@RestController
@RequestMapping("/dubboCloud")
public class TestCloudServiceImpl  {

    @Autowired
    private UserService userService;
    //@Autowired
    //private RedisTestDao redisTestDao;
    //@Autowired
    //private MongoTestDao mtdao;

    /**
     *
     * 功能描述: dubbo 增加controller层，转cloud
     *
     * @param: 
     * @return: 
     * @auther: zouco
     * @date: 2019/7/30 19:38
     */
    @RequestMapping(value = "testMysql", method = RequestMethod.POST, produces = "application/json")
    public String testMysql(@RequestBody String p) {
        String s = "providerCloud:";
        long l = Long.parseLong(p);
        User one = userService.getOne(l);
        String jsonString =s+ JSONObject.toJSONString(one);

        System.out.println("userMapper.getOne" + jsonString);

        return jsonString;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    //@RequestMapping(value = "testUser" ,method = RequestMethod.POST,produces ="application/json")
    public String testUser(@RequestBody User user) {
        //long l = Long.parseLong(p);
        User one = userService.getOne(user.getUserId().longValue());
        String jsonString = JSONObject.toJSONString(one);

        System.out.println("userMapper.getOne" + jsonString);

        return jsonString;
    }


    @RequestMapping(value = "testMongo", method = RequestMethod.POST, produces = "application/json")
    public String testMongo(Integer id,String name) {

        //MongoTest mgtest = new MongoTest();
        //mgtest.setId(id);
        //mgtest.setAge(33);
        //mgtest.setName(name);
        //mtdao.saveTest(mgtest);
        //
        //MongoTest mgtest1 = mtdao.findTestByName(name);

        String jsonString = JSONObject.toJSONString("testMongo");
        System.out.println("mgtest is " + jsonString);

        return jsonString;
    }

    //@Override
    @RequestMapping(value = "testRedis", method = RequestMethod.GET, produces = "application/json")
    public String testRedis(String p) {
        //Long id=2L;
        long id = Long.parseLong(p);
        //User user = redisTestDao.getUser(id);
        String jsonString = JSONObject.toJSONString(1);
        System.out.println("mgtest is " + jsonString);
        return jsonString;
    }


}