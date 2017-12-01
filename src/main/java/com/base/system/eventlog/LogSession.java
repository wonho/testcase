package com.base.system.eventlog;

import com.google.common.base.MoreObjects;

public class LogSession {
	private String contentType;
	private String localAddr;
	private String requestURI;
	private String userAgent;
	private String contextPath;
	private String remoteAddr;
	private String serviceName;
	private String result;
	private String parameter;
	
	private long sttime=0;
	private long edtime=0;
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getLocalAddr() {
		return localAddr;
	}
	
	public void setLocalAddr(String localAddr) {
		this.localAddr = localAddr;
	}
	
	public String getRequestURI() {
		return requestURI;
	}
	
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	
	public String getUserAgent() {
		return userAgent;
	}
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getContextPath() {
		return contextPath;
	}
	
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public void start() {
		this.sttime = System.currentTimeMillis();
	}

	public void end() {
		this.edtime = System.currentTimeMillis();
	}

	public long getElapsedTime() {
		return edtime - sttime;
	}
	
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			   .add("contentType",contentType)
			   .add("localAddr",localAddr)
			   .add("requestURI",requestURI)
			   .add("userAgent",userAgent)
			   .add("contextPath",contextPath)
			   .add("remoteAddr",remoteAddr)
			   .add("serviceName",serviceName)
			   .add("result",result)
			   .add("parameter",parameter)
			   .toString();
	}
}
