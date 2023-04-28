package com.example.currencyexchange.model.req;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class RegisterVo {
    @Email(message = "信箱格式不正確")
    private String mail;
    private String phoneNumber;
    private String mailCode;

    @NotBlank(message = "密碼必填")
    @Length(min = 6, message = "密碼長度必須大於5")
    private String password;
    @NotBlank(message = "密碼必填")
    @Length(min = 6, message = "密碼長度必須大於5")
    private String confirmPassword;

}
