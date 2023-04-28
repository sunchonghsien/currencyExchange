package com.example.currencyexchange.helper;

import org.python.google.common.hash.Hashing;
import org.springframework.util.Base64Utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class EncryptionUtils {
    public static final SecretKey MD5_KEY = new SecretKeySpec("QTJsBZL".getBytes(StandardCharsets.UTF_8), "HmacMD5");
    public static final SecretKey USER_MD5_KEY = new SecretKeySpec("THAsVNU".getBytes(StandardCharsets.UTF_8), "HmacMD5");

    public static String hmacMD5(String str, SecretKey key) {
        return Hashing.hmacMd5(key).hashBytes(str.getBytes(StandardCharsets.UTF_8)).toString();
    }

    public static String hmacMD5(String str) {
        return Hashing.hmacMd5(MD5_KEY).hashBytes(str.getBytes(StandardCharsets.UTF_8)).toString();
    }

}
