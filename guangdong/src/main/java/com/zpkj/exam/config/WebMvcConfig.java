package com.zpkj.exam.config;

import com.zpkj.exam.interceptor.AccessTokenVerifyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2018/6/6.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public AccessTokenVerifyInterceptor tokenVerifyInterceptor() {
        return new AccessTokenVerifyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenVerifyInterceptor()).addPathPatterns("/m/**").addPathPatterns("/f/**");
        super.addInterceptors(registry);
    }

}
