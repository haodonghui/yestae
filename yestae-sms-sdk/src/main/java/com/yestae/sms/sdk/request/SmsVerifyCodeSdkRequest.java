package com.yestae.sms.sdk.request;


/**
 * @author zhangzhi
 * @description: TODO
 * @Date 2019/8/9 17:21
 **/
public class SmsVerifyCodeSdkRequest implements SmsRequest {
    //	必选	String	手机号
    private final String mobile;
    //	必选	String	业务平台编码
    private final String platform;
    //	必选	Integer	业务类型
    private final Integer bizType;
    //		Integer	类型:0-国内 1-国际 默认0
    private final Integer type;
    //	必选	String	验证码短信内容
    private final String content;
    //		Integer	生存时间，单位：秒;默认1800秒
    private final Integer expires;
    //	必选	String	验证码
    private final String code;

    public SmsVerifyCodeSdkRequest(Builder builder) {
        this.mobile = builder.mobile;
        this.platform = builder.platform;
        this.bizType = builder.bizType;
        this.type = builder.type;
        this.content = builder.content;
        this.expires = builder.expires;
        this.code = builder.code;
    }

    public static class Builder {
        private String mobile;
        private String platform;
        private Integer bizType;
        private Integer type = 0;
        private String content;
        private Integer expires;
        private String code;

        public Builder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder platform(String platform) {
            this.platform = platform;
            return this;
        }

        public Builder bizType(Integer bizType) {
            this.bizType = bizType;
            return this;
        }

        public Builder type(Integer type) {
            this.type = type;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder expires(Integer expires) {
            this.expires = expires;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public SmsVerifyCodeSdkRequest build() {
            return new SmsVerifyCodeSdkRequest(this);
        }
    }

}
