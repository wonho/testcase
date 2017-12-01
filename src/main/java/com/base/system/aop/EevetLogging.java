package com.base.system.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.base.system.eventlog.BaseVO;
import com.base.system.eventlog.LogService;
import com.base.system.eventlog.LogSession;

@Aspect
public class EevetLogging {

	Logger logger = LoggerFactory.getLogger(EevetLogging.class);

	private LogSession logSession;
	public void setUserSession(LogSession logSession) {
		this.logSession = logSession;
	}
	
	private LogService<LogSession> dbLogService;
	public void setDbLogService(LogService<LogSession> dbLogService) {
		this.dbLogService = dbLogService;
	}
	
	@After("com.base.system.aop.SystemArchitecture.businessService()")
	public void sessionLog(JoinPoint jp) {
		
		logger.debug("{}",logSession.toString());
		
			String serviceParam = "";
			
			for(Object args :jp.getArgs()) {
				if (args instanceof BaseVO) {
		 		    serviceParam = args.toString();
					break;
				}
			}
		
		logger.debug("service parameter : {}",serviceParam);
		
		String serviceName = jp.getSignature().getDeclaringTypeName();
		logSession.setServiceName(serviceName);
		logSession.setParameter(serviceParam);
		logSession.setResult("sucess");
		
		LogSession saveSession = new LogSession();
		BeanUtils.copyProperties(logSession, saveSession);

		dbLogService.sendEvent(saveSession);
	
		}		
}
