package com.yestae.modules.sms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yestae.modules.sms.entity.SmsBizPlatformEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 业务平台表
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-07-25 11:30:35
 */
@Mapper
public interface SmsBizPlatformDao extends BaseMapper<SmsBizPlatformEntity> {
	
}
