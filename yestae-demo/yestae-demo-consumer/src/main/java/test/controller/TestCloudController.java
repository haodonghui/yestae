package test.controller;

import com.yestae.feign.TestFeignCloudApi;
import com.yestae.api.TestDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testCloud")
public class TestCloudController {

    private final Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    private TestFeignCloudApi testFeignApi;

    /**
     *
     * 功能描述: 测试cloud消费者
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 19:36
     */
    @RequestMapping(value = "testMysql", method = RequestMethod.POST, produces = "application/json")
    public String testMysql(@RequestBody String p) {
        String s = "testCloud-testMysql:";
        System.out.println("p = [" + p + "]");
        String stringApiResult =s+ testFeignApi.testMysql(p);
        System.out.println("stringApiResult = [" + stringApiResult + "]");
        logger.info("{}.testMysql():{}",getClass().getSimpleName(),stringApiResult);
        return stringApiResult;
    }


}
