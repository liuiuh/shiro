package com.example.shiro.service.impl;

import com.example.shiro.dao.MenuDao;
import com.example.shiro.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @Author:  Liu
 * @Date: 2020/6/17 13:18
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<String> listPerms(String username) {
        return menuDao.listPerms(username);
    }
}
