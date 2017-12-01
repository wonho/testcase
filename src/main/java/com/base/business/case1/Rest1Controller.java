package com.base.business.case1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.business.case1.model.Greeting;
import com.base.business.case1.model.TestData;
import com.base.business.case1.service.Rest1Service;
import com.google.common.collect.Maps;

@RestController
public class Rest1Controller {

	Logger logger = LoggerFactory.getLogger(Rest1Controller.class);
	
	@Autowired
	Rest1Service service;
	
	@RequestMapping(value="/rest1/getdata1",method=RequestMethod.GET)
	public Map<String,Object> getData1(@RequestParam Map<String,Object> search) {
		
		logger.debug("{}",search);
		List<?> data = service.getData();
		
		Map<String,Object> resultMap = Maps.newHashMap();
		
		resultMap.put("packet", data);
		
		return resultMap;		
	}	
	
	@RequestMapping(value="/rest1/getdata2",method=RequestMethod.GET)
	public HttpEntity<List> getData2(@RequestParam Map<String,Object> search) {
		
		logger.debug("{}",search);
		
		List<?> data = service.getData();
		
		return new ResponseEntity<List>(data, HttpStatus.OK);		
	}	
	
	@RequestMapping(value="/rest1/getdata3",method=RequestMethod.GET)
	public HttpEntity<TestData> getHateaos(@RequestParam String name) {
		
		logger.debug("{}",name);
		
//		linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel()
		
		TestData data = new TestData("header1",name,"tail1");
		
		data.add(linkTo(methodOn(Rest1Controller.class).getHateaos(name)).withSelfRel());
		
		return new ResponseEntity<TestData>(data, HttpStatus.OK);		
	}	
	
	   private static final String TEMPLATE = "Hello, %s!";

	    @RequestMapping("/greeting")
	    public HttpEntity<Greeting> greeting(
	            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

	        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
	        greeting.add(linkTo(methodOn(Rest1Controller.class).greeting(name)).withSelfRel());

	        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	    }	
}
