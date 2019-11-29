package com.lizq.cache.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserVO implements Serializable {

    @NotBlank(message = "用户名不能为空")
    @Length(max = 50, message = "用户名最多{max}个字")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Length(max = 64, message = "密码最多{max}个字")
    private String password;

    private String nickname;

    private String avatar;

    private static final long serialVersionUID = 1L;
}
