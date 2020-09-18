package com.yestae.sms.sdk;

import com.yestae.sms.sdk.request.SmsNotifyRequest;
import com.yestae.sms.sdk.request.SmsVerifyCodeSdkRequest;
import com.yestae.sms.sdk.response.SmsSdkResponse;
import com.yestae.sms.sdk.utils.SmsSdkUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangzhi
 * @description: 单元测试
 * @Date 2019/8/12 14:25
 **/
public class TestMain {

//    private String url = "https://sms-api-test.yestae.com/sms-api/";
        private String url = "http://localhost:8088/sms-api/";

    @Test
    public void testSendVerifyCode() {
        url += "sms/open/send/sendVerificationCode";
        Map<String, String> params = new HashMap();
        SmsVerifyCodeSdkRequest smsVerifyCodeSdkRequest = new SmsVerifyCodeSdkRequest.Builder()
                .mobile("13240409798").platform("FWWWPPIPALE").bizType(1).type(0)
                .content("【益友会】你好,你的验证码是123456").expires(180).code("123456").build();
        SmsSdkResponse<Boolean> smsSdkResponse = SmsSdkUtils.sendVerfyCode(url, smsVerifyCodeSdkRequest);
        System.out.println("smsSdkResponse====" + smsSdkResponse);
    }


    @Test
    public void testCheckVerifyCode() {
        url += "sms/open/send/checkValidCode";
//        Map<String, String> params = new HashMap();
//        SmsCheckVerfyCodeRequest smsCheckVerfyCodeRequest = new SmsCheckVerfyCodeRequest.Builder()
//                .mobile("13240409798").platform("FWWWPPIPALE").bizType(1).type(0)
//                .code("123456").build();
//        SmsSdkResponse<Boolean> smsSdkResponse = SmsSdkUtils.checkVerfyCode(url, smsCheckVerfyCodeRequest);
//        System.out.println("smsSdkResponse====" + smsSdkResponse);
    }

    /**
     * 普通发送
     */
    @Test
    public void testGeneralSendNotify() {
        url += "sms/open/send/sendBatchNotify";
        Map<String, String> params = new HashMap();

        //不带有定时发送时间的
        SmsNotifyRequest smsNotifyRequest = new SmsNotifyRequest.Builder()
                .platform("FWWWPPIPALE").type(0).bizType(9)
                .content("【益友会】尊敬的,您好,您即将参加是爬山活动,报名将在10分钟后截止，请尽快完成报名。")
                .params("13240409798").build();
        //带有入参定时发送时间
//        String sendtime = DateUtil.format(new Date(), "yyyyMMddHHmm");
//        SmsNotifyRequest smsNotifyRequestSendtime = smsNotifyRequest = new SmsNotifyRequest.Builder()
//                .platform("FWWWPPIPALE")
//                .type(0).bizType(9).content("【益友会】尊敬的,您好,您即将参加是爬山活动,报名将在10分钟后截止，请尽快完成报名。")
//                .params("18937091992,13240409798").sendtime(sendtime).build();
//        SmsSdkResponse<Boolean> smsSdkResponse = SmsSdkUtils.sendSmsNotify(url, smsNotifyRequest);
//        System.out.println("smsSdkResponse====" + smsSdkResponse);
    }

    /**
     * 变量发送
     */
    @Test
    public void testVariableSendNotify() {
        url += "sms/open/send/sendBatchNotify";
//        Map<String, String> params = new HashMap();
//
//        //不带有定时发送时间的
//        SmsNotifyRequest smsNotifyRequest = new SmsNotifyRequest.Builder()
//                .platform("FWWWPPIPALE").type(0).bizType(9)
//                .content("【益友会】尊敬的{$var},您好,您的验证码是{$var},{$var}分钟内有效")
//                .params("18937091992,先生,123456,3;13240409798,先生,123456,3;").build();
//        //带有入参定时发送时间
//        String sendtime = DateUtil.format(new Date(), "yyyyMMddHHmm");
//        SmsNotifyRequest smsNotifyRequestSendtime = smsNotifyRequest = new SmsNotifyRequest.Builder()
//                .platform("FWWWPPIPALE").type(0).bizType(9)
//                .content("【益友会】尊敬的{$var},您好,您的验证码是{$var},{$var}分钟内有效")
//                .params("18937091992,先生,123456,3;13240409798,先生,123456,3;").sendtime(sendtime).build();
//        SmsSdkResponse<Boolean> smsSdkResponse = SmsSdkUtils.sendSmsNotify(url, smsNotifyRequest);
//        System.out.println("smsSdkResponse====" + smsSdkResponse);
    }

    /**
     * 普通短息你发送
     */
    @Test
    public void testGeneralSendMarketing() {
//        url += "sms/open/send/sendBatchMarketing";
//        Map<String, String> params = new HashMap();
//        //不带定时发送的时间的
//        SmsMarketingRequest smsMarketingRequest = new SmsMarketingRequest.Builder()
//                .platform("FWWWPPIPALE").bizType(10).type(0)
//                .content("【益友会】尊敬的,您好,您即将参加是爬山活动,报名将在10分钟后截止，请尽快完成报名。")
//                .params("18937091992,13240409798").build();
//        //带有定时发送时间的
//        SmsMarketingRequest smsMarketingRequestSendtime = new SmsMarketingRequest.Builder()
//                .params("13240409798").platform("FWWWPPIPALE").bizType(10).type(0)
//                .content("【益友会】尊敬的,您好,您即将参加是爬山活动,报名将在10分钟后截止，请尽快完成报名。")
//                .sendtime(DateUtil.format(new Date(), "yyyyMMddHHmm")).build();
//
//        SmsSdkResponse<Boolean> smsSdkResponse = SmsSdkUtils.sendSmsMarketing(url, smsMarketingRequest);
//        System.out.println("smsSdkResponse====" + smsSdkResponse);
    }


    @Test
    public void testVariableSendMarketing() {
        url += "sms/open/send/sendBatchMarketing";
//        Map<String, String> params = new HashMap();
//        //不带定时发送的时间的
//        SmsMarketingRequest smsMarketingRequest = new SmsMarketingRequest.Builder()
//                .platform("FWWWPPIPALE").bizType(10).type(0)
//                .content("【益友会】尊敬的{$var},您好,您即将参加是{$var},报名将在{$var}分钟后截止，请尽快完成报名。")
//                .params("18937091992,先生,爬山活动,3;13240409798,先生,爬山活动,3;")
//                .build();
//        //带有定时发送时间的
//        SmsMarketingRequest smsMarketingRequestSendtime = new SmsMarketingRequest.Builder()
//                .platform("FWWWPPIPALE").bizType(10).type(0)
//                .content("【益友会】尊敬的{$var},您好,您即将参加是{$var},报名将在{$var}分钟后截止，请尽快完成报名。")
//                .params("18937091992,先生,爬山活动,3;13240409798,先生,爬山活动,3;")
//                .sendtime(DateUtil.format(new Date(), "yyyyMMddHHmm")).build();
//        SmsSdkResponse<Boolean> smsSdkResponse = SmsSdkUtils.sendSmsMarketing(url, smsMarketingRequest);
//        System.out.println("smsSdkResponse====" + smsSdkResponse);
    }

    @Test
    public void testGenerateCode () {
        url += "sms/open/send/generateCode";
        Map<String, String> params = new HashMap();
        SmsVerifyCodeSdkRequest smsVerifyCodeSdkRequest = new SmsVerifyCodeSdkRequest.Builder()
                .mobile("18310714195").platform("HOAQZTSPCXI").bizType(1).type(0)
                .content("【益友会】你好,你的验证码是123456").expires(180).code("123456").build();
        SmsSdkResponse<Boolean> smsSdkResponse = SmsSdkUtils.fictitiousSendCode(url, smsVerifyCodeSdkRequest);
        System.out.println("smsSdkResponse====" + smsSdkResponse);

    }
}
