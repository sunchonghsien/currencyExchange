package com.example.currencyexchange.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "user_satisfaction")
public class UserSatisfaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(name = "user_id", nullable = false)
    public Long userId;
    @Column(name = "user_id_to", nullable = false)
    public Long UserIdTo;
    @Column(name = "satisfy", length = 1, nullable = false)
    public Integer satisfy;
    @Column(name = "Comment")
    public String comment;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Timestamp createTime;
}
