package com.yestae.sms.sdk.response;

import com.yestae.common.api.ApiResult;
import com.yestae.common.utils.MessageUtils;

import java.io.Serializable;

/**
 * @author zhangzhi
 * @description: TODO
 * @Date 2019/8/9 17:21
 **/
public class SmsSdkResponse<T> extends ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 4131532620976855849L;

    public SmsSdkResponse() {
    }

    @Override
    public SmsSdkResponse<T> setRetCode(String code) {
        super.setRetCode(code);
        this.setRetMsg();
        return this;
    }

    public SmsSdkResponse<T> setRetMsg() {
        String msg = MessageUtils.message(this.getRetCode());
        super.setRetMsg(msg);
        return this;
    }

}
