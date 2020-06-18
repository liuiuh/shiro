package com.example.shiro.service;

/**
 * @Description: TODO
 * @Author: Liu
 * @Date: 2020/6/15 11:15
 */
public interface RoleService {

    /**
     * 通过用户名获取角色标识
     * @param username
     * @return
     */
    String getSignByUsername(String username);
}
