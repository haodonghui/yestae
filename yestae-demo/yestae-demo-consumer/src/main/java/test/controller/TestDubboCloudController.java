package test.controller;

import com.yestae.feign.TestFeignCloudFromDubboApi;
import com.yestae.feign.TestFeignDubbleCloudApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("testDubboCloud")
public class TestDubboCloudController {
    @Autowired
    private TestFeignDubbleCloudApi testFeignApi;

    @Autowired
    private TestFeignCloudFromDubboApi testFeignCloudFromDubboApi;

    /**
     *
     * 功能描述: 测试dubbo服务新建contorller层，转变为cloud
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 19:37
     */
    @GetMapping("testMysql")
    public String testCloudMysql(@RequestParam String p) {

        String s = "testDubboCloud-testCloudMysql:";
        System.out.println("p = [" + p + "]");
        String stringApiResult =s+ testFeignApi.testCloudMysql(p);
        System.out.println("stringApiResult = [" + stringApiResult + "]");
        return stringApiResult;
    }
    /**
     *
     * 功能描述: 测试dubbo服务直接注解方式，转变为cloud
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 19:37
     */
    @GetMapping("testDubbleMysql")
    public String testDubboMysql(@RequestParam String p) {

        String s = "testDubboCloud-testDubbleMysql:";
        System.out.println("p = [" + p + "]");
        String stringApiResult =s+ testFeignApi.testDubbleMysql(p);
        System.out.println("stringApiResult = [" + stringApiResult + "]");
        return stringApiResult;
    }

    /**
     *
     * 功能描述: 新建模块依赖dubbo服务jar包，新建contorller层，转变为cloud
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 19:37
     */
    @GetMapping("testCloudFromDubbleMysql")
    public String testFromDubboMysql(@RequestParam String p) {

        String s = "testCloudFromDubboCloud-testMysql:";
        System.out.println("p = [" + p + "]");
        String stringApiResult =s+ testFeignCloudFromDubboApi.testMysql(p);
        System.out.println("stringApiResult = [" + stringApiResult + "]");
        return stringApiResult;
    }

}
