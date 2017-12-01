package com.base.system.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;


@Configuration
public class DatabaseConfig {
	
	@Profile("default")
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				   .setType(EmbeddedDatabaseType.HSQL)
				   .addScript("classpath:jdbc/schema.sql")
				   .addScript("classpath:jdbc/data.sql")
				   .build();
	}

//	@Profile({"default","dev"})
//	@Bean(name="dataSource")
//	public DataSource dataSourceBbcp(@Value("${db.DriverClassName}") String driverClassName,
//			                         @Value("${db.Url}") String url,
//			                         @Value("${db.UserName}") String userName,
//			                         @Value("${db.Password}") String password) {
//		
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName(driverClassName);
//		dataSource.setUrl(url);
//		dataSource.setUsername(userName);
//		dataSource.setPassword(password);
//		dataSource.setMaxActive(3);
////		dataSource.setValidationQuery("SELECT * FROM DUAL");
//		
//		return dataSource;
//	}
	
//	@Profile({"test","real"})
//	@Bean(name="dataSource")
//	public JndiObjectFactoryBean dataSourceJndi(@Value("${db.jndi}") String jndi) {
//		JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
//		factoryBean.setJndiName(jndi);
//		factoryBean.setResourceRef(true);
//		return factoryBean;
//	}	
	
	@Profile({"test","oper"})
	@Bean(name="dataSource")
	public DataSource dataSourceJndi(@Value("${db.jndi}") String jndi) {
		JndiDataSourceLookup lookup = new JndiDataSourceLookup();
		lookup.setResourceRef(true);
		return lookup.getDataSource(jndi);
	}	
	
}
