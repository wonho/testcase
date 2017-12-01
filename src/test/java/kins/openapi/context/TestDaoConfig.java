package kins.openapi.context;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.base.system.dao.CommonDao;
import com.base.system.dao.CommonDaoImpl;

@Configuration
@PropertySource(value="classpath:app.properties")
@EnableTransactionManagement
public class TestDaoConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();
		return config;
	}

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
	
	private String driverClassName="oracle.jdbc.driver.OracleDriver";
	private String url="jdbc:oracle:thin:@203.230.33.185:1521:kinsdint";
	private String userName="kins_csr";
	private String password="kins_csr";
	
//	private @Value("${dev.db.DriverClassName}") String driverClassName;
//	private @Value("${dev.db.Url}") String url;
//	private @Value("${dev.db.UserName}") String userName;
//	private @Value("${dev.db.Password}") String password;
//	
	
	
	@Bean(name="dataSourceJdbc")
	public DataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		
		return dataSource;
	}
	
	@Bean(name="dataSource")
	public DataSource dataSourceDbcp() {

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setMaxActive(3);
		dataSource.setValidationQuery("SELECT * FROM DUAL");
		
		return dataSource;
	}

	
	@Autowired
	private ApplicationContext applicationContext;
	
//	@Bean	
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:jdbc/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:**/service/sqlmap-*.xml"));
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		return sqlSessionFactoryBean.getObject();
	}

	
}
