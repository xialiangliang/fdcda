package com.keyou.fdcda.app.dao;


import com.keyou.fdcda.api.model.User;
import com.keyou.fdcda.app.dao.base.BaseMapper;

public interface UserMapper extends BaseMapper<User, Long> {
    User getUserById(Long id);
}