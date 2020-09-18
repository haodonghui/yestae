package com.yestae.modules.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class BatchSmsDto {

    private String platform;//	必选	Integer	业务平台编码
    private Integer type;//		Integer	类型:0-国内 1-国际 默认0
    private String content;//	必选	String	短信内容
    private String params;//	必选	String	参数
    private Integer bizType;//业务类型
    private String sendtime;//定时发送给的时间

}
