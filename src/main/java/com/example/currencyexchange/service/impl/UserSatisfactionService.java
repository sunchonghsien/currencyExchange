package com.example.currencyexchange.service.impl;

import com.example.currencyexchange.dao.jdbc.UserSatisfactionJdbc;
import com.example.currencyexchange.helper.UserInfo;
import com.example.currencyexchange.model.entity.User;
import com.example.currencyexchange.model.entity.UserSatisfaction;
import com.example.currencyexchange.model.resp.CommentsRsp;
import com.example.currencyexchange.model.resp.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserSatisfactionService {
    @Autowired
    public UserSatisfactionJdbc satisfactionJdbc;

    public Map<String, Object> countAndSatisfy(Long userId) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> countAndSatisfyToMap = satisfactionJdbc.countAndSatisfyToMap(userId);
        BigDecimal count = new BigDecimal(countAndSatisfyToMap.get("count").toString());
        BigDecimal unIdentity = new BigDecimal(countAndSatisfyToMap.get("unIdentity").toString());
        BigDecimal percentage = BigDecimal.ZERO;
        if (count.compareTo(BigDecimal.ZERO) > 0)
            percentage = unIdentity.divide(count, 0, RoundingMode.DOWN).multiply(BigDecimal.valueOf(100)).subtract(BigDecimal.valueOf(100)).abs();

        res.put("satisfy", percentage.intValue());
        res.put("commentNum", countAndSatisfyToMap.get("commentNum"));
        return res;
    }

    public Page comments(Integer satisfy, Integer page) {
        User user = UserInfo.cacheGet();
        Page res = satisfactionJdbc.findByUserIdToAndSatisfyAndComment(user.getId(), satisfy, page);
        res.list.stream().peek((e)-> ((CommentsRsp)e).realName = UserInfo.hiddenFormat(((CommentsRsp)e).realName));
        return res;
    }
}
