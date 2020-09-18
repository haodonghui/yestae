package com.yestae.modules.sms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 短信渠道产品（一个产品多个渠道）
 *
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-07-25 11:30:35
 */
@Data
@TableName("sms_pipe_product")
public class SmsPipeProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    private Long id;
    /**
     * 短信渠道id
     */
    private Long pipeId;
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
     * 优先级
     */
    private Integer priorityLevel;

    private String pipeProductApi;

    private String account;
    private String password;

    /**
     * 类型:0-国内 1-国际 默认0
     */
    private Integer type;
    /**
     * 状态：
     */
    private Integer status;

}
