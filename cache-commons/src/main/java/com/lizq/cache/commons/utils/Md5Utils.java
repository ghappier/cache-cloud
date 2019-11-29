package com.lizq.cache.commons.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class Md5Utils {
    /**
     * 获取加密
     */
    public static String getMd5(String pwd) {
        return DigestUtils.md5Hex(pwd);
    }

    public static String randomPassword(){
        String password = RandomStringUtils.randomNumeric(32);
        return getMd5(password);
    }

    /**
     * 获取随机盐
     *
     * @return
     */
    public static String getSalt() {
        String salt = RandomStringUtils.randomNumeric(8);
        return getMd5(salt);
    }

    /**
     * 获取密码
     * @param password
     * @param salt
     * @return
     */
    public static String getPassword(String password, String salt) {
        return getMd5(password + salt);
    }

    public static void main(String[] args) {
        System.out.println(getPassword("123456","c1b6f04c-34e7-44de-8519-2d5a3632b1b5"));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
