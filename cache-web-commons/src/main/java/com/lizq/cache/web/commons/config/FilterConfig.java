package com.lizq.cache.web.commons.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean contextFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(tokenAuthorFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("tokenAuthorFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public Filter tokenAuthorFilter() {
        return new TokenAuthorFilter();
    }
}
