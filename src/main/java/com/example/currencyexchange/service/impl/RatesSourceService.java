package com.example.currencyexchange.service.impl;

import com.example.currencyexchange.dao.repository.RatesSourceRepository;
import com.example.currencyexchange.helper.Constant;
import com.example.currencyexchange.model.entity.RatesSource;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatesSourceService {

    @Setter(onMethod_ = {@Autowired})
    public RatesSourceRepository ratesSourceRepository;

    public List<RatesSource> findAllByIsOpen() {
        return ratesSourceRepository.findAllByIsOpen(Constant.OPEN).orElse(new ArrayList<>());
    }

    public Optional<RatesSource> findByIdAndIsOpen(Integer id) {
        return ratesSourceRepository.findByIdAndIsOpen(id, Constant.OPEN);
    }
}
