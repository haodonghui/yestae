package com.yestae.modules.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.yestae.modules.sms.entity.SmsPipeProductEntity;

import java.util.Map;

/**
 * 短信渠道产品（一个产品多个渠道）
 *
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-07-25 11:30:35
 */
public interface SmsPipeProductService extends IService<SmsPipeProductEntity> {



    /**
     * 修改短信渠道产品（一个产品多个渠道）
     */
    void update(SmsPipeProductEntity smsPipeProduct) throws Exception;


}

