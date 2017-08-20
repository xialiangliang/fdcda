package com.keyou.fdcda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.keyou.fdcda.dao.UserDao;
import com.keyou.fdcda.model.User;

/**
 * Created by Wataru on 2017-07-08.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public String test() {
        return "test" + userDao.getUserById(1).getName();
    }

    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }
}
