package com.yestae.modules.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidCodeDto {

    private String mobile;//	必选	String	手机号
    private String platform;//	必选	String	业务平台编码
    private Integer bizType;//	必选	Integer	业务类型
    private String code;//	必选	String	验证码
    private Integer type;//0国内1国际
    private Integer changeCodeStatus; //是否在校验后设置验证码未失效状态，0：不设置， 1:设置

}
