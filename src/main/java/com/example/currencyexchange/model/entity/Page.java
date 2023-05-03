package com.example.currencyexchange.model.entity;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public class Page {
    public Integer total;
    public Integer limit;
    public Integer offset;
    public Integer totalPage;
    public List<Map<String, Object>> list;
}
