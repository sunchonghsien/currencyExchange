package com.example.currencyexchange.helper;

import org.python.google.common.hash.Hashing;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class EncryptionUtils {
    public static final SecretKey MD5_KEY = new SecretKeySpec("QTJsBZL".getBytes(StandardCharsets.UTF_8), "HmacMD5");
    public static final SecretKey USER_MD5_KEY = new SecretKeySpec("THAsVNU".getBytes(StandardCharsets.UTF_8), "HmacMD5");
    final static Base64.Decoder decoder = Base64.getDecoder();
    final static Base64.Encoder encoder = Base64.getEncoder();


    public static String hmacMD5(String str, SecretKey key) {
        return Hashing.hmacMd5(key).hashBytes(str.getBytes(StandardCharsets.UTF_8)).toString();
    }

    public static String hmacMD5(String str) {
        return Hashing.hmacMd5(MD5_KEY).hashBytes(str.getBytes(StandardCharsets.UTF_8)).toString();
    }

    public static String base64Encoder(String value) {
        return encoder.encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    public static String base64Decoder(String value) {
        return new String(decoder.decode(value.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public static String random(int length) {
        int left = 48;
        int right = 122;
        Random random = new Random();
        return random.ints(left, right + 1).filter(i -> (i <= 57 || i > 65) && (i <= 90 || i >= 97)).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();


    }
}
