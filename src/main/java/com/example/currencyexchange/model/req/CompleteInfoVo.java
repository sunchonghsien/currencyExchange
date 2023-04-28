package com.example.currencyexchange.model.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompleteInfoVo {
    @NotBlank(message = "請輸入真實姓名")
    private String realName;
    private String nickName;
    /**
     * 自介
     */
    private String selfDesc;
    private Integer gender;
    @Max(value = 150,message = "年齡不正常")
    private Integer age;
    @Pattern(regexp = "^[A-Z]{1}[0-9]{9}$", message = "身分證格式錯誤")
    private String idCard;
}
