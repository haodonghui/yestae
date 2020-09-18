package com.yestae.modules.sms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信发送记录
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-07-25 11:30:35
 */
@Data
@TableName("sms_send_record")
public class SmsSendRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId
	private Long id;
	/**
	 * 业务平台编码
	 */
	private String platformCode;
	/**
	 * 业务平台名称
	 */
	private String platformName;
	/**
	 * 签名名称
	 */
	private String signName;
	/**
	 * 短信通道编码
	 */
	private String pipeCode;
	/**
	 * 短信通道名称
	 */
	private String pipeName;
	/**
	 * 短信通道产品编码
	 */
	private String pipeProductCode;
	/**
	 * 短信通道产品名称
	 */
	private String pipeProductName;
	/**
	 * 业务短信类型
	 */
	private String bizSmsType;
	/**
	 * 业务短信类型名称
	 */
	private String bizSmsTypeName;
	/**
	 * 业务短信内容
	 */
	private String bizSmsContent;
	/**
	 * 发送时间
	 */
	private Date sendTime;

}
