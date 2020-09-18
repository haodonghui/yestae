package com.yestae.sms.sdk.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangpeng
 * @title: SmsConstants
 * @packageName: com.yestae.sms.sdk.utils
 * @description: 短信相关常量
 * @date 2020-03-09 11:07
 */
public class SmsConstants {

    //短信类型对应内容
    //注册
    public static String REGISTER= "【益友会】验证码%s，欢迎注册益友会账户，本验证码%s分钟内有效。";
    //登录、重置密码、重置手机验证码
    public static String LOGIN_UPDATEPASSWORD_UPDATEMOBILE_CHECKOLDUPDATEMOBILE_CHECKNEWFORGETPASSWORD=
        "【益友会】您的益友会验证码是%s，此码可登录益友会或重置密码、手机，本验证码%s分钟内有效。";
    //大益卡
    public static String TEACARDPAY = "【益友会】您的大益卡支付验证码是%s，请勿转发、泄露，本验证码%s分钟内有效。";
    //取茶
    public static String PICKTEA = "【益友会】您的大益商城取茶身份验证码是%s，请勿转发、泄露，本验证码%s分钟内有效。";

    //注销
    public static String CANCELUSER = "【益友会】您正在申请注销益友会账号，验证码：%s。如非本人操作，请致电400-618-7572。本验证码%s分钟内有效。";
    //其他
    public static String OTHER= "【益友会】您的验证码为%s，请输入验证码完成操作。";

    //短信业务类型
    public static Map<Integer, String> smsContentMap = new HashMap();

    static {
        smsContentMap.put(0, LOGIN_UPDATEPASSWORD_UPDATEMOBILE_CHECKOLDUPDATEMOBILE_CHECKNEWFORGETPASSWORD);
        smsContentMap.put(1, REGISTER );
        smsContentMap.put(2, LOGIN_UPDATEPASSWORD_UPDATEMOBILE_CHECKOLDUPDATEMOBILE_CHECKNEWFORGETPASSWORD);
        smsContentMap.put(3, LOGIN_UPDATEPASSWORD_UPDATEMOBILE_CHECKOLDUPDATEMOBILE_CHECKNEWFORGETPASSWORD);
        smsContentMap.put(4, LOGIN_UPDATEPASSWORD_UPDATEMOBILE_CHECKOLDUPDATEMOBILE_CHECKNEWFORGETPASSWORD);
        smsContentMap.put(5, LOGIN_UPDATEPASSWORD_UPDATEMOBILE_CHECKOLDUPDATEMOBILE_CHECKNEWFORGETPASSWORD);
        smsContentMap.put(6, TEACARDPAY);
        smsContentMap.put(7, PICKTEA);
        smsContentMap.put(8, CANCELUSER);
        smsContentMap.put(9, OTHER);
    }
}
