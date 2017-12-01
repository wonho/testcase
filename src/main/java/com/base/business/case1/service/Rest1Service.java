package com.base.business.case1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.system.dao.CommonDao;

@Service
public class Rest1Service {

	@Autowired
	CommonDao dao;
	
	public List<?> getData() {
		
		List<?> userList = dao.queryForList("rest1.select",  null);
		
		return userList;
		
	}


}
