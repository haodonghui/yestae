package com.yestae.sms.sdk.request;

/**
 * @author zhangzhi
 * @description: TODO
 * @Date 2019/8/9 17:34
 **/
public class SmsNotifyRequest implements SmsRequest {
    //	必选	String	业务平台编码
    private final String platform;
    //		Integer	类型:0-国内 1-国际 默认0
    private final Integer type;
    //	必选	Integer	业务类型
    private final Integer bizType;
    //	必选	String	短信内容
    private final String content;
    //	必选	String	参数
    private final String params;
    //	非必填	String	定时发送短信时间
    private final String sendtime;

    public SmsNotifyRequest(Builder builder) {
        this.platform = builder.platform;
        this.type = builder.type;
        this.bizType = builder.bizType;
        this.content = builder.content;
        this.params = builder.params;
        this.sendtime = builder.sendtime;
    }

    public static class Builder {
        private String platform;
        private Integer type = 0;
        private Integer bizType;
        private String content;
        private String params;
        private String sendtime;

        public Builder platform(String platform) {
            this.platform = platform;
            return this;
        }

        public Builder type(Integer type) {
            this.type = type;
            return this;
        }

        public Builder bizType(Integer bizType) {
            this.bizType = bizType;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder params(String params) {
            this.params = params;
            return this;
        }

        public Builder sendtime(String sendtime) {
            this.sendtime = sendtime;
            return this;
        }

        public SmsNotifyRequest build() {
            return new SmsNotifyRequest(this);
        }
    }


}
