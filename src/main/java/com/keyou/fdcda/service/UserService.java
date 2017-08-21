package com.keyou.fdcda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.keyou.fdcda.dao.UserMapper;
import com.keyou.fdcda.model.User;

import java.util.Date;

/**
 * Created by Wataru on 2017-07-08.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

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

    public User getUserById(Integer id) {
        return userMapper.getUserById(new Long(id));
    }
}
