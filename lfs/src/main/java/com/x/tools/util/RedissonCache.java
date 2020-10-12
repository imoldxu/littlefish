package com.x.tools.util;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

public class RedissonCache {
    
	private RedissonClient redissonClient;

	public RedissonCache(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}
	    
    public void remove(final String key) {
    	RBucket<Object> ret = this.redissonClient.getBucket(key);
    	ret.delete();
    }
    
    public Object get(final String key) {
    	RBucket<Object> ret = this.redissonClient.getBucket(key);
    	return ret.get();
    }
    
    public boolean set(String key, Object value){
    	RBucket<Object> ret = this.redissonClient.getBucket(key);
    	ret.set(value);
    	return true;
    }
    
    public boolean exists(final String key) {
    	RBucket<Object> ret = this.redissonClient.getBucket(key);
    	return ret.isExists();
    }
    
    public boolean set(final String key, Object value, Long expireTime) {
    	RBucket<Object> ret = this.redissonClient.getBucket(key);
    	ret.set(value, expireTime, TimeUnit.MILLISECONDS);
    	return true;
    }
}
