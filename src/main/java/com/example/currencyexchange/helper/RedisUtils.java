package com.example.currencyexchange.helper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    private static RedisTemplate redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    public static void setValue(String key, Object value, long minutes) {
        redisTemplate.boundValueOps(key).setIfAbsent(value, minutes, TimeUnit.MINUTES);
    }

    public static void setValue(String key, Object value) {
        redisTemplate.boundValueOps(key).setIfAbsent(value);
    }

    public static void setKeyTimeOutOfMinutes(String key, long minutes) {
        redisTemplate.expire(key,Duration.ofMinutes(minutes));
    }

    public static boolean delValue(String key) {
        return redisTemplate.delete(key);
    }

    public static Object getValue(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    public static boolean existsValue(String key) {
        return redisTemplate.boundValueOps(key).size() > 0;
    }
}
