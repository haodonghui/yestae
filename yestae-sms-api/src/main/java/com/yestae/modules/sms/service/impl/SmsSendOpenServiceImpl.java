package com.yestae.modules.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.yestae.common.utils.RedisUtils;
import com.yestae.modules.sms.dto.BatchSmsDto;
import com.yestae.modules.sms.dto.SmsOpenResultDto;
import com.yestae.modules.sms.dto.ValidCodeDto;
import com.yestae.modules.sms.dto.VerificationCodeDto;
import com.yestae.modules.sms.entity.SmsBizPlatformEntity;
import com.yestae.modules.sms.entity.SmsPipeProductEntity;
import com.yestae.modules.sms.entity.SmsSendRecordEntity;
import com.yestae.modules.sms.productenum.*;
import com.yestae.modules.sms.sendutils.Send253Utils;
import com.yestae.modules.sms.sendutils.response.SmsVariableResponse;
import com.yestae.modules.sms.service.SmsBizPlatformService;
import com.yestae.modules.sms.service.SmsPipeProductService;
import com.yestae.modules.sms.service.SmsSendOpenService;
import com.yestae.modules.sms.service.SmsSendRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author zzy
 */
@Service("smsSendOpenService")
public class SmsSendOpenServiceImpl implements SmsSendOpenService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final static String REDIS_CODE_PREFIX = "sms:send:code";
    private final static String REDIS_CODE_ISVALID_PREFIX = "sms:send:code:valid";
    @Autowired
    private SmsPipeProductService smsPipeProductService;
    @Autowired
    private SmsBizPlatformService smsBizPlatformService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private SmsSendRecordService smsSendRecordService;

    @Override
    public SmsOpenResultDto sendVerificationcCode(VerificationCodeDto verificationCodeDto) throws Exception {
        SmsOpenResultDto smsOpenResultDto = new SmsOpenResultDto();
        String codeKey = REDIS_CODE_PREFIX + ":" + verificationCodeDto.getMobile() + ":" + verificationCodeDto.getPlatform() +
                ":" + verificationCodeDto.getBizType() + ":" + verificationCodeDto.getType() + ":" + verificationCodeDto.getCode();

        String validKey = REDIS_CODE_ISVALID_PREFIX + ":" + verificationCodeDto.getMobile() + ":" + verificationCodeDto.getPlatform() +
                ":" + verificationCodeDto.getBizType() + ":" + verificationCodeDto.getType() + ":" + verificationCodeDto.getCode();
        //1.拿到入参  内容里的签名
        //2.通过产品编码获取 短信渠道产品集合
        Map<String, Object> map = Maps.newHashMap();
        initResult(map, verificationCodeDto.getContent(), verificationCodeDto.getType(), ProductEnum.VERIFICATIONCODE_SMS.getCode());
        String signName = (String) map.get("signName");
        List<SmsPipeProductEntity> smsPipeProductEntities = (List<SmsPipeProductEntity>) map.get("smsPipeProductEntities");
        SmsSendRecordEntity smsSendRecordEntity = null;
        for (SmsPipeProductEntity smsPipeProductEntity : smsPipeProductEntities) {
            smsSendRecordEntity = new SmsSendRecordEntity();
            String pipeCode = smsPipeProductEntity.getPipeCode();
            setSmsSendRecordEntity(smsSendRecordEntity, signName, smsPipeProductEntity.getPipeCode(),
                    smsPipeProductEntity.getPipeName(), smsPipeProductEntity.getPipeProductCode(), smsPipeProductEntity.getPipeProductName());
            if (PipeEnum.CL253_SMS.getCode().equals(pipeCode)) {
                //判断国内  国际
                String result = "";
                if (InternationalEnum.INTERNATIONAL.getType() == verificationCodeDto.getType()) {
                    result = Send253Utils.sendSingleInternationalSms(verificationCodeDto.getContent(), verificationCodeDto.getMobile(), SmsEnum.INTERNATIONAL.getCode());
                } else {
                    result = Send253Utils.sendGeneralMsg(verificationCodeDto.getMobile(), verificationCodeDto.getContent(), SmsEnum.VERIFICODE.getCode(), null);
                }
                if (StringUtils.isEmpty(result)) {
                    smsOpenResultDto = new SmsOpenResultDto(false, SmsError.SMS_MESSAGE_SEND_ERROR.getName(), SmsError.SMS_MESSAGE_SEND_ERROR.getDesc());
                    saveSmsRecord(smsSendRecordEntity, verificationCodeDto.getPlatform(), verificationCodeDto.getBizType() + "", verificationCodeDto.getContent());
                    continue;
                }
                //{"code":"0","msgId":"19072617281027497","time":"20190726172810","errorMsg":""}
                JSONObject resultObject = JSONObject.parseObject(result);
                String resultCode = resultObject.getString("code");
                if (!"0".equals(resultCode)) {
                    logger.error("短信发送失败code={},原因={},国际短信失败原因={}", resultCode, resultObject.getString("errorMsg"), resultObject.getString("message"));
                    smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_MESSAGE_SEND_ERROR.getName(), SmsError.SMS_MESSAGE_SEND_ERROR.getDesc());
                    saveSmsRecord(smsSendRecordEntity, verificationCodeDto.getPlatform(), verificationCodeDto.getBizType() + "", verificationCodeDto.getContent());
                    continue;
                } else {
                    smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_MESSAGE_SEND_SUCCESS.getName(), SmsError.SMS_MESSAGE_SEND_SUCCESS.getDesc());
                    //发送成功需要将验证码存入redis
                    redisUtils.set(codeKey, verificationCodeDto.getCode(), verificationCodeDto.getExpires());
                    redisUtils.set(validKey, "1");
                    saveSmsRecord(smsSendRecordEntity, verificationCodeDto.getPlatform(), verificationCodeDto.getBizType() + "", verificationCodeDto.getContent());
                    break;
                }

            } else if (PipeEnum.RLYUN_SMS.getCode().equals(pipeCode)) {
                logger.info("{}目前没有对接,无法发送短信", pipeCode);
                smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_MESSAGE_SEND_ERROR.getName(), SmsError.SMS_MESSAGE_SEND_ERROR.getDesc());
                saveSmsRecord(smsSendRecordEntity, verificationCodeDto.getPlatform(), verificationCodeDto.getBizType() + "", verificationCodeDto.getContent());
                continue;
            } else if (PipeEnum.AIXUN_SMS.getCode().equals(pipeCode)) {
                logger.info("{}目前没有对接,无法发送短信", pipeCode);
                smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_MESSAGE_SEND_ERROR.getName(), SmsError.SMS_MESSAGE_SEND_ERROR.getDesc());
                saveSmsRecord(smsSendRecordEntity, verificationCodeDto.getPlatform(), verificationCodeDto.getBizType() + "", verificationCodeDto.getContent());
                continue;
            } else if (PipeEnum.ALI_MNS.getCode().equals(pipeCode)) {
                logger.info("{}目前没有对接,无法发送短信", pipeCode);
                smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_MESSAGE_SEND_ERROR.getName(), SmsError.SMS_MESSAGE_SEND_ERROR.getDesc());
                saveSmsRecord(smsSendRecordEntity, verificationCodeDto.getPlatform(), verificationCodeDto.getBizType() + "", verificationCodeDto.getContent());
                continue;
            }
        }
        //发送完成后开始写日志
        return smsOpenResultDto;
    }


    @Override
    public SmsOpenResultDto isValidCode(ValidCodeDto validCodeDto) throws Exception {
        SmsOpenResultDto smsOpenResultDto = new SmsOpenResultDto();
        String end_key = ":" + validCodeDto.getMobile() + ":" + validCodeDto.getPlatform() +
                ":" + validCodeDto.getBizType() + ":" + validCodeDto.getType() + ":" + validCodeDto.getCode();
        String codeKey = REDIS_CODE_PREFIX + end_key;

        String validKey = REDIS_CODE_ISVALID_PREFIX + end_key;

        String codeResult = redisUtils.get(codeKey);
        if (StringUtils.isEmpty(codeResult)) {
            smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_CODE_ERROR.getName(), SmsError.SMS_CODE_ERROR.getDesc());
            return smsOpenResultDto;
        }
        String validResult = redisUtils.get(validKey);
        if ("0".equals(validResult)) {
            smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_CODE_USED.getName(), SmsError.SMS_CODE_USED.getDesc());
            return smsOpenResultDto;
        }

        //是否设置验证码为失效，默认设置0：不设置， 1:设置
        Integer changeCodeStatus = validCodeDto.getChangeCodeStatus() == null ? 1 : validCodeDto.getChangeCodeStatus() ;
        if ("1".equals(validResult) && validCodeDto.getCode().equals(codeResult)) {
            if(1 == changeCodeStatus){
                redisUtils.delete(validKey);
                redisUtils.set(validKey, "0");
            }
            smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_VERIFY_SUCCESS.getName(), SmsError.SMS_VERIFY_SUCCESS.getDesc());
            return smsOpenResultDto;
        }
        smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_VERIFY_ERROR.getName(), SmsError.SMS_VERIFY_ERROR.getDesc());
        return smsOpenResultDto;
    }

    @Override
    public SmsOpenResultDto sendBatchNotify(BatchSmsDto batchSmsDto) throws Exception {
        // 短信内容  String msg = "【益友会】尊敬的{$var},您好,您的验证码是{$var},{$var}分钟内有效";
//        //参数组 String params = "18937091992,先生,123456,3;13240409798,先生,123456,3;";
        //1.拿到入参  内容里的签名
        return senBatchSms(batchSmsDto, ProductEnum.VERIFICATIONCODE_SMS.getCode());
    }

    @Override
    public SmsOpenResultDto sendBatchMarketing(BatchSmsDto batchSmsDto) throws Exception {
        // 短信内容  String msg = "【益友会】尊敬的{$var},您好,您的验证码是{$var},{$var}分钟内有效";
//        //参数组 String params = "18937091992,先生,123456,3;13240409798,先生,123456,3;";
        //1.拿到入参  内容里的签名
        return senBatchSms(batchSmsDto, ProductEnum.MARKETING_SMS.getCode());
    }

    @Override
    public String generateCode(VerificationCodeDto verificationCodeDto) {
        String codeKey = REDIS_CODE_PREFIX + ":" + verificationCodeDto.getMobile() + ":" + verificationCodeDto.getPlatform() +
                ":" + verificationCodeDto.getBizType() + ":" + verificationCodeDto.getType() + ":" + verificationCodeDto.getCode();

        String validKey = REDIS_CODE_ISVALID_PREFIX + ":" + verificationCodeDto.getMobile() + ":" + verificationCodeDto.getPlatform() +
                ":" + verificationCodeDto.getBizType() + ":" + verificationCodeDto.getType() + ":" + verificationCodeDto.getCode();

        redisUtils.set(codeKey, verificationCodeDto.getCode(), verificationCodeDto.getExpires());
        redisUtils.set(validKey, "1");
        return verificationCodeDto.getCode();
    }

    private List<SmsBizPlatformEntity> querySmsBizPlatformEntity(String platformCode) {
        QueryWrapper<SmsBizPlatformEntity> smsBizPlatformEntityQueryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(platformCode)) {
            smsBizPlatformEntityQueryWrapper.eq("platform_code", platformCode);
        }
        smsBizPlatformEntityQueryWrapper.eq("status", 1);
        return smsBizPlatformService.list(smsBizPlatformEntityQueryWrapper);
    }

    private List<SmsPipeProductEntity> querySmsPipeProductEntity(boolean isProductCode, int type, String pipProductCode) {
        QueryWrapper<SmsPipeProductEntity> smsPipeProductEntityQueryWrapper = new QueryWrapper();
        if (isProductCode) {
            smsPipeProductEntityQueryWrapper.eq("pipe_product_code", pipProductCode);
        }
        smsPipeProductEntityQueryWrapper.eq("type", type);
        smsPipeProductEntityQueryWrapper.eq("status", 1);
        smsPipeProductEntityQueryWrapper.orderByDesc("priority_level");
        return smsPipeProductService.list(smsPipeProductEntityQueryWrapper);

    }


    private void initResult(Map<String, Object> map, String content, int type, String pipeProductName) {
        String signName = content.substring(content.indexOf("【") + 1, content.indexOf("】"));
        map.put("signName", signName);
        //2.通过产品编码获取 短信渠道产品集合
        List<SmsPipeProductEntity> smsPipeProductEntities = querySmsPipeProductEntity(true, type, pipeProductName);
        if (CollectionUtils.isEmpty(smsPipeProductEntities)) {
            smsPipeProductEntities = querySmsPipeProductEntity(false, type, pipeProductName);
        }
        map.put("smsPipeProductEntities", smsPipeProductEntities);
    }


    private SmsOpenResultDto senBatchSms(BatchSmsDto batchSmsDto, String pipeProductName) {
        SmsOpenResultDto smsOpenResultDto = new SmsOpenResultDto();
        //2.通过产品编码获取 短信渠道产品集合
        HashMap<String, Object> map = Maps.newHashMap();
        initResult(map, batchSmsDto.getContent(), batchSmsDto.getType(), pipeProductName);
        String signName = (String) map.get("signName");
        SmsSendRecordEntity smsSendRecordEntity = null;
        List<SmsPipeProductEntity> smsPipeProductEntities = (List<SmsPipeProductEntity>) map.get("smsPipeProductEntities");
        for (SmsPipeProductEntity smsPipeProductEntity : smsPipeProductEntities) {
            smsSendRecordEntity = new SmsSendRecordEntity();
            String pipeCode = smsPipeProductEntity.getPipeCode();
            setSmsSendRecordEntity(smsSendRecordEntity, signName, smsPipeProductEntity.getPipeCode(),
                    smsPipeProductEntity.getPipeName(), smsPipeProductEntity.getPipeProductCode(), smsPipeProductEntity.getPipeProductName());
            if (PipeEnum.CL253_SMS.getCode().equals(pipeCode)) {
                //判断国内  国际
                String result = "";
                String params = batchSmsDto.getParams();
                String content = batchSmsDto.getContent();
                if (InternationalEnum.INTERNATIONAL.getType() == batchSmsDto.getType()) {
                    result = Send253Utils.sendBatchInternationalSms(content, params, SmsEnum.INTERNATIONAL.getCode());
                } else {
                    if (params.contains(";") && content.contains("var")) {
                        if (pipeProductName.equals(ProductEnum.MARKETING_SMS.getCode())) {
                            result = Send253Utils.sendVariableMsg(params, content, SmsEnum.MARKETING.getCode(), batchSmsDto.getSendtime());
                        } else {
                            result = Send253Utils.sendVariableMsg(params, content, SmsEnum.VERIFICODE.getCode(), batchSmsDto.getSendtime());
                        }
                    } else {
                        if (pipeProductName.equals(ProductEnum.MARKETING_SMS.getCode())) {
                            result = Send253Utils.sendGeneralMsg(params, content, SmsEnum.MARKETING.getCode(), batchSmsDto.getSendtime());
                        } else {
                            result = Send253Utils.sendGeneralMsg(params, content, SmsEnum.VERIFICODE.getCode(), batchSmsDto.getSendtime());
                        }
                    }
                }
                if (StringUtils.isEmpty(result)) {
                    smsOpenResultDto = new SmsOpenResultDto(false, SmsError.SMS_MESSAGE_SEND_ERROR.getName(), SmsError.SMS_MESSAGE_SEND_ERROR.getDesc());
                    saveSmsRecord(smsSendRecordEntity, batchSmsDto.getPlatform(), batchSmsDto.getBizType() + "", batchSmsDto.getContent());
                    continue;
                }
                //{"code":"0","msgId":"19072617281027497","time":"20190726172810","errorMsg":""}
                SmsVariableResponse smsVariableResponse = JSON.parseObject(result, SmsVariableResponse.class);
                String resultCode = smsVariableResponse.getCode();
                if (!"0".equals(resultCode)) {
                    logger.error("短信发送失败code={},原因{}", resultCode, smsVariableResponse.getErrorMsg());
                    smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_MESSAGE_SEND_ERROR.getName(), SmsError.SMS_MESSAGE_SEND_ERROR.getDesc());
                    saveSmsRecord(smsSendRecordEntity, batchSmsDto.getPlatform(), batchSmsDto.getBizType() + "", batchSmsDto.getContent());
                    continue;
                } else {
                    smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_MESSAGE_SEND_SUCCESS.getName(), SmsError.SMS_MESSAGE_SEND_SUCCESS.getDesc());
                    saveSmsRecord(smsSendRecordEntity, batchSmsDto.getPlatform(), batchSmsDto.getBizType() + "", batchSmsDto.getContent());
                    break;
                }
            } else if (PipeEnum.RLYUN_SMS.getCode().equals(pipeCode)) {
                logger.info("{}目前没有对接,无法发送短信", pipeCode);
                smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_MESSAGE_SEND_ERROR.getName(), SmsError.SMS_MESSAGE_SEND_ERROR.getDesc());
                saveSmsRecord(smsSendRecordEntity, batchSmsDto.getPlatform(), batchSmsDto.getBizType() + "", batchSmsDto.getContent());
                continue;
            } else if (PipeEnum.AIXUN_SMS.getCode().equals(pipeCode)) {
                logger.info("{}目前没有对接,无法发送短信", pipeCode);
                smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_MESSAGE_SEND_ERROR.getName(), SmsError.SMS_MESSAGE_SEND_ERROR.getDesc());
                saveSmsRecord(smsSendRecordEntity, batchSmsDto.getPlatform(), batchSmsDto.getBizType() + "", batchSmsDto.getContent());
                continue;
            } else if (PipeEnum.ALI_MNS.getCode().equals(pipeCode)) {
                logger.info("{}目前没有对接,无法发送短信", pipeCode);
                smsOpenResultDto = new SmsOpenResultDto(true, SmsError.SMS_MESSAGE_SEND_ERROR.getName(), SmsError.SMS_MESSAGE_SEND_ERROR.getDesc());
                saveSmsRecord(smsSendRecordEntity, batchSmsDto.getPlatform(), batchSmsDto.getBizType() + "", batchSmsDto.getContent());
                continue;
            }
        }
        return smsOpenResultDto;
    }


    private void saveSmsRecord(SmsSendRecordEntity smsSendRecordEntity, String platformCode, String bizType, String conetent) {
        SmsSendRecordEntity finalSmsSendRecordEntity = smsSendRecordEntity;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<SmsBizPlatformEntity> smsBizPlatformEntities = querySmsBizPlatformEntity(platformCode);
                finalSmsSendRecordEntity.setPlatformCode(platformCode);
                finalSmsSendRecordEntity.setPlatformName(CollectionUtils.isEmpty(smsBizPlatformEntities) ? "" : smsBizPlatformEntities.get(0).getPlatformName());
                finalSmsSendRecordEntity.setSignName(smsSendRecordEntity.getSignName());
                finalSmsSendRecordEntity.setBizSmsType(bizType);
                if(!StringUtils.isEmpty(bizType)){
                    finalSmsSendRecordEntity.setBizSmsTypeName(BizTypeEnum.getValueByCode(Integer.valueOf(bizType)));
                }else{
                    finalSmsSendRecordEntity.setBizSmsTypeName("未定义");
                }
                finalSmsSendRecordEntity.setBizSmsContent(conetent);
                finalSmsSendRecordEntity.setSendTime(new Date());
                smsSendRecordService.save(finalSmsSendRecordEntity);
            }
        });
    }


    private void setSmsSendRecordEntity(SmsSendRecordEntity smsSendRecordEntity, String signName, String pipeCode, String pipeName, String pipeProductCode, String pipeProductName) {
        smsSendRecordEntity.setSignName(signName);
        smsSendRecordEntity.setPipeCode(pipeCode);
        smsSendRecordEntity.setPipeName(pipeName);
        smsSendRecordEntity.setPipeProductCode(pipeProductCode);
        smsSendRecordEntity.setPipeProductName(pipeProductName);
    }


}
