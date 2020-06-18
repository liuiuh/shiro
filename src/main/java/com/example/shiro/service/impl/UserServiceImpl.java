package com.example.shiro.service.impl;

import com.example.shiro.dao.UserDao;
import com.example.shiro.model.UserDO;
import com.example.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author:  Liu
 * @Date: 2020/6/15 10:15
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDO getById(Long id) {
        return userDao.get(id);
    }

    @Override
    public UserDO queryUserByUserName(String username) {
        return userDao.queryUserByUserName(username);
    }
}
