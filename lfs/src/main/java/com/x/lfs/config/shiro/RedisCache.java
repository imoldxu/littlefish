package com.x.lfs.config.shiro;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.x.lfs.constant.RedisConstant;


/**
 * shiro的redis缓存
 * @author 老徐
 *
 * @param <K>
 * @param <V>
 */
public class RedisCache<K, V> implements Cache<K, V> {

	private RedisTemplate<String, V> redisTemplate;
	private String cacheKey;
	private static final String prefix="shiro";
	
	public RedisCache(RedisTemplate<String, V> redisTemplate, String name) {
		this.redisTemplate = redisTemplate;
		this.cacheKey = prefix+RedisConstant.DIVIDER+name;
	}
	
    @Override
    public V get(K key) throws CacheException {
    	BoundHashOperations<String,K,V> hash = redisTemplate.boundHashOps(cacheKey);
        //Object k = getFieldKey(key);
        V value = hash.get(key);
        return value;
    }

    @Override
    public V put(K key, V value) throws CacheException {
    	BoundHashOperations<String,K,V> hash = redisTemplate.boundHashOps(cacheKey);
        //Object k = getFieldKey(key);
        //fixme，此处强转是可能会出错的哦
        hash.put(key, value);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
    	BoundHashOperations<String,K,V> hash = redisTemplate.boundHashOps(cacheKey);
        //Object k = getFieldKey(key);
        V value=hash.get(key);
        hash.delete(key);
        return value;
    }

    @Override
    public void clear() throws CacheException {
    	redisTemplate.delete(cacheKey);
    }

    @Override
    public int size() {
    	BoundHashOperations<String,K,V> hash = redisTemplate.boundHashOps(cacheKey);
        return hash.size().intValue();
    }

    @Override
    public Set<K> keys() {
    	BoundHashOperations<String,K,V> hash = redisTemplate.boundHashOps(cacheKey);
        return hash.keys();
    }

    @Override
    public Collection<V> values() {
    	BoundHashOperations<String,K,V> hash = redisTemplate.boundHashOps(cacheKey);
        return hash.values();
    }

//    private Object getFieldKey(K key) {
//    	//此处很重要,如果key是登录凭证,那么这是访问用户的授权缓存;将登录凭证转为user对象,
//    	//返回user的name属性做为hash key,否则会以user对象做为hash key,这样就不好清除指定用户的缓存了
//        if(key instanceof PrincipalCollection) {
//            PrincipalCollection pc=(PrincipalCollection) key;
//            User user =(User)pc.getPrimaryPrincipal();
//            return user.getId();
//        }else if (key instanceof User) {
//        	User user =(User) key;
//        	return user.getId();
//		}
//        return key;
//    }
}
