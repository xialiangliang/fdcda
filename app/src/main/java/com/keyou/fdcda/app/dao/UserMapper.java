package com.keyou.fdcda.app.dao;


import com.keyou.fdcda.api.model.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User getUserById(Long id);
}