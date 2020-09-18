package com.yestae.modules.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCodeDto {

    private String mobile;//	必选	String	手机号
    private String platform;//	必选	String	业务平台编码
    private Integer bizType;//	必选	Integer	业务类型
    private Integer type = 0;//		Integer	类型:0-国内 1-国际 默认0
    private String content;//	必选	String	验证码短信内容
    private Integer expires = 1800;//		Integer	生存时间，单位：秒;默认1800秒
    private String code;//	必选	String	验证码


}
