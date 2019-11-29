package com.lizq.cache.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lizq.cache.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 带缓存
 */
@CacheConfig(cacheNames = "users")//users跟ehcache.xml里的cache name一致
@Component
public class UserService {

    @Autowired
    private UserBaseService userBaseService;

    @Cacheable(key = "#id")
    public User getById(String id) {
        return userBaseService.getById(id);
    }

    @Cacheable(key = "#name")
    public User getByName(String name) {
        User query = new User();
        query.setName(name);
        QueryWrapper<User> ew = new QueryWrapper<>(query);
        List<User> list = userBaseService.list(ew);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    //@CachePut(key="#p0.id")
    @Caching(
            put = {
                    @CachePut(key = "#result.id", condition = "#result != null"),
                    @CachePut(key = "#result.name", condition = "#result != null")
            })
    public User updateById(User user) {
        userBaseService.updateById(user);
        return userBaseService.getById(user.getId());
    }

    //@CachePut(key="#p0.id")
    @Caching(
            put = {
                    @CachePut(key = "#result.id"),
                    @CachePut(key = "#result.name")
            })
    public User insert(User user) {
        userBaseService.save(user);
        return userBaseService.getById(user.getId());
    }

    //@CacheEvict(key = "#id")
    @Caching(
            evict = {
                    @CacheEvict(key = "#result.id", condition = "#result != null"),
                    @CacheEvict(key = "#result.name", condition = "#result != null")
            })
    public User deleteById(String id) {
        User user = userBaseService.getById(id);
        userBaseService.removeById(id);
        return user;
    }

}
