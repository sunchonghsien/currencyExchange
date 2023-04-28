package com.example.currencyexchange.service.impl;

import com.example.currencyexchange.dao.repository.CurrencyRepository;
import com.example.currencyexchange.helper.Constant;
import com.example.currencyexchange.model.entity.Currency;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {
    @Setter(onMethod_ = {@Autowired})
    public CurrencyRepository currencyRepository;

    public List<Currency> findAllByIsOpen() {
        return currencyRepository.findAllByIsOpen(Constant.OPEN).orElse(new ArrayList<>());
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }
}
