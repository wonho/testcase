package com.base.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import com.base.system.aop.EevetLogging;
import com.base.system.eventlog.BatchWriter;
import com.base.system.eventlog.DbLogWriter;
import com.base.system.eventlog.EmptyLogWriter;
import com.base.system.eventlog.LogSession;
import com.base.system.eventlog.ModumLogService;
import com.base.system.util.MessageUtil.MessageUtil;

@Configuration
@ComponentScan(basePackages={"com.base.business"}, 
               excludeFilters=@ComponentScan.Filter(type=FilterType.ANNOTATION,
               value=Controller.class))
@PropertySource("classpath:properties/${spring.profiles.active:dev}.app.properties")
@EnableAspectJAutoProxy
public class CommonConfig {
	
	@Autowired
    Environment env;
	
//	@Bean
//	public LoggerAOP loggerAOP() {
//		return new LoggerAOP();
//	}
	
	@Bean
	public EevetLogging eventLogging() {

		EevetLogging eventlog = new EevetLogging();
		eventlog.setDbLogService(logService());
		eventlog.setUserSession(logSession());
		
		return eventlog;
	}
	
	@Bean
	@Scope(value="request",proxyMode=ScopedProxyMode.TARGET_CLASS)
	public LogSession logSession() {
		LogSession logSession = new LogSession();
		return logSession;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	 
	@Bean
	public MessageSource messageSource() {		
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//		messageSource.setBasename("classpath:messages/messages");
		
		messageSource.setBasename("WEB-INF/messages/messages");
		messageSource.setDefaultEncoding("UTF-8");
		
		messageSource.setUseCodeAsDefaultMessage(true);
		// default : true -> message_ko_KR.properties 와 message.prorperties 
//		messageSource.setFallbackToSystemLocale(false);
//		messageSource.setCacheSeconds(30);
		
		return messageSource;
	}
	
	@Bean
	public MessageUtil messageUtil() {
		MessageUtil messageUtil = new MessageUtil();
		messageUtil.setMessageSource(messageSource());		
		return messageUtil;
	}
	
	@Bean
	public DbLogWriter dbLogWriter() {
		DbLogWriter dbLogWriter = new DbLogWriter();
		return dbLogWriter;
	}
	
	@Bean
	public BatchWriter<LogSession> emptyLogWriter() {
		BatchWriter<LogSession> logWriter = new EmptyLogWriter();
		return logWriter;
	}
	
	@Bean
	public ModumLogService<LogSession> logService() {
		ModumLogService<LogSession> logService = new ModumLogService<LogSession>();
		logService.setBatchWriter(emptyLogWriter());
		logService.setLatency(1000);
		logService.setThresold(100);
		return logService;
	}
}
