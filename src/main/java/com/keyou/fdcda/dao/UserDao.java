package com.keyou.fdcda.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import com.keyou.fdcda.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Wataru on 2017-07-08.
 */
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_GET_USER_BY_id = " select * from user where id = ? ";

    public User getUserById(Integer id) {
        final User user = new User();
        jdbcTemplate.query(SQL_GET_USER_BY_id, new Object[]{id}, (resultSet) -> {
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setCredit(resultSet.getInt("credit"));
            user.setCreatedAt(resultSet.getTimestamp("created_at"));
            user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        });
        return user;
    }
}
