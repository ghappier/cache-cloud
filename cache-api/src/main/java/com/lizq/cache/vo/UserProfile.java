package com.lizq.cache.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class UserProfile implements Serializable {

    private String name;

    private String nickname;

    private String avatar;

    private Date lastLoginTime;

    private static final long serialVersionUID = 1L;
}
