package com.example.currencyexchange.dao.repository;

import com.example.currencyexchange.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Long countByMail(String mail);

    Optional<User> findByMailAndPassword(String mail, String password);

    Optional<User> findByPhoneNumberAndPassword(String number, String password);
}
