package com.example.currencyexchange.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cur_code", unique = true, length = 10)
    private String curCode;
    @Column(name = "cur_desc", length = 10)
    private String curDesc;
    @Column(name = "is_open")
    private Boolean isOpen;
}
