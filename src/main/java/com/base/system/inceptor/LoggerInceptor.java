package com.base.system.inceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.base.system.eventlog.LogSession;

public class LoggerInceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(LoggerInceptor.class);
	
	private LogSession logSession;

	public void setUserSession(LogSession logSession) {
		this.logSession = logSession;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		logSession.setContentType(request.getContentType());
		logSession.setLocalAddr(request.getLocalAddr());
		logSession.setRequestURI(request.getRequestURI());
		logSession.setUserAgent(request.getHeader("user-agent"));
		logSession.setContextPath(request.getContextPath());
		logSession.setRemoteAddr(request.getRemoteAddr());
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.debug("postHandle : {}","");
		super.postHandle(request, response, handler, modelAndView);
	}
}
