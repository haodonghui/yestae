package com.yestae.modules.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yestae.modules.sms.dao.SmsSendRecordDao;
import com.yestae.modules.sms.entity.SmsSendRecordEntity;
import com.yestae.modules.sms.service.SmsSendRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 短信发送记录
 * 根据数据源手动配置 @DataSource("slave1")并继承对应的ServiceImpl* 比如数据源1：继承ServiceImplSava1  据源2：继承ServiceImplSava2
 * 默认数据源无需配置
 *
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-07-25 11:30:35
 */
@Service("smsSendRecordService")
//@DataSource("slave1")
public class SmsSendRecordServiceImpl extends ServiceImpl<SmsSendRecordDao, SmsSendRecordEntity> implements SmsSendRecordService {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    @Transactional
    public void update(SmsSendRecordEntity smsSendRecord) throws Exception {
        this.updateById(smsSendRecord);
    }

}