package com.example.currencyexchange.dao.repository;

import com.example.currencyexchange.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<List<Currency>> findAllByIsOpen(boolean isOpen);
}
