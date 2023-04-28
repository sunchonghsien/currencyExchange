package com.example.currencyexchange.interceptor;

import com.example.currencyexchange.helper.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CheckLoginInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo.cacheGet();
        UserInfo.cacheSetTimeOut(60);
        return super.preHandle(request, response, handler);
    }
}
