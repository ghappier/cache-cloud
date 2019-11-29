package com.lizq.cache.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserResetPasswordVO implements Serializable {

    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    private static final long serialVersionUID = 1L;
}
