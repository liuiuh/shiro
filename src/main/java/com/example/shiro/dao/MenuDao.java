package com.example.shiro.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description: MenuDao
 * @Author: Liu
 * @Date: 2020/6/17 13:17
 */
@Mapper
public interface MenuDao {

    List<String> listPerms(String username);
}
