package com.example.currencyexchange.helper;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassIteratorBuild {

    public void iteratorBuild(Object target) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.convertValue(target, Map.class);
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object value = map.get(field.getName());
            if (value != null) {
                field.setAccessible(true);
                field.set(this, value);
            }
        }
    }
}
