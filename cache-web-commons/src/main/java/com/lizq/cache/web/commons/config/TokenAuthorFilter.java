package com.lizq.cache.web.commons.config;

import com.lizq.cache.web.commons.utils.CorsUtils;
import com.lizq.cache.web.commons.utils.JsonResult;
import com.lizq.cache.commons.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TokenAuthorFilter implements Filter {

    @Autowired
    private JwtUtils jwtUtils;

    private List<String> whiteList = Arrays.asList(
            "/user/login",
            "/user/logout",
            "/user/register",
            "/druid"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;

        CorsUtils.handleCors(req, rep);

        String method = ((HttpServletRequest) request).getMethod();
        if (method.equals("OPTIONS")) {
            rep.setStatus(HttpServletResponse.SC_OK);
        } else {
            boolean filter = false;
            String uri = req.getRequestURI();
            if (pass(uri)) {
                filter = true;
            } else {
                String token = req.getHeader("token");
                if (StringUtils.isBlank(token)) {
                    JsonResult.failure().msg("获取token信息失败").code(HttpServletResponse.SC_UNAUTHORIZED).toJson(req, rep);
                } else if (jwtUtils.isTokenExpired(token)) {
                    JsonResult.failure().msg("token失效，请重新登录").code(HttpServletResponse.SC_UNAUTHORIZED).toJson(req, rep);
                } else {
                    filter = true;
                }
            }
            if (filter) {
                chain.doFilter(request, response);
            }

        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 包含在白名单的url放行
     *
     * @param url
     * @return 是否放行
     */
    private boolean pass(String url) {
        for (String w : whiteList) {
            //if (w.contains(url)) {
            if (url.contains(w)) {
                return true;
            }
        }
        return false;
    }

}
