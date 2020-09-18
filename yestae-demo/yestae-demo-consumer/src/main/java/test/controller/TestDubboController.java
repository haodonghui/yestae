package test.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yestae.api.TestDubboService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dubbo")
public class TestDubboController {
    @Reference(version = "1.0.0")
    private TestDubboService testDubboService;

    /**
     *
     * 功能描述: 测试dubbo消费者
     *
     * @param:
     * @return:
     * @auther: zouco
     * @date: 2019/7/30 19:38
     */
    @GetMapping("testMysql")
    public String testMysql(@RequestParam String p) {

        String s = "consumer-testMysql:";
        System.out.println("p = [" + p + "]");
        String stringApiResult =s+ testDubboService.testMysql(p);
        System.out.println("stringApiResult = [" + stringApiResult + "]");
        return stringApiResult;
    }

}
