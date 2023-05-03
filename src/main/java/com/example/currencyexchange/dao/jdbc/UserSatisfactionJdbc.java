package com.example.currencyexchange.dao.jdbc;

import com.example.currencyexchange.model.entity.Page;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserSatisfactionJdbc extends BaseJdbc {

    public Map<String, Object> countAndSatisfyToMap(Long userId) {
        StringBuilder sql = new StringBuilder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        sql.append("SELECT ");
        sql.append("COUNT(*) AS count,COALESCE(SUM(IF(satisfy=1,1,0)),0) AS unIdentity,COALESCE(SUM(IF(comment IS NULL,1,0)),0) AS commentNum ");
        sql.append("FROM user_satisfaction WHERE user_id = :userId LIMIT 1");
        params.addValue("userId", userId);
        return jdbcTemplate.queryForMap(sql.toString(), params);
    }

    public Page findByUserIdToAndSatisfyAndComment(Long userId, Integer satisfy, Integer page) {
        StringBuilder sql = new StringBuilder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        sql.append("SELECT ");
        sql.append("u.real_name,u.nick_name,u.self_photo,us.satisfy,us.comment,us.create_time ");
        sql.append("FROM user_satisfaction us INNER JOIN user u ON us.user_id_to=u.id WHERE us.user_id =:userId ");
        if (satisfy != null) {
            sql.append("AND us.satisfy=:satisfy ");
        }
        sql.append("ORDER BY us.create_time DESC,us.comment");
        params.addValue("userId", userId);
        params.addValue("satisfy", satisfy);
        return super.queryPage(sql, params, page);
    }
}
