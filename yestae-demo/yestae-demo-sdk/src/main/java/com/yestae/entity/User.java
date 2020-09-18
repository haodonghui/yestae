package com.yestae.entity;


import com.yestae.framework.businesslog.annotation.LogField;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class User implements Serializable {
    @LogField(desc = "用户id")
    private Integer userId;

    @LogField(desc = "用户名称")
    private String userName;

    @LogField(desc = "用户密码")
    private String password;

    @LogField(desc = "用户手机")
    private String phone;
}

