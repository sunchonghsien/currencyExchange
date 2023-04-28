package com.example.currencyexchange.dao.repository;


import com.example.currencyexchange.model.entity.RatesSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatesSourceRepository extends JpaRepository<RatesSource, Integer> {
    Optional<List<RatesSource>> findAllByIsOpen(boolean isOpen);

    Optional<RatesSource> findByIdAndIsOpen(Integer id, boolean isOpen);
}
