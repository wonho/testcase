package com.base.system.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.base.system.dao.CommonDao;
import com.base.system.dao.CommonDaoImpl;

@Configuration
@EnableTransactionManagement
public class InfrastructureConfig {
		
	@Bean
	public PlatformTransactionManager transactionManager() {
	  return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean
	public CommonDao commonDao() throws Exception {
		CommonDaoImpl commonDao = new CommonDaoImpl();
		commonDao.setSqlSessionTemplate(sqlSessionTemplate());
		return commonDao;
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
		return sqlSessionTemplate;
	}

	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private ApplicationContext applicationContext;
	
//	@Bean(destroyMethod="clearCache")
//	@Bean	
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:jdbc/mybatis-config.xml"));
//		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:**/service/sqlmap-*.xml"));
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/base/business/**/service/sqlmap-*.xml"));
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		return sqlSessionFactoryBean.getObject();
	}

	
}
