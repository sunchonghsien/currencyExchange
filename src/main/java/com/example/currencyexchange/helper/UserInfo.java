package com.example.currencyexchange.helper;

import com.example.currencyexchange.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserInfo {

    public static String cacheSet(long time, User user) {
        String authorization = EncryptionUtils.hmacMD5(buildCacheKey(time, user.getId()));
        RedisUtils.setValue(authorization, user, 60);
        return authorization;
    }

    public static void cacheDel(long loginTime, long id) {
        String beforeKey = EncryptionUtils.hmacMD5(buildCacheKey(loginTime, id));
        if (RedisUtils.existsValue(beforeKey))
            RedisUtils.delValue(beforeKey);
    }

    private static String buildCacheKey(Object date, Object key) {
        return "user:" + date + ":" + key;
    }

    public static User cacheGet() {
        User user = (User) RedisUtils.getValue(getAuthorization());
        if (user != null)
            return user;

        throw new RuntimeException("請登入帳號");
    }

    public static String userId() {
        return cacheGet().getId().toString();
    }

    public static void cacheUpdate(User user) {
        String authorization = getAuthorization();
        if (authorization != null)
            RedisUtils.setValue(authorization, user);

    }

    public static void cacheSetTimeOut(long minutes) {
        RedisUtils.setKeyTimeOutOfMinutes(getAuthorization(), minutes);
    }

    private static String getAuthorization() {
        String authorization = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            authorization = request.getHeader("Authorization");
        }
        return authorization;
    }

    public static Long idCardToLong(String idCard) {
        if (idCard == null || idCard.length() == 0)
            return null;

        int pointOf = idCard.toUpperCase().charAt(0);
        String toStr = pointOf + idCard.substring(1);
        return Long.parseLong(toStr);
    }

    public static String idCardToStr(Long idCard) {
        if (idCard == null)
            return null;

        char id = (char) Integer.parseInt(idCard.toString().substring(0, 2));
        return id + idCard.toString().substring(2);
    }

    public static String hiddenFormat(Object target) {
        if (target == null)
            return null;

        String toStr = target.toString();
        int display = (int) Math.round(toStr.length() * .20);
        if (toStr.length() <= 2)
            return toStr.replace(toStr.substring(1), "*");

        String substring = toStr.substring(display, toStr.length() - display);
        String repeat = "*".repeat(substring.length());
        return toStr.replace(substring, repeat);
    }
}
