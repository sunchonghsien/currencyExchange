package com.example.currencyexchange.model.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class LoginVo implements Serializable {
    public final static int EMAIL_TYPE = 0;
    public final static int PHONE_TYPE = 1;
    @Email(message = "信箱格式不正確")
    private String mail;
    private String phoneNumber;
    @NotBlank(message = "必填")
    private String password;
    private int type;
}
