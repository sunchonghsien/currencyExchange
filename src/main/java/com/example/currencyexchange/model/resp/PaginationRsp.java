package com.example.currencyexchange.model.resp;

import lombok.Data;

@Data
public class PaginationRsp {
    public Integer offset;
    public Integer totalPage;
    public Integer total;
    public Integer limit;
}
