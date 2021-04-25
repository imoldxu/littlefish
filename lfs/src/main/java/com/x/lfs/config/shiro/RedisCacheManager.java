package com.x.lfs.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 实现用redis来缓存shiro
 * @author 老徐
 *
 */
public class RedisCacheManager implements CacheManager {

	private RedisTemplate redisTemplate;
	
	public RedisCacheManager(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new RedisCache<K, V>(redisTemplate, name);
	}
	
}
