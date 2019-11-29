package com.lizq.cache.provider;

import com.lizq.cache.api.UserApi;
import com.lizq.cache.commons.exception.ServiceException;
import com.lizq.cache.commons.utils.JwtUser;
import com.lizq.cache.commons.utils.JwtUtils;
import com.lizq.cache.commons.utils.Md5Utils;
import com.lizq.cache.entity.User;
import com.lizq.cache.service.UserService;
import com.lizq.cache.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@Service(interfaceClass = UserApi.class)
public class UserProvider implements UserApi {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserLoginResult login(UserLoginVO user) throws ServiceException {
        User loginUser = userService.getByName(user.getName());
        if (loginUser == null) {
            throw new ServiceException("账号不存在");
        }
        if (loginUser.getStatus() == 2) {
            throw new ServiceException("账号已被禁用");
        }
        String salt = StringUtils.isBlank(loginUser.getSalt()) ? "" : loginUser.getSalt();
        if (!StringUtils.equalsIgnoreCase(Md5Utils.getPassword(user.getPassword(), salt), loginUser.getPassword())) {
            throw new ServiceException("账号或密码不正确");
        }
        User updatedUser = new User();
        updatedUser.setId(loginUser.getId());
        updatedUser.setLastLoginTime(new Date());
        userService.updateById(updatedUser);

        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(loginUser.getId());
        jwtUser.setName(loginUser.getName());
        jwtUser.setNickname(loginUser.getNickname());
        jwtUser.setAvatar(loginUser.getAvatar());
        String accessToken = jwtUtils.createJWT(jwtUser);

        UserLoginResult result = new UserLoginResult();
        BeanUtils.copyProperties(loginUser, result);
        result.setToken(accessToken);

        return result;
    }

    @Override
    public UserLoginResult register(UserVO user) throws ServiceException {
        User exist = userService.getByName(user.getName());
        if (exist != null) {
            throw new ServiceException("账号已存在");
        }
        User registerUser = new User();
        BeanUtils.copyProperties(user, registerUser);
        String salt = UUID.randomUUID().toString();
        registerUser.setPassword(Md5Utils.getPassword(user.getPassword(), salt));
        registerUser.setSalt(salt);
        registerUser.setCreateTime(new Date());
        userService.insert(registerUser);

        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(registerUser.getId());
        jwtUser.setName(registerUser.getName());
        jwtUser.setNickname(registerUser.getNickname());
        jwtUser.setAvatar(registerUser.getAvatar());
        String accessToken = jwtUtils.createJWT(jwtUser);

        UserLoginResult result = new UserLoginResult();
        BeanUtils.copyProperties(registerUser, result);
        result.setToken(accessToken);

        return result;
    }

    @Override
    public boolean resetPassword(String id, UserResetPasswordVO user) throws ServiceException {
        if (!StringUtils.equals(user.getPassword(), user.getConfirmPassword())) {
            throw new ServiceException("密码和确认密码不一致");
        }
        String salt = UUID.randomUUID().toString();
        User resetUser = new User();
        resetUser.setId(id);
        resetUser.setSalt(salt);
        resetUser.setPassword(Md5Utils.getPassword(user.getPassword(), salt));
        userService.updateById(resetUser);
        return true;
    }

    @Override
    public UserProfile getProfileById(String id) {
        User user = userService.getById(id);
        if (user == null) {
            return null;
        }
        UserProfile profile = new UserProfile();
        BeanUtils.copyProperties(user, profile);
        return profile;
    }

    @Override
    public UserProfile getProfileByName(String name) {
        User user = userService.getByName(name);
        if (user == null) {
            return null;
        }
        UserProfile profile = new UserProfile();
        BeanUtils.copyProperties(user, profile);
        return profile;
    }
}
