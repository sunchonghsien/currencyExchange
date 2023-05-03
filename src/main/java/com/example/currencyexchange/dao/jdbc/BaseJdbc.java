package com.example.currencyexchange.dao.jdbc;

import com.example.currencyexchange.helper.Constant;
import com.example.currencyexchange.model.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class BaseJdbc {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public Page queryPage(StringBuilder sql, MapSqlParameterSource parameter, Integer page){
        Integer total = jdbcTemplate.queryForObject("SELECT count(*) FROM(" + sql + ") _TEMP", parameter, Integer.class);
        String query = sql.append(" LIMIT ").append(Constant.PAGE_LIMIT20).append(" OFFSET ")
                .append(Constant.PAGE_LIMIT20 * (page - 1)).toString();
        Integer totalPage = (total + Constant.PAGE_LIMIT20 - 1) / Constant.PAGE_LIMIT20;
        return Page.builder()
                .offset(page)
                .limit(Constant.PAGE_LIMIT20)
                .totalPage(totalPage)
                .list(jdbcTemplate.queryForList(query, parameter))
                .total(total)
                .build();
    }
}
