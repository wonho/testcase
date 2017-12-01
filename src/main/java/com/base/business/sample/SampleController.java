package com.base.business.sample;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.business.sample.service.SampleService;
import com.base.system.util.MessageUtil.MessageUtil;

@RestController
public class SampleController {

	Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	@Autowired
	MessageUtil messageUtilService;
	
	@Autowired
	SampleService service;
	
	@RequestMapping(value="/sample/getdata",method=RequestMethod.GET)
	public Map<String,Object> getData(@RequestParam Map<String,Object> search) {
		
		logger.debug("{}",search);

		Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
		
		Map<String,Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("data1", "value1");
		dataMap.put("data2", "value2");
		dataMap.put("data4", "value4");
		dataMap.put("data5", "value5");
		
		resultMap.put("resCode", "Y");
		resultMap.put("resMessage", "하하");
		resultMap.put("isData", "호호");
		resultMap.put("data",dataMap);
		
		return resultMap;		
	}
	
	@RequestMapping(value="/sample/putdata", method=RequestMethod.POST)
	public Map<String,Object> putData(@RequestBody Map<String,Object> paramMap) {
		
		logger.debug("{}",paramMap);
		
		Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
		
		Map<String,Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("data1", "value1");
		dataMap.put("data2", "value2");
		dataMap.put("data3", "value3");
		dataMap.put("data4", "value4");
		dataMap.put("data5", "value5");
		
		resultMap.put("resCode", "Y");
		resultMap.put("resMessage", "Y");
		resultMap.put("isData", "Y");
		resultMap.put("data",dataMap);
		
		return resultMap;
	}	
	
	/**
	 * 스케줄링 테스트
	 * @throws Exception
	 */
    //@Scheduled(fixedRate=3000) : 3초마다 실행
	//또는 @Scheduled(cron="*/30 * * * * *") : 3초마다 실행 : crontab 형식 참고
	@RequestMapping(value="/sample/schedule")
//	@Scheduled(fixedRate=3000)
	public void executeSchedule()throws Exception{
		logger.debug("{}","scheduleing...........................................");
	}	
}
