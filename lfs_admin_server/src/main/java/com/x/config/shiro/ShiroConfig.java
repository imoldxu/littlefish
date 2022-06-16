package com.x.config.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 配置shiro实现权限管理
 * @author 老徐
 *
 */
@Configuration
@ConditionalOnProperty(name = "shiro.web.enabled", matchIfMissing = true)
public class ShiroConfig {
	
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
	    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
	    
//	    logged in users with the 'admin' role
//	    chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");
//	    logged in users with the 'document:read' permission
//	    chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");
//      chainDefinition.addPathDefinition("/css/**", "anon");
//      chainDefinition.addPathDefinition("/js/**", "anon");
//      chainDefinition.addPathDefinition("/img/**", "anon");
//      chainDefinition.addPathDefinition("/user/session", "anon");
//      chainDefinition.addPathDefinition("/user", "anon");
//      chainDefinition.addPathDefinition("/error/**", "anon");
//      chainDefinition.addPathDefinition("/customer/**", "anon");//客户接口不被shiro管理
//      chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
//      chainDefinition.addPathDefinition("/swagger-ui.html#/**", "anon");
//      chainDefinition.addPathDefinition("/swagger-resources", "anon");
//      chainDefinition.addPathDefinition("/swagger-resources/**", "anon");
//      chainDefinition.addPathDefinition("/v2/api-docs", "anon");
//      chainDefinition.addPathDefinition("/webjars/springfox-swagger-ui/**", "anon");
//      chainDefinition.addPathDefinition("/user/session", "logout");
	    

	  // all other paths require a logged in user
      //chainDefinition.addPathDefinition("/**", "authc");
	    
	    //所有路径采用注解方式认证
	    chainDefinition.addPathDefinition("/**", "anon");
	    return chainDefinition;
	}
	
	@Bean(name="shiroRedisTemplate")
    public RedisTemplate<String, Object> shiroRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //不配置序列化会采用默认的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        //避免自定义的序列化，避免shiro的simpleSession反序列化的出错
        template.setValueSerializer(new ShiroRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new ShiroRedisSerializer());
        return template;
    }
	
	//配置redis来替代默认的cacheManager
	@Bean
	public CacheManager shiroRedisCacheManager(@Qualifier("shiroRedisTemplate")RedisTemplate<?, ?> redisTemplate) {
		CacheManager cacheManager = new RedisCacheManager(redisTemplate);
		return cacheManager;
	}
	
	@Bean
	public SessionDAO sessionDAO(@Qualifier("shiroRedisTemplate") RedisTemplate<String, Object> shiroRedisTemplate) {
		return new RedisSessionDAO(shiroRedisTemplate);
	}
	
	//必须要配置Realm,realm会注入到scurityManager
	@Bean
	public Realm myUserRealm(CacheManager shiroRedisCacheManager) {
		MyStaffRealm myShiroRealm = new MyStaffRealm();
		//myShiroRealm.setCacheManager(new MemoryConstrainedCacheManager());
		myShiroRealm.setCacheManager(shiroRedisCacheManager);
		return myShiroRealm;
	}
	
	//在controller层使用注解时必须显示声明以下方式，因为@controller的AOP必须是以基于类的cglib方式代理，否则导致@controller会扫描不到
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}
	
}
