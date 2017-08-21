package com.keyou.fdcda.dao;

import com.keyou.fdcda.model.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User getUserById(Long id);
}