package com.example.currencyexchange.model.entity;

import com.example.currencyexchange.helper.UserInfo;
import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "phone_number", columnDefinition = "INT UNSIGNED", unique = true, length = 10)
    private Integer phoneNumber;
    @Column(name = "mail", unique = true, length = 50)
    private String mail;
    /**
     * 身分證
     */
    @Column(name = "id_card", unique = true, length = 11)
    private Long idCard;
    @Column(name = "ip", columnDefinition = "INT UNSIGNED")
    private Integer ip;
    @Column(name = "real_name", length = 10)
    private String realName;
    @Column(name = "nick_name", columnDefinition = "default 沒有暱稱的浪人", length = 50)
    private String nickName;
    @Column(name = "self_photo", columnDefinition = "comment '頭像'")
    private String selfPhoto;
    @Column(name = "self_desc", columnDefinition = "TEXT")
    private String selfDesc;
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @Unsigned
    @Column(name = "gender", columnDefinition = "TINYINT", length = 1)
    private Integer gender;
    @Unsigned
    @Column(name = "age", columnDefinition = "TINYINT", length = 3)
    private Integer age;
    @Column(name = "device")
    private String device;
    @Column(name = "city", length = 50)
    private String city;
    @Column(name = "identify_photo", columnDefinition = "comment '識別照'")
    private String identifyPhoto;
    @Column(name = "id_photo", columnDefinition = "comment '證件照'")
    private String idPhoto;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Timestamp createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "login_time")
    private Timestamp loginTime;

    public void setGender(Integer gender) {
        if (gender == 0 || gender == 1) {
            this.gender = gender;
        }
    }

    public void setIdCard(String idCard) {
        this.idCard = UserInfo.idCardToLong(idCard);
    }

    public String getIdCard() {
        return UserInfo.idCardToStr(this.idCard);
    }
}
