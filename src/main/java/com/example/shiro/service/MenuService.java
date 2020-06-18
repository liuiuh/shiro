package com.example.shiro.service;

import java.util.List;

/**
 * @Description: TODO
 * @Author: LIU
 * @Date: 2020/6/17 13:17
 */
public interface MenuService {

    List<String> listPerms(String username);
}
