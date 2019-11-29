package com.lizq.cache.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private String id;

    private String name;

    private String password;

    private String salt;

    private String nickname;

    private String avatar;

    /**
     * 状态：1、启用；2、禁用
     */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    private Date lastLoginTime;

    public static final int STATUS_ENABLE = 1;//启用
    public static final int STATUS_DISABLE = 2;//禁用

    private static final long serialVersionUID = 1L;
}
