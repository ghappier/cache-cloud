package com.lizq.cache.web.controller;

import com.lizq.cache.api.UserApi;
import com.lizq.cache.commons.utils.JwtUtils;
import com.lizq.cache.vo.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtils jwtHelper;
    @Reference
    private UserApi userApi;

    @PostMapping("/login")
    public UserLoginResult login(@RequestBody @Validated UserLoginVO user) {
        return userApi.login(user);
    }

    @PostMapping("/register")
    public UserLoginResult register(@RequestBody @Validated UserVO user) {
        return userApi.register(user);
    }

    @PostMapping("/reset-password")
    public Boolean resetPassword(@RequestBody @Validated UserResetPasswordVO user) {
        String id = jwtHelper.getUserFromRequest().getId();
        return userApi.resetPassword(id, user);
    }

    @GetMapping("/profile-id")
    public UserProfile profileById() {
        String id = jwtHelper.getUserFromRequest().getId();
        return userApi.getProfileById(id);
    }

    @GetMapping("/profile-name")
    public UserProfile profileByName() {
        String name = jwtHelper.getUserFromRequest().getName();
        return userApi.getProfileByName(name);
    }
}
