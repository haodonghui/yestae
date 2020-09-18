package com.yestae.modules.sms.productenum;

import lombok.Getter;

@Getter
public enum BizTypeEnum {
    LOGIN(0, "登录"),
    SIGNUP(1, "注册"),
    UPDATE_PASSWORD(2, "更新密码"),
    UPDATE_MOBILE_CHECKOLD(3, "修改手机号-校验原手机号"),
    UPDATE_MOBILE_CHECKNEW(4, "修改手机号-校验新手机号"),
    FORGET_PASSWORD(5, "忘记密码"),
    DEPOSIT_PAY(6, "预存款支付"),
    PICK_TEA(7, "取茶身份校验"),
    CANCEL_USER(8, "账号注销"),
    OTHER(9, "其他"),
    MARKETING(10, "营销短信发送"),
    NOTIFY(11, "通知短信发送"),
    ;
    private int code;
    private String desc;

    BizTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getValueByCode(Integer code) {
        for (BizTypeEnum bizTypeEnum : BizTypeEnum.values()) {
            if (code.equals(bizTypeEnum.getCode())) {
                return bizTypeEnum.getDesc();
            }
        }
        return null;
    }
}
