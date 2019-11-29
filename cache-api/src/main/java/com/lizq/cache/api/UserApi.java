package com.lizq.cache.api;


import com.lizq.cache.commons.exception.ServiceException;
import com.lizq.cache.vo.*;

public interface UserApi {

    UserLoginResult login(UserLoginVO vo) throws ServiceException;

    UserLoginResult register(UserVO vo) throws ServiceException;

    boolean resetPassword(String id, UserResetPasswordVO user) throws ServiceException;

    UserProfile getProfileById(String id);

    UserProfile getProfileByName(String name);
}
