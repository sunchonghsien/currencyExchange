package com.example.currencyexchange.model.resp;

import jakarta.persistence.*;
import lombok.Builder;

import java.sql.Timestamp;

@Builder
public class CommentsRsp {
    public String realName;
    public String nickName;
    public String selfPhoto;
    public Integer satisfy;
    public String comment;
    public Timestamp createTime;
}
