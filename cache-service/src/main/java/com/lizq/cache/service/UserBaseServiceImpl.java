package com.lizq.cache.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizq.cache.entity.User;
import com.lizq.cache.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserBaseServiceImpl extends ServiceImpl<UserMapper, User> implements UserBaseService {

}
