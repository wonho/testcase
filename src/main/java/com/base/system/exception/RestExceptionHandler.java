package com.base.system.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.base.system.eventlog.LogService;
import com.base.system.eventlog.LogSession;

@ControllerAdvice
public class RestExceptionHandler {

//	Message Resource Required
//	@Autowired
//	MessageSource messageSource;
	
	@Autowired
	LogService<LogSession> logService;
	
	@Autowired
	LogSession logSession;
	
	Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	/**
	 * 파라메터 Validation 에러
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public RestResult ArgumentValidError(Exception exception) {
		
		String exceptionMsg = exception.getMessage();
		
		logger.debug("exception.getMessage() : {}",exceptionMsg);
		
		saveErrrorLog(exceptionMsg);
		
		RestResult restResult = new RestResult();
		restResult.setIsData(Boolean.FALSE);
		restResult.setRespCode("-1");
		restResult.setRespMessage(exceptionMsg);
	    return restResult;
	}
	
	/**
	 * JWS 암호화 메시지 키 및 메지시 검증 에러
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(JwsMapperException.class)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public RestResult JwsMapperExeption(Exception exception) {
		
		String exceptionMsg = exception.getMessage();
		
		logger.debug("exception.getMessage() : {}",exceptionMsg);
		
		saveErrrorLog(exceptionMsg);
		
		RestResult restResult = new RestResult();
		restResult.setIsData(Boolean.FALSE);
		restResult.setRespCode("-1");
		restResult.setRespMessage(exceptionMsg);
		return restResult;
	}
	
	/**
	 * 메시지 컨버터 변환 예외
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(HttpMessageConversionException.class)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public RestResult MessageConversionExeption(Exception exception) {
		
		String exceptionMsg = exception.getMessage();
		
		logger.debug("exception.getMessage() : {}",exceptionMsg);
		
		saveErrrorLog(exceptionMsg);
		
		RestResult restResult = new RestResult();
		restResult.setIsData(Boolean.FALSE);
		restResult.setRespCode("-1");
		restResult.setRespMessage(exceptionMsg);
		return restResult;
	}
	
	/**
	 * 파리메터 오류
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public RestResult ArgumentException(Exception exception) {
		
		String exceptionMsg = exception.getMessage();
		
		logger.debug("exception.getMessage() : {}",exceptionMsg);
		
		saveErrrorLog(exceptionMsg);
		
		RestResult restResult = new RestResult();
		restResult.setIsData(Boolean.FALSE);
		restResult.setRespCode("-1");
		restResult.setRespMessage(exceptionMsg);
		return restResult;
	}
	
	/**
	 * 파리메터 오류
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public RestResult DataAccessException(Exception exception) {
		
		String exceptionMsg = exception.getMessage();
		
		logger.debug("exception.getMessage() : {}",exceptionMsg);
		
		saveErrrorLog(exceptionMsg);
		
		RestResult restResult = new RestResult();
		restResult.setIsData(Boolean.FALSE);
		restResult.setRespCode("-1");
		restResult.setRespMessage(exceptionMsg);
		return restResult;
	}	
	/**
	 * 미 정의 에러
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public RestResult AllException(Exception exception) {

		String exceptionMsg = exception.getMessage();
		
		logger.debug("exception.getMessage() : {}",exceptionMsg);
		
		saveErrrorLog(exceptionMsg);
		
		RestResult restResult = new RestResult();
		restResult.setIsData(Boolean.FALSE);
		restResult.setRespCode("-1");
		restResult.setRespMessage(exceptionMsg);

		return restResult;
	}
	
	private void saveErrrorLog(String message) {
		
		LogSession saveSession = new LogSession();
		
		BeanUtils.copyProperties(logSession, saveSession);
		
		saveSession.setResult("fail");
		saveSession.setParameter(message);
		
		logService.sendEvent(saveSession);
		
		
	}
	
}
