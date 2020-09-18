package com.yestae.modules.sms.productenum;

import lombok.Getter;

@Getter
public enum SmsEnum {
    VERIFICODE(1, "验证码短信类型"),
    MARKETING(2, "营销短信类型"),
    INTERNATIONAL(3, "国际化类型短信"),
    ;

    private int code;
    private String desc;

    SmsEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
