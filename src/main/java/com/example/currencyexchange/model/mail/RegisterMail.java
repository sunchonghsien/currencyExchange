package com.example.currencyexchange.model.mail;

import com.example.currencyexchange.helper.EncryptionUtils;
import com.example.currencyexchange.helper.RedisUtils;
import com.example.currencyexchange.service.EmailFactory;
import lombok.Data;
import org.python.google.common.hash.Hashing;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Data
public class RegisterMail implements EmailFactory {
    String mail;

    public RegisterMail(String mail) {
        this.mail = mail;
    }

    public String getMailCode() {
        Object code = RedisUtils.getValue(EncryptionUtils.hmacMD5(mail));
        return code == null ? "" : code.toString();
    }

    @Override
    public String subject() {
        return "XXX驗證通知";
    }

    @Override
    public String toEmail() {
        return this.mail;
    }

    @Override
    public String text() {
        Random random = new Random();
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            num.append(random.nextInt(10));
        }
        RedisUtils.setValue(EncryptionUtils.hmacMD5(mail), num.toString(), 2);
        return "您的驗證碼為:" + num;
    }
}
