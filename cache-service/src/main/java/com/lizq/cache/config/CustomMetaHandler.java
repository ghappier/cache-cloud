package com.lizq.cache.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomMetaHandler implements MetaObjectHandler {

    /**
     * 新增数据执行
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        // this.setFieldValByName("createTime", now, metaObject); // 版本号3.0.6以及之前的版本
        // this.setFieldValByName("updateTime", now, metaObject); // 版本号3.0.6以及之前的版本
        this.setInsertFieldValByName("createTime", now, metaObject); // 版本号3.0.6以后
        this.setInsertFieldValByName("updateTime", now, metaObject); // 版本号3.0.6以后
    }

    /**
     * 更新数据执行
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // this.setFieldValByName("updateTime", new Date(), metaObject); // 版本号3.0.6以及之前的版本
        this.setUpdateFieldValByName("updateTime", new Date(), metaObject); // 版本号3.0.6以后
    }
}
