package com.example.currencyexchange.helper;

import com.example.currencyexchange.service.EmailFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendMail {

    @Autowired
    JavaMailSender mailSender;

    EmailFactory emailFactory;


    public SendMail setEmailFactory(EmailFactory emailFactory) {
        this.emailFactory = emailFactory;
        return this;
    }

    @Async("taskExecutor")
    public void send() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailFactory.toEmail());
        mailMessage.setSubject(emailFactory.subject());
        mailMessage.setText(emailFactory.text());
        mailSender.send(mailMessage);
    }
}
