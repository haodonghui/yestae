package com.yestae.modules.sms.sendutils;

import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yestae.modules.sms.productenum.SmsEnum;
import com.yestae.modules.sms.sendutils.request.SendInternationalRequest;
import com.yestae.modules.sms.sendutils.request.SmsSendRequest;
import com.yestae.modules.sms.sendutils.request.SmsVariableRequest;
import com.yestae.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Component
public class Send253Utils {

    private static final Logger logger = LoggerFactory.getLogger(Send253Utils.class);

    private static final int SEND_MAX = 10000;

    private static String sendGeneralUrl;
    private static String sendVariableUrl;

    private static String sendSingleInternationalUrl;
    private static String sendBatchInternationalUrl;


    private static String accountVerificationCode;
    private static String passwordVerificationCode;

    private static String accountMarketing;
    private static String passwordMarketing;


    private static String accountInternational;
    private static String passwordInternational;
    private static int maxNumber;

    @Value("${yun253.send.maxNumber}")
    public void setMaxNumber(int maxNumber) {
        Send253Utils.maxNumber = maxNumber;
    }

    @Value("${yun253.send.sendSingleInternationalUrl}")
    public void setSendSingleInternationalUrl(String sendSingleInternationalUrl) {
        Send253Utils.sendSingleInternationalUrl = sendSingleInternationalUrl;
    }

    @Value("${yun253.send.sendBatchInternationalUrl}")
    public void setSendBatchInternationalUrl(String sendBatchInternationalUrl) {
        Send253Utils.sendBatchInternationalUrl = sendBatchInternationalUrl;
    }

    @Value("${yun253.send.generalurl}")
    public void setSendGeneralUrl(String sendGeneralUrl) {
        Send253Utils.sendGeneralUrl = sendGeneralUrl;
    }

    @Value("${yun253.send.variableurl}")
    public void setSendVariableUrl(String sendVariableUrl) {
        Send253Utils.sendVariableUrl = sendVariableUrl;
    }

    @Value("${yun253.send.accountVerificationCode}")
    public void setAccountVerificationCode(String accountVerificationCode) {
        Send253Utils.accountVerificationCode = accountVerificationCode;
    }

    @Value("${yun253.send.passwordVerificationCode}")
    public void setPasswordVerificationCode(String passwordVerificationCode) {
        Send253Utils.passwordVerificationCode = passwordVerificationCode;
    }

    @Value("${yun253.send.accountMarketing}")
    public void setAccountMarketing(String accountMarketing) {
        Send253Utils.accountMarketing = accountMarketing;
    }

    @Value("${yun253.send.passwordMarketing}")
    public void setPasswordMarketing(String passwordMarketing) {
        Send253Utils.passwordMarketing = passwordMarketing.trim();
    }

    @Value("${yun253.send.accountInternational}")
    public void setAccountInternational(String accountInternational) {
        Send253Utils.accountInternational = accountInternational;
    }

    @Value("${yun253.send.passwordInternational}")
    public void setPasswordInternational(String passwordInternational) {
        Send253Utils.passwordInternational = passwordInternational;
    }

    //发送普通短信
    public static String sendGeneralMsg(String phone, String content, Integer smsType, String sendtime) {
        Map<String, String> map = new HashMap();
        setMap(map, smsType);
        String response = "";
        Map<Integer, Object> mapSplit = Maps.newLinkedHashMap();
        if (phone.contains(",")) {
            CommonUtils.shardingString(mapSplit, maxNumber, phone, ",", "");
            for (Map.Entry<Integer, Object> entry : mapSplit.entrySet()) {
                response = sendSms(setSmsSendRequest(map, content, (String) entry.getValue(), sendtime), null);
            }
        } else {
            response = sendSms(setSmsSendRequest(map, content, phone, sendtime), null);
        }
        return response;
    }

    public static SmsSendRequest setSmsSendRequest(Map<String, String> map, String content, String phone, String sendtime) {
        SmsSendRequest smsSendRequest = new SmsSendRequest(map.get("account"), map.get("password"),
                content, phone, "true");
        if (!StringUtils.isEmpty(sendtime)) {
            smsSendRequest.setSendtime(coventTime(sendtime));
        }
        return smsSendRequest;
    }

    private static String coventTime(String sendtime) {
        Date re = DateUtil.parse(sendtime, "yyyy-MM-dd HH:mm:ss");
        return DateUtil.format(re, "yyyyMMddHHmm");
    }

    //发送变量短息
    public static String sendVariableMsg(String params, String content, Integer smsType, String sendtime) {
        Map<String, String> map = new HashMap();
        setMap(map, smsType);
        String report = "true";
        String response = "";
        if (params.contains(";")) {
            Map<Integer, Object> mapSplit = Maps.newLinkedHashMap();
            CommonUtils.shardingString(mapSplit, maxNumber, params, ";", "");
            for (Map.Entry<Integer, Object> entry : mapSplit.entrySet()) {
                response = sendSms(null, setSmsVariableRequest(map, content, (String) entry.getValue(), sendtime, smsType));
            }
        } else {
            response = sendSms(null, setSmsVariableRequest(map, content, params, sendtime, smsType));
        }
        return response;
    }

    public static SmsVariableRequest setSmsVariableRequest(Map<String, String> map, String content, String params, String sendtime, Integer smsType) {
        SmsVariableRequest smsVariableRequest = null;
        if (SmsEnum.VERIFICODE.getCode() == smsType) {
            smsVariableRequest = new SmsVariableRequest(map.get("account"), map.get("password"),
                    content, params, "true");
        } else {
            smsVariableRequest = new SmsVariableRequest(map.get("account"), map.get("password"),
                    content, params, "true");
        }

        if (!StringUtils.isEmpty(sendtime)) {
            smsVariableRequest.setSendtime(coventTime(sendtime));
        }
        return smsVariableRequest;
    }

    private static String sendSms(SmsSendRequest smsSendRequest, SmsVariableRequest smsVariableRequest) {
        String requestJson = "";
        String response = "";
        String url = "";
        String prefix = "";
        if (smsSendRequest != null) {
            prefix = "发送普通短信";
            url = sendGeneralUrl;
            requestJson = JSON.toJSONString(smsSendRequest);
        } else if (smsVariableRequest != null) {
            prefix = "发送变量短信";
            url = sendVariableUrl;
            requestJson = JSON.toJSONString(smsVariableRequest);
        }
        logger.info(prefix + " before  request-url={} request string is:{} ", url, requestJson);
        response = ChuangLanSmsUtil.sendSmsByPost(url, requestJson);
        logger.info(prefix + " response after request result is {} ", response);
        return response;
    }

    public static String sendSingleInternationalSms(String content, String params, Integer smsType) {
        Map<String, String> map = new HashMap();
        setMap(map, smsType);
        String report = "true";
        SendInternationalRequest sendInternationalRequest = new SendInternationalRequest(map.get("account"), map.get("password"), content, params);
        String requestJson = JSON.toJSONString(sendInternationalRequest);
        logger.info("smsSingleInternational before request-url={} ,request string is:{} ", sendSingleInternationalUrl, requestJson);
        String response = ChuangLanSmsUtil.sendSmsByPost(sendSingleInternationalUrl, requestJson);
        logger.info("smsVariable response after request result is : " + response);
        return response;
    }

    public static String sendBatchInternationalSms(String content, String params, Integer smsType) {
        Map<String, String> map = new HashMap();
        setMap(map, smsType);
        String report = "true";
        String response = "";
        Map<Integer, Object> mapSplit = Maps.newLinkedHashMap();
        CommonUtils.shardingString(mapSplit, maxNumber, params, ",", "");
        for (Map.Entry<Integer, Object> entry : mapSplit.entrySet()) {
            SendInternationalRequest sendInternationalRequest = new SendInternationalRequest(map.get("account"), map.get("password"), content, (String) entry.getValue());
            String requestJson = JSON.toJSONString(sendInternationalRequest);
            logger.info("smsVariable before request url={},request string is:{} ", sendBatchInternationalUrl, requestJson);
            response = ChuangLanSmsUtil.sendSmsByPost(sendBatchInternationalUrl, requestJson);
            logger.info("smsVariable response after request result is : " + response);
        }
        return response;
    }


    private static void setMap(Map<String, String> map, Integer smsType) {
        if (SmsEnum.VERIFICODE.getCode() == smsType) {
            //发送验证码应用
            map.put("account", accountVerificationCode);
            map.put("password", passwordVerificationCode);
        } else if (SmsEnum.MARKETING.getCode() == smsType) {
            //发送营销短信
            map.put("account", accountMarketing);
            map.put("password", "eCWdFI8gVr8533");
        } else if (SmsEnum.INTERNATIONAL.getCode() == smsType) {
            //发送国际化
            map.put("account", accountInternational);
            map.put("password", passwordInternational);
        }
    }
}
