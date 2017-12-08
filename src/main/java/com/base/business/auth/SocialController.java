package com.base.business.auth;

import javax.servlet.http.HttpSession;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

@Controller
public class SocialController {

	Logger logger = LoggerFactory.getLogger(SocialController.class);
	
	@Value("${facebook.app.id}")
	String FACEBOOK_CLIENT_ID;
	@Value("${facebook.app.secret}")
	String FACEBOOK_CLIENT_SECRET_KEY;
	@Value("${facebook.redirecturl}")
	String REDIRECT_URL;
	
	@RequestMapping(value = "/facebookSignin.do")
	public String getfacebookSigninCode(HttpSession session) {
		logger.debug("facebookSignin");

		String facebookUrl = "https://www.facebook.com/v2.8/dialog/oauth?"+
						"client_id="+FACEBOOK_CLIENT_ID+
						"&redirect_uri="+REDIRECT_URL+
						"&scope=public_profile";

		return "redirect:"+facebookUrl;
		
//		https://www.facebook.com/v2.8/dialog/oauth?
//			client_id=1482180898560754&redirect_uri=https://localhost:8080/restful/facebookAccessToken.do&scope=public_profile

	}
	
	@RequestMapping(value = "/facebookAccessToken.do")
	public String getFacebookSignIn(String code, HttpSession session, String state) throws Exception {
		logger.debug("facebookAccessToken / code : "+code);
		
		String accessToken = requesFaceBooktAccesToken(session, code);
		facebookUserDataLoadAndSave(accessToken, session);
		
		return "redirect:main.do";
	}	
	
	private void facebookUserDataLoadAndSave(String accessToken, HttpSession session) throws Exception {
	    String facebookUrl = "https://graph.facebook.com/me?"+
	            "access_token="+accessToken+
	            "&fields=id,name,email,picture";

	    HttpClient client = HttpClientBuilder.create().build();
	    HttpGet getRequest = new HttpGet(facebookUrl);
	    String rawJsonString = client.execute(getRequest, new BasicResponseHandler());
	    logger.debug("facebookAccessToken / rawJson!  : "+rawJsonString);

	    JSONParser jsonParser = new JSONParser();
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(rawJsonString);
	    logger.debug("facebookUserDataLoadAndSave / raw json : "+jsonObject);
	}	
		
	private String requesFaceBooktAccesToken(HttpSession session, String code) throws Exception {
		String facebookUrl = "https://graph.facebook.com/v2.8/oauth/access_token?"+
						 	"client_id="+FACEBOOK_CLIENT_ID+
						 	"&redirect_uri="+REDIRECT_URL+
						 	"&client_secret="+FACEBOOK_CLIENT_SECRET_KEY+
						 	"&code="+code;
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(facebookUrl);
		String rawJsonString = client.execute(getRequest, new BasicResponseHandler());
		logger.debug("facebookAccessToken / raw json : "+rawJsonString);
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(rawJsonString);
		String faceBookAccessToken = (String) jsonObject.get("access_token");
		logger.debug("facebookAccessToken / accessToken : "+faceBookAccessToken);
		
		session.setAttribute("faceBookAccessToken", faceBookAccessToken);
		
		return faceBookAccessToken;
	}
}
