package com.example.currencyexchange.model.json;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BankOfTaiwan {

    private String curCode;
    private Float cashBuy;
    private Float cashSell;
    private Float spotBuy;
    private Float spotSell;
}
