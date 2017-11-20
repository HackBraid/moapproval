package com.longfor.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by issuser on 2017/7/18.
 */
@Configuration
public class WebConfigurerIntercepter extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链拦截跨域
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
