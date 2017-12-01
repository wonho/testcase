package com.base.system.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggerAOP {

    Logger logger = LoggerFactory.getLogger(LoggerAOP.class);

	@Before("com.base.system.aop.SystemArchitecture.businessService()")
	public void beforeAdvice(JoinPoint thisJoinPoint) {
		logger.debug("{}","beforeAdvice");
		logger.debug("join point  : {}",thisJoinPoint.getSignature().getDeclaringType().getName());
	}
	
}
