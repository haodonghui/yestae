package com.yestae.modules.sms.productenum;

import lombok.Getter;

@Getter
public enum SmsError {

    SYSTEM_FAIL("fail", "操作失败"),
    SYSTEM_SUCCESS("success", "操作成功"),
    SYSTEM_ERROR("system.error", "系统异常"),
    SYSTEM_SIGN_ERROR("system.sign.error", "签名错误"),
    SYSTEM_ILLEGAL_ARGUMENT("illegal.argument", "缺少必要参数"),
    SYSTEM_API_ERROR("api.error", "接口访问错误"),
    SYSTEM_ARGUMENT_PARSE_ERROR("argument.parse.error", "参数解析错误"),
    MOBILE_ILLEGAL("mobile.illegal", "手机号码格式有误"),
    SMS_CODE_EMPTY("sms.code.empty", "短信验证码不能为空"),
    SMS_CODE_ERROR("sms.code.error", "短信验证码有误"),
    SMS_CODE_USED("sms.code.used", "验证码已使用"),
    SMS_CODE_OVERDUE("sms.code.overdue", "验证码过期"),
    SMS_CODE_MAXNUM("sms.code.maxnum", "验证码获取已超过当天次数"),
    SMS_SEND_ERROR("sms.send.error", "验证码发送失败"),
    SMS_VERIFY_ERROR("sms.verify.error", "验证码校验失败"),
    SMS_VERIFY_SUCCESS("sms.verify.success", "验证码校验成功"),
    SMS_UPDATE_STATUS_ERROR("sms.update.status.error", "更新验证码状态失败"),
    SMS_PLATFORM_PIPE_NULL("sms.platform.pipe.null", "业务平台短信通道不存在"),
    SMS_MESSAGE_SEND_ERROR("sms.message.send.error", "短信发送失败"),
    SMS_MESSAGE_SEND_SUCCESS("sms.message.send.success", "短信发送成功"),
    SMS_PLATFORM_NULL("sms.platform.null", "业务平台编码不存在"),
    SMS_CONTENT_ARGUMENT("sms.content.argument", "短信内容缺少必要签名"),
    ;
    private String name;
    private String desc;

    SmsError(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
