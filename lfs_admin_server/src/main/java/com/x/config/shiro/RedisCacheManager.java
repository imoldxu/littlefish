package com.x.config.shiro;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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

	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();
	
	private RedisTemplate redisTemplate;
	
	public RedisCacheManager(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		Cache<K, V> cache = caches.get(name);

		if (cache == null) {
		  cache= new RedisCache<K, V>(redisTemplate, name);
		  caches.put(name, cache);
		}
		return cache;
	}
	
}
