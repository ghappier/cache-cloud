package com.lizq.cache.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginResult implements Serializable {

    private String id;
    private String name;
    private String nickname;
    private String avatar;
    private String token;

    private static final long serialVersionUID = 1L;
}
