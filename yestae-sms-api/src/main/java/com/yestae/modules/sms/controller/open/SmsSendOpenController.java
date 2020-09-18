package com.yestae.modules.sms.controller.open;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yestae.common.utils.R;
import com.yestae.modules.sms.dto.BatchSmsDto;
import com.yestae.modules.sms.dto.SmsOpenResultDto;
import com.yestae.modules.sms.dto.ValidCodeDto;
import com.yestae.modules.sms.dto.VerificationCodeDto;
import com.yestae.modules.sms.entity.SmsBizPlatformEntity;
import com.yestae.modules.sms.productenum.BizTypeEnum;
import com.yestae.modules.sms.productenum.SmsError;
import com.yestae.modules.sms.service.SmsBizPlatformService;
import com.yestae.modules.sms.service.SmsSendOpenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 对外发送短信接口
 */
@RestController
@RequestMapping("sms/open/send")
@Api(value = "smsOpen", description = "外部短信发送接口", produces = "application/json")
public class SmsSendOpenController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SmsSendOpenService smsSendOpenService;
    @Autowired
    private SmsBizPlatformService smsBizPlatformService;

    /**
     * 发送短息验证码
     */
    @PostMapping("/sendVerificationCode")
    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码", produces = "application/json", hidden = false)
    public R sendVerificationcCode(@ApiParam(name = "verificationCodeDto", value = "", required = true)
                                   @RequestBody VerificationCodeDto verificationCodeDto) {
        try {
            String checkParams = checkParams(verificationCodeDto.getPlatform(), verificationCodeDto.getBizType(), verificationCodeDto.getContent());
            if (!StringUtils.isEmpty(checkParams)) {
                return R.error(checkParams);
            }
            SmsOpenResultDto smsOpenResultDto = smsSendOpenService.sendVerificationcCode(verificationCodeDto);
            return R.ok().put("smsOpenResultDto", smsOpenResultDto);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    /**
     * 验证验证码
     */
    @PostMapping("/checkValidCode")
    @ApiOperation(value = "验证短信验证码", notes = "发送短信验证码", produces = "application/json", hidden = false)
    public R checkValidCode(@ApiParam(name = "validCodeDto", value = "", required = true) @RequestBody ValidCodeDto
                                    validCodeDto) {
        try {
            String checkParams = checkParams(validCodeDto.getPlatform(), validCodeDto.getBizType(), "checkValidCode");
            if (!StringUtils.isEmpty(checkParams)) {
                return R.error(checkParams);
            }
            SmsOpenResultDto smsOpenResultDto = smsSendOpenService.isValidCode(validCodeDto);
            return R.ok().put("smsOpenResultDto", smsOpenResultDto);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    /**
     * 批量发送通知短信
     */
    @PostMapping("/sendBatchNotify")
    @ApiOperation(value = "批量发送通知短信", notes = "批量发送通知短信", produces = "application/json", hidden = false)
    public R sendBatchNotify(@ApiParam(name = "batchSmsDto", value = "", required = true) @RequestBody BatchSmsDto
                                     batchSmsDto) {
        try {
            String checkParams = checkParams(batchSmsDto.getPlatform(), batchSmsDto.getBizType(), batchSmsDto.getContent());
            if (!StringUtils.isEmpty(checkParams)) {
                return R.error(checkParams);
            }
            SmsOpenResultDto smsOpenResultDto = smsSendOpenService.sendBatchNotify(batchSmsDto);
            return R.ok().put("smsOpenResultDto", smsOpenResultDto);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    /**
     * 批量发送营销短信
     */
    @PostMapping("/sendBatchMarketing")
    @ApiOperation(value = "批量发送营销短信", notes = "批量发送营销短信", produces = "application/json", hidden = false)
    public R sendBatchMarketing(@ApiParam(name = "batchSmsDto", value = "", required = true) @RequestBody BatchSmsDto batchSmsDto) {
        try {
            String checkParams = checkParams(batchSmsDto.getPlatform(), batchSmsDto.getBizType(), batchSmsDto.getContent());
            if (!StringUtils.isEmpty(checkParams)) {
                return R.error(checkParams);
            }
            SmsOpenResultDto smsOpenResultDto = smsSendOpenService.sendBatchMarketing(batchSmsDto);
            return R.ok().put("smsOpenResultDto", smsOpenResultDto);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    @PostMapping("/generateCode")
    @ApiOperation(value = "生成虚拟短信验证码", notes = "生成虚拟短信验证码", produces = "application/json", hidden = false)
    public R generateCode(@ApiParam(name = "verificationCodeDto", value = "", required = true)
                                   @RequestBody VerificationCodeDto verificationCodeDto) {
        try {
            String checkParams = checkParams(verificationCodeDto.getPlatform(), verificationCodeDto.getBizType(), verificationCodeDto.getContent());
            if (!StringUtils.isEmpty(checkParams)) {
                return R.error(checkParams);
            }
            String code = smsSendOpenService.generateCode(verificationCodeDto);
            return R.ok().put("smsCode", code);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    private String checkParams(String platform, Integer bizType, String content) {
        String checkParmas = "";
        if (!"checkValidCode".equals(content)) {
            if (!content.contains("【") && !content.contains("】")) {
                return SmsError.SMS_CONTENT_ARGUMENT.getDesc();
            }
        }

        String valueByCode = BizTypeEnum.getValueByCode(bizType);
        if (StringUtils.isEmpty(valueByCode)) {
            return SmsError.SMS_PLATFORM_NULL.getDesc();
        }
        QueryWrapper<SmsBizPlatformEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("platform_code", platform);
        queryWrapper.eq("status", 1);
        SmsBizPlatformEntity one = smsBizPlatformService.getOne(queryWrapper);
        if (one == null) {
            return SmsError.SMS_PLATFORM_NULL.getDesc();
        }
        return checkParmas;
    }

}
