package com.example.shiro.service.impl;

import com.example.shiro.dao.RoleDao;
import com.example.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author:  Liu
 * @Date: 2020/6/15 11:16
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Override
    public String getSignByUsername(String username) {
        return roleDao.getSignByUsername(username);
    }
}
