package com.example.currencyexchange.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;


@Data
@Entity
@Table(name = "rates_schedule")
public class RatesSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cur_code", nullable = false, length = 10)
    private String curCode;
    @Column(name = "rates_source_id", nullable = false)
    private Integer ratesSourceId;
    @Column(name = "data", columnDefinition = "json", nullable = false)
    private String data;
    @Column(name = "create_time", columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private Timestamp createTime;
    @Column(name = "update_time", columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = false)
    private Timestamp updateTime;

}
