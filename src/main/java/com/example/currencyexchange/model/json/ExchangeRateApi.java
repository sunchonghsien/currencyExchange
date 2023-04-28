package com.example.currencyexchange.model.json;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.aspectj.apache.bcel.classfile.annotation.NameValuePair;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ExchangeRateApi {
    private String provider;
    private String warningUpgradeToV6;
    private String terms;
    private String base;
    private Date date;
    private Integer timeLastUpdated;
    private List<NameValuePair> rates;
}
