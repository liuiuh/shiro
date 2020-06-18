package com.example.shiro.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * @Description:  解决Shiro重复读取redis
 * @Author: Liu
 * @Date: 2020/6/15 09:21
 */
public class ShiroSessionManager extends DefaultWebSessionManager {
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        //在SessionKey中获取SessionId
        Serializable sessionId = getSessionId(sessionKey);
        //创建一个ServletRequest
        ServletRequest request=null;
        //判断sessionKey是否为WebSessionKey的实例。是，则获取ServletRequest
        if(sessionKey instanceof WebSessionKey){
            request=((WebSessionKey) sessionKey).getServletRequest();
        }
        //定义一个Session对象
        Session session;
        //如果request不为空，并且在Sessionkey中获取的sessionId不为空，则在request请求中根据sessionId获取Session信息返回
        if(request!=null && sessionId !=null){
            session= (Session) request.getAttribute(sessionId.toString());
            if(session != null){
                return session;
            }
        }
        //否则，在redis中获取Session信息，request不为空且sessionId不为空时，把session信息存储在request中以便使用
        session=super.retrieveSession(sessionKey);
        if(request != null && sessionId != null){
            request.setAttribute(sessionId.toString(),session);
        }
        return  session;
    }
}
