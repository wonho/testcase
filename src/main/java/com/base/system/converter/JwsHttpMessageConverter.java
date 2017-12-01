package com.base.system.converter;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JwsHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
	
	Logger logger = LoggerFactory.getLogger(JwsHttpMessageConverter.class);

	private JwsMapper jwsMapper;
	
	public void setJwsMapper(JwsMapper jwsMapper) {
		this.jwsMapper = jwsMapper;
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}
	public JwsHttpMessageConverter() {
//		super(new MediaType("application","jws"));
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		InputStream body = inputMessage.getBody();
		
		String input = IOUtils.toString(body,"utf-8");
		
		logger.debug("jws input value : {}",input);
		
		String unMarshaller = jwsMapper.unMarshaller(input);
		
		logger.debug("jws unMarshaller value : {}",unMarshaller);

		ObjectMapper mapper = new ObjectMapper();
		
		Object readValue = mapper.readValue(unMarshaller, clazz);
		
		return readValue;		
	}

	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String input = mapper.writeValueAsString(t);
		
		String marshaller = jwsMapper.marshaller(input);
		
		logger.debug("jws unMarshaller value : {}",t);
		
		outputMessage.getBody().write(marshaller.getBytes());
	}

}
