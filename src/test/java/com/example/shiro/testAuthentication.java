package com.example.shiro;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @Description:  测试shiro的身份认证
 * @Author:  Liu
 * @Date: 2020/6/15 13:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShiroApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class testAuthentication {

    /**
     * 创建SecurityManager
     * @return
     */
    private DefaultSecurityManager getSecurityManager() {
        DefaultSecurityManager securityManager=new DefaultSecurityManager();
        //设置Authenticator
        ModularRealmAuthenticator authenticator=new ModularRealmAuthenticator();
        //认证策略选择至少一个认证通过
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);
        //设置Authorizer
        ModularRealmAuthorizer authorizer=new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);
        //设置Realm
        //设置数据源-采用mysql
        DruidDataSource dataSource=new DruidDataSource();
        String url="jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        dataSource.setUrl(url);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("liu12345");
        JdbcRealm jdbcRealm=new JdbcRealm();
        //重写认证信息时根据用户名查询用户信息的sql语句
        jdbcRealm.setAuthenticationQuery("select password from t_user where username = ?");
        //重写授权管理时查询角色的sql语句
        jdbcRealm.setUserRolesQuery("select `role_sign` from t_role t1 left join t_user t2 on t1.user_id=t2.id  where t2.username=?");
        jdbcRealm.setDataSource(dataSource);
        securityManager.setRealms(Arrays.asList((Realm) jdbcRealm));
        //将securityManager设置到SecurityUtils，供全局使用
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    /**
     * 模拟用户登录
     */
    private void login(String username,String password,String role){
        //构造SecurityManager
        SecurityManager securityManager=getSecurityManager();
        Subject subject=SecurityUtils.getSubject();
        //构造用户认证信息
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        subject.login(token);
        /**
         * 如果当前主体有role角色，继续
         * 否则抛出org.apache.shiro.authz.UnauthorizedException: Subject does not have role [admin]/role[user]
         */
        if(subject.hasRole("admin")){
            //doSomething
            System.out.println("这是一个管理员");
        }
        //通过subject.getSession()可以获取Session信息
        System.out.println("sessionId:"+subject.getSession().getId());
    }

    /**
     * org.apache.shiro.realm.AuthenticatingRealm#assertCredentialsMatch(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.authc.AuthenticationInfo)
     * 构建securityManager、设置Authenticator、Authorizer，
     * AuthenticatingRealm 的getAuthenticationInfo（token）获取AuthenticationInfo信息
     * 调用assertCredentialsMatch(token,info)信息进行比较
     */
    @Test
    public void testLogin(){
       try{
           String username="abc";
           String password="abc";
           String role="admin";
           login(username,password,role);
           Subject subject=SecurityUtils.getSubject();
           subject.logout();
       }catch (AuthenticationException | NullPointerException  e){
           e.printStackTrace();
       }
    }




}
