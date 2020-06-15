package com.example.shiro.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 用户DO
 * @Author: Liu
 * @Date: 2020/6/15 09:17
 */
@Getter
@Setter
@ToString
public class UserDO implements Serializable {

    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
}
