package com.mxw.applicationWeb.config;

import com.google.common.collect.Lists;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);

        // 指定运行地址
        //corsConfiguration.setAllowedOrigins(Lists.newArrayList("http://192.168.10.1:8080"));
        // 允许任何域名
        corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);

        // 允许任何头部信息
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        // 允许所有请求类型
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        corsConfiguration.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}