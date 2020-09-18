package com.yestae.modules.sms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yestae.modules.sms.entity.SmsPipeProductEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信渠道产品（一个产品多个渠道）

 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-07-25 11:30:35
 */
@Mapper
public interface SmsPipeProductDao extends BaseMapper<SmsPipeProductEntity> {
	
}
