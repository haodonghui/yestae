package com.yestae.modules.sms.productenum;

import lombok.Getter;

@Getter
public enum InternationalEnum {
    INTERNAL(0, "国内"),
    INTERNATIONAL(1, "国际"),
    ;

    private int type;
    private String desc;

    InternationalEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
