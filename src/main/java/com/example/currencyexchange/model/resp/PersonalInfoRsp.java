package com.example.currencyexchange.model.resp;

import com.example.currencyexchange.helper.ClassIteratorBuild;
import lombok.Builder;

@Builder
public class PersonalInfoRsp extends ClassIteratorBuild {

    public Integer satisfy;
    public Integer comments;
    public String realName;
    public String nickName;
    public String selfPhoto;
    public String selfDesc;
    public String gender;
    public String idCard;
    public String mail;
    public String phoneNumber;
    public String device;
    public String city;
}
