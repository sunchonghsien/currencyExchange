package com.example.currencyexchange.interceptor;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class WeConfig implements WebMvcConfigurer {
    @Setter(onMethod_ = {@Autowired})
    IndexInterceptor indexInterceptor;
    @Setter(onMethod_ = {@Autowired})
    CheckLoginInterceptor checkLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> checkLogin = new ArrayList<>();
        checkLogin.add("/index/**");

        registry.addInterceptor(indexInterceptor);
        registry.addInterceptor(checkLoginInterceptor).excludePathPatterns(checkLogin);
    }
}
