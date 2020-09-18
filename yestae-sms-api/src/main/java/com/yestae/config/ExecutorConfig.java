package com.yestae.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author zhangzhi
 * @description: TODO
 * @Date 2019/8/2 10:36
 **/
@Configuration
public class ExecutorConfig {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    @Bean
    public ExecutorService getExecutorService() {
        return new ThreadPoolExecutor(1,
                Runtime.getRuntime().availableProcessors() * 2
                , 0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new SmsRejectHandler());
    }


    class SmsRejectHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            new ThreadPoolExecutor.CallerRunsPolicy();
        }
    }
}
