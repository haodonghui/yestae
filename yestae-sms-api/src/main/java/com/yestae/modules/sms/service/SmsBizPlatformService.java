package com.yestae.modules.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.yestae.modules.sms.entity.SmsBizPlatformEntity;

import java.util.Map;

/**
 * 业务平台表
 *
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-07-25 11:30:35
 */
public interface SmsBizPlatformService extends IService<SmsBizPlatformEntity> {


	/**
	 * 修改业务平台表
	 */
	void update(SmsBizPlatformEntity smsBizPlatform) throws Exception;
}

