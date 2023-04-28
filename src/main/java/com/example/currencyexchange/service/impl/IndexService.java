package com.example.currencyexchange.service.impl;

import com.example.currencyexchange.helper.*;
import com.example.currencyexchange.model.entity.User;
import com.example.currencyexchange.model.req.RegisterVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

@Service
public class IndexService {

    @Setter(onMethod_ = {@Autowired})
    UserService userService;

    public String login(HttpServletRequest request, User user) throws IOException {
        Timestamp loginTime = user.getLoginTime();
        long time = DateUtils.getTimestampToMillis();
        String authorization = UserInfo.cacheSet(time, user);
        Timestamp timestamp = DateUtils.timestamp(time);

        String requestIP = IpUtils.getRequestIP(request);
        Map<String, Object> ipInfoMap = IpUtils.getIpInfo(requestIP);
        user.setIp(IpUtils.ipToInt(requestIP));
        user.setCity(!Objects.isNull(ipInfoMap.get("city")) ? ipInfoMap.get("city").toString() : null);
        user.setDevice(request.getHeader("user-agent"));
        user.setLoginTime(timestamp);
        userService.save(user);

        if (loginTime != null)
            UserInfo.cacheDel(loginTime.getTime(), user.getId());

        return authorization;
    }

    public void register(RegisterVo register) {
        User user = new User();
        user.setMail(register.getMail());
        user.setPhoneNumber(Integer.getInteger(register.getPhoneNumber()));
        user.setPassword(EncryptionUtils.hmacMD5(register.getPassword()));
        userService.save(user);
    }
}
