package com.x.lfs.config.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.x.commons.mongo.pageHelper.MongoPageHelper;

@Configuration
public class MongoDBConfig {

	//配置mongodb的分页查询器
	@Bean
	public MongoPageHelper mongoPageHelper(MongoTemplate template) {
		return new MongoPageHelper(template);
	}
	
//  配置MappingMongoConverter，在mongodb数据保存时不设置_class字段，保存字段可以帮助快速映射
//	@Bean
//  public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
//      DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
//      MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
//      try {
//          mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
//      } catch (NoSuchBeanDefinitionException ignore) {
//      }
//
//      // Don't save _class to mongo
//      mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
//
//      return mappingConverter;
//    }
	
	//配置mongodb的事务管理，其他地方只需要设置@Transaction即可开启事务
	@Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory factory){
        return new MongoTransactionManager(factory);
    }
	
}
