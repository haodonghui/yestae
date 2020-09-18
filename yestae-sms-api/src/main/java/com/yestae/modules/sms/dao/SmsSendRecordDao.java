package com.yestae.modules.sms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yestae.modules.sms.entity.SmsSendRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信发送记录
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-07-25 11:30:35
 */
@Mapper
public interface SmsSendRecordDao extends BaseMapper<SmsSendRecordEntity> {
	
}
