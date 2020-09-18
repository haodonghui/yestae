package com.yestae.modules.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yestae.modules.sms.entity.SmsSendRecordEntity;

import java.util.Map;

/**
 * 短信发送记录
 *
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-07-25 11:30:35
 */
public interface SmsSendRecordService extends IService<SmsSendRecordEntity> {



	/**
	 * 修改短信发送记录
	 */
	void update(SmsSendRecordEntity smsSendRecord) throws Exception;
}

