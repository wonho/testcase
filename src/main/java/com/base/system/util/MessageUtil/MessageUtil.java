package com.base.system.util.MessageUtil;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtil {

	private MessageSourceAccessor messageSourceAccessor = null;
	
//	@Resource(name="messageSource")
	public void setMessageSource(MessageSource messageSource) {
		messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}
	
	public String getMessage(String key) {
		return messageSourceAccessor.getMessage(key);
	}
	
}
