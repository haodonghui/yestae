package com.yestae.modules.sms.productenum;

import lombok.Getter;

/**
 * 短信渠道枚举
 */
@Getter
public enum PipeEnum {

    ALI_MNS("ALI_MNS", "阿里云mns短信服务"),
    AIXUN_SMS("AIXUN_SMS", "爱讯短信服务"),
    RLYUN_SMS("RLYUN_SMS", "容联云通讯服务"),
    CL253_SMS("CL253_SMS", "创蓝253服务"),
    ;
    private String code;
    private String desc;

    PipeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(String code) {
        for (PipeEnum pipeEnum : PipeEnum.values()) {
            if (code.equals(pipeEnum.getCode())) {
                return pipeEnum.getDesc();
            }
        }
        return "";
    }

}
