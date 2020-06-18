package com.example.shiro.config;

import com.example.shiro.utils.RedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: SessionDao
 * @Author: Liu
 * @Date: 2020/6/15 09:21
 */
public class ShiroSessionDao extends AbstractSessionDAO {

    @Override
    protected Serializable doCreate(Session session) {
        //构造一个新的SessionId
        Serializable sessionId=this.generateSessionId(session);
        //对Session进行SessionId赋值
        assignSessionId(session,sessionId);
        //使用Redis储存Session信息，key为SessionId,value为Session
        //RedisUtil对于key使用StringRedisSerializer()，value使用自定义的字符数组序列化
        RedisUtil.set((String) sessionId,session);
        //创建完后返回一个SessionId
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return (Session) RedisUtil.get((String) sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if(session==null || session.getId()==null){
            return;
        }
        //在Redis中更新Session信息
        RedisUtil.set((String) session.getId(), session);
    }

    @Override
    public void delete(Session session) {
        if(session==null){
            return;
        }
        //在redis中根据key值即SessionId删除Session
        RedisUtil.del((String) session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }
}
