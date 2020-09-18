package com.yestae.sms.sdk.request;

/**
 * @author zhangzhi
 * @description: TODO
 * @Date 2019/8/12 16:11
 **/
public class SmsCheckVerfyCodeRequest implements SmsRequest {
    //	必选	String	手机号
    private final String mobile;
    //	必选	String	业务平台编码
    private final String platform;
    //	必选	Integer	业务类型
    private final Integer bizType;
    //	必选	String	验证码
    private final String code;
    //0国内1国际
    private final Integer type;
    private Integer changeCodeStatus; //是否在校验后设置验证码未失效状态，0：不设置， 1:设置

    public SmsCheckVerfyCodeRequest(Builder builder) {
        this.mobile = builder.mobile;
        this.platform = builder.platform;
        this.bizType = builder.bizType;
        this.code = builder.code;
        this.type = builder.type;
        this.changeCodeStatus = builder.changeCodeStatus;
    }

    public static class Builder {
        private String mobile;
        private String platform;
        private Integer bizType;
        private String code;
        private Integer type = 0;
        //是否在校验后设置验证码未失效状态，0：不设置， 1:设置
        private Integer changeCodeStatus = 1;

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

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder type(Integer type) {
            this.type = type;
            return this;
        }
        public Builder changeCodeStatus(Integer changeCodeStatus) {
            this.changeCodeStatus = changeCodeStatus;
            return this;
        }

        public SmsCheckVerfyCodeRequest build() {
            return new SmsCheckVerfyCodeRequest(this);
        }

    }
}
