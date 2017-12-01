package com.base.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.base.business.auth.UserService;

//@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("1111").roles("USER").and()
				.withUser("admin").password("2222").roles("USER", "ADMIN");
	}

	/**
	 * 직접 userDetailService를 호출하지 않고 AuthenticationProvider를 연결 가능(DaoAuthenticationProvider)
	 *  authProvider.setUserDetailsService,authProvider.setPasswordEncoder 
	 *  ex) auth.authenticationProvider(authenticationProvider)
	 */
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();
//		
////		auth.userDetailsService(userDetailsServiceBean());
////		auth.userDetailsService(new UserService());
//		auth.userDetailsService(new UserService()).passwordEncoder(passwordEncoder);
//	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);		
	}	
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return new UserService();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .authorizeRequests()
		    .antMatchers("/resource/**").permitAll()
		    .antMatchers("/**").hasRole("USER")
		    .anyRequest().authenticated()
			.and()
		    .formLogin()
		        .loginPage("/auth/login.do")
		        .defaultSuccessUrl("/auth/main.do")
		        .loginProcessingUrl("/auth/loginProcess.do")
		        .failureUrl("/auth/login.do")
		        .permitAll()
		        .and()
		    .logout()
		        .and()
		    .csrf().disable()
		    .httpBasic();
		
//		http.requiresChannel().antMatchers("/auth/login.do","/auth/loginProcess.do","/auth/logout.do").requiresSecure();
//        http.portMapper().http(8080).mapsTo(8443);
//        http.sessionManagement().sessionFixation().none();
	}
}
