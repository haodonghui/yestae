//package com.yestae.listener;
//
//import com.alibaba.fastjson.JSONObject;
//import com.yestae.dao.BusinessOperationLogMapper;
//import com.yestae.framework.businesslog.entity.BusinessLog;
//import com.yestae.framework.businesslog.entity.BusinessOperationLog;
//import com.yestae.framework.businesslog.enums.BusinessOperationLogTypeEnum;
//import com.yestae.framework.businesslog.enums.RabbitMqConstants;
//import com.yestae.framework.businesslog.utils.BusinessLogUtil;
//import com.yestae.framework.rabbit.annotation.MessageListener;
//import com.yestae.framework.rabbit.listener.MessageProcessor;
//import com.yestae.framework.rabbit.model.MessageContext;
//import com.yestae.service.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//import java.util.Date;
//
///**
// * @program: yestae-framework
// * @description: 业务日志消费
// * @author: zouco
// * @create: 2019-08-02 13:59
// **/
//@Component
//@MessageListener(routingKey = RabbitMqConstants.ROUTINGKEY_BUSINESS_OPERATION_LOG_SAVE,queue = RabbitMqConstants.QUEUE_BUSINESS_OPERATION_LOG_SAVE)
//public class TestBusinessLogListener implements MessageProcessor<BusinessLog> {
//    private   final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private BusinessLogUtil businessLogUtil;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private BusinessOperationLogMapper businessOperationLogMapper;
//
//    /**
//     * 处理消息
//     *
//     * @param context 消息上下文
//     */
//    @Override
//    public void process(MessageContext<BusinessLog> context) throws Exception {
//
//        BusinessLog businessLog = context.getMessage().getContent();
//        logger.info("businessLog1 = {}", JSONObject.toJSONString(businessLog));
//        /**
//         * 执行业务逻辑
//         * 1、建议不要加try-catch块
//         * 2、如果异常了消息组件会自动处理
//         * 3、后续也会根据异常，自动执行重试机制
//         */
//        //获取业务操作日志
//        BusinessOperationLog businessOperationLog= businessLogUtil.getBusinessOperationLog(businessLog);
//        //todo 数据存储，方便用户查看操作日志
//        businessOperationLog.setId(Instant.now().getEpochSecond());
//        businessOperationLog.setLogTypeName(BusinessOperationLogTypeEnum.UPDATE.getName());
//        businessOperationLogMapper.insert(businessOperationLog);
//        //userService.insert()
//        // 确认消息消费成功
//        context.ack();
//    }
//
//
//}
