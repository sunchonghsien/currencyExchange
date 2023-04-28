package com.example.currencyexchange.dao.repository;

import com.example.currencyexchange.model.entity.RatesSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatesScheduleRepository extends JpaRepository<RatesSchedule,Integer> {
    Optional<RatesSchedule> findByCurCodeAndRatesSourceId(String curCode,Integer ratesSourceId);
}
