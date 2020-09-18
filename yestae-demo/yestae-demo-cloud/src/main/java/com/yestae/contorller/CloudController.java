package com.yestae.contorller;

import com.alibaba.fastjson.JSONObject;
import com.yestae.api.TestDubboService;
import com.yestae.entity.User;
import com.yestae.framework.businesslog.entity.BusinessLog;
import com.yestae.framework.businesslog.enums.BusinessOperationLogTypeEnum;
import com.yestae.framework.businesslog.utils.BusinessLogUtil;
import com.yestae.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @program: yestae-demo
 * @description: cloud-demo演示
 * @author: zouco
 * @create: 2019-07-29 17:14
 **/
@RestController
@RequestMapping("/demoCloud")
public class CloudController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;


    @Autowired
    private BusinessLogUtil businessLogUtil;

    /**
     * 功能描述: cloud-demo演示
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 18:18
     */
    @RequestMapping(value = "testMysql", method = RequestMethod.POST, produces = "application/json")
    public String testMysql(@RequestBody String p) {

        String s = "demoCloud-testMysql (" + p + "):";
        long l = Long.parseLong(p);
        User one = userService.getOne(l);
        String jsonString = s + JSONObject.toJSONString(one);

        logger.info("{}#testMysql():{}", getClass(), jsonString);

        //one.setPhone("123123");
        //one.setUserId(one.getUserId() + 2);
        //userService.insert(one);


        User two = userService.getOne(l + 2);
        //记录日志对象
        BusinessLog businessLog = new BusinessLog<User>()
                //操作类型
                .setLogTypeName(BusinessOperationLogTypeEnum.UPDATE.getName())
                //关联单据ID(方便查看单据日志)
                .setRelationOrderId(one.getUserId().longValue())
                //更新前数据
                .setOldObj(one)
                //更新后数据（请求数据）
                .setNewObj(two);
        sendBusinessLog(businessLog);
        logger.info("用户操作日志:{}", JSONObject.toJSONString(businessLog));
        return jsonString;
    }

    /**
     * 统一处理操作人相关信息
     *
     * @param businessLog
     */
    public void sendBusinessLog(BusinessLog businessLog) {
        businessLog.setUserId(123L);
        businessLog.setUserName("1111");
        businessLog.setUserAccount("123");
        //记录日志
        businessLogUtil.sendLogMsg(businessLog);
    }
}
