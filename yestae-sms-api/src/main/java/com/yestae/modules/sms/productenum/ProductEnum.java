package com.yestae.modules.sms.productenum;

import lombok.Getter;

@Getter
public enum ProductEnum {

    VERIFICATIONCODE_SMS("VERIFICATIONCODE_SMS", "验证码"),
    MARKETING_SMS("MARKETING_SMS", "营销短信"),
    INTERNATIONAL_SMS("INTERNATIONAL_SMS", "国际短信"),
    ;

    private String code;
    private String desc;

    ProductEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
