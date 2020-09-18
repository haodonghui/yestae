package com.yestae.modules.sms.service;


import com.yestae.modules.sms.dto.BatchSmsDto;
import com.yestae.modules.sms.dto.SmsOpenResultDto;
import com.yestae.modules.sms.dto.ValidCodeDto;
import com.yestae.modules.sms.dto.VerificationCodeDto;

public interface SmsSendOpenService {

    SmsOpenResultDto sendVerificationcCode(VerificationCodeDto verificationCodeDto) throws Exception;

    SmsOpenResultDto isValidCode(ValidCodeDto validCodeDto) throws Exception;

    SmsOpenResultDto sendBatchNotify(BatchSmsDto batchSmsDto) throws Exception;

    SmsOpenResultDto sendBatchMarketing(BatchSmsDto batchSmsDto) throws Exception;

    /**
     *  生成短信验证码，不下发短信
     * @param verificationCodeDto
     * @return
     */
    String generateCode(VerificationCodeDto verificationCodeDto);
}
