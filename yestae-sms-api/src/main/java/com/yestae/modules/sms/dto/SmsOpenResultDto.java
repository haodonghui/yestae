package com.yestae.modules.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsOpenResultDto {
    private boolean succeed;
    private String retCode;
    private String retMsg;

}
