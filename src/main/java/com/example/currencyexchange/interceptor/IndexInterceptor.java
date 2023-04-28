package com.example.currencyexchange.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.example.currencyexchange.helper.Constant;
import com.example.currencyexchange.helper.ResponseUtils;
import com.example.currencyexchange.model.resp.Message;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class IndexInterceptor extends BaseInterceptor {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        if (!isActionAllowed(request.getRemoteAddr(), "All", 1, 2)) {
            ResponseUtils.writeError(response, "頻繁操作");
            flag = false;
        }
        return flag;
    }

    boolean isActionAllowed(String uid, String aKey, int period, int maxCount) {
        RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
        long nowTs = System.currentTimeMillis();
        List<Object> list = redisTemplate.executePipelined((RedisCallback<?>) connect -> {
            byte[] key = Objects.requireNonNull(redisSerializer.serialize("hist:" + uid + aKey));
            connect.multi();
            connect.zAdd(key, nowTs, Objects.requireNonNull(redisSerializer.serialize(Long.toString(nowTs))));
            connect.zRemRangeByScore(key, 0, nowTs - period * 1000L);
            connect.zCard(key);
            connect.expire(key, period + 1);
            connect.exec();
            connect.close();
            return null;
        });
        List<Object> asList = (List<Object>) list.get(0);
        return Long.parseLong(asList.get(2).toString()) <= maxCount;
    }
}
