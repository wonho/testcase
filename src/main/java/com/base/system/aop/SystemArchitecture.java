package com.base.system.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemArchitecture {
	@Pointcut("execution(* com.base.business..*Service.*(..)) && !@annotation(org.springframework.beans.factory.annotation.Autowired)")
	public void businessService() {
	}
}
