package com.lizq.cache.web.commons.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Page.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest request,
                                  WebDataBinderFactory binderFactory) throws Exception {
        // 判断数据是否合理, 不合理就给定默认值
        int limit = str2int(request.getParameter(PageConstant.GLOBAL_LIMIT));
        int offset = str2int(request.getParameter(PageConstant.GLOBAL_OFFSET));
        if (limit <= 0) {
            limit = PageConstant.DEFAULT_LIMIT;
        }
        if (limit > PageConstant.DEFAULT_MAX_LIMIT) {
            limit = PageConstant.DEFAULT_MAX_LIMIT;
        }
        if (offset <= 0) {
            offset = PageConstant.DEFAULT_OFFSET;
        }
        Page page = new Page(offset, limit);
        return page;
    }

    private static int str2int(String str) {
        return str2int(str, 0);
    }

    private static int str2int(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException var3) {
                return defaultValue;
            }
        }
    }
}
