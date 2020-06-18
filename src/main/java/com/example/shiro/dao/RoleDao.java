package com.example.shiro.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: TODO
 * @Author: Liu
 * @Date: 2020/6/15 11:12
 */
@Mapper
public interface RoleDao {
    /**
     * 通过用户名获取角色标识
     * @param username 用户名
     * @return
     */
    String getSignByUsername(String username);
}
