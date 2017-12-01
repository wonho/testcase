package com.base.system.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.context.ConfigurableWebApplicationContext;

@Configuration
@Import(value= {CommonConfig.class, DatabaseConfig.class, InfrastructureConfig.class, WebSecurityConfig.class})
public class MainConfig {

	@Autowired
	private Environment env;
	
	@Autowired
	ConfigurableWebApplicationContext context;
	
    @PostConstruct
    public void initApp() {
        System.out.println("Looking for Spring profiles...");
        if (env.getActiveProfiles().length == 0) {
        	System.out.println("No Spring profile configured, running with default configuration.");
        } else {
            for (String profile : env.getActiveProfiles()) {
            	System.out.println("Detected Spring profile: {}" + profile);
            }
        }
    }
}
