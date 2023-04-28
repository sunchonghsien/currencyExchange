package com.example.currencyexchange.model.resp;

import lombok.Builder;

import java.util.List;

@Builder
public class Page {
    public Integer total;
    public Integer limit;
    public Integer offset;
    public Integer totalPage;
    public List<?> list;
}
