package com.yestae.modules.sms.sendutils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangzhi
 * @description: TODO
 * @Date 2019/8/7 15:38
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendInternationalRequest {
    //	String	是	用户标识，在管理控制台获取	I1234567
    private String account;
    //	String	是	用户标识，在管理控制台获取	12a34b5678
    private String password;
    //	String	是	用户自定义短信内容	【253云通讯】您的验证码是1234
    private String msg;
    //	String	是	接收短信的手机号码	8618212345678,8618213456789"国际代码+手机号"
    private String mobile;

}
