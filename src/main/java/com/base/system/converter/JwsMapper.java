package com.base.system.converter;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.system.exception.JwsMapperException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

public class JwsMapper {
	
	Logger logger = LoggerFactory.getLogger(JwsMapper.class);
	
	private String sharedKey;
	JWSSigner signer;
	JWSVerifier verifier;
	
	public static String DEFAULT_SHARED_KEY ="5d82f5a121b3bd825cda145040751f481e26a161b307aafaae46c0e4b3514e22"; 

	public JwsMapper(String sharedKey) {
		
		this.sharedKey = sharedKey;
		
		try {
			signer = new MACSigner(this.sharedKey);
		} catch (KeyLengthException e) {
			throw new JwsMapperException("서명키 생성(MACSigner)오류",e);
		}		
		
		try {
			verifier = new MACVerifier(this.sharedKey);
		} catch (JOSEException e) {
			throw new JwsMapperException("데이터 검증 키 생성 오류  ",e);			
		}
	}
	
	public String marshaller(String input){
		
		JWSHeader header;
    	JWSObject jwsObject;
		
		header = new JWSHeader(JWSAlgorithm.HS256);
		
		Payload payload = new Payload(input);
		
		jwsObject = new JWSObject(header,payload);

		try {
			jwsObject.sign(signer);
		} catch (JOSEException e) {
			throw new JwsMapperException("데이터 암호화 암호화 키  오류",e);
		}

		String serializeValue = jwsObject.serialize();
		
		logger.debug("serializeValue : {}",serializeValue);
		
		try {
			jwsObject = JWSObject.parse(serializeValue);
		} catch (ParseException e) {
			throw new JwsMapperException("데이터 암호화 오류 ",e);
		}
		
		return serializeValue;
	}

	
	public String unMarshaller(String input){
		
		JWSObject jwsObject;
		
		try {
			jwsObject = JWSObject.parse(input);
		} catch (ParseException e) {
			throw new JwsMapperException("암호화 데이터 복호화 오류 ",e);
		}
		
		boolean verify;
		
		try {
			verify = jwsObject.verify(verifier);
		} catch (JOSEException e) {
			throw new JwsMapperException("암호화 데이터 복호화 후 키 검증 오류  ",e);						
		}
		
		logger.debug("verify : {}",verify);
		
		String payload = jwsObject.getPayload().toString();
		
		logger.debug("payload : {}",payload);
		
		return payload;
	}
}
