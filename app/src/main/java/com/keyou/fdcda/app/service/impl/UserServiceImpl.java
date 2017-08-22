package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.app.dao.UserMapper;
import com.keyou.fdcda.api.model.User;
import com.keyou.fdcda.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Wataru on 2017-07-08.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User test() {
//        User user = new User();
//        user.setId(2L);
//        user.setName("zzq2");
//        user.setCreatedAt(new Date());
//        user.setPassword("1111");
//        user.setUpdatedAt(new Date());
//        user.setCredit(10L);
//        userMapper.insert(user);
        User user = userMapper.getUserById(1L);
        return user;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(new Long(id));
    }
}
