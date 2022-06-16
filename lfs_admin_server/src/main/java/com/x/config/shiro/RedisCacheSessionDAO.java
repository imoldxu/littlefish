package com.x.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 依托于缓存处理的DAO，感觉没有必要，缓存也是从redis里面读，和直接从redis读没区别，除非这个是写入数据库的
 * @author 老徐
 *
 */
public class RedisCacheSessionDAO extends EnterpriseCacheSessionDAO {

	private static final String REDIS_PREFIX = "shiro:session:";
	
	private RedisTemplate<String, Object> redisTemplate;
	
	public RedisCacheSessionDAO(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	public void doUpdate(Session session) throws UnknownSessionException {
		super.doUpdate(session);
		saveSession(session);
	}

	@Override
	public void doDelete(Session session) {
		if (session == null) {
            throw new NullPointerException("session argument cannot be null.");
        }
        Serializable id = session.getId();
        if (id != null) {
        	redisTemplate.delete(REDIS_PREFIX + session.getId().toString());
        }
        super.doDelete(session);
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = super.doCreate(session);
        saveSession(session);
        return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session = super.doReadSession(sessionId);
		
		if(session == null) {
			BoundValueOperations<String, Object> sessionValueOperations = redisTemplate.boundValueOps("shiro_session_" + sessionId.toString());
			session = (Session) sessionValueOperations.get();
		}
        return session;
	}

	private void saveSession(Session session) {
		BoundValueOperations<String, Object> sessionValueOperations = redisTemplate.boundValueOps(REDIS_PREFIX + session.getId().toString());
        sessionValueOperations.set(session);
        sessionValueOperations.expire(30, TimeUnit.MINUTES);
	}

}