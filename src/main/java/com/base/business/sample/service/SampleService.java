package com.base.business.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.system.dao.CommonDao;

@Service
public class SampleService {

	@Autowired
	CommonDao dao;
	
	public List<?> getUser() {
		
		List<?> userList = dao.queryForList("user.select2",  null);
		
		return userList;
		
	}


}
