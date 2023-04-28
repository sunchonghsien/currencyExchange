package com.example.currencyexchange.dao.repository;

import com.example.currencyexchange.model.entity.UserSatisfaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSatisfactionRepository extends JpaRepository<UserSatisfaction, Long> {
}
