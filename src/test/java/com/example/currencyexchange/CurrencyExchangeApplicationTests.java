package com.example.currencyexchange;

import com.example.currencyexchange.model.entity.RatesSource;
import com.example.currencyexchange.service.impl.CurrencyService;
import com.example.currencyexchange.service.impl.RatesScheduleService;
import com.example.currencyexchange.service.impl.RatesSourceService;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@SpringBootTest
class CurrencyExchangeApplicationTests {
    @Value("${python.path}")
    public String pythonPath;
    @Value("${python.ratebot}")
    public String pythonRateBot;
    @Setter(onMethod_ = {@Autowired})
    public RatesScheduleService ratesScheduleService;
    @Setter(onMethod_ = {@Autowired})
    public CurrencyService currencyService;
    @Setter(onMethod_ = {@Autowired})
    public RatesSourceService ratesSourceService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    JavaMailSender mailSender;

    @Test
    void contextLoads() {
        //請求來源是否開啟
        Optional<RatesSource> ratesSource = ratesSourceService.findByIdAndIsOpen(RatesSource.BANKOFTAIWAN);
        if (ratesSource.isPresent()) {
            String[] args1 = new String[]{pythonPath, pythonRateBot};
            Process python_;
            try {
                python_ = Runtime.getRuntime().exec(args1);
                BufferedReader in = new BufferedReader(new InputStreamReader(python_.getInputStream()));
                String line = in.readLine();
                System.out.println(line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Test
    void sendToGmail(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("abc15cd5@gmail.com");
        mailMessage.setFrom("abc11cd5@gmail.com");
        mailMessage.setSubject("測試透過 Gmail 去發信");
        mailMessage.setText("org.springframework.mail.SimpleMailMessage 透過 Gmail 發信。");
        mailSender.send(mailMessage);
    }
    @Test
    void simpleRateLimiter() {
        int f = "F102".charAt(0);
        System.out.println(f);
    }

    boolean isActionAllowed_限流(String uid, String aKey, int period, int maxCount) {
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        long nowTs = System.currentTimeMillis();

        List<Object> list = redisTemplate.executePipelined((RedisCallback<?>) connect -> {
            byte[] key = Objects.requireNonNull(stringSerializer.serialize("hist:" + uid + aKey));
            connect.multi();
            connect.zAdd(key, nowTs, Objects.requireNonNull(stringSerializer.serialize(Long.toString(nowTs))));
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
