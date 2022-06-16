package com.x.config.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;


/**
 * 配置mybatis
 * @author 老徐
 *
 */
@Configuration
@MapperScan(basePackages="com.x.dao") //使用通用Mapper的MapperScan代替Mybatis自带的,指明basepackage，避免每个类都加上@Mapper，多数据源需要指明这类是与哪个sqlSessionFactory关联
public class MybatisConfig {	
//	1、Autodetect an existing DataSource
//	自动发现一个存在的DataSource，多个源需要明确的指明
//	2、Will create and register an instance of a SqlSessionFactory passing that DataSource as an input using the SqlSessionFactoryBean
//	利用SqlSessionFactoryBean创建并注册SqlSessionFactory
//	3、Will create and register an instance of a SqlSessionTemplate got out of the SqlSessionFactory
//	创建并注册SqlSessionTemplate
//	4、Auto-scan your mappers, link them to the SqlSessionTemplate and register them to Spring context so they can be injected into your beans
//	自动扫描Mappers，并注册到Spring上下文环境方便程序的注入使用
	
//	侦测到beans实现以下的Mybatis接口
//	Interceptor
//	TypeHandler
//	LanguageDriver
//	DatabaseIdProvider
//	@Bean
//	MyInterceptor myInterceptor() {
//		return MyInterceptor();
//	}

	
	//mybatis-plug的配置
	@Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        pageInterceptor.setOptimizeJoin(true);//优化部分leftJoin
        //pageInterceptor.setMaxLimit(500L);//设置最大返回限制,默认是-1不限制
        //pageInterceptor.setOverflow(false);//设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        interceptor.addInnerInterceptor(pageInterceptor);
        return interceptor;
    }
	
//  多数据源需要自己配置sqlSessionFactory	
//	/**
//     * 根据数据源创建SqlSessionFactory
//     */
//    @Bean(name="coreSqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("coreDS") DataSource ds) throws Exception{
//        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
//        fb.setDataSource(ds);//指定数据源(这个必须有，否则报错)
//        //设置配置
//        org.apache.ibatis.session.Configuration configuration =  new org.apache.ibatis.session.Configuration();
//        configuration.setDefaultExecutorType(ExecutorType.REUSE);//开启重用prepare Statement
//		fb.setConfiguration(configuration );
//        //FIXME:下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
//        fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));//指定基包
//        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));//指定xml文件位置
//        //fb.setPlugins(plugins); 设置插件
//        return fb.getObject();
//    }
//    
//    /**创建事务管理器*/
//	@Bean("coreTransactionManger")
//	@Primary
//	public DataSourceTransactionManager firstTransactionManger(@Qualifier("coreDS") DataSource dataSource){
//		return new DataSourceTransactionManager(dataSource);
//	}
}
