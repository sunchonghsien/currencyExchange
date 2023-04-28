package com.example.currencyexchange.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rates_source")
public class RatesSource {
    public final static Integer EXCHANGERATEAPI = 1;
    public final static Integer BANKOFTAIWAN = 2;
    public final static String REPLACECODE = "{code}";

    @Id
    private Integer id;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "address", unique = true, nullable = false, length = 50)
    private String address;

    @Column(name = "description", unique = true, nullable = false, length = 50)
    private String description;

    @Column(name = "is_open")
    private Boolean isOpen;
}
