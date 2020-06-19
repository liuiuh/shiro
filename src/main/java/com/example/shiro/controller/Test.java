package com.example.shiro.controller;

import com.example.shiro.model.UserDO;
import com.example.shiro.service.RoleService;
import com.example.shiro.service.UserService;
import com.example.shiro.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 测试Controller
 * @Author: Liu
 * @Date: 2020/6/15 10:06
 */
@Controller
public class Test {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 根据用户id获取用户信息
     * @return
     */
    @GetMapping("/getById")
    @ResponseBody
    public UserDO get(){
        UserDO userDO = userService.getById(1L);
        return userDO;
    }

    /**
     * 获取用户角色标识
     * @param username
     * @return
     */
    @PostMapping("getSign")
    @ResponseBody
    public String getSign(String username){
        String sign=roleService.getSignByUsername(username);
        return sign;
    }

    /**
     * to登录页面
     * @return
     */
    @RequestMapping("login")
    public String login(){
        return "login";
    }

    /**
     * 登录请求
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    Result Login(String username, String password) {
        try {
            //构造用户身份验证的token
            UsernamePasswordToken token=new UsernamePasswordToken(username,password);
            //获取当前用户主体
            Subject subject= SecurityUtils.getSubject();
            subject.login(token);
            //资源标识符：操作：资源实例标识符
            subject.isPermitted("sys:menu:add");
            //判断当前用户的角色
            if(subject.hasRole("admin")){
                System.out.println("这是一个管理员");
            }else {
                System.out.println("这是一个普通用户");
            }
            return Result.ok();
        }catch (NullPointerException | AccountException e){
            return Result.error(e.getMessage());
        }
    }

    /**
     *登录成功跳转到主页面
     * @return
     */
    @GetMapping({ "/main" })
    String index() {
        return "main";
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    String logout() {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return "redirect:/login";
    }

}
