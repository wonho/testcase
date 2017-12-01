package com.base.business.sample;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class TestController {

	Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	RestTemplate template;
	
	@Value("${openapi.naver.book.url}") 
	String bookUrl;
	
	
	@RequestMapping(value="/openapi/sample/book1",method=RequestMethod.GET)
	public String getBook(@RequestParam Map<String,Object> search) {
		
		String bookUrl="https://openapi.naver.com/v1/search/book_adv.json";
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
		
		multiValueMap.add("X-Naver-Client-Id", "9IM7eXb1zhzGpILiOlim");
		multiValueMap.add("X-Naver-Client-Secret", "kor3VQsqJr");
		multiValueMap.add("Content-Type",MediaType.APPLICATION_JSON_VALUE);
		
		search.put("d_titl",  "bigdata");
		search.put("display", "5");
		
		for (Map.Entry<String, Object> entry : search.entrySet()) {
			multiValueMap.add(entry.getKey(), (String) entry.getValue());
		}
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(bookUrl);
//		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url+"?d_titl=big data&display=5");
		
		String queryString = builder.queryParams(multiValueMap).build().toString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("user-agent", "kins");
		
		HttpEntity<String> entity = new HttpEntity<>(multiValueMap);

		ResponseEntity<String> exchange = template.exchange(queryString, HttpMethod.GET, entity, String.class);
		
		String book = exchange.getBody();
		
		return book;
	}
	
	@RequestMapping(value="/openapi/sample/getdataVO",method=RequestMethod.GET)
	public List<Book> getBookVO(@RequestParam Map<String,Object> search) {
		
		String bookUrl="https://openapi.naver.com/v1/search/book_adv.json";
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
		
		multiValueMap.add("X-Naver-Client-Id", "9IM7eXb1zhzGpILiOlim");
		multiValueMap.add("X-Naver-Client-Secret", "kor3VQsqJr");
		multiValueMap.add("Content-Type",MediaType.APPLICATION_JSON_VALUE);
		
		search.put("d_titl",  "bigdata");
		search.put("display", "5");
		
		
		for (Map.Entry<String, Object> entry : search.entrySet()) {
			multiValueMap.add(entry.getKey(), (String) entry.getValue());
		}
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(bookUrl);
//		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url+"?d_titl=big data&display=5");
		
		String queryString = builder.queryParams(multiValueMap).build().toString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("user-agent", "kins");
		
		HttpEntity<String> entity = new HttpEntity<>(multiValueMap);
		
		ResponseEntity<List> exchange = template.exchange(queryString, HttpMethod.GET, entity, List.class);
		
		List books = exchange.getBody();
		
		return books;
	}
}
