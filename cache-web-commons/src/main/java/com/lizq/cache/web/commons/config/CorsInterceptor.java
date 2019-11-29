package com.lizq.cache.web.commons.config;

import com.lizq.cache.web.commons.utils.CorsUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        CorsUtils.handleCors(request, response);
        return true;
    }
}
