package com.lizq.cache.commons.utils;

import lombok.Data;

@Data
public class JwtUser {

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;
}
