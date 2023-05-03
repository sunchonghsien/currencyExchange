package com.example.currencyexchange.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import org.python.bouncycastle.util.Strings;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Map;

public class ClassIteratorConvert {

    public static Object dbInClassBuild(Class<?> clazz, Map<?, ?> target) throws Exception {
        Object o = clazz.getConstructor().newInstance();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object value = target.get(strAppend_(field.getName()));
            if (value != null) {
                field.setAccessible(true);
                String typeName = value.getClass().getSimpleName();
                String name = field.getType().getSimpleName();
                if (typeName.equals(name)) {
                    field.set(o, value);
                } else if (typeName.equals("Timestamp")) {
                    field.set(o, DateUtils.timestampToY_M_D_H_M_S(((Timestamp)value).getTime()));
                }
            }
        }
        return o;
    }

    public static String strAppend_(String s) {
        StringBuilder sb = new StringBuilder();
        if (s == null || s.trim().isBlank()) {
            return "";
        }
        if (s.length() <= 1) {
            return s;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
