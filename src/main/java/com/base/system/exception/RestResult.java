package com.base.system.exception;

public class RestResult {
	
	String respCode;
	String respMessage;
	Boolean isData;
	/**
	 * respCode attribute를 리턴한다.
	 * @return respCode String
	 */
	public String getRespCode() {
		return respCode;
	}
	/**
	 * respCode attribute 값을 설정한다.
	 * @param respCode String
	 */
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	/**
	 * respMessage attribute를 리턴한다.
	 * @return respMessage String
	 */
	public String getRespMessage() {
		return respMessage;
	}
	/**
	 * respMessage attribute 값을 설정한다.
	 * @param respMessage String
	 */
	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}
	/**
	 * isData attribute를 리턴한다.
	 * @return isData Boolean
	 */
	public Boolean getIsData() {
		return isData;
	}
	/**
	 * isData attribute 값을 설정한다.
	 * @param isData Boolean
	 */
	public void setIsData(Boolean isData) {
		this.isData = isData;
	}
}
