package com.base.business.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.base.business.auth.User;
import com.base.system.dao.CommonDao;

@Service
public class UserService implements UserDetailsService{
	
	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	CommonDao commonDao;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		logger.debug("{}",userName);
		
		Object userMap = commonDao.queryForObject("user.select", new HashMap());
		
		logger.debug("{}",userName);
		
		logger.debug("{}",userMap);

		List<SimpleGrantedAuthority> roleList = new ArrayList<SimpleGrantedAuthority>();
		
		ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
		
		String encodePassword = passwordEncoder.encodePassword("1234", null);
		
		roleList.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		UserDetails user = new User("melong", encodePassword, roleList);
		
		return user;
	}

}
