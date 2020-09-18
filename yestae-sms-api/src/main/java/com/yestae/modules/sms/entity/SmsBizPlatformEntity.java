package com.yestae.modules.sms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 业务平台表
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-07-25 11:30:35
 */
@Data
@TableName("sms_biz_platform")
public class SmsBizPlatformEntity implements Serializable {
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
	 * 状态：
	 */
	private Integer status;

}
