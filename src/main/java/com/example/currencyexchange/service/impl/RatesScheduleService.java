package com.example.currencyexchange.service.impl;

import com.example.currencyexchange.dao.repository.RatesScheduleRepository;
import com.example.currencyexchange.model.entity.RatesSchedule;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatesScheduleService {

    @Setter(onMethod_ = {@Autowired})
    private RatesScheduleRepository ratesScheduleRepository;

    public void save(RatesSchedule ratesSchedule) {
        ratesScheduleRepository.save(ratesSchedule);
    }

    public RatesSchedule findByCurCodeAndRatesSourceId(String curCode,Integer ratesSourceId) {
        return ratesScheduleRepository.findByCurCodeAndRatesSourceId(curCode,ratesSourceId).orElse(new RatesSchedule());
    }
}
