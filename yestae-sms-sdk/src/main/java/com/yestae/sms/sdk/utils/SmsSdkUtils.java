package com.yestae.sms.sdk.utils;

import cn.hutool.core.map.MapUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yestae.sms.sdk.request.*;
import com.yestae.sms.sdk.response.SmsSdkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author zhangzhi
 * @description: 发送短信工具类
 * @Date 2019/8/9 17:21
 **/
public final class SmsSdkUtils {

    private static final Logger logger = LoggerFactory.getLogger(SmsSdkUtils.class);
    private static final String CHARSET = "UTF-8";

    /**
     * 发送验证码
     *
     * @param smsVerifyCodeSdkRequest
     * @return
     */
    public static SmsSdkResponse<Boolean> sendVerfyCode(String url, SmsVerifyCodeSdkRequest smsVerifyCodeSdkRequest) {
        return send(url, smsVerifyCodeSdkRequest);
    }

    /**
     *  虚拟下发短信，只存储，不发送
     * @param url
     * @param smsVerifyCodeSdkRequest
     * @return
     */
    public static SmsSdkResponse<Boolean> fictitiousSendCode(String url, SmsVerifyCodeSdkRequest smsVerifyCodeSdkRequest) {
        return send(url, smsVerifyCodeSdkRequest);
    }

    /**
     * 验证 验证码
     *
     * @param smsVerifyCodeSdkRequest
     * @return
     */
    public static SmsSdkResponse<Boolean> checkVerfyCode(String url, SmsCheckVerfyCodeRequest smsVerifyCodeSdkRequest) {
        return send(url, smsVerifyCodeSdkRequest);
    }

    /**
     * 发送通知
     *
     * @param smsNotifyRequest
     * @return
     */
    public static SmsSdkResponse<Boolean> sendSmsNotify(String url, SmsNotifyRequest smsNotifyRequest) {
        return send(url, smsNotifyRequest);
    }

    /**
     * 发送营销
     *
     * @param smsMarketingRequest
     * @return
     */
    public static SmsSdkResponse<Boolean> sendSmsMarketing(String url, SmsMarketingRequest smsMarketingRequest) {
        return send(url, smsMarketingRequest);
    }


    private static SmsSdkResponse send(String url, SmsRequest smsRequest) {
        SmsSdkResponse<Boolean> smsSdkResponse = new SmsSdkResponse<>();
        Map<String, String> params = MapUtil.newHashMap();
        String result = "";
        Gson gson = new Gson();
        try {
            params = new Gson().fromJson(gson.toJson(smsRequest), new TypeToken<Map<String, String>>() {
            }.getType());
            result = HttpRequestUtils.doPost(url, params);
            smsSdkResponse.bulidSuccess().setResult(true).setRetMsg(result);
            return smsSdkResponse;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("调用发送验证码接口抛出异常,e={}", e);
            smsSdkResponse.bulidFail().setRetMsg(result);
            return smsSdkResponse;
        }
    }

}
