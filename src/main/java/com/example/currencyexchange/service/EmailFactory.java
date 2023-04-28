package com.example.currencyexchange.service;

public interface EmailFactory {
    String subject();

    String toEmail();

    String text();
}
