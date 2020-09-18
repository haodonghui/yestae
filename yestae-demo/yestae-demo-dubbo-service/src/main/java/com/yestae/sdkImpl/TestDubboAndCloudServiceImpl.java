package com.yestae.sdkImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.yestae.api.TestDubboService;
import com.yestae.entity.User;
import com.yestae.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * 功能描述: dubbo服务实现层，兼容cloud
 *
 * @param:
 * @return:
 * @auther: zouco
 * @date: 2019/7/30 19:41
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/dubbo")
public class TestDubboAndCloudServiceImpl implements TestDubboService {

    @Autowired
    private UserService userService;
    //@Autowired
    //private RedisTestDao redisTestDao;
    //@Autowired
    //private MongoTestDao mtdao;

    /**
     *
     * 功能描述: dubbo 服务实现，增加controller注解，转cloud
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 19:39
     */
    @Override
    @RequestMapping(value = "testMysql", method = RequestMethod.POST, produces = "application/json")
    public String testMysql(@RequestBody String p) {

        String s = "provider-dubbo ("+p+"):";
        long l = Long.parseLong(p);
        User one = userService.getOne(l);
        String jsonString =s+ JSONObject.toJSONString(one);

        System.out.println("UserService.getOne" + jsonString);

        return jsonString;
    }
    //@Override
    ////@RequestMapping(value = "testUser" ,method = RequestMethod.POST,produces ="application/json")
    //public String testUser(@RequestBody User user) {
    //    //long l = Long.parseLong(p);
    //    User one = userMapper.getOne(user.getUserId().longValue());
    //    String jsonString = JSONObject.toJSONString(one);
    //
    //    System.out.println("userMapper.getOne" + jsonString);
    //
    //    return jsonString;
    //}
    //
    //@Override
    //@RequestMapping(value = "testMongo", method = RequestMethod.POST, produces = "application/json")
    //public String testMongo(Integer id,String name) {
    //
    //    //MongoTest mgtest = new MongoTest();
    //    //mgtest.setId(id);
    //    //mgtest.setAge(33);
    //    //mgtest.setName(name);
    //    //mtdao.saveTest(mgtest);
    //    //
    //    //MongoTest mgtest1 = mtdao.findTestByName(name);
    //
    //    String jsonString = JSONObject.toJSONString("testMongo");
    //    System.out.println("mgtest is " + jsonString);
    //
    //    return jsonString;
    //}
    //
    ////@Override
    //@RequestMapping(value = "testRedis", method = RequestMethod.GET, produces = "application/json")
    //public String testRedis(String p) {
    //    //Long id=2L;
    //    long id = Long.parseLong(p);
    //    //User user = redisTestDao.getUser(id);
    //    String jsonString = JSONObject.toJSONString(1);
    //    System.out.println("mgtest is " + jsonString);
    //    return jsonString;
    //}
    //

}