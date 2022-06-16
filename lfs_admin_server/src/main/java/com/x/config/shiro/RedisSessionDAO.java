package com.x.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisSessionDAO extends AbstractSessionDAO {

	private static final String REDIS_PREFIX = "shiro:session:";
	private static final int DURATION_MINUTES = 30;
	
	private RedisTemplate<String, Object> redisTemplate;
	
	public RedisSessionDAO(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		saveSession(session);
	}

	@Override
	public void delete(Session session) {
		if (session == null) {
            throw new NullPointerException("session argument cannot be null.");
        }
        Serializable id = session.getId();
        if (id != null) {
        	redisTemplate.delete(REDIS_PREFIX + session.getId().toString());
        }
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		BoundValueOperations<String, Object> sessionValueOperations = redisTemplate.boundValueOps("shiro_session_" + sessionId.toString());
		Session session = (Session) sessionValueOperations.get();
		return session;
	}

	private void saveSession(Session session) {
		BoundValueOperations<String, Object> sessionValueOperations = redisTemplate.boundValueOps(REDIS_PREFIX + session.getId().toString());
        sessionValueOperations.set(session, Duration.ofMinutes(DURATION_MINUTES));
 	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<String> keys = redisTemplate.keys(REDIS_PREFIX+"*");
		List<Object> redisSessions = redisTemplate.opsForValue().multiGet(keys);
		Set<Session> sessions = redisSessions.stream().map(rs->(Session)rs).collect(Collectors.toSet());
		return sessions;
	}

}