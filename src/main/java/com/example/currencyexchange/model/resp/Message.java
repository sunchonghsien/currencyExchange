package com.example.currencyexchange.model.resp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message<T> {
    private Boolean code;
    private String msg;
    private T data;
}
