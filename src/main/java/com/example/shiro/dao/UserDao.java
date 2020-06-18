package com.example.shiro.dao;

import com.example.shiro.model.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: TODO
 * @Author: Liu
 * @Date: 2020/6/15 09:56
 */
@Mapper
public interface UserDao {
    UserDO get(Long userId);
    UserDO queryUserByUserName(String username);
}
