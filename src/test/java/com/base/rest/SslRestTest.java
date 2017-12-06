package com.base.rest;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class SslRestTest {

	Logger logger = LoggerFactory.getLogger(SslRestTest.class);
	
	@Test
	public void SslRestTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		 SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).useTLS().build();
	     SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, new AllowAllHostnameVerifier());
	     BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	     
	     credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("m11elong", "1234"));
	          
	     HttpClient httpClient = HttpClientBuilder.create()
	                                                .setSSLSocketFactory(connectionFactory)
	                                                .setDefaultCredentialsProvider(credentialsProvider)
	                                                .build();
	          
	     ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
	     
	     RestTemplate restTemplate = new RestTemplate(requestFactory);
	     
	     Map forObject = restTemplate.getForObject("http://localhost:8080/restful/sample/getdata", Map.class);
	     
	     logger.debug("{}",forObject);
	}
}
