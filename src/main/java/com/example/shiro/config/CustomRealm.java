package com.example.shiro.config;

import com.example.shiro.model.UserDO;
import com.example.shiro.service.MenuService;
import com.example.shiro.service.RoleService;
import com.example.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: Liu
 * @Date: 2020/6/15 09:20
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    /**
     * 身份信息认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken authenticationToken=(UsernamePasswordToken)token;
        //在token中获取密码
        String password= new String((char[])authenticationToken.getCredentials());
        String username=authenticationToken.getUsername();
        //根据用户名在数据库中获取用户信息
        UserDO userDO=userService.queryUserByUserName(username);
        if(userDO == null){
            throw new AccountException("用户名或密码不正确");
        }
        if(userDO.getPassword()==null){
            throw new AccountException("用户名或密码不正确");
        }
        if(!password.equals(userDO.getPassword())){
            throw new AccountException("用户名或密码不正确");
        }
        return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(),password,getName());
    }

    /**
     * 权限认证
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName= (String)SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //根据用户名获取用户角色
        String roleSign=roleService.getSignByUsername(userName);
        //根据用户名获取用户的权限集合
        List<String> perms=menuService.listPerms(userName);
        Set<String> set=new HashSet<>();
        Set<String> setPerm=new HashSet<>();
        set.add(roleSign);
        setPerm.addAll(perms);
        //认证信息添加角色信息
        info.setRoles(set);
        //认证信息添加权限信息
        info.setStringPermissions(setPerm);
        return info;
    }


}
